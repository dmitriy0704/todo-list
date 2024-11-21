package todolist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import todolist.entity.Todo;
import todolist.repository.TodoRepository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
class TodoControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Test
    void getTodos_ReturnsValidResponseEntity() throws Exception {
        // given
        var requestBuilder = MockMvcRequestBuilders.get("/v1/api/todos/all");
        this.todoRepository.findAll().addAll(
                List.of(
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
                                "new comment")
                )
        );
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                        [
                                            {
                                                "id": 9,
                                                "title": "titl5",
                                                "description": "desc",
                                                "user_email": "admin@gmail.com",
                                                "status": "created",
                                                "priority": "low",
                                                "author": "admin@gmail.com",
                                                "executor": "user@gmail.com",
                                                "comments": "new comment"
                                            },
                                            {
                                                "id": 10,
                                                "title": "titl5",
                                                "description": "desc",
                                                "user_email": "user1@gmail.com",
                                                "status": "created",
                                                "priority": "low",
                                                "author": "admin@gmail.com",
                                                "executor": "user@gmail.com",
                                                "comments": "new comment"
                                            }
                                        ]
                                """)
                );

    }

    @Test
    void createTodo_IsValid_ReturnsValidResponseEntity() throws Exception {
        // given

        var requestBuilder = MockMvcRequestBuilders.post("/v1/api/todos/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                                                "id": 30,
                                                "title": "titl5",
                                                "description": "desc",
                                                "user_email": "user1@gmail.com",
                                                "status": "created",
                                                "priority": "low",
                                                "author": "admin@gmail.com",
                                                "executor": "user@gmail.com",
                                                "comments": "new comment"
                                            }
                        """);
        // when
        this.mockMvc.perform(requestBuilder)
                // then
                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json("""
                                {
                                      "id": 30,
                                      "title": "titl5",
                                      "description": "desc",
                                      "user_email": "user1@gmail.com",
                                      "status": "created",
                                      "priority": "low",
                                      "author": "admin@gmail.com",
                                      "executor": "user@gmail.com",
                                      "comments": "new comment"
                                   }
                                """),
                        jsonPath("$.id").exists()

                );

                assertEquals(1, this.todoRepository.findAll().size());

    }
}