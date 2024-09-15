package com.finki.journalingapplication.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "userjournal")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String password;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Notes> listOfNotes;
    @OneToMany(mappedBy = "user")
    private List<ToDo> todos;

    @OneToMany(mappedBy = "user")
    private List<Diary> diaries;





    public User(String name, String surname, String email,String username, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.email=email;

        listOfNotes=new ArrayList<>();
        diaries=new ArrayList<>();
        todos=new ArrayList<>();
    }

    public User() {
    }
}
