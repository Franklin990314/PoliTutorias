package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.entity.Tutorial;
import co.edu.poli.tutorias.entity.repository.TutorialRepository;
import co.edu.poli.tutorias.logic.TutorialLogic;
import co.edu.poli.tutorias.logic.dto.TutorialDTO;
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
public class TutorialController {

    @Autowired
    TutorialLogic tutorialLogic;

    @PostMapping("/tutorial")
    public ResponseEntity<Object> createTutorial(@RequestBody TutorialDTO tutorialDTO, HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            TutorialDTO response = tutorialLogic.createTutorial(tutorialDTO, user);
            if (response != null) {
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/tutorial")
    public ResponseEntity<Object> getTutorial(HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            List<TutorialDTO> response = tutorialLogic.getTutorial(user);
            if (response != null) {
                if(response.size() != 0){
                    return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @GetMapping("/tutorial/{id}")
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

}
