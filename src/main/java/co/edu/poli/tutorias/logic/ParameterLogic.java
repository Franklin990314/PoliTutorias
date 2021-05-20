package co.edu.poli.tutorias.logic;

import co.edu.poli.tutorias.logic.dto.ParameterDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;

public interface ParameterLogic {
    ParameterDTO getParameterCourses(UserDTO userDTO) throws Exception;
    ParameterDTO getParameterInstructors(String id, UserDTO userDTO) throws Exception;
}
