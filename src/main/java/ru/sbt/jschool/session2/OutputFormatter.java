package ru.sbt.jschool.session2;

import ru.sbt.jschool.session2.printers.*;

import java.util.*;

public class OutputFormatter {

    private final Map<Class<?>, Printer> knownPrinters = new HashMap<>();

    private final Printer anyPrinter = new Printer() {
        @Override
        public List<Class<?>> supported() {
            return Collections.emptyList();
        }

        @Override
        public int length(Object obj) {
            return print(obj).length();
        }

        @Override
        public String print(Object obj) {
            return obj == null ? "-" : obj.toString();
        }
    };

    public OutputFormatter() {
        List<Printer> printers = List.of(
                new StringPrinter(),
                new DatePrinter(),
                new DoublePrinter(),
                new NumberPrinter()
        );

        for (Printer printer : printers) {
            for (Class<?> clazz : printer.supported()) {
                knownPrinters.put(clazz, printer);
            }
        }
    }

    private Printer printerFor(Object obj) {
        if (obj == null) return anyPrinter;
        return knownPrinters.getOrDefault(obj.getClass(), anyPrinter);
    }

    public void printTable(List<String> headers, List<List<Object>> rows) {
        int columns = headers.size();
        int[] colWidths = new int[columns];

        // Считаем максимальную ширину по каждому столбцу
        for (int i = 0; i < columns; i++) {
            colWidths[i] = headers.get(i).length();
        }

        for (List<Object> row : rows) {
            for (int i = 0; i < columns; i++) {
                Object value = row.get(i);
                Printer printer = printerFor(value);
                colWidths[i] = Math.max(colWidths[i], printer.length(value));
            }
        }

        // Формируем разделители
        String border = buildBorder(colWidths);
        System.out.println(border);

        // Заголовки
        System.out.print("|");
        for (int i = 0; i < columns; i++) {
            String header = headers.get(i);
            int width = colWidths[i];
            int padLeft = (width - header.length()) / 2;
            int padRight = width - header.length() - padLeft;
            System.out.print(" " + " ".repeat(padLeft) + header + " ".repeat(padRight) + " |");
        }
        System.out.println();
        System.out.println(border);

        // Данные
        for (List<Object> row : rows) {
            System.out.print("|");
            for (int i = 0; i < columns; i++) {
                Object value = row.get(i);
                Printer printer = printerFor(value);
                String text = printer.print(value);
                int width = colWidths[i];
                int pad = width - text.length();

                // Строки — влево, остальное — вправо
                if (value instanceof String) {
                    System.out.print(" " + text + " ".repeat(pad) + " |");
                } else {
                    System.out.print(" " + " ".repeat(pad) + text + " |");
                }
            }
            System.out.println();
            System.out.println(border);
        }
    }

    private String buildBorder(int[] colWidths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : colWidths) {
            sb.append("-".repeat(w + 2)).append("+");
        }
        return sb.toString();
    }
}
