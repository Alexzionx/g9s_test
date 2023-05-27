package com.gridnine.testing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Filter implements Filters {

    @Override
    public List<Flight> departureOnDate(List<Flight> flights) {
        LocalDate day = convertStringToDate();
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            for (Segment s : f.getSegments()) {
                if (s.getDepartureDate().toLocalDate().equals(day)) {
                    result.add(f);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<Flight> departureAfterDate(List<Flight> flights) {
        LocalDate day = convertStringToDate();
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            for (Segment s : f.getSegments()) {
                if (s.getDepartureDate().toLocalDate().isAfter(day)) {
                    result.add(f);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<Flight> sooner(List<Flight> flights) {
        for (int i = 0; i < flights.size() - 1; i++) {
            Flight a = flights.get(i);
            Flight b = flights.get(i + 1);
            if (a.getSegments().get(0).getDepartureDate().isAfter(b.getSegments().get(0).getDepartureDate())) {
                flights.set(i, b);
                flights.set(i + 1, a);
                i = 0;
            }
        }
        return flights;
    }

    @Override
    public List<Flight> faster(List<Flight> flights) {
        for (int i = 0; i < flights.size() - 1; i++) {
            int a = (int) (ChronoUnit.SECONDS.between(flights.get(i).getSegments().get(0).getDepartureDate(),
                    flights.get(i).getSegments().get(flights.get(i).getSegments().size() - 1).getArrivalDate()));
            int b = (int) (ChronoUnit.SECONDS.between(flights.get(i + 1).getSegments().get(0).getDepartureDate(),
                    flights.get(i + 1).getSegments().get(flights.get(i + 1).getSegments().size() - 1).getArrivalDate()));

            Flight fa = flights.get(i);
            Flight fb = flights.get(i + 1);
            if (a > b) {
                flights.set(i, fb);
                flights.set(i + 1, fa);
                i = 0;
            }
        }
        return flights;
    }

    @Override
    public List<Flight> minimalLayover(List<Flight> flights) {
        for (int i = 0; i < flights.size() - 1; i++) {
            Flight a = flights.get(i);
            Flight b = flights.get(i + 1);
            if (a.getSegments().size() > b.getSegments().size()) {
                flights.set(i, b);
                flights.set(i + 1, a);
                i = 0;
            }
        }
        return flights;
    }

    @Override
    public List<Flight> arrivalOnDate(List<Flight> flights) {
        LocalDate day = convertStringToDate();
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            for (Segment s : f.getSegments()) {
                if (s.getArrivalDate().toLocalDate().equals(day)) {
                    result.add(f);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public List<List<Flight>> notCorrectFlightsFilter(List<Flight> flights) {
        List<List<Flight>> result = new ArrayList<>();
        List<Flight> flightCorrect = new ArrayList<>();
        List<Flight> datePassed = new ArrayList<>();
        List<Flight> errorDates = new ArrayList<>();
        List<Flight> longLayover = new ArrayList<>();
        for (Flight f : flights) {
            boolean flightIsCorrect = true;
            LocalDateTime arrivalDate = null;
            int minutesInEarth = 0;
            if (f.getSegments().get(0).getDepartureDate().isBefore(LocalDateTime.now())) {
                datePassed.add(f);
                flightIsCorrect = false;
            }
            for (Segment s : f.getSegments()) {
                if (s.getDepartureDate().isAfter(s.getArrivalDate())) {
                    if (!errorDates.contains(f)) {
                        errorDates.add(f);
                        flightIsCorrect = false;
                        break;
                    }
                }
                // Duration layover = Duration.between(arrivalDate, s.getDepartureDate());
                if (arrivalDate == null) {
                    arrivalDate = f.getSegments().get(0).getArrivalDate();
                    continue;
                }
                int layover = (int) (ChronoUnit.SECONDS.between(arrivalDate, s.getDepartureDate()));
                minutesInEarth += layover / 60;
                arrivalDate = s.getArrivalDate();
            }
            if (minutesInEarth >= 120) {
                longLayover.add(f);
                flightIsCorrect = false;
            }
            if (flightIsCorrect) {
                flightCorrect.add(f);
            }
        }
        result.add(flightCorrect);
        result.add(datePassed);
        result.add(errorDates);
        result.add(longLayover);
        return result;
    }

    public LocalDate convertStringToDate() {
        LocalDate result = null;
        System.out.print(Menu.menuInputDay);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        try {
            result = LocalDate.parse(new Scanner(System.in).nextLine(), formatter);
        } catch (Exception x) {
            System.out.print("Вы ввели не верный формат даты, ");
            return convertStringToDate();
        }
        return result;
    }


}
