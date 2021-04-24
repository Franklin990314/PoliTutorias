package co.edu.poli.tutorias.logic.impl;

import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.entity.repository.UserProfileRepository;
import co.edu.poli.tutorias.logic.UserProfileLogic;
import co.edu.poli.tutorias.logic.dto.UserProfileDTO;
import co.edu.poli.tutorias.logic.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileLogicImpl implements UserProfileLogic {

    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public UserProfileDTO getProfile(Integer id) throws Exception {

        UserProfileDTO response;
        try {
            UserProfile data = userProfileRepository.findById(id).get();

            response = Util.mapEntityToDTOUserProfile(data);
        } catch (Exception exc) {
            throw new Exception("Error. No se pudo obtener el perfil del usuario.");
        }

        return response;
    }
}
