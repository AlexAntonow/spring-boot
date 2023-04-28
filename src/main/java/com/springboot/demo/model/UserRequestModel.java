package com.springboot.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
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
    @Min(value = 0, message = "Age cannot be less than 0 years")
    @Max(value = 150, message = "Age cannot be more than 150 years")
    private int age;
}
