package co.edu.poli.tutorias.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="tutorials")
public class Tutorial {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="college_career", nullable = false)
    private String collegeCareer;

    @Column(name="course", nullable = false)
    private String course;

    @Column(name="student", nullable = false)
    private String student;

    @Column(name="instructor", nullable = false)
    private String instructor;

    @Column(name="scheduled_date")
    private String scheduledDate;

    @Column(name="availability_date", nullable = false)
    private String availabilityDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time", nullable = false)
    @DateTimeFormat(pattern = "YYYY-MM-DD HH:MM:SS")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    @DateTimeFormat(pattern = "YYYY-MM-DD HH:MM:SS")
    private Date endTime;

    @Column(name="status", nullable = false)
    private String status;

    @Column(name="comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonIgnore
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "INSTRUCTOR_ID")
    @JsonIgnore
    private UserProfile instructorProfile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCollegeCareer() {
        return collegeCareer;
    }

    public void setCollegeCareer(String collegeCareer) {
        this.collegeCareer = collegeCareer;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public String getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(String availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public UserProfile getInstructorProfile() {
        return instructorProfile;
    }

    public void setInstructorProfile(UserProfile instructorProfile) {
        this.instructorProfile = instructorProfile;
    }
}
