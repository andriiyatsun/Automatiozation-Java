package org.example;

import org.example.model.UserDto;
import org.example.validator.Validator;

public class Main {
    public static void main(String[] args) {
        UserDto dto = new UserDto();
        dto.email = "test@ukma.edu.ua";
        dto.username = "Andrii Yatsun";

        try {
            Validator.validate(dto);
            System.out.println("Валідація пройшла успішно!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
