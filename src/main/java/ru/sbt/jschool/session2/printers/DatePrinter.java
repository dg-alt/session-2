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
        if (obj == null) return 1;  // Для null можно вернуть 1 (символ "-")

        return sdf.format(obj).length();  // Мы точно знаем формат, и можем посчитать длину строки
    }

    public String print(Object obj) {
        if (obj == null) return "-";
        return sdf.format((Date) obj);
    }

    @Override
    public boolean isPaddingRight() {
        return true;  // Даты выравниваются по правому краю
    }
}
