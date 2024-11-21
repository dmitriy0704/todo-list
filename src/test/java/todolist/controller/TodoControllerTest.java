package todolist.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import todolist.entity.Todo;
import todolist.repository.TodoRepository;
import todolist.services.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

    @Mock
    TodoRepository todoRepository;

    @Mock
    TodoService todoService;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    TodoController todoController;

    @Test
    @DisplayName("GET /v1/api/todos/all возвращает HTTP-ответ со статусом 200 ОК и списком задач")
    void getTodos_ReturnsValidResponseEntity() {
        // given
        var todos = List.of(
                new Todo(
                        9L,
                        "titl5",
                        "desc",
                        "admin@mail.ru",
                        "created",
                        "low",
                        "admin@gmail.com",
                        "user@gmail.com",
                        "new comment"),
                new Todo(
                        10L,
                        "titl5",
                        "desc10",
                        "admin@mail.ru",
                        "created",
                        "low",
                        "admin@gmail.com",
                        "user@gmail.com",
                        "new comment"));

//        doReturn(todos).when(this.todoRepository.findAll());

        // when
        var responseEntity = this.todoController.getTodos();

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertEquals(todos, responseEntity.getBody());
    }

    @Test
    void createTodo_IsValid_ReturnsValidResponseEntity() {
        // given
        var id = 10L;
        var title = "title25";
        var description = "description25";
        var user_email = "admin@mail.ru";
        var status = "created";
        var priority = "low";
        var author = "admin@gmail.com";
        var executor = "user@gmail.com";
        var comments = "new comment";

        //when
        var responseEntity = this.todoController
                .createTodo(new Todo(id, title, description, user_email, status, priority, author, executor, comments));

        // then
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatus());

        if (responseEntity instanceof Todo todo) {


            assertEquals(title, todo.getTitle());
            assertEquals(id, todo.getId());
            assertEquals(description, todo.getDescription());
            assertEquals(id, todo.getId());
            assertEquals(status, todo.getStatus());
            assertEquals(priority, todo.getPriority());
            assertEquals(author, todo.getAuthor());
            assertEquals(executor, todo.getExecutor());
            assertEquals(comments, todo.getComments());


            verify(this.todoRepository).save(todo);
        } else {
            assertEquals(Task.class, responseEntity.getClass());
        }
    }


}