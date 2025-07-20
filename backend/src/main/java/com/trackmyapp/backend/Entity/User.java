package com.trackmyapp.backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    
    @Column(nullable = false)
    private String password;

    private String fullName;


    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @PrePersist
    protected void onCreate(){
        createTime=LocalDateTime.now();
        updateTime=LocalDateTime.now();
    }

    @PostPersist
    protected void onUpdate(){
        updateTime=LocalDateTime.now();
    }

}
