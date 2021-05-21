package co.edu.poli.tutorias.logic.impl;

import co.edu.poli.tutorias.entity.Course;
import co.edu.poli.tutorias.entity.Tutorial;
import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.entity.repository.CourseRepository;
import co.edu.poli.tutorias.entity.repository.TutorialRepository;
import co.edu.poli.tutorias.entity.repository.UserProfileRepository;
import co.edu.poli.tutorias.logic.TutorialLogic;
import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;
import co.edu.poli.tutorias.logic.util.Util;
import co.edu.poli.tutorias.service.mail.MailService;
import co.edu.poli.tutorias.service.mail.model.Mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TutorialLogicImpl implements TutorialLogic {

    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private MailService notificationService;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public TutorialDTO createTutorial(TutorialDTO data, UserDTO user) throws Exception {

        UserProfile userProfile;
        UserProfile instructorProfile;
        this.validateDTOTutorial(data);
        Tutorial tutorial;
        try {
            userProfile = userProfileRepository.findById(user.getId()).get();
            instructorProfile = userProfileRepository.findById(Integer.parseInt(data.getInstructor())).get();
            List<Course> courseList = courseRepository.findByCode(data.getCourse());
            tutorial = Util.mapDTOToEntityTutorial(data, userProfile, instructorProfile, courseList.get(0));
            tutorialRepository.save(tutorial);
            data.setId(tutorial.getId());
        } catch (Exception exc) {
            throw new Exception("Error. Se encontro un problema al crear la tutoria.");
        }

        try {
            userProfile.getTutorials().add(tutorial);
            userProfileRepository.save(userProfile);
            instructorProfile.getInstructorTutorials().add(tutorial);
            userProfileRepository.save(instructorProfile);
        } catch (Exception exc) {
            throw new Exception("Error. Se encontro un problema al asociar la tutoria.");
        }

        Mail mail = notificationService.createMail(Util.ACTION_CREATION, Util.ROLE_STUDENT, userProfile.getMail(), data);
        notificationService.sendEmail(mail);
        Mail instructorMail = notificationService.createMail(Util.ACTION_CREATION, Util.ROLE_TEACHER, instructorProfile.getMail(), data);
        notificationService.sendEmail(instructorMail);

        return data;
    }

    @Override
    public List<TutorialDTO> getTutorial(UserDTO user) throws Exception {
        List<TutorialDTO> response = new ArrayList<>();
        try {
            UserProfile userProfile = userProfileRepository.findById(user.getId()).get();
            Set<Tutorial> tutorials = userProfile.getTutorials();
            for (Tutorial data: tutorials) {
                response.add(Util.mapEntityToDTOTutorial(data));
            }
        } catch (Exception exc) {
            throw new Exception("Error. Se encontro un problema al consultar las tutorias del usuario.");
        }
        return response;
    }

    @Override
    public TutorialDTO getTutorial(Integer id, UserDTO user) throws Exception {
        TutorialDTO response;
        try {
            Tutorial tutorial = tutorialRepository.findById(id).get();
            UserProfile userProfile = tutorial.getUserProfile();

            if (user.getId() == userProfile.getId()) {
                response = Util.mapEntityToDTOTutorial(tutorial);
                response.setAvailabilityStartTime(Util.generateAvailabilityDate(response));
                response.setAvailabilityDate(new ArrayList<>());
                response.setAvailabilityEndTime("");
            } else {
                return null;
            }
        } catch (Exception exc) {
            throw new Exception("Error. No se pudo obtener la tutoria");
        }

        return response;
    }

    @Override
    public boolean deleteTutorial(Integer id, UserDTO user) throws Exception {

        try {
            Tutorial tutorial = tutorialRepository.findById(id).get();
            TutorialDTO data = Util.mapEntityToDTOTutorial(tutorial);
            UserProfile userProfile = tutorial.getUserProfile();
            UserProfile instructorProfile = tutorial.getInstructorProfile();

            if (user.getId() == userProfile.getId()) {
                tutorialRepository.delete(tutorial);

                Mail mail = notificationService.createMail(Util.ACTION_DELETE, Util.ROLE_STUDENT, userProfile.getMail(), data);
                notificationService.sendEmail(mail);
                Mail instructorMail = notificationService.createMail(Util.ACTION_DELETE, Util.ROLE_TEACHER, instructorProfile.getMail(), data);
                notificationService.sendEmail(instructorMail);

                return (true);
            } else {
                return (false);
            }
        } catch (Exception exc) {
            throw new Exception("Error. No se pudo obtener la tutoria");
        }
    }

    @Override
    public TutorialDTO updateTutorial(Integer id, TutorialDTO tutorialDTO, UserDTO user) throws Exception {
        Tutorial tutorial = tutorialRepository.findById(id).get();

        if (Util.ROLE_STUDENT.equals(Util.getRole(user.getRoles()))) {
            if (tutorialDTO.getStatus().equals(Util.STATUS_RETURNED_STUDENT)){
                tutorial.setStatus(Util.STATUS_RETURNED_STUDENT);
                tutorial.setAvailabilityDate(Util.generateAvailabilityDate(tutorialDTO));
                tutorial.setComment(tutorialDTO.getComment() != null ? tutorialDTO.getComment() : "");
            } else if (tutorialDTO.getStatus().equals(Util.STATUS_FINISHED)){
                tutorial.setStatus(Util.STATUS_FINISHED);
                Date today = Calendar.getInstance().getTime();
                String endTime = new SimpleDateFormat(Util.DATE_FORMAT_HOUR).format(today);
                tutorial.setEndTime(new SimpleDateFormat(Util.DATE_FORMAT_HOUR).parse(endTime));
            }
        } else if (Util.ROLE_TEACHER.equals(Util.getRole(user.getRoles()))) {
            if (tutorialDTO.getStatus().equals(Util.STATUS_RETURNED_TEACHER)){
                tutorial.setStatus(Util.STATUS_RETURNED_TEACHER);
                tutorial.setAvailabilityDate(Util.generateAvailabilityDate(tutorialDTO));
                tutorial.setComment(tutorialDTO.getComment() != null ? tutorialDTO.getComment() : "");
            }
        }

        if (tutorialDTO.getStatus().equals(Util.STATUS_SCHEDULED)){
            tutorial.setStatus(Util.STATUS_SCHEDULED);
            tutorial.setScheduledDate(tutorial.getScheduledDate());
            tutorial.setComment(tutorialDTO.getComment() != null ? tutorialDTO.getComment() : "");
        }

        tutorialRepository.save(tutorial);

        TutorialDTO data = Util.mapEntityToDTOTutorial(tutorial);

        if (tutorialDTO.getStatus().equals(Util.STATUS_SCHEDULED)){
            Mail mail = notificationService.createMail(Util.ACTION_ACCEPTATION, Util.ROLE_STUDENT, tutorial.getUserProfile().getMail(), data);
            notificationService.sendEmail(mail);
            Mail instructorMail = notificationService.createMail(Util.ACTION_ACCEPTATION, Util.ROLE_TEACHER, tutorial.getInstructorProfile().getMail(), data);
            notificationService.sendEmail(instructorMail);
        } else {
            Mail mail = notificationService.createMail(Util.ACTION_MODIFICATION, Util.ROLE_STUDENT, tutorial.getUserProfile().getMail(), data);
            notificationService.sendEmail(mail);
            Mail instructorMail = notificationService.createMail(Util.ACTION_MODIFICATION, Util.ROLE_TEACHER, tutorial.getInstructorProfile().getMail(), data);
            notificationService.sendEmail(instructorMail);
        }

        return null;
    }

    private void validateDTOTutorial(TutorialDTO data) throws Exception {

        if (data.getCollegeCareer() == null) {
            throw new Exception("Error. El campo \"Programa\" es obligatorio.");
        } else if (data.getCollegeCareer().equals("")) {
            throw new Exception("Error. El campo \"Programa\" es obligatorio.");
        }

        if (data.getCourse() == null) {
            throw new Exception("Error. El campo \"Materia\" es obligatorio.");
        } else if (data.getCourse().equals("")) {
            throw new Exception("Error. El campo \"Materia\" es obligatorio.");
        }

        if (data.getInstructor() == null) {
            throw new Exception("Error. El campo \"Docente\" es obligatorio.");
        } else if (data.getInstructor().equals("")) {
            throw new Exception("Error. El campo \"Docente\" es obligatorio.");
        }

        if (data.getAvailabilityDate() == null) {
            throw new Exception("Error. Los campos \"Disponilidad sugerida\" son obligatorio.");
        } else if (data.getAvailabilityDate().size() == 0) {
            throw new Exception("Error. Los campos \"Disponilidad sugerida\" son obligatorio.");
        }

        if (data.getAvailabilityStartTime() == null) {
            throw new Exception("Error. Los campos \"Disponilidad sugerida\" son obligatorio.");
        } else if (data.getAvailabilityStartTime().equals("")) {
            throw new Exception("Error. Los campos \"Disponilidad sugerida\" son obligatorio.");
        }

        if (data.getAvailabilityEndTime() == null) {
            throw new Exception("Error. Los campos \"Disponilidad sugerida\" son obligatorio.");
        } else if (data.getAvailabilityEndTime().equals("")) {
            throw new Exception("Error. Los campos \"Disponilidad sugerida\" son obligatorio.");
        }

    }
}
