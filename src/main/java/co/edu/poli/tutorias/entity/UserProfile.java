package co.edu.poli.tutorias.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="user_profile")
public class UserProfile {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="code")
    private String code;

    @Column(name="mail")
    private String mail;

    @Column(name="faculty")
    private String faculty;

    @Column(name="program")
    private String program;

    @Column(name="cell_phone")
    private String cell_phone;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private Set<Tutorial> tutorials;

    @OneToMany(mappedBy = "instructorProfile", cascade = CascadeType.ALL)
    private Set<Tutorial> instructorTutorials;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getCell_phone() {
        return cell_phone;
    }

    public void setCell_phone(String cell_phone) {
        this.cell_phone = cell_phone;
    }

    public Set<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(Set<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }

    public Set<Tutorial> getInstructorTutorials() {
        return instructorTutorials;
    }

    public void setInstructorTutorials(Set<Tutorial> instructorTutorials) {
        this.instructorTutorials = instructorTutorials;
    }
}
