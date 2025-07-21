package ru.sbt.jschool.session2.printers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class NumberPrinter implements Printer {

    private final DecimalFormat format;

    public NumberPrinter() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' '); // Для разделения тысяч пробелом
        symbols.setDecimalSeparator(',');  // Для разделителя десятичной части запятая
        this.format = new DecimalFormat("#,##0", symbols); // Форматируем числа
    }

    @Override
    public List<Class<?>> supported() {
        return List.of(Number.class);
    }

    @Override
    public int length(Object obj) {
        if (obj == null) return 1;

        // Преобразуем число в строку с нужным форматом, но без пробела
        String formattedValue = format.format(obj);
        return formattedValue.length(); // Длина строки с пробелами для тысяч
    }

    @Override
    public String print(Object obj) {
        if (obj == null) return "-";

        // Форматируем число в строку с пробелами для тысяч
        return format.format(obj);
    }

    @Override
    public boolean isPaddingRight() {
        return true;  // Числа всегда выравниваются по правому краю
    }
}
