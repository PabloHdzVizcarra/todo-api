package jvm.pablohdz.todoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

import jvm.pablohdz.todoapi.entity.Role;
import jvm.pablohdz.todoapi.entity.RoleUser;
import jvm.pablohdz.todoapi.repository.RoleRepository;

@SpringBootApplication
public class TodoApiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TodoApiApplication.class, args);
    }

}
