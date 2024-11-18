package todolist.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import todolist.entity.Todo;
import todolist.entity.User;
import todolist.exceptions.Answer;
import todolist.repository.TodoRepository;
import todolist.repository.UsersRepository;

import java.util.Optional;

@Service
public class AdminService {

    private final TodoRepository todoRepository;
    private final UsersRepository usersRepository;

    public AdminService(TodoRepository todoRepository, UsersRepository usersRepository) {
        this.todoRepository = todoRepository;
        this.usersRepository = usersRepository;
    }

    public Todo createTodo(Todo todo) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        todo.setUser_email(username);
        return todo;
    }


    public Todo updateExecutor(Long id, Todo patch) {
        Todo todo = todoRepository.findById(id).get();
        if (todo.getExecutor() != null) {
            todo.setExecutor(patch.getExecutor());
        }
        return todoRepository.save(todo);
    }


    public Answer updateExecutorAnswer(Long id, Todo patch) {
        /* Условия обновления задачи:
         *  TODO:
         *  1. Найдена/не найдена задача по id
         *  2. Проверить существует ли такой исполнитель
         *  3. Установлен ли уже этот исполнитель
         */

        // шаг #1
        Optional<Todo> todo = todoRepository.findById(id);
        // если есть,
        if (todo.isPresent()) {
            // шаг #2
            Optional<User> user = usersRepository.findByEmail(patch.getExecutor());
            // если есть
            if (user.isPresent()) {
                // шаг #3
                if (todo.map(Todo::getExecutor) != Optional.ofNullable(patch.getExecutor())) {
                    todo.map(t -> {
                                t.setExecutor(patch.getExecutor());
                                return todoRepository.save(t);
                            }
                    );
                    return new Answer(1, "Исполнитель обновлен успешно", todo);
                } else {
                    return new Answer(-1, "Этот исполнитель уже установлен ранее", null);
                }
            } else {
                return new Answer(-1, "Такого исполнителя не существует", null);
            }
        } else {
            return new Answer(-1, "Такой задачи не существует", null);
        }
    }
}
