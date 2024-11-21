package todolist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import todolist.entity.Todo;
import todolist.exceptions.Answer;
import todolist.services.AdminService;
import todolist.records.*;


@Slf4j
@RestController
@Tag(name = "Администратор")
@RequestMapping("/v1/api/admin/todos")
public class AdminController {



    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Обновление Исполнителя")
    @PatchMapping(path = "/update-executor/{id}", consumes = "application/json")
    public @ResponseBody Answer updateExecutor(@PathVariable("id") Long id, @RequestBody @Valid Executor patch) {
        return adminService.updateExecutor(id, patch);
    }

    @Operation(summary = "Обновление приоритета")
    @PatchMapping(path = "/update-priority/{id}", consumes = "application/json")
    public @ResponseBody Answer updatePriority(@PathVariable("id") Long id, @RequestBody @Valid Priority patch) {
        return adminService.updatePriority(id, patch);
    }

    @Operation(summary = "Удаление задачи по Id")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody Answer deleteTodo(@PathVariable("id") Long id) {
        return adminService.deleteTodo(id);
    }
}
