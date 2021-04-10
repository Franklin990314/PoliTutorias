package co.edu.poli.tutorias.entity;

import javax.persistence.*;

@Entity
@Table(name="user_profile")
public class UserProfile {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="code")
    private String code;

    @Column(name="mail")
    private String mail;

    @Column(name="program")
    private String program;

    @Column(name="cell_phone")
    private String cell_phone;

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
}
