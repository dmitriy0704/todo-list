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

    public Answer updateExecutor(Long id, Todo patch) {
        /* Условия обновления Исполнителя:
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
                    return new Answer(-1, "Этот исполнитель уже установлен ранее");
                }
            } else {
                return new Answer(-1, "Такого исполнителя не существует");
            }
        } else {
            return new Answer(-1, "Такой задачи не существует");
        }
    }

    public Answer updatePriority(Long id, Todo patch) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todo.map(t -> {
                        t.setExecutor(patch.getPriority());
                        return todoRepository.save(t);
                    }
            );
            return new Answer(1, "Приоритет обновлен успешно", todo);
        } else {
            return new Answer(-1, "Такой задачи не существует");
        }
    }

    public Answer deleteTodo(Long id) {
        if (todoRepository.existsById(id)) {
            todoRepository.deleteById(id);
            return new Answer(1, "Задача удалена", null);
        } else {
            return new Answer(-1, "Такой задачи нет", null);
        }

    }
}
