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

    @Override
    public List<Class<?>> supported() {
        return List.of(Double.class, Float.class);
    }

    @Override
    public int length(Object obj) {
        if (obj == null) return 1;  // Для null возвращаем 1 (символ "-")

        double value = ((Number) obj).doubleValue();
        if (value == 0) return 4;  // "-0,00" — минимальная длина для 0

        // Получаем целую часть
        long integerPart = (long) Math.abs(value);
        int integerLength = (int) Math.log10(integerPart) + 1;  // Длина целой части числа

        // Параметры для десятичной части
        int decimalLength = 3;  // Два знака после запятой + запятая

        return integerLength + decimalLength;  // Длина числа с учетом десятичной части
    }

    @Override
    public String print(Object obj) {
        if (obj == null) return "-";

        double value = ((Number) obj).doubleValue();
        return format.format(value);  // Форматируем число с учетом десятичных знаков
    }

    @Override
    public boolean isPaddingRight() {
        return true;  // Числа выравниваются по правому краю
    }
}
