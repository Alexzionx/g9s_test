package com.gridnine.testing;

import java.util.List;

public class Tables {

    Filter filter = new Filter();

    List<Flight> getFlightsList() {
        return FlightBuilder.createFlights();
    }

    public String getTableFlightsdDepartureOnDate() {
        return outTables(
                filter.notCorrectFlightsFilter(
                        filter.departureOnDate(
                                getFlightsList())));
    }

    public String getTableFlightsdDepartureAfterDate() {
        return outTables(
                filter.notCorrectFlightsFilter(
                        filter.departureAfterDate(
                                getFlightsList())));
    }

    public String getTableSooner() {
        return outTables(
                filter.notCorrectFlightsFilter(
                        filter.sooner(getFlightsList())));
    }

    public String getTableFaster() {
        return outTables(
                filter.notCorrectFlightsFilter(
                        filter.faster(getFlightsList())));
    }

    public String getTableMinimalLayover() {
        return outTables(
                filter.notCorrectFlightsFilter(
                        filter.minimalLayover(getFlightsList())));
    }

    public String getTableArrivalOnDate() {
        return outTables(
                filter.notCorrectFlightsFilter(
                        filter.arrivalOnDate(getFlightsList())));
    }

    StringBuilder outList(String label, List<Flight> flights) {
        StringBuilder result = new StringBuilder();
        int id = 0;
        String line = "---------------------------" + label + "-------------------------------\n";
        result.append(line);
        for (Flight f : flights) {
            result.append("--  " + (++id) + ".  " + f.toString() + "\n");
        }
        result.append("---------------------------------------------------------------------\n\n");
        return result;
    }

    String outTables(List<List<Flight>> L) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (!L.get(i).isEmpty()) {
                switch (i) {
                    case 0 -> {
                        result.append(this.outList("Список рейсов", L.get(i)));
                    }
                    case 1 -> {
                        result.append(this.outList("Ошибка - Дата рейса раньше текущей даты", L.get(i)));
                    }
                    case 2 -> {
                        result.append(this.outList("Ошибка - Дата вылета позже чем дата прилета", L.get(i)));
                    }
                    case 3 -> {
                        result.append(this.outList("Ошибка - пересадки занимают больше 2-х часов", L.get(i)));
                    }
                }

            }
        }
        return result.toString();
    }

}
