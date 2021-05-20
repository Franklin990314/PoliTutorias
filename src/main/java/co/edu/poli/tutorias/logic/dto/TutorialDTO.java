package co.edu.poli.tutorias.logic.dto;

import java.util.List;

public class TutorialDTO {

    private Integer id;
    private String collegeCareer;
    private String course;
    private String instructor;
    private String student;
    private String scheduledDate;
    private List<String> availabilityDate;
    private String availabilityStartTime;
    private String availabilityEndTime;
    private String startTime;
    private String endTime;
    private String status;
    private String comment;

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

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(String scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public List<String> getAvailabilityDate() {
        return availabilityDate;
    }

    public void setAvailabilityDate(List<String> availabilityDate) {
        this.availabilityDate = availabilityDate;
    }

    public String getAvailabilityStartTime() {
        return availabilityStartTime;
    }

    public void setAvailabilityStartTime(String availabilityStartTime) {
        this.availabilityStartTime = availabilityStartTime;
    }

    public String getAvailabilityEndTime() {
        return availabilityEndTime;
    }

    public void setAvailabilityEndTime(String availabilityEndTime) {
        this.availabilityEndTime = availabilityEndTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
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
}
