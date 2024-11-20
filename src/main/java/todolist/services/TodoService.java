package todolist.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import todolist.entity.Todo;
import todolist.exceptions.Answer;
import todolist.repository.TodoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
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


    public Answer updateComment(Long id, Todo patch) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todo.map(t -> {
                        t.setComments(patch.getComments());
                        return todoRepository.save(t);
                    }
            );
            return new Answer(1, "Комментарий обновлен", todo);
        } else {
            return new Answer(-1, "Такой задачи не существует");
        }
    }

    public Answer updateStatus(Long id, Todo path) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todo.map(t -> {
                if (!t.getExecutor().equalsIgnoreCase(path.getAuthor())) {
                    t.setStatus(path.getStatus());
                    return todoRepository.save(t);
                } else {
                    return new Answer(-1, "Пользователь является и автором", todo);
                }
            });
            return new Answer(1, "Статус обновлен", todo);
        } else {

            return new Answer(-1, "Такой задачи нет", null);
        }
    }
}
