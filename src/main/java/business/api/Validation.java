package business.api;

import java.util.Calendar;

import business.api.exceptions.InvalidDateException;
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

    public static void validateDay(Calendar day) throws InvalidDateException {
        Calendar calendarDay = Calendar.getInstance();
        calendarDay.add(Calendar.DAY_OF_YEAR, -1);
        if (calendarDay.after(day)) {
            throw new InvalidDateException("La fecha no puede ser un día pasado");
        }
    }

}
