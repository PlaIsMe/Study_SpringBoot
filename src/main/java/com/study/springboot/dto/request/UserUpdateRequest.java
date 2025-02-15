package com.study.springboot.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;
    
    private String firstName;
    private String lastName;
    private LocalDate dob;
}