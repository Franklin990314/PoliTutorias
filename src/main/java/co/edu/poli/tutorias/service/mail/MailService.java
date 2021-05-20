package co.edu.poli.tutorias.service.mail;

import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.service.mail.model.Mail;

public interface MailService {
    void sendEmail (Mail mail);
    Mail createMail(String action, String role, String mail, TutorialDTO tutorial);
}
