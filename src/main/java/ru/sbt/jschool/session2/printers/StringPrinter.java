package ru.sbt.jschool.session2.printers;

import java.util.List;

public class StringPrinter implements Printer {
    public List<Class<?>> supported() {
        return List.of(String.class);
    }

    public int length(Object obj) {
        return print(obj).length();
    }

    public String print(Object obj) {
        if (obj == null) return "-";
        return obj.toString().replaceAll("\\R", " ");
    }
}
