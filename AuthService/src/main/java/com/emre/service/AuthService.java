package com.emre.service;

import com.emre.dto.request.LoginRequestDto;
import com.emre.dto.request.RegisterRequestDto;
import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.exception.AuthException;
import com.emre.exception.ErrorType;
import com.emre.manager.IUserProfileManager;
import com.emre.mapper.IAuthMapper;
import com.emre.rabbitmq.model.CreateUserModel;
import com.emre.rabbitmq.producer.CreateUserProducer;
import com.emre.repository.IAuthRepository;
import com.emre.repository.entity.Auth;
import com.emre.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final IUserProfileManager userProfileManager;
    private final CreateUserProducer createUserProducer;

    public AuthService(IAuthRepository authRepository, IUserProfileManager userProfileManager, CreateUserProducer createUserProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userProfileManager = userProfileManager;
        this.createUserProducer = createUserProducer;
    }

    public Optional<Auth> doLogin(LoginRequestDto dto){
         return authRepository.findOptionalByEmailAndPassword(dto.getEmail(),dto.getPassword());
    }

    public void register(RegisterRequestDto dto){
        //aynı email veritabanımda var mı kontrol ediyorum, var ise hata fırlatıcam
        if (authRepository.existsByEmail(dto.getEmail()))
            throw new AuthException(ErrorType.ERROR_EMAIL);
        //if {} bloğu açmaya gerek yok çünkü varsa email hata fırlatıcak ve save yapmıyacak
        //ama yok ise de throw satırına girmeyip alt satırda save metodunu çalıştıracak.
        Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
        //yok ise ServiceManagerdaki Save Metodu ile kayıt ediyorum.
        save(auth);
        //managerdaki save metodu için builder ile tanımlama işlemini yapıyorum.
        //yukaridaki id bizim diger tarafta authId ye eşit oldugu için böyle tanımlayabildik.
        /*UserProfileSaveRequestDto requestDto= UserProfileSaveRequestDto.builder()
                .username(auth.getUsername())
                .email(auth.getEmail())
                .authId(auth.getId())
                .build();*/
        //openfeign uzerinden göndermeye yarıyordu
        //UserProfileSaveRequestDto requestDto=IAuthMapper.INSTANCE.fromAuthToUserDto(auth);
        //userProfileManager.save(requestDto);

        //rabbitmq üzerinden yollamaya yarıyor. mapper yazdım buna özel.
        CreateUserModel createUserModel=IAuthMapper.INSTANCE.toModel(auth);
        createUserProducer.converAndSendData(createUserModel);

    }
}
