package todolist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import todolist.entity.Todo;
import todolist.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> todoById(@PathVariable("id") Long id) {
        Optional<Todo> optional = todoRepository.findById(id);
        return optional.map(todo ->
                new ResponseEntity<>(todo, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping
    public ResponseEntity<List<Todo>> getToDos(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>
                (this.todoRepository.findAll(),
                        headers, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public Todo updateTodo(
            @PathVariable("id") Long id,
            @RequestBody Todo todo
    ) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    @PatchMapping(path = "/{id}", consumes = "application/json")
    public Todo pathTodo(
            @PathVariable("id") Long id,
            @RequestBody Todo patch
    ) {
        Todo todo = todoRepository.findById(id).get();
        if (todo.getTitle() != null) {
            todo.setTitle(patch.getTitle());
        }

        if (todo.getDescription() != null) {
            todo.setDescription(patch.getDescription());
        }
        return todoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable("id") Long id) {
        try {
            todoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
