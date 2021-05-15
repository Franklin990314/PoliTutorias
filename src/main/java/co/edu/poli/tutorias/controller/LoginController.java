package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.logic.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {

    @Autowired
    HttpSession session;

    @RequestMapping (value = "/login", method = RequestMethod.GET)
    public ResponseEntity<Object> login(HttpSession session) throws Exception {
        try {
            UserDTO user = (UserDTO) session.getAttribute("userInfo");

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
