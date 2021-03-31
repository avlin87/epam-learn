package liadov.mypackage.lesson3;

import java.util.*;

public class HumanOperations {

    private Set<Human> humanDuplicateSet = new HashSet<>();

    /**
     * 2. Find Duplicate records in humanList and print it to console
     *
     * @param humanList List<Human>
     */
    public void findDuplicates(List<Human> humanList) {
        for (Human human : getSetOfDuplicates(humanList)) {
            System.out.println("Duplicated records: " + human);
        }
    }

    /**
     * 3. Remove Duplicate records from humanList
     *
     * @param humanList List<Human>
     */
    public void removeDuplicates(List<Human> humanList) {
        for (Human human : getSetOfDuplicates(humanList)) {
            humanList.remove(human);
        }
    }

    /**
     * Method identifies duplicate records in humanList
     *
     * @param humanList List<Human>
     * @return Set<Human> set of duplicated records
     */
    private Set<Human> getSetOfDuplicates(List<Human> humanList) {
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
     * @param humanList List<Human> to be sorted
     */
    public void sortHumanByFullName(List<Human> humanList) {
        humanList.sort(Comparator.comparing(Human::getFullName));
    }

    /**
     * 5. Sort people by age
     * @param humanList List<Human> to be sorted
     */
    public void sortHumanByAge(List<Human> humanList) {
        humanList.sort(Comparator.comparingInt(Human::getAge));
    }

    /**
     * 6. Sort people by address
     * @param humanList List<Human> to be sorted
     */
    public void sortHumanByAddress(List<Human> humanList) {
        humanList.sort(Comparator.comparing(Human::getFullStringAddress));
    }

    /**
     * Method converts List<Human> to String value
     * @param humanList List<Human> to be converted to String
     * @return String of humans data
     */
    public String toString(List<Human> humanList) {
        StringBuilder sb = new StringBuilder("HumanOperations{\n");
        for (Human human : humanList) {
            sb.append(human + "\n");
        }
        sb.append('}');
        return sb.toString();
    }
}