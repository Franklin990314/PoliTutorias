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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

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

}
