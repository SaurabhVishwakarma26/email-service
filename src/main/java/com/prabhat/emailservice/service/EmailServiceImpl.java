package com.prabhat.emailservice.service;

import com.prabhat.emailservice.dto.EmailRequest;
import com.prabhat.emailservice.exception.EmailException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(EmailRequest emailRequest, MultipartFile resume, String messageId) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            // Set recipient
            helper.setTo(emailRequest.getEmail());

            // Set default from
            helper.setFrom("no-reply@example.com");

            // Set subject and body
            helper.setSubject(emailRequest.getSubject());
            String body = "Application to " + emailRequest.getCompany() + "\n\n" + emailRequest.getContent();
            helper.setText(body, emailRequest.getBodyType().equals("text/html"));

            // Validate and add resume
            if (resume == null || resume.isEmpty()) {
                throw new EmailException("Resume file is missing");
            }
            String contentType = resume.getContentType();
            if (!"application/pdf".equals(contentType) &&
                    !"application/msword".equals(contentType) &&
                    !"application/vnd.openxmlformats-officedocument.wordprocessingml.document".equals(contentType)) {
                throw new EmailException("Invalid file type. Only PDF, DOC, or DOCX allowed");
            }
            if (resume.getSize() > 10 * 1024 * 1024) {
                throw new EmailException("Resume size exceeds 10MB limit");
            }
            helper.addAttachment(resume.getOriginalFilename(), resume);

            // Send email
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Failed to send email: " + e.getMessage());
        }
    }
}