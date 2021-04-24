package co.edu.poli.tutorias.logic.util;

import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.logic.dto.UserProfileDTO;

public class Util {

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
}
