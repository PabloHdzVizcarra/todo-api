package jvm.pablohdz.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import jvm.pablohdz.todoapi.entity.UserAdmin;

@Repository
public interface UserAdminRepository extends JpaRepository<UserAdmin, UUID>
{

    @Query(value = "SELECT * FROM user_admin u WHERE u.user_admin_email = :email",
            nativeQuery = true)
    Optional<UserAdmin> findByEmail(@Param("email") String email);

    @Query(nativeQuery = true,
            value = "SELECT * FROM user_admin u WHERE u.user_admin_username = :username")
    UserAdmin findByUsername(@Param("username") String username);
}
