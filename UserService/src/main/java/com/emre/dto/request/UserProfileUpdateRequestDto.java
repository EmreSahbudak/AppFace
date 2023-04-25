package com.emre.dto.request;

import com.emre.repository.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileUpdateRequestDto {
    @NotEmpty
    @Size(min = 5, max = 400)
    String token;
    String name;
    String surname;
    String phone;
    String address;
    String avatar;
    Gender gender;
}
