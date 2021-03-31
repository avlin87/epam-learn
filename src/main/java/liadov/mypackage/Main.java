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

        Map<Role, String> roleDescription = new HashMap<>();
        roleDescription.put(Role.ADMIN, "ADMIN: User who has administrative grants");
        roleDescription.put(Role.USER, "USER: User who has moderate grants");
        roleDescription.put(Role.MODERATOR, "MODERATOR: User who has moderator grants");
        User testUser = new User("Now Or Never", Role.ADMIN);
        testUser.setRoleDescription(roleDescription);
        testUser.sayHello(testUser);

        sortingHashMapExample();
    }

    /**
     * Method intended for representation of result examples for 9 and 10 requirements implementation.
     */
    private static void sortingHashMapExample() {
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
