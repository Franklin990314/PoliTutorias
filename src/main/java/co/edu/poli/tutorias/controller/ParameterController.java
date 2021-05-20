package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.logic.ParameterLogic;
import co.edu.poli.tutorias.logic.dto.ParameterDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;
import co.edu.poli.tutorias.service.handler.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/parameter")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ParameterController {

    @Autowired
    ParameterLogic parameterLogic;

    @RequestMapping (value = "/courses", method = RequestMethod.GET)
    public ResponseEntity<Object> getParameterCourses(HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            ParameterDTO response = parameterLogic.getParameterCourses(user);
            if (response != null) {
                if (response.getParameter() != null) {
                    if(response.getParameter().size() != 0){
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                }
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping (value = "/instructors/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getParameterInstructors(@PathVariable String id, HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            ParameterDTO response = parameterLogic.getParameterInstructors(id, user);
            if (response != null) {
                if (response.getParameter() != null) {
                    if(response.getParameter().size() != 0){
                        return new ResponseEntity<>(response, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                }
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
