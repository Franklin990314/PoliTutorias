package co.edu.poli.tutorias.logic;

import co.edu.poli.tutorias.logic.dto.UserProfileDTO;

public interface UserProfileLogic {

    UserProfileDTO getProfile(Integer id) throws Exception;
}
