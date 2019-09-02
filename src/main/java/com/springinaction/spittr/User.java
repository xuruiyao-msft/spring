package com.springinaction.spittr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @NotNull
    @Size(min = 5, max = 6, message = "{firstName.size}")
    @Column(name = "first_name")
    String firstName;

    @NotNull
    @Size(min = 5, max = 6, message = "{lastName.size}")
    @Column(name = "last_name")
    String lastName;

    @NotNull
//  @Size(min=5, max=6, message = "{username.size}")
    @Column(name = "user_name")
    String username;

    @NotNull
    @Size(min = 5, max = 6, message = "{password.size}")
    @Column(name = "password")
    String password;

}
