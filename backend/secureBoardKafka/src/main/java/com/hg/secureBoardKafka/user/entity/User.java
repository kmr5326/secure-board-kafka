package com.hg.secureBoardKafka.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
