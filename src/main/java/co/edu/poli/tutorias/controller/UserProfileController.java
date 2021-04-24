package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.logic.UserProfileLogic;
import co.edu.poli.tutorias.logic.dto.UserDTO;
import co.edu.poli.tutorias.logic.dto.UserProfileDTO;
import co.edu.poli.tutorias.service.handler.ExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/")
public class UserProfileController {

    @Autowired
    UserProfileLogic userProfileLogic;

    @RequestMapping (value = "/profile/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getProfile(@PathVariable Integer id, HttpSession session) {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            if (user.getId().equals(id)) {
                UserProfileDTO response = userProfileLogic.getProfile(id);
                if (response != null) {
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NON_AUTHORITATIVE_INFORMATION);
            }
        } catch (Exception exc) {
            ExceptionHandler exceptionHandler = new ExceptionHandler();
            return exceptionHandler.toResponse(exc, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
