package jvm.pablohdz.todoapi.mapper;

import org.mapstruct.Mapper;

import jvm.pablohdz.todoapi.dto.UserAdminDto;
import jvm.pablohdz.todoapi.entity.UserAdmin;

@Mapper(componentModel = "spring")
public interface UserAdminMapper
{
    UserAdminDto userAdminToDto(UserAdmin userAdmin);
}
