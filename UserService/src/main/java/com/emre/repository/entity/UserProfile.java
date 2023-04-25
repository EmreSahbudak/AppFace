package com.emre.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document
public class UserProfile {
    @Id
    private String id;
    private Long authId;
    private String username;
    private String email;
    private String name;
    private String surname;
    private String phone;
    private String address;
    private String avatar;

    private Gender gender;


}
