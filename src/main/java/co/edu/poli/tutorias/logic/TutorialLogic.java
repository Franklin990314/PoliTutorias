package co.edu.poli.tutorias.logic;

import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;

public interface TutorialLogic {

    TutorialDTO createTutorial(TutorialDTO data, UserDTO user) throws Exception;
}
