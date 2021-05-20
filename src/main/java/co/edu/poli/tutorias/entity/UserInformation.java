package co.edu.poli.tutorias.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="userInformation")
public class UserInformation {

    @Id
    @Column(name="id")
    private Integer id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "studentInformation")
    private Set<Course> studentCourse;

    @OneToMany(mappedBy = "instructorInformation", cascade = CascadeType.ALL)
    private Set<Course> instructorCourse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Course> getStudentCourse() {
        return studentCourse;
    }

    public void setStudentCourse(Set<Course> studentCourse) {
        this.studentCourse = studentCourse;
    }

    public Set<Course> getInstructorCourse() {
        return instructorCourse;
    }

    public void setInstructorCourse(Set<Course> instructorCourse) {
        this.instructorCourse = instructorCourse;
    }
}
