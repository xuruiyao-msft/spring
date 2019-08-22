package com.springinaction.spittr;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class User {

    @NotNull
    @Size(min=5, max=6, message = "{firstName.size}")
    String firstName;

    @NotNull
    @Size(min=5, max=6, message = "{lastName.size}")
    String lastName;

    @NotNull
    @Size(min=5, max=6, message = "{username.size}")
    String username;

    @NotNull
    @Size(min=5, max=6, message = "{password.size}")
    String password;
}
