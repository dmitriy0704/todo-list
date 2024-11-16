package todolist.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import todolist.entity.Todo;
import todolist.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("/{id}")
    public Todo updateTodo(
            @PathVariable("id") Long id,
            @RequestBody Todo todo
    ){
        todo.setId(id);
        return todoRepository.save(todo);
    }
}
