package co.edu.poli.tutorias.logic.impl;

import co.edu.poli.tutorias.entity.Tutorial;
import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.entity.repository.TutorialRepository;
import co.edu.poli.tutorias.entity.repository.UserProfileRepository;
import co.edu.poli.tutorias.logic.TutorialLogic;
import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;
import co.edu.poli.tutorias.logic.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialLogicImpl implements TutorialLogic {

    @Autowired
    TutorialRepository tutorialRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public TutorialDTO createTutorial(TutorialDTO data, UserDTO user) throws Exception {

        this.validateDTOTutorial(data);
        Tutorial tutorial;
        try {
            tutorial = Util.mapDTOToEntityTutorial(data);
            tutorialRepository.save(tutorial);
            data.setId(tutorial.getId());
        } catch (Exception exc) {
            throw new Exception("Error. Se encontro un problema al crear la tutoria.");
        }

        try {
            UserProfile userProfile = userProfileRepository.findById(user.getId()).get();
            userProfile.getTutorials().add(tutorial);
            userProfileRepository.save(userProfile);
        } catch (Exception exc) {
            throw new Exception("Error. Se encontro un problema al asociar la tutoria.");
        }

        return data;
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
