package com.yzc.concurrency.memoryvisibility;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Collections;
import java.util.List;

public class ThisEscape {
    public static void main(String[] args) {
        List emptyList = Collections.EMPTY_LIST;
    }
}
