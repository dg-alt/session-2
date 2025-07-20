package ru.sbt.jschool.session2;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import ru.sbt.jschool.session2.printers.*;

public class OutputFormatter {
    private PrintStream out;

    public OutputFormatter(PrintStream out) {
        this.out = out;
    }

    // Метод для вывода таблицы
    public void output(String[] names, Object[][] data) {
        // Считаем максимальную ширину для каждого столбца
        int columns = names.length;
        int[] colWidths = new int[columns];

        // Определяем максимальную ширину для каждого столбца, включая заголовки
        for (int i = 0; i < columns; i++) {
            colWidths[i] = names[i].length();
        }

        // Создаем массив принтеров для каждого столбца
        Printer[] columnPrinters = new Printer[columns];
        for (int i = 0; i < columns; i++) {
            columnPrinters[i] = new StringPrinter();  // по умолчанию StringPrinter
            for (Object[] row : data) {
                Object value = row[i];
                if (value != null) {
                    columnPrinters[i] = printerFor(value);  // находим принтер для первого ненулевого значения
                    break; // как только нашли первый ненулевой элемент, хватит
                }
            }
        }

        // Определяем максимальную ширину для каждого столбца, включая данные
        for (Object[] row : data) {
            for (int i = 0; i < columns; i++) {
                String value = formatValue(row[i], i);  // Передаем индекс столбца
                colWidths[i] = Math.max(colWidths[i], value.length());
            }
        }

        // Формируем границу таблицы
        String border = buildBorder(colWidths);
        out.println(border);

        // Печатаем заголовок
        out.print("|");
        for (int i = 0; i < columns; i++) {
            String header = names[i];
            int width = colWidths[i];
            int padLeft = (width - header.length()) / 2;
            int padRight = width - header.length() - padLeft;
            out.print(" ".repeat(padLeft) + header + " ".repeat(padRight) + "|");
        }
        out.println();
        out.println(border);

        // Печатаем данные
        for (Object[] row : data) {
            out.print("|");
            for (int i = 0; i < columns; i++) {
                String value = formatValue(row[i], i);  // Передаем индекс столбца
                int width = colWidths[i];
                int pad = width - value.length();

                Printer printer = columnPrinters[i]; // Получаем принтер для столбца
                // Форматируем вывод данных в зависимости от типа
                if (printer instanceof StringPrinter) {
                    // Строки выравниваем по левому краю
                    out.print(value + " ".repeat(pad) + "|");
                } else {
                    // Для чисел (в том числе дефиса) выравниваем по правому краю
                    out.print(" ".repeat(pad) + value + "|");
                }
            }
            out.println();
            out.println(border);
        }
    }

    // Метод для форматирования значения в строку
    private String formatValue(Object value, int columnIndex) {
        if (value == null) {
            // Для строкового столбца дефис по левому краю
            if (columnIndex == 0) {  // Столбец с названиями (например, "Город")
                return "-";
            }
            // Для всех других столбцов дефис по правому краю
            return "-";
        }

        Printer printer = printerFor(value); // Получаем принтер для типа данных
        return printer.print(value);  // Форматируем значение
    }

    // Метод для построения границы таблицы
    private String buildBorder(int[] colWidths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : colWidths) {
            sb.append("-".repeat(w)).append("+");
        }
        return sb.toString();
    }

    // Метод для получения принтера для типа данных
    private Printer printerFor(Object obj) {
        if (obj == null) return new StringPrinter(); // Возвращаем принтер для строк по умолчанию
        // Возвращаем подходящий принтер для типа данных
        if (obj instanceof Double) return new DoublePrinter();
        if (obj instanceof Number) return new NumberPrinter();
        if (obj instanceof java.util.Date) return new DatePrinter();
        return new StringPrinter(); // Для строк
    }
}
