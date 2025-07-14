package ru.sbt.jschool.session2.printers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class NumberPrinter implements Printer {
    private final DecimalFormat format;

    public NumberPrinter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        this.format = new DecimalFormat("#,###", symbols);
    }

    public List<Class<?>> supported() {
        return List.of(Integer.class, Long.class, Short.class);
    }

    public int length(Object obj) {
        return print(obj).length();
    }

    public String print(Object obj) {
        if (obj == null) return "-";
        return format.format(((Number) obj).longValue());
    }
}
