package liadov.mypackage;

import liadov.mypackage.lesson3.HumanOperations;
import liadov.mypackage.lesson3.Role;
import liadov.mypackage.lesson3.User;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");

        HumanOperations humanOperations = new HumanOperations();
        humanOperations.createHumansData();
        humanOperations.findDuplicates();
        humanOperations.removeDuplicates();

        humanOperations.sortHumanByFullName();
        humanOperations.sortHumanByAge();

        //System.out.println(humanOperations);

        humanOperations.sortHumanByAddress();
        //System.out.println(humanOperations);

        User.sayHello(new User("Test Now Or Never", Role.ADMIN));
    }
}
