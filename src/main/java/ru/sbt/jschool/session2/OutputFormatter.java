package ru.sbt.jschool.session2;

import ru.sbt.jschool.session2.printers.*;

import java.io.PrintStream;
import java.util.*;

public class OutputFormatter {
    private final PrintStream out;  // Поток для вывода
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

    public OutputFormatter(PrintStream out) {
        this.out = out;

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

        // Считаем максимальную ширину по каждому столбцу (для заголовков и данных)
        for (int i = 0; i < columns; i++) {
            colWidths[i] = headers.get(i).length(); // Начальная ширина для столбца - это длина заголовка
        }

        // Пробежимся по данным и обновим максимальную ширину столбцов
        for (List<Object> row : rows) {
            for (int i = 0; i < columns; i++) {
                Object value = row.get(i);
                Printer printer = printerFor(value);
                int valueLength = printer.length(value);
                colWidths[i] = Math.max(colWidths[i], valueLength); // Обновляем ширину столбца
            }
        }

        // --- Вот важный блок: определяем тип принтера для каждого столбца по первому ненулевому значению ---
        Printer[] columnPrinters = new Printer[columns];
        for (int i = 0; i < columns; i++) {
            columnPrinters[i] = anyPrinter; // По умолчанию
            for (List<Object> row : rows) {
                Object value = row.get(i);
                if (value != null) {
                    columnPrinters[i] = printerFor(value);
                    break; // нашли тип — дальше не ищем
                }
            }
        }

        // Формируем разделитель
        String border = buildBorder(colWidths);
        out.println(border);

        // Заголовки
        out.print("|");
        for (int i = 0; i < columns; i++) {
            String header = headers.get(i);
            int width = colWidths[i];
            int padLeft = (width - header.length()) / 2;
            int padRight = width - header.length() - padLeft;
            // Форматируем заголовок с пробелами по обеим сторонам
            out.print(" ".repeat(padLeft) + header + " ".repeat(padRight) + "|");
        }
        out.println();
        out.println(border);

        // Данные
        for (List<Object> row : rows) {
            out.print("|");
            for (int i = 0; i < columns; i++) {
                Object value = row.get(i);
                Printer printer = columnPrinters[i];  // Используем принтер столбца!
                String text = printer.print(value);
                int width = colWidths[i];
                int pad = width - text.length();

                // Выровняем в зависимости от типа столбца
                if (printer instanceof StringPrinter) {
                    // Для строк — слева, добавляем пробелы справа
                    out.print(text + " ".repeat(pad) + "|");
                } else {
                    // Для чисел и дат — справа, добавляем пробелы слева
                    out.print(" ".repeat(pad) + text + "|");
                }
            }
            out.println();
            out.println(border);
        }
    }


    private String buildBorder(int[] colWidths) {
        StringBuilder sb = new StringBuilder("+");
        for (int w : colWidths) {
            sb.append("-".repeat(w)).append("+"); // Убираем лишние пробелы и добавляем только w
        }
        return sb.toString();
    }

}
