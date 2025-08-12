package com.example.springexample.mapper;

import com.example.springexample.dto.UsersDto;
import com.example.springexample.dto.WeathersDto;
import com.example.springexample.model.Locations;
import com.example.springexample.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

//@Mapper(componentModel = "spring")
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface Mapping {

//    Mapping mapping= Mappers.getMapper(Mapping.class);

    @org.mapstruct.Mapping(target = "id", source = "id")
    @org.mapstruct.Mapping(target = "login", source = "login")
    UsersDto toUsersDto(Users users);


//    @org.mapstruct.Mapping(target = "latitude", source = "latitude")
//    @org.mapstruct.Mapping(target = "longitude", source = "longitude")

    @org.mapstruct.Mapping(target = "user", ignore = true)
    @org.mapstruct.Mapping(target = "id", ignore = true)
    Locations toLocationsDto(WeathersDto weathersDto);
}
