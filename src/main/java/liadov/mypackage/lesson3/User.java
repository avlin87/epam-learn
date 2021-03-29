package liadov.mypackage.lesson3;

public class User {
    private String fullName;
    private Role role;

    public User(String fullName, Role role) {
        this.fullName = fullName;
        this.role = role;
    }

    public static void sayHello(User user) {
        System.out.printf("Приветствуем %s с ролью %s", user.fullName, user.role);
    }
}