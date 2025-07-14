package ru.sbt.jschool.session2.printers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DatePrinter implements Printer {
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public List<Class<?>> supported() {
        return List.of(Date.class);
    }

    public int length(Object obj) {
        return print(obj).length();
    }

    public String print(Object obj) {
        if (obj == null) return "-";
        return sdf.format((Date) obj);
    }
}
