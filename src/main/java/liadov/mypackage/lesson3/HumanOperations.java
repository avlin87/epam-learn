package liadov.mypackage.lesson3;

import java.util.*;

public class HumanOperations {
    private List<Human> humanList = new ArrayList<>();
    private Set<Human> humanDuplicateSet = new HashSet<>();

    /**
     * 1. Fill ArrayList with Human objects
     */
    public void createHumansData() {
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
    }

    /**
     * 2. Find Duplicate records in humanList and print it to console
     */
    public void findDuplicates() {
        for (Human human : getSetOfDuplicates()) {
            System.out.println("Duplicated records: " + human);
        }
    }

    /**
     * 3. Remove Duplicate records from humanList
     */
    public void removeDuplicates() {
        for (Human human : getSetOfDuplicates()) {
            humanList.remove(human);
        }
    }

    /**
     * Method identifies duplicate records in humanList
     *
     * @return Set<Human> set of duplicated records
     */
    private Set<Human> getSetOfDuplicates() {
        Set<Human> humanDuplicateSet = new HashSet<>();
        for (Human human : humanList) {
            if (!humanDuplicateSet.contains(human) && (humanList.indexOf(human) != humanList.lastIndexOf(human))) {
                humanDuplicateSet.add(human);
            }
        }
        return humanDuplicateSet;
    }

    /**
     * 4. Sort people by fullName
     */
    public void sortHumanByFullName() {
        humanList.sort(Comparator.comparing(Human::getFullName));
    }

    /**
     * 5. Sort people by age
     */
    public void sortHumanByAge() {
        humanList.sort(Comparator.comparingInt(Human::getAge));
    }

    /**
     * 6. Sort people by address
     */
    public void sortHumanByAddress() {
        humanList.sort(Comparator.comparing(Human::getFullStringAddress));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HumanOperations{\n");
        for (Human human : humanList) {
            sb.append(human + "\n");
        }
        sb.append('}');
        return sb.toString();
    }
}