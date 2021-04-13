package liadov.mypackage;

import java.util.LinkedList;
import java.util.List;

public class Main {
    static final List<String[]> arrays = new LinkedList<>();


    public static void main(String[] args) {
        System.out.println("Hello World");

        long start = System.currentTimeMillis();
        try {
            for (int i = 0; i < 10000; i++) {
                String[] strings = new String[10000];
                for (int j = 0; j < strings.length - 1; j++) {
                    strings[j]=String.valueOf(System.currentTimeMillis());
                }
                arrays.add(strings);
            }
        } finally {
            System.out.println((System.currentTimeMillis() - start) / 60000.0);
        }
    }
}
