package liadov.mypackage.lesson3;

public class User {
    private String fullName;
    private Role role;

    public User(String fullName, Role role) {
        this.fullName = fullName;
        this.role = role;
    }

    /**
     * 8. Method receives object of User class and prints hello message based on user full name and user role
     * @param user this object is used to print hello message
     */
    public static void sayHello(User user) {
        System.out.printf("Приветствуем %s с ролью %s", user.fullName, user.role);
    }
}