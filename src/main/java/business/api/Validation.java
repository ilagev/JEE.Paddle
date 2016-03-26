package business.api;

import business.api.exceptions.InvalidUserFieldException;
import business.wrapper.UserWrapper;

public class Validation {
    
    public static void validateUser(UserWrapper userWrapper) throws InvalidUserFieldException {
        Validation.validateField(userWrapper.getUsername(), "username");
        Validation.validateField(userWrapper.getEmail(), "email");
        Validation.validateField(userWrapper.getPassword(), "password");
    }
    
    public static void validateField(String field, String msg) throws InvalidUserFieldException {
        if (field == null || field.isEmpty()) {
            throw new InvalidUserFieldException(msg);
        }
    }

}
