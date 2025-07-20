package ru.sbt.jschool.session2.printers;

import java.util.List;

public class StringPrinter implements Printer {

    @Override
    public List<Class<?>> supported() {
        return List.of(String.class);
    }

    @Override
    public int length(Object obj) {
        return print(obj).length();
    }

    @Override
    public String print(Object obj) {
        if (obj == null) return "-";
        return obj.toString().replaceAll("\\R", " ");  // Заменяем новые строки на пробелы
    }

    @Override
    public boolean isPaddingRight() {
        return false;  // Строки выравниваются по левому краю
    }
}
