package co.edu.poli.tutorias.logic;

import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;

import java.util.List;

public interface TutorialLogic {

    TutorialDTO createTutorial(TutorialDTO data, UserDTO user) throws Exception;
    List<TutorialDTO> getTutorial(UserDTO user) throws Exception;
    TutorialDTO getTutorial(Integer id, UserDTO user) throws Exception;
}
