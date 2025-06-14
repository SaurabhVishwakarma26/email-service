package com.prabhat.emailservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class EmailRequestWrapper {
    private List<EmailRequest> requests;
}

