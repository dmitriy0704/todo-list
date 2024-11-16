package todolist.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.entity.Todo;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findAll();
}
