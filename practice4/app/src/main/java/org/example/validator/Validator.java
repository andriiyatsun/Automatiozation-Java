package org.example.validator;

import org.example.annotations.NotNull;
import java.lang.reflect.Field;

public class Validator {
    public static void validate(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {

            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value == null) {
                    throw new IllegalArgumentException("Помилка валідації: Поле '" + field.getName() + "' не може бути null!");
                }
            }
        }
    }
}