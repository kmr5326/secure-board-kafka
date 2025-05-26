package com.hg.secureBoardKafka.user.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name="user")
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="pk")
    private Long pk;

    @Column(name = "user_id", unique=true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    private String role;
}
