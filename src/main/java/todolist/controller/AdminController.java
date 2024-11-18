package todolist.controller;


import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/v1/api/admin/todos")
public class AdminController {

    private final TodoRepository todoRepository;

    public AdminController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Todo> todoById(@PathVariable("id") Long id) {
        Optional<Todo> optional = todoRepository.findById(id);
        return optional.map(todo ->
                new ResponseEntity<>(todo, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<Todo>> getToDos(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>
                (this.todoRepository.findAll(),
                        headers, HttpStatus.OK);
    }


    @PutMapping(path = "/put/{id}", consumes = "application/json")
    public Todo updateTodo(
            @PathVariable("id") Long id,
            @RequestBody Todo todo
    ) {
        todo.setId(id);
        return todoRepository.save(todo);
    }

    @PatchMapping(path = "/path/{id}", consumes = "application/json")
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

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable("id") Long id) {
        try {
            todoRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
