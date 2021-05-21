package co.edu.poli.tutorias.logic.util;

import co.edu.poli.tutorias.entity.Course;
import co.edu.poli.tutorias.entity.Tutorial;
import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.dto.UserProfileDTO;

import java.text.SimpleDateFormat;
import java.util.*;

public class Util {

    public static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH:mm:ss";

    public static final String STATUS_CREATED = "Creada";
    public static final String STATUS_RETURNED_STUDENT = "Devuelta por Alumno";
    public static final String STATUS_RETURNED_TEACHER = "Devuelta por Instructor";
    public static final String STATUS_SCHEDULED = "Agendada";
    public static final String STATUS_RE_SCHEDULED = "Re-agendada";
    public static final String STATUS_FINISHED = "Finalizada";
    public static final String ROLE_STUDENT = "STUDENT";
    public static final String ROLE_TEACHER = "TEACHER";
    public static final String ACTION_CREATION = "Creation";
    public static final String ACTION_MODIFICATION = "Modification";
    public static final String ACTION_DELETE = "Delete";
    public static final String ACTION_ACCEPTATION = "Acceptation";

    public static UserProfileDTO mapEntityToDTOUserProfile(UserProfile data) {
        UserProfileDTO response = new UserProfileDTO();

        response.setId(data.getId());
        response.setName(data.getName());
        response.setCode(data.getCode());
        response.setMail(data.getMail());
        response.setFaculty(data.getFaculty());
        response.setProgram(data.getProgram() != null ? data.getProgram() : "");
        response.setCell_phone(data.getCell_phone());

        return response;
    }

    public static Tutorial mapDTOToEntityTutorial(TutorialDTO data, UserProfile userProfile, UserProfile instructorProfile, Course course) throws Exception {
        Tutorial response = new Tutorial();

        Date today = Calendar.getInstance().getTime();
        data.setStartTime(new SimpleDateFormat(DATE_FORMAT_HOUR).format(today));
        data.setStatus(STATUS_CREATED);

        response.setCollegeCareer(data.getCollegeCareer());
        response.setCourse(course.getName());
        response.setInstructor(instructorProfile.getName());
        response.setStudent(userProfile.getName());
        response.setAvailabilityDate(generateAvailabilityDate(data));
        response.setStartTime(new SimpleDateFormat(DATE_FORMAT_HOUR).parse(data.getStartTime()));
        response.setStatus(data.getStatus());
        response.setUserProfile(userProfile);
        response.setInstructorProfile(instructorProfile);

        return response;
    }

    public static TutorialDTO mapEntityToDTOTutorial(Tutorial data) {
        TutorialDTO response = new TutorialDTO();

        response.setId(data.getId());
        response.setIdString(formatCaseNumber(data.getId().intValue()));
        response.setCollegeCareer(data.getCollegeCareer());
        response.setCourse(data.getCourse());
        response.setInstructor(data.getInstructor());
        response.setStudent(data.getStudent());

        String availabilityDateParts[] = data.getAvailabilityDate().split("-");
        String availabilityDate[] = availabilityDateParts[0].split(", ");
        List<String> availabilityDateList = new ArrayList<>();
        for (String day: availabilityDate) availabilityDateList.add(day.trim());

        response.setScheduledDate(data.getScheduledDate() != null ? new SimpleDateFormat(DATE_FORMAT_HOUR).format(data.getScheduledDate()): "");
        response.setAvailabilityDate(availabilityDateList);
        response.setAvailabilityStartTime(availabilityDateParts[1].trim());
        response.setAvailabilityEndTime(availabilityDateParts[2].trim());
        response.setStartTime(new SimpleDateFormat(DATE_FORMAT_HOUR).format(data.getStartTime()));
        response.setEndTime(data.getEndTime() != null ? new SimpleDateFormat(DATE_FORMAT_HOUR).format(data.getEndTime()) : "");
        response.setStatus(data.getStatus());
        response.setComment(data.getComment() != null ? data.getComment() : "");

        return response;
    }

    public static String formatCaseNumber(int caseNumber){
        String response = "TUT-";

        Formatter formatter = new Formatter();
        formatter.format("%04d", caseNumber);
        response += formatter.toString();

        return response;
    }

    public static String getRole(String role){
        if (role.equals("ROLE_STUDENT")) {
            return ROLE_STUDENT;
        } else if (role.equals("ROLE_TEACHER")) {
            return ROLE_TEACHER;
        }
        return "NO_ROLE";
    }

    public static String generateAvailabilityDate(TutorialDTO data) {
        String response = "";

        for (String day: data.getAvailabilityDate()) {
            if(response.equals("")) response = response.concat(day);
            else response = response.concat(", "+day);
        }
        response = response.concat(" - "+data.getAvailabilityStartTime());
        response = response.concat(" - "+data.getAvailabilityEndTime());

        return response;
    }
}
