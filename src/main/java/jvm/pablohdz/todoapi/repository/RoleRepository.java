package jvm.pablohdz.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jvm.pablohdz.todoapi.entity.Role;
import jvm.pablohdz.todoapi.entity.RoleUser;

@Repository
public interface RoleRepository extends JpaRepository<RoleUser, Long>
{

    RoleUser findByName(Role name);
}
