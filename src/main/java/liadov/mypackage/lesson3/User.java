package liadov.mypackage.lesson3;

import java.util.Map;

public class User {
    private String fullName;
    private Role role;
    private Map<Role, String> roleDescription;

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
    public void sayHello(User user) {
        System.out.printf("Приветствуем %s с ролью %s\n", user.fullName, (roleDescription != null && roleDescription.get(user.role) != null) ? roleDescription.get(user.role) : user.role.name());
    }

    /**
     * Method receives Map<Role, String>
     *
     * @param roleDescription Map<Role, String>
     */
    public void setRoleDescription(Map<Role, String> roleDescription) {
        this.roleDescription = roleDescription;
    }
}