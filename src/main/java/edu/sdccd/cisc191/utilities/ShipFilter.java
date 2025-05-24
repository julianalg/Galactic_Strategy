package edu.sdccd.cisc191.utilities;

import com.opencsv.bean.CsvToBeanFilter;

@FunctionalInterface
public interface ShipFilter extends CsvToBeanFilter {
    ShipFilter filter = (line) -> line[0].startsWith("E");

    // The doFilter method can be implemented using a lambda
    @Override
    boolean allowLine(String[] line);
}