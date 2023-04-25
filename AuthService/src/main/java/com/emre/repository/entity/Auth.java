package com.emre.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tblauth")
public class Auth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Long createat;
    private int status;
    /* Status Durumu
    * 0- Kullanıcı kayıt edilmiş ama onaylanmamış
     * 1- Kullanıcı kayıt edilmiş ve onaylanmış
     * 2- Kullanıcı kayıt edilmiş ve onaylanmış fakat hesabı kilitlenmiş
     * 3- Kullanıcı kayıt edilmiş ve onaylanmış fakat hesabı silinmiş
     */
    /* Bu Status durumu Enum bir Class ile ACTIVE PENDING gibi de tutulabilirdi. Tercih
    @Enumerated(EnumType.STRING)
    Status status;
    * */

}
