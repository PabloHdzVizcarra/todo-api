package jvm.pablohdz.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

import jvm.pablohdz.todoapi.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>
{
    @Query(
            nativeQuery = true,
            value = "SELECT * FROM todo t WHERE t.user_api_key =: apikey"
    )
    Collection<Todo> findByApiKey(@Param("apikey") String apikey);
}
