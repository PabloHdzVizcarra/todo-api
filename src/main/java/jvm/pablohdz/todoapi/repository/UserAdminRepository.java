package jvm.pablohdz.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import jvm.pablohdz.todoapi.entity.UserAdmin;

@Repository
public interface UserAdminRepository extends JpaRepository<UserAdmin, UUID>
{

}
