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
        if (obj == null) return 1;  // Для null можно вернуть 1 (символ "-")

        double value = ((Number) obj).doubleValue();
        if (value == 0) return 4;  // "-0,00" — это минимальная длина

        // Для более длинных чисел вычислим длину через логарифм
        int integerPartLength = (int) Math.log10(Math.abs(value)) + 1;
        return integerPartLength + 3;  // Дополнительные два знака для десятичной части и запятой
    }

    public String print(Object obj) {
        if (obj == null) return "-";
        return format.format(((Number) obj).doubleValue());
    }

    @Override
    public boolean isPaddingRight() {
        return true;  // Числа выравниваются по правому краю
    }
}
