package liadov.mypackage;

import liadov.mypackage.lesson3.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        humanOperationsExample();
        userExample();
        sortingHashMapExample();
    }

    /**
     * Method intended for representation of result examples for 1-6 requirements representation
     */
    private static void humanOperationsExample(){
        /* 1. Fill ArrayList with Human objects*/
        List<Human> humanList = new ArrayList<>();
        humanList.add(new Human("Thomas Jeffrey Hanks", 21, new Address("Toledo", "Calle de Roma", "5", 1)));
        humanList.add(new Human("Samuel Leroy Jackson", 32, new Address("Sofia", "Ivan Vazov", "1408", 2)));
        humanList.add(new Human("Mary Louise Streep", 41, new Address("Overland Part", "Goddard St", "10350", 6)));
        humanList.add(new Human("Dwayne Douglas Johnson", 27, new Address("Odessa", "Haharinske Plateau", "5", 4)));
        humanList.add(new Human("Matthew David McConaughey", 18, new Address("Samara", "Ulitsa Sovetskoy Armii", "180с", 50)));
        humanList.add(new Human("Cameron Michelle Diaz", 21, new Address("Tokyo", "Higashiazabu", "1", 16)));
        humanList.add(new Human("Dwayne Douglas Johnson", 27, new Address("Odessa", "Haharinske Plateau", "5", 4)));
        humanList.add(new Human("Martin John Freeman", 53, new Address("Matamata", "Buckland Road", "501", 8)));
        humanList.add(new Human("Mary Louise Streep", 41, new Address("Overland Part", "Goddard St", "10350", 6)));
        humanList.add(new Human("Thomas Jeffrey Hanks", 21, new Address("Toledo", "Calle de Roma", "5", 1)));

        HumanOperations humanOperations = new HumanOperations();

        humanOperations.findDuplicates(humanList);
        humanOperations.removeDuplicates(humanList);
        humanOperations.sortHumanByFullName(humanList);
        System.out.println("Sorted by FullName\n"+humanOperations.toString(humanList));
        humanOperations.sortHumanByAge(humanList);
        System.out.println("Sorted by Age\n"+humanOperations.toString(humanList));
        humanOperations.sortHumanByAddress(humanList);
        System.out.println("Sorted by Address\n"+humanOperations.toString(humanList));
    }

    /**
     * Method intended for representation of result examples for 7 and 8 requirements representation
     */
    private static void userExample(){
        Map<Role, String> roleDescription = new HashMap<>();
        roleDescription.put(Role.ADMIN, "ADMIN: User who has administrative grants");
        roleDescription.put(Role.USER, "USER: User who has moderate grants");
        roleDescription.put(Role.MODERATOR, "MODERATOR: User who has moderator grants");
        User testUser = new User("Now Or Never", Role.ADMIN);
        testUser.setRoleDescription(roleDescription);
        testUser.sayHello(testUser);
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
