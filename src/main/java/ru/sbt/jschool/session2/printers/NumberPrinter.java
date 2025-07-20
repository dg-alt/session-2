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
        if (obj == null) return 1;  // Для null можно вернуть 1 (символ "-")

        long value = ((Number) obj).longValue();
        if (value == 0) return 3;  // "-0" — это минимальная длина

        // Для более длинных чисел вычислим длину через логарифм
        return (int) Math.log10(Math.abs(value)) + 1;
    }

    public String print(Object obj) {
        if (obj == null) return "-";
        return format.format(((Number) obj).longValue());
    }

    @Override
    public boolean isPaddingRight() {
        return true;  // Числа выравниваются по правому краю
    }
}
