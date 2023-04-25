package com.emre.service;

import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.dto.request.UserProfileUpdateRequestDto;
import com.emre.exception.ErrorType;
import com.emre.exception.UserException;
import com.emre.mapper.IUserProfileMapper;
import com.emre.rabbitmq.model.CreateUserModel;
import com.emre.repository.IUserProfileRepository;
import com.emre.repository.entity.UserProfile;
import com.emre.utility.JwtTokenManager;
import com.emre.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final IUserProfileRepository userProfileRepository;
    //private final TokenCreator tokenCreator;
    private final JwtTokenManager jwtTokenManager;

    public UserProfileService(IUserProfileRepository userProfileRepository, JwtTokenManager jwtTokenManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
    }


    public void save(UserProfileSaveRequestDto dto){
        save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
    }
    public void save(CreateUserModel model){
        save(IUserProfileMapper.INSTANCE.toUserProfile(model));
    }

    public void update(UserProfileUpdateRequestDto dto){
        Optional<Long> authId=jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(authId.get());
        if (userProfile.isPresent()){
            UserProfile profile=userProfile.get();
            profile.setAddress(dto.getAddress());
            profile.setAvatar(dto.getAvatar());
            profile.setGender(dto.getGender());
            profile.setName(dto.getName());
            profile.setPhone(dto.getPhone());
            profile.setSurname(dto.getSurname());
            update(profile);
        }
    }
    public void updateBean(UserProfileUpdateRequestDto dto){
        Optional<Long> authId=jwtTokenManager.getIdFromToken(dto.getToken());
        if (authId.isEmpty())
            throw new UserException(ErrorType.ERROR_INVALID_TOKEN);
        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(authId.get());
        if (userProfile.isEmpty()){
            throw new UserException(ErrorType.ERROR_NOT_FOUND);
        }
        update(IUserProfileMapper.INSTANCE.updateFromDtotoUserProfile(dto,userProfile.get()));
    }

    //redis cachleme mekanizması denemek için
    @Cacheable(value = "getnametoupper")
    public String getNameToUpper(String name){
        try{
            Thread.sleep(3000);
        }catch (Exception ex){

        }
        return name.toUpperCase();
    }
    //önbelleğe alınan verileri temizleme
    @CacheEvict(value = "getnametoupper",allEntries = true)
    public void clearCacheToUpper(){
        System.out.println("Tüm cache i temizledim");
    }








}
