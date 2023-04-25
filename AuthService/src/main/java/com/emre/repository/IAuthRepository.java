package com.emre.repository;

import com.emre.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {

    //Kullanıcı login olurken bize email ve şifre verir bizde onu
    //veritabanına sorgu atarak kontrol ederiz bu durumun sorgu metodu;
    Optional<Auth> findOptionalByEmailAndPassword(String email, String password);

    //aynı mail ile kayıt olmuş mu diye bakmak gereklidir.
    //böyle bir email var mı? varsa true dönecek ona göre kullanıcaz bunu
    @Query("select count(a)>0 from Auth a where a.username = ?1")
    Boolean existsByEmailQuery(String email);

    //yukardaki sorgunun düz spring anahtar kelimeleri ile sorgusu;
    Boolean existsByEmail(String email);

}
