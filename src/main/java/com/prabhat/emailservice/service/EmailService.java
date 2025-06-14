package com.prabhat.emailservice.service;

import com.prabhat.emailservice.dto.EmailRequest;
import org.springframework.web.multipart.MultipartFile;

public interface EmailService {
    void sendEmail(EmailRequest emailRequest, MultipartFile resume, String messageId);
}
