package liadov.mypackage.lesson7;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomClassLoader {
    public Class loadClass(String testClass) {
        return Object.class;
    }
}
