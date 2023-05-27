package com.gridnine.testing;

import java.time.LocalDate;
import java.util.List;

public interface Filters {

    List<Flight> departureOnDate(List<Flight> flights);

    List<Flight> departureAfterDate(List<Flight> flights);

    List<Flight> sooner(List<Flight> flights);

    List<Flight> faster(List<Flight> flights);

    List<Flight> minimalLayover(List<Flight> flights);

    List<Flight> arrivalOnDate(List<Flight> flights);

    List<List<Flight>> notCorrectFlightsFilter(List<Flight> flights);

    LocalDate convertStringToDate();
}
