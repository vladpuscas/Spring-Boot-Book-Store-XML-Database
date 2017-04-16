package application.Validator;

import application.Entity.User;

/**
 * Created by Vlad on 14-Apr-17.
 */
public class UserValidator {
    public boolean validate(User user) {
        return validateUsername(user.getUsername());
    }
    private boolean validateUsername(String username) {
        return username.matches("[a-zA-Z]+");
    }
}
