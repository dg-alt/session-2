package ru.sbt.jschool.session2.printers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DatePrinter implements Printer {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public List<Class<?>> supported() {
        return List.of(Date.class);
    }

    @Override
    public int length(Object obj) {
        return 10;  // Статическая длина для даты в формате "dd.MM.yyyy"
    }

    @Override
    public String print(Object obj) {
        if (obj == null) return "-";
        return sdf.format((Date) obj);
    }

    @Override
    public boolean isPaddingRight() {
        return true;  // Даты должны быть выровнены по правому краю
    }
}
