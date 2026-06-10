package org.example.model;

import org.example.annotations.AutoDto;
import org.example.annotations.NotNull;

@AutoDto
public class User {
    @NotNull
    private String username;
    private String email;
}
