package com.emre.mapper;

import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.dto.request.UserProfileUpdateRequestDto;
import com.emre.rabbitmq.model.CreateUserModel;
import com.emre.repository.entity.UserProfile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IUserProfileMapper {

    IUserProfileMapper INSTANCE= Mappers.getMapper(IUserProfileMapper.class);

    UserProfile toUserProfile(final UserProfileSaveRequestDto dto);

    //updateBean metodu için eskileri Ignore edebilen metot
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserProfile updateFromDtotoUserProfile(UserProfileUpdateRequestDto dto, @MappingTarget UserProfile userProfile);

    //rabbitmq save için
    UserProfile toUserProfile(final CreateUserModel model);
}
