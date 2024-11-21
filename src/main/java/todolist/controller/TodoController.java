package todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import todolist.entity.Todo;
import todolist.exceptions.Answer;
import todolist.records.Comment;
import todolist.records.Status;
import todolist.repository.TodoRepository;
import todolist.services.TodoService;
import todolist.services.UserService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Tag(name = "Общий доступ для авторизованных")
@RequestMapping("/v1/api/todos")
public class TodoController {
    private final TodoRepository todoRepository;
    private final TodoService todoService;

    public TodoController(TodoRepository todoRepository, TodoService todoService) {
        this.todoRepository = todoRepository;
        this.todoService = todoService;
    }

    @Operation(summary = "Создание задачи")
    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createTodo(@RequestBody @Valid Todo todo) {
        todo = todoService.createTodo(todo);
        return todoRepository.save(todo);
    }

    @Operation(summary = "Просмотр задачи по ID")
    @GetMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<Todo> todoById(@PathVariable("id") Long id) {
        Optional<Todo> optional = todoRepository.findById(id);
        return optional.map(todo ->
                new ResponseEntity<>(todo, HttpStatus.OK)).orElseGet(() ->
                new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Просмотр всех задач")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Todo>> getTodos(
     @RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(this.todoRepository.findAll(),
                headers, HttpStatus.OK);
    }

    @Operation(summary = "Просмотр всех задач исполнителя")
    @GetMapping(value = "/all-executor", params = "executor")
    public ResponseEntity<List<Todo>> getTodosByExecutor(@RequestHeader HttpHeaders headers,
                                                         @RequestParam("executor") String executor) {
        return new ResponseEntity<>(this.todoRepository.findAllByExecutor(executor),
                headers, HttpStatus.OK);
    }

    @Operation(summary = "Просмотр всех задач автора")
    @GetMapping(value = "/all-author", params = "author")
    public ResponseEntity<List<Todo>> getTodosByAuthor(@RequestHeader HttpHeaders headers,
                                                       @RequestParam("author") String author) {
        return new ResponseEntity<>(this.todoRepository.findAllByAuthor(author),
                headers, HttpStatus.OK);
    }


    @Operation(summary = "Обновление статуса задачи")
    @PatchMapping(path = "/update-status/{id}", consumes = "application/json")
    public @ResponseBody Answer updateStatus(@PathVariable("id") Long id, @RequestBody @Valid Status patch) {
        return todoService.updateStatus(id, patch);
    }


    @Operation(summary = "Обновление комментария")
    @PatchMapping(path = "/update-comment/{id}", consumes = "application/json")
    public @ResponseBody Answer updateComment(@PathVariable("id") Long id, @RequestBody @Valid Comment patch) {
        return todoService.updateComment(id, patch);
    }

}
