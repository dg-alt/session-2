package ru.sbt.jschool.session2;

import java.util.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        OutputFormatter formatter = new OutputFormatter();

        List<String> headers = Arrays.asList("#", "DATE", "MONEY", "STRING");
        List<List<Object>> rows = Arrays.asList(
                Arrays.asList(1, new GregorianCalendar(2018, Calendar.FEBRUARY, 2).getTime(), 123456789.0, "Hello, world!"),
                Arrays.asList(2, new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime(), 444777.0, "See you."),
                Arrays.asList(3, null, 0.4, null)
        );

        formatter.printTable(headers, rows);
    }
}
