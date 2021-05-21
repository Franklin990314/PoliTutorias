package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.logic.TutorialLogic;
import co.edu.poli.tutorias.logic.dto.TutorialDTO;
import co.edu.poli.tutorias.logic.dto.TutorialsDTO;
import co.edu.poli.tutorias.logic.dto.UserDTO;
import co.edu.poli.tutorias.service.handler.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class TutorialController {

    @Autowired
    TutorialLogic tutorialLogic;

    @RequestMapping (value = "/tutorial", method = RequestMethod.POST)
    public ResponseEntity<Object> createTutorial(@RequestBody TutorialDTO tutorialDTO, HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            TutorialDTO response = tutorialLogic.createTutorial(tutorialDTO, user);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping (value = "/tutorial", method = RequestMethod.GET)
    public ResponseEntity<Object> getTutorial(HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            List<TutorialDTO> data = tutorialLogic.getTutorial(user);
            if (data != null) {
                if(data.size() != 0){
                    TutorialsDTO response = new TutorialsDTO();
                    response.setTutorialList(data);
                    return new ResponseEntity<>(response, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping (value = "/tutorial/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getTutorialById(@PathVariable Integer id, HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            TutorialDTO response = tutorialLogic.getTutorial(id, user);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping (value = "/tutorial/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateTutorial(@PathVariable Integer id, @RequestBody TutorialDTO tutorialDTO, HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            TutorialDTO response = tutorialLogic.updateTutorial(id, tutorialDTO, user);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping (value = "/tutorial/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteTutorialById(@PathVariable Integer id, HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            boolean response = tutorialLogic.deleteTutorial(id, user);
            if (response) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }
    }

}
