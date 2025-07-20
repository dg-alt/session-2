package ru.sbt.jschool.session2.printers;

import java.util.List;

public interface Printer {
    List<Class<?>> supported();
    int length(Object obj);
    String print(Object obj);

    boolean isPaddingRight();
}
