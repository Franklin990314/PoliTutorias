package co.edu.poli.tutorias.logic.impl;

import co.edu.poli.tutorias.entity.Course;
import co.edu.poli.tutorias.entity.UserInformation;
import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.entity.repository.CourseRepository;
import co.edu.poli.tutorias.entity.repository.UserInformationRepository;
import co.edu.poli.tutorias.entity.repository.UserProfileRepository;
import co.edu.poli.tutorias.logic.ParameterLogic;
import co.edu.poli.tutorias.logic.dto.ParameterDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;
import co.edu.poli.tutorias.logic.dto.VariableDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParameterLogicImpl implements ParameterLogic {

    @Autowired
    UserInformationRepository userInformationRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Override
    public ParameterDTO getParameterCourses(UserDTO userDTO) throws Exception {
        ParameterDTO response = new ParameterDTO();
        response.setParameter(new ArrayList<>());

        UserInformation userInformation = userInformationRepository.findById(userDTO.getId()).get();

        for (Course course : userInformation.getStudentCourse()) {
            VariableDTO variable = new VariableDTO();
            variable.setId(course.getCode());
            variable.setValue(course.getName());
            response.getParameter().add(variable);
        }

        return response;
    }

    @Override
    public ParameterDTO getParameterInstructors(String id, UserDTO userDTO) throws Exception {
        ParameterDTO response = new ParameterDTO();
        response.setParameter(new ArrayList<>());

        List<Course> courseList = courseRepository.findByCode(id);

        for (Course course : courseList) {
            UserProfile instructorProfile = userProfileRepository.findById(course.getInstructorInformation().getId()).get();
            VariableDTO variable = new VariableDTO();
            variable.setId(String.valueOf(instructorProfile.getId()));
            variable.setValue(instructorProfile.getName());
            response.getParameter().add(variable);
        }

        return response;
    }
}
