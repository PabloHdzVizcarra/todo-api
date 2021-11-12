package jvm.pablohdz.todoapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jvm.pablohdz.todoapi.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>
{
    /**
     * Find all todos with match by apikey
     *
     * @param apikey the user apikey
     * @return a list with all found todos
     */
    @Query(nativeQuery = true,
            value = "SELECT * FROM todo WHERE user_api_key = :apikey")
    List<Todo> findByApiKey(@Param("apikey") String apikey);

    @Query(nativeQuery = (true),
            value = "SELECT * FROM todo WHERE todo_name = :todoName")
    Optional<Todo> findByName(@Param("todoName") String todoName);

    @Query(nativeQuery = true,
            value = "DELETE FROM todo WHERE todo_name = :todoName")
    void deleteByName(@Param("todoName") String todoName);

}
