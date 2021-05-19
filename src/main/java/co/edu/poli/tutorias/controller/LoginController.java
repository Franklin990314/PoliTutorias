package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.logic.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
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

    @RequestMapping (value = "/api/logout", method = RequestMethod.GET)
    public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return new ResponseEntity<>("/logout", HttpStatus.OK);
    }
}
