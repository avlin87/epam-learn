package liadov.mypackage.lesson3;

public class Human {

    private String fullName;
    private int age;
    private Address address;

    public Human(String fullName, int age, Address address) {
        this.fullName = fullName;
        this.age = age;
        this.address = address;
    }

    /**
     * Method return String value of fullName variable
     *
     * @return String value of fullName variable
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Method return int value of age variable
     *
     * @return int value of age variable
     */
    public int getAge() {
        return age;
    }

    /**
     * Method return String value of combined Address class variables
     *
     * @return String value as Full address
     */
    public String getFullStringAddress() {
        return address.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Human)) return false;

        Human human = (Human) o;

        if (age != human.age) return false;
        if (fullName != null ? !fullName.equals(human.fullName) : human.fullName != null) return false;
        return address != null ? address.equals(human.address) : human.address == null;
    }

    @Override
    public int hashCode() {
        int result = fullName != null ? fullName.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Human{" +
                "fullName='" + fullName + '\'' +
                ", age=" + age +
                ", address=" + address +
                '}';
    }
}
