package co.edu.poli.tutorias.logic.util;

import co.edu.poli.tutorias.entity.Tutorial;
import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.dto.UserProfileDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Util {

    public static final String DATE_FORMAT_HOUR = "yyyy-MM-dd HH:mm:ss";

    public static final String STATUS_CREATED = "Creada";
    public static final String STATUS_RETURNED = "Devuelta";
    public static final String STATUS_SCHEDULED = "Agendada";
    public static final String STATUS_RE_SCHEDULED = "Re-agendada";
    public static final String STATUS_CANCELLED = "Cancelada";
    public static final String STATUS_FINISHED = "Finalizada";

    public static UserProfileDTO mapEntityToDTOUserProfile(UserProfile data) {
        UserProfileDTO response = new UserProfileDTO();

        response.setId(data.getId());
        response.setName(data.getName());
        response.setCode(data.getCode());
        response.setMail(data.getMail());
        response.setProgram(data.getProgram());
        response.setCell_phone(data.getCell_phone());

        return response;
    }

    public static Tutorial mapDTOToEntityTutorial(TutorialDTO data, UserProfile userProfile) throws Exception {
        Tutorial response = new Tutorial();

        Date today = Calendar.getInstance().getTime();
        data.setStartTime(new SimpleDateFormat(DATE_FORMAT_HOUR).format(today));
        data.setStatus(STATUS_CREATED);

        response.setCollegeCareer(data.getCollegeCareer());
        response.setCourse(data.getCourse());
        response.setInstructor(data.getInstructor());
        response.setStudent(userProfile.getName());

        String availabilityDate = "";
        for (String day: data.getAvailabilityDate()) {
            if(availabilityDate.equals("")) availabilityDate = availabilityDate.concat(day);
            else availabilityDate = availabilityDate.concat(", "+day);
        }
        availabilityDate = availabilityDate.concat(" - "+data.getAvailabilityStartTime());
        availabilityDate = availabilityDate.concat(" - "+data.getAvailabilityEndTime());

        response.setAvailabilityDate(availabilityDate);
        response.setStartTime(new SimpleDateFormat(DATE_FORMAT_HOUR).parse(data.getStartTime()));
        response.setStatus(data.getStatus());
        response.setUserProfile(userProfile);

        return response;
    }

    public static TutorialDTO mapEntityToDTOTutorial(Tutorial data) {
        TutorialDTO response = new TutorialDTO();

        response.setId(data.getId());
        response.setCollegeCareer(data.getCollegeCareer());
        response.setCourse(data.getCourse());
        response.setInstructor(data.getInstructor());
        response.setStudent(data.getStudent());

        String availabilityDateParts[] = data.getAvailabilityDate().split("-");
        String availabilityDate[] = availabilityDateParts[0].split(", ");
        List<String> availabilityDateList = new ArrayList<>();
        for (String day: availabilityDate) availabilityDateList.add(day.trim());

        response.setAvailabilityDate(availabilityDateList);
        response.setAvailabilityStartTime(availabilityDateParts[1].trim());
        response.setAvailabilityEndTime(availabilityDateParts[2].trim());
        response.setStartTime(new SimpleDateFormat(DATE_FORMAT_HOUR).format(data.getStartTime()));
        response.setEndTime(data.getEndTime() != null ? new SimpleDateFormat(DATE_FORMAT_HOUR).format(data.getEndTime()) : "");
        response.setStatus(data.getStatus());
        response.setComment(data.getComment() != null ? data.getComment() : "");

        return response;
    }
}
