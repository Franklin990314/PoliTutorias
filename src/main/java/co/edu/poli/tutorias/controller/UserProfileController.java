package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.entity.User;
import co.edu.poli.tutorias.entity.UserProfile;
import co.edu.poli.tutorias.entity.repository.UserProfileRepository;
import co.edu.poli.tutorias.entity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UserProfileController {

    @Autowired
    UserProfileRepository userProfileRepository;

    @GetMapping("/profile/{id}")
    public UserProfile getProfile(@PathVariable Integer id) {
        return userProfileRepository.findById(id).get();
    }
}
