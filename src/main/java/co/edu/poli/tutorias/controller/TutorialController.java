package co.edu.poli.tutorias.controller;

import co.edu.poli.tutorias.entity.Tutorial;
import co.edu.poli.tutorias.entity.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class TutorialController {

    @Autowired
    TutorialRepository tutorialRepository;

    @PostMapping("/tutorial")
    public Tutorial createTutorial(@RequestBody Tutorial tutorial) {
        return  tutorialRepository.save(tutorial);
    }

}
