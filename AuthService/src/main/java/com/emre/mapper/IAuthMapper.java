package com.emre.mapper;

import com.emre.dto.request.RegisterRequestDto;
import com.emre.dto.request.UserProfileSaveRequestDto;
import com.emre.rabbitmq.model.CreateUserModel;
import com.emre.repository.entity.Auth;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IAuthMapper {

    IAuthMapper INSTANCE= Mappers.getMapper(IAuthMapper.class);

    Auth toAuth(final RegisterRequestDto dto);

    @Mapping(source = "id", target = "authId")
    UserProfileSaveRequestDto fromAuthToUserDto(final Auth auth);

    //rabbitmq ile iletmek için
    @Mapping(source = "id", target = "authId")
    CreateUserModel toModel(final Auth auth);
}
