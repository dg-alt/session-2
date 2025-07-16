package ru.sbt.jschool.session2.printers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class DoublePrinter implements Printer {
    private final DecimalFormat format;

    public DoublePrinter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        symbols.setDecimalSeparator(',');
        this.format = new DecimalFormat("#,##0.00", symbols);
    }

    public List<Class<?>> supported() {
        return List.of(Double.class, Float.class);
    }

    public int length(Object obj) {
        return print(obj).length();
    }

    public String print(Object obj) {
        if (obj == null) return "-";
        return format.format(((Number) obj).doubleValue());
    }
}
