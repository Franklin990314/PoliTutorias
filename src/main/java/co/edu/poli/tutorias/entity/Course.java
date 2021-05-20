package co.edu.poli.tutorias.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="courses")
public class Course {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="code")
    private String code;

    @Column(name="semester_code")
    private String semesterCode;

    @Column(name="name")
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "courses_users", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<UserInformation> studentInformation;

    @ManyToOne
    @JoinColumn(name = "INSTRUCTOR_ID")
    @JsonIgnore
    private UserInformation instructorInformation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSemesterCode() {
        return semesterCode;
    }

    public void setSemesterCode(String semesterCode) {
        this.semesterCode = semesterCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<UserInformation> getStudentInformation() {
        return studentInformation;
    }

    public void setStudentInformation(Set<UserInformation> studentInformation) {
        this.studentInformation = studentInformation;
    }

    public UserInformation getInstructorInformation() {
        return instructorInformation;
    }

    public void setInstructorInformation(UserInformation instructorInformation) {
        this.instructorInformation = instructorInformation;
    }
}
