package co.edu.poli.tutorias.service.mail.impl;

import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.util.Util;
import co.edu.poli.tutorias.service.mail.MailService;
import co.edu.poli.tutorias.service.mail.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment environment;

    private String GREET_STUDENT = "Buen día Apreciado estudiante,";
    private String GREET_INSTRUCTOR = "Buen día Apreciado docente,";

    private String TXT_INFO = ", para más información ingrese al canal Politutorias.";
    private String ACTION_CREATION_STUDENT = "Se le informa que se ha creado satisfactoriamente la tutoría con el radicado ";
    private String ACTION_CREATION_INSTRUCTOR = "Se le informa que se ha creado una solicitud de tutoría a su nombre con el radicado ";
    private String INFO_ACTION = "Se le informa que la solicitud de tutoría con el radicado ";
    private String ACTION_MODIFICATION = ", cambio al estado ";
    private String ACTION_DELETE = " ha sido eliminada satisfactoriamente";
    private String ACTION_DELETE_INFO = " ha sido eliminada por el estudiante ";
    private String ACTION_ACCEPTATION = " ha sido aprobada para la fecha ";

    @Override
    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(environment.getProperty("spring.mail.username"));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getMailContent(), true);

            mailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Mail createMail(String action, String role, String mail, TutorialDTO tutorial) {
        Mail response = new Mail();

        String mailContent = "";
        mailContent += "<table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" width=\"100%\" style=\"width:100.0%; border-collapse:collapse\"> <tbody> <tr> <td style=\"padding:7.5pt 7.5pt 7.5pt 7.5pt\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100.0%; border-collapse:collapse\"> <tbody> <tr> <td valign=\"top\" style=\"padding:0cm 0cm 0cm 0cm\"> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"100%\" style=\"width:100.0%; background:#E1E1E1; border-collapse:collapse\"> <tbody> <tr> <td width=\"100%\" valign=\"top\" style=\"width:100.0%; padding:0cm 0cm 0cm 0cm\"> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:white; border-collapse:collapse\"> <tbody> <tr> <td style=\"padding:0cm 0cm 0cm 0cm\"> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:#0D385A; border-collapse:collapse\"> <tbody> <tr> <td valign=\"top\" style=\"padding:0cm 0cm 0cm 0cm\"> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:white; border-collapse:collapse\"> <tbody> <tr> <td valign=\"top\" style=\"padding:0cm 0cm 0cm 0cm\"> <p class=\"x_MsoNormal\"> <span style=\"color:black\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c5391619cb45ad8e3578b30b167c6ed9985b4cff5132a2050d28e3a2649eed6463d91bdbce622b51bbc75dd\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"1\"><span style=\"color:blue; text-decoration:none\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/junio/plantillaMercadeoArtes/mail_01.jpg\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"600\" height=\"310\" id=\"x__x0000_i1038\" alt=\"POLI\" style=\"width:6.25in; height:3.2291in\"></span></a></span><span style=\"color:black\"></span> </p> </td> </tr> </tbody> </table> </div> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:white; border-collapse:collapse\"> <tbody> <tr> <td style=\"padding:37.5pt 57.0pt 37.5pt 57.0pt\"> <p class=\"x_MsoNormal\"> <span style=\"font-family:'Intro Regular'; color:#002060\">";
        if (role.equals(Util.ROLE_STUDENT)) {
            mailContent += this.GREET_STUDENT;
        } else if (role.equals(Util.ROLE_TEACHER)) {
            mailContent += this.GREET_INSTRUCTOR;
        }
        mailContent += "</span> </p> <p class=\"x_MsoNormal\"> <span style=\"font-family:'Intro Regular'; color:#002060\">";
        if (action.equals(Util.ACTION_CREATION)) {
            if (role.equals(Util.ROLE_STUDENT)) {
                mailContent += this.ACTION_CREATION_STUDENT;
            } else if (role.equals(Util.ROLE_TEACHER)) {
                mailContent += this.ACTION_CREATION_INSTRUCTOR;
            }
        } else {
            mailContent += this.INFO_ACTION;
        }
        mailContent += "</span><span style=\"font-family:'Intro Regular'; color:red\">";
        mailContent += Util.formatCaseNumber(tutorial.getId());
        mailContent += "</span><span style=\"font-family:'Intro Regular'; color:#002060\">";
        if (action.equals(Util.ACTION_MODIFICATION)) {
            mailContent += this.ACTION_MODIFICATION + tutorial.getStatus();
        } else if (action.equals(Util.ACTION_DELETE)) {
            if (role.equals(Util.ROLE_STUDENT)) {
                mailContent += this.ACTION_DELETE;
            } else if (role.equals(Util.ROLE_TEACHER)) {
                mailContent += this.ACTION_DELETE_INFO + tutorial.getStudent();
            }
        } else if (action.equals(Util.ACTION_ACCEPTATION)) {
            mailContent += this.ACTION_ACCEPTATION + tutorial.getScheduledDate();
        }
        mailContent += this.TXT_INFO;
        mailContent += "</span> </p> <p class=\"x_MsoNormal\" style=\"margin-top:12.0pt\"> <span style=\"font-family:'Intro Regular'; color:#002060\">";
        mailContent += "Cualquier inquietud por favor comunicarse a nuestra línea de servicio 7440740 - o la línea de servicio nacional 01 8000 180 779.";
        mailContent += "</p> <p class=\"x_MsoNormal\"> <span style=\"font-family:'Intro Regular'; color:#002060\">";
        mailContent += "Agradecemos su atención";
        mailContent += "</span><span style=\"font-size:10.5pt; font-family:'Arial',sans-serif; color:#263C59\"></span> </p> </td> </tr> </tbody> </table> </div> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"display:none\">&nbsp;</span> </p> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:#0D385A; border-collapse:collapse\"> <tbody> <tr style=\"height:16.5pt\"> <td width=\"600\" style=\"width:450.0pt; padding:0cm 0cm 0cm 0cm; height:16.5pt\"> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"color:white\">&nbsp;</span><span style=\"color:black\"></span> </p> </td> </tr> </tbody> </table> </div> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"display:none\">&nbsp;</span> </p> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:#0D385A; border-collapse:collapse\"> <tbody> <tr> <td style=\"padding:0cm 0cm 0cm 0cm\"> <p class=\"x_MsoNormal\" align=\"right\" style=\"text-align:right\"> <span style=\"color:white; display:none\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/marzo/mailContactoPregradoV1/mail_23.png\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"192\" height=\"36\" id=\"x__x0000_i1037\" alt=\"POLI\" style=\"width:2.0in; height:.375in\"></span><span style=\"display:none\"></span> </p> </td> <td valign=\"top\" style=\"padding:0cm 0cm 0cm 0cm\"> <p class=\"x_MsoNormal\"> <span style=\"color:white; display:none\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/marzo/mailContactoPregradoV1/mail_27.jpg\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"600\" height=\"18\" id=\"x__x0000_i1036\" alt=\"POLI\" style=\"width:6.25in; height:.1875in\"></span><span style=\"display:none\"></span> </p> </td> <td style=\"padding:0cm 0cm 0cm 24.75pt\"> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c539161615d61ac512db12af48f4fde8e8d6450efba48de37e71b04fbe5abd4ec4c92fc92b392615e4f7c96\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"2\"><span style=\"color:blue; text-decoration:none\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/marzo/mailContactoPregradoV1/mail_22.png\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"177\" height=\"34\" id=\"x__x0000_i1035\" alt=\"POLI\" style=\"width:1.8437in; height:.3541in\"></span></a></span><span style=\"\"></span> </p> </td> <td style=\"padding:0cm 29.25pt 0cm 0cm\"> <p class=\"x_MsoNormal\" align=\"right\" style=\"text-align:right\"> <span style=\"color:white\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/marzo/mailContactoPregradoV1/mail_19.png\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"192\" height=\"36\" id=\"x__x0000_i1034\" alt=\"POLI\" style=\"width:2.0in; height:.375in\"></span><span style=\"\"></span> </p> </td> </tr> </tbody> </table> </div> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"display:none\">&nbsp;</span> </p> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:#0D385A; border-collapse:collapse\"> <tbody> <tr> <td style=\"padding:0cm 0cm 0cm 0cm\"> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"color:white\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/marzo/mailContactoPregradoV1/mail_26.jpg\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"528\" height=\"18\" id=\"x__x0000_i1033\" alt=\"POLI\" style=\"width:5.5in; height:.1875in\"></span><span style=\"\"></span> </p> </td> </tr> </tbody> </table> </div> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"display:none\">&nbsp;</span> </p> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:#0D385A; border-collapse:collapse\"> <tbody> <tr> <td width=\"442\" style=\"width:331.5pt; padding:15.0pt 0cm 15.0pt 24.0pt\"> <p class=\"x_MsoNormal\" style=\"line-height:12.75pt\"> <span style=\"font-size:10.0pt; font-family:'Arial',sans-serif; color:#1B9AC3\">Llámanos sin costo desde celular a la línea nacional: <span style=\"display:none\"><br></span></span><span style=\"color:white\"><a href=\"tel:018000180779\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"3\"><span style=\"font-size:10.0pt; font-family:'Arial',sans-serif; color:white\">01 8000 180 779.</span></a></span><span style=\"font-size:10.0pt; font-family:'Arial',sans-serif; color:white\"><br><span style=\"display:none\"><br></span></span><span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c539161a22301f1e14283fd83bacc4d239e8de8d11bc75b5526901b6bb6d68841b3ce9d4a48804ed0b7c648\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"4\"><span style=\"font-size:7.0pt; font-family:'Arial',sans-serif; color:white\">Sede Principal - Bogotá</span></a></span><span style=\"font-size:7.0pt; font-family:'Arial',sans-serif; color:white\"> | </span><span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c5391613ab17fa6b6701a08b89fd517d6f3b14223e3d92d220d9fc8aaff47395d58def3b29a4e35bdb381a4\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"5\"><span style=\"font-size:7.0pt; font-family:'Arial',sans-serif; color:white\">Sede Medellín</span></a></span><span style=\"font-size:7.0pt; font-family:'Arial',sans-serif; color:white\"> |<span style=\"display:none\"><br></span></span><span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c5391610bcabc1c9d22f3ef23294546d18dc52f545801389b56642099c908e0e812e02c0097cda5f1fd956a\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"6\"><span style=\"font-size:7.0pt; font-family:'Arial',sans-serif; color:white\">Sede Posgrados - Bogotá</span></a></span><span style=\"font-size:7.0pt; font-family:'Arial',sans-serif; color:white\"> | </span><span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c53916129fb6b0d8b0486fed1c3dce3a840b36c0ef821fd69a058858f0d199933f727f767b4ba1472b0ea15\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"7\"><span style=\"font-size:7.0pt; font-family:'Arial',sans-serif; color:white\">Sede Graduados - Bogotá</span></a></span><span style=\"font-size:10.0pt; font-family:'Arial',sans-serif; color:white\"> </span> </p> </td> <td width=\"126\" style=\"width:94.5pt; padding:0cm 0cm 0cm 0cm\"> <p class=\"x_MsoNormal\"> <span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c539161dd909917a2b588f87dfba9929eedd74caed1cab95896c860a21f67d0b51d5ba130bcc6c34f40272a\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"8\"><span style=\"color:blue; text-decoration:none\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/abril/mailRestablecimientoContacto1/mail-02.jpg\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"23\" height=\"25\" id=\"x__x0000_i1032\" alt=\"POLI\" style=\"width:.2395in; height:.2604in\"></span></a></span><span style=\"color:white\">&nbsp; </span><span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c5391616b27ed1f2075b6c6da77bac39d6ddc5b487049ffe44d899c4fe7b7b881b8d3c85b04cc6dd531b6a1\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"9\"><span style=\"color:blue; text-decoration:none\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/abril/mailRestablecimientoContacto1/mail-03.jpg\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"24\" height=\"25\" id=\"x__x0000_i1031\" alt=\"POLI\" style=\"width:.25in; height:.2604in\"></span></a></span><span style=\"color:white\">&nbsp; </span><span style=\"color:white\"><a href=\"https://cl.s6.exct.net/?qs=b329cb4f3c539161588a2c3805e1b88dd0be6445f54ffdaa33c6cc494f48f8af3805755bb300f15eda05b8ecf1d637e0\" target=\"_blank\" rel=\"noopener noreferrer\" data-auth=\"NotApplicable\" data-linkindex=\"10\"><span style=\"color:blue; text-decoration:none\"><img data-imagetype=\"External\" src=\"http://hechoparaliderar.com/mailings/poli/2018/abril/pasoAdminVirtu/instagram.jpg\" data-connectorsauthtoken=\"1\" data-imageproxyendpoint=\"/actions/ei\" data-imageproxyid=\"\" border=\"0\" width=\"24\" height=\"25\" id=\"x__x0000_i1030\" alt=\"POLI\" style=\"width:.25in; height:.2604in\"></span></a></span><span style=\"color:black\"></span> </p> </td> </tr> </tbody> </table> </div> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"display:none\">&nbsp;</span> </p> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; border-collapse:collapse\"> <tbody> <tr> <td width=\"600\" valign=\"top\" style=\"width:450.0pt; background:white; padding:0cm 0cm 0cm 0cm\"> <div align=\"center\"> <table class=\"x_MsoNormalTable\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"width:450.0pt; background:#1AB1DC; border-collapse:collapse\"> <tbody> <tr> <td style=\"padding:5.25pt 0cm 5.25pt 0cm\"> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <b><span style=\"font-size:5.5pt; font-family:'Open Sans'; color:#0D385A\">Vigilada MEN Resolución Nº. 19349 de 1980-11-04.</span></b><span style=\"font-size:5.5pt; font-family:'Open Sans'; color:#0D385A\"> </span> </p> </td> </tr> </tbody> </table> </div> </td> </tr> </tbody> </table> </div> <p class=\"x_MsoNormal\" align=\"center\" style=\"text-align:center\"> <span style=\"\"></span> </p> </td> </tr> </tbody> </table> </div> </td> </tr> </tbody> </table> </div> </td> </tr> </tbody> </table> </div> </td> </tr> </tbody> </table> </td> </tr> </tbody> </table>";

        response.setMailContent(mailContent);
        if (action.equals(Util.ACTION_CREATION)) {
            response.setMailSubject("Notificación - Creación de Tutoria");
        } else if (action.equals(Util.ACTION_DELETE )) {
            response.setMailSubject("Notificación - Eliminación de Tutoria");
        } else if (action.equals(Util.ACTION_MODIFICATION )) {
            response.setMailSubject("Notificación - Modificación de estado Tutoria");
        } else if (action.equals(Util.ACTION_ACCEPTATION )) {
            response.setMailSubject("Notificación - Agendación de Tutoria");
        }
        response.setMailTo(mail);

        return response;
    }
}
