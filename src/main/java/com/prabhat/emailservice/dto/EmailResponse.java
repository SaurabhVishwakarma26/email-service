package com.prabhat.emailservice.dto;

import lombok.Data;
@Data
public class EmailResponse {
    private String messageId;
    private String status;
    private String message;

    public EmailResponse(String messageId, String status, String message) {
        this.messageId = messageId;
        this.status = status;
        this.message = message;
    }
}
