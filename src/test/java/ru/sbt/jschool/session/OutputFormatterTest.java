package ru.sbt.jschool.session;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.junit.Test;
import ru.sbt.jschool.session2.OutputFormatter;

import static org.junit.Assert.assertEquals;

public class OutputFormatterTest {

    @Test
    public void testFormatter0() throws Exception {
        doTest("0");
    }

    @Test
    public void testFormatter1() throws Exception {
        doTest("1");
    }

    @Test
    public void testFormatter2() throws Exception {
        doTest("2");
    }

    @Test
    public void testFormatter3() throws Exception {
        doTest("3");
    }

    @Test
    public void testFormatter4() throws Exception {
        doTest("4");
    }

    private void doTest(String dir) throws Exception {
        Scanner sc = new Scanner(Objects.requireNonNull(OutputFormatterTest.class.getResourceAsStream("/" + dir + "/input.csv")));

        int size = Integer.parseInt(sc.nextLine());

        String[] types = sc.nextLine().split(",");
        String[] names = sc.nextLine().split(",");

        Object[][] data = new Object[size][];
        for (int i = 0; i < size; i++) {
            String[] strLine = sc.nextLine().split(",", -1);

            Object[] line = new Object[strLine.length];
            for (int j = 0; j < strLine.length; j++) {
                line[j] = format(strLine[j], types[j]);
            }
            data[i] = line;
        }

        // Создание временного файла для тестирования вывода
        File temp = File.createTempFile("test" + dir, "txt");
        //temp.deleteOnExit();

// Запись вывода в файл
        try (FileOutputStream output = new FileOutputStream(temp)) {
            // Передаем PrintStream в OutputFormatter
            PrintStream printStream = new PrintStream(output);
            OutputFormatter formatter = new OutputFormatter(printStream);  // Передаем PrintStream
            formatter.printTable(Arrays.asList(names), convertToListOfLists(data));
        }

// Сравнение с ожидаемым выводом
        try (Scanner actualOutput = new Scanner(temp);
             Scanner expectedOutput = new Scanner(Objects.requireNonNull(OutputFormatterTest.class.getResourceAsStream("/" + dir + "/output.txt")))) {

            while (expectedOutput.hasNextLine()) {
                String expected = expectedOutput.nextLine();

                if (!actualOutput.hasNextLine()) {
                    // Раскомментируй, если хочешь видеть логирование ошибки
                    // System.out.println("Expected output is \"" + expected + "\", but actual output is empty!");
                    throw new AssertionError("Expected output is \"" + expected + "\", but actual output is empty!");
                }

                String actual = actualOutput.nextLine();
                actual = actual.replace((char)160, (char)32); // Заменяем неразрывный пробел
                expected = expected.replace((char)160, (char)32); // Заменяем неразрывный пробел

                assertEquals(expected, actual);
            }
        }

    }

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

    private Object format(String str, String type) throws ParseException {
        if ("".equals(str))
            return null;

        switch (type) {
            case "string":
                return str;
            case "number":
                return Integer.valueOf(str);
            case "date":
                return dateFormat.parse(str);
            case "money":
                return Double.valueOf(str);
        }

        throw new RuntimeException("Unknown data type: " + type);
    }

    // Метод для преобразования Object[][] в List<List<Object>>
    private List<List<Object>> convertToListOfLists(Object[][] data) {
        return Arrays.stream(data)
                .map(Arrays::asList)  // Преобразуем каждую строку в список
                .collect(Collectors.toList());   // Собираем все строки в список
    }
}
