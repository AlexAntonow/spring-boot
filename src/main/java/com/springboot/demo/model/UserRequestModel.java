package com.springboot.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestModel {

    @JsonProperty("first-name")
    @NotBlank(message = "First name is mandatory")
    @Size(min=2, message = "First name cannot be less than 2 characters")
    private String firstName;

    @JsonProperty("last-name")
    @NotBlank(message = "Last name is mandatory")
    @Size(min=2, message = "Last name cannot be less than 2 characters")
    private String lastName;

    @Email
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Positive
    private int age;
}
