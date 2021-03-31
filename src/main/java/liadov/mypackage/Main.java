package liadov.mypackage;

import liadov.mypackage.lesson3.HashMapSortOperations;
import liadov.mypackage.lesson3.HumanOperations;
import liadov.mypackage.lesson3.Role;
import liadov.mypackage.lesson3.User;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        HumanOperations humanOperations = new HumanOperations();
        humanOperations.createHumansData();
        humanOperations.findDuplicates();
        humanOperations.removeDuplicates();

        humanOperations.sortHumanByFullName();
        humanOperations.sortHumanByAge();

        System.out.println(humanOperations);

        humanOperations.sortHumanByAddress();
        System.out.println(humanOperations);

        User.sayHello(new User("Now Or Never", Role.ADMIN));

        /**
         * Sorting of HashMap example:
         */
        {
            HashMapSortOperations sortOperations = new HashMapSortOperations();

            HashMap<String, Integer> map = new HashMap<>();
            map.put("e_one", 1);
            map.put("b_two", 2);
            map.put("a_three", 3);
            map.put("c_six", 6);
            map.put("f_three", 3);
            map.put("d_five", 5);

            System.out.println("before sorting: " + map);
            map = sortOperations.getHashMapSortedByKey(map);
            System.out.println("Sorted by Key: " + map);
            map = sortOperations.getHashMapSortedByValue(map);
            System.out.println("Sorter by Value:" + map);
        }
    }
}
