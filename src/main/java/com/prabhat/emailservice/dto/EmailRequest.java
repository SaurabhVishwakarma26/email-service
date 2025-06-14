package com.prabhat.emailservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EmailRequest {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Company cannot be blank")
    private String company;

    @NotBlank(message = "Subject cannot be blank")
    private String subject;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @Pattern(regexp = "text/plain|text/html", message = "Body type must be text/plain or text/html")
    private String bodyType = "text/plain";
}