package com.prabhat.emailservice.controller;

import com.prabhat.emailservice.dto.EmailRequest;
import com.prabhat.emailservice.dto.EmailResponse;
import com.prabhat.emailservice.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping(value = "/send", consumes = {"multipart/form-data"})
    public ResponseEntity<List<EmailResponse>> sendEmails(
            @Valid @RequestPart("requests") List<EmailRequest> requests,
            @RequestPart("resume") MultipartFile resume) {
        log.info("Received {} email requests with resume", requests.size());

        List<EmailResponse> responses = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++) {
            String messageId = UUID.randomUUID().toString();
            try {
                emailService.sendEmail(requests.get(i), resume, messageId);
                responses.add(new EmailResponse(messageId, "queued", "Email queued for sending"));
            } catch (Exception e) {
                log.error("Failed to send email for request index {}: {}", i, e.getMessage());
                responses.add(new EmailResponse(messageId, "failed", e.getMessage()));
            }
        }
        return new ResponseEntity<>(responses, HttpStatus.ACCEPTED);
    }
}