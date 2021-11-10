package jvm.pablohdz.todoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import jvm.pablohdz.todoapi.configuration.SwaggerConfiguration;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
public class TodoApiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(TodoApiApplication.class, args);
    }

}
