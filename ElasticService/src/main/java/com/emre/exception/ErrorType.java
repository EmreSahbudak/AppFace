package com.emre.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorType {

    ERROR_PASSWORD(2004,"Girmiş olduğunuz şifreler uyuşmamaktadır.",HttpStatus.NOT_FOUND),

    ERROR_NOT_FOUND(2003,"Kullanıcı Bulunamamıştır.",HttpStatus.NOT_FOUND),

    ERROR_EMAIL(2005,"Bu email daha önce kayıt edilmiştir. " +
            "Lütfen oturum açınız.",HttpStatus.BAD_REQUEST),

    ERROR_INVALID_TOKEN(3000,"Geçersiz Token",HttpStatus.UNAUTHORIZED),

    BAD_REQUEST(4000,"Geçersiz istek yada parametre",HttpStatus.BAD_REQUEST),

    ERROR(9000,"Beklenmeyen bir hata oluştu. Lütfen daha sonra deneyiniz",
            HttpStatus.INTERNAL_SERVER_ERROR);

    int code;

    String message;

    HttpStatus httpStatus;
}
