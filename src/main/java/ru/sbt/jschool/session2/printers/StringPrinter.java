package ru.sbt.jschool.session2.printers;

import java.util.List;

public class StringPrinter implements Printer {
    @Override
    public List<Class<?>> supported() {
        return List.of(String.class);
    }

    @Override
    public int length(Object obj) {
        if (obj == null) return 1;
        String str = (String) obj;
        if (str.contains("\n")) return print(obj).length();
        return str.length();
    }

    @Override
    public String print(Object obj) {
        if (obj == null) return "-";
        return obj.toString().replaceAll("\\R", " ");
    }

    @Override
    public boolean isPaddingRight() {
        return false;  // Для строк всегда по левому краю
    }
}
