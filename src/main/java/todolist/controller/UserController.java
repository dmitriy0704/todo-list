package todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import todolist.entity.Todo;
import todolist.exceptions.Answer;
import todolist.services.UserService;

@Slf4j
@RestController
@Tag(name = "Пользователь")
@RequestMapping("/v1/api/user/todos")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Обновление комментариев")
    @PatchMapping(path = "/update-comments/{id}", consumes = "application/json")
    public @ResponseBody Answer updateComments(@PathVariable("id") Long id, @RequestBody @Valid Todo patch) {
        return userService.updateComments(id, patch);
    }

    @Operation(summary = "Обновление статуса задачи пользователем")
    @PatchMapping(path = "/update-status/{id}", consumes = "application/json")
    public @ResponseBody Answer updateStatus(@PathVariable("id") Long id, @RequestBody @Valid Todo patch) {
        return userService.updateStatus(id, patch);
    }
}
