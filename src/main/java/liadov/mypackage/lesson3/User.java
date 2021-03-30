package liadov.mypackage.lesson3;

import java.util.HashMap;

public class User {
    private String fullName;
    private Role role;
    private static HashMap<Role, String> roleDescription = new HashMap<>();

    {
        roleDescription.put(Role.ADMIN, "ADMIN: User who has administrative grants");
        roleDescription.put(Role.USER, "USER: User who has moderate grants");
        roleDescription.put(Role.MODERATOR, "MODERATOR: User who has moderator grants");
    }

    /**
     * @param fullName String name of a User
     * @param role
     */
    public User(String fullName, Role role) {
        this.fullName = fullName;
        this.role = role;
    }

    /**
     * 8. Method receives object of User class and prints hello message based on user full name and user role
     *
     * @param user this object is used to print hello message
     */
    public static void sayHello(User user) {
        System.out.printf("Приветствуем %s с ролью %s\n", user.fullName, roleDescription.get(user.role));
    }
}