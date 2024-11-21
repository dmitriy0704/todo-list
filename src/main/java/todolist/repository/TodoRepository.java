package todolist.repository;

import org.springframework.data.repository.CrudRepository;
import todolist.entity.Todo;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface TodoRepository extends CrudRepository<Todo, Long> {

    List<Todo> findAll();
    Todo findByIdAndExecutor(Long id, String executor);

    List<Todo> findAllByExecutor(String executor);
    List<Todo> findAllByAuthor(String author);
}
