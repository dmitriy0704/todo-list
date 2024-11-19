package todolist.services;


import org.springframework.stereotype.Service;
import todolist.entity.Todo;
import todolist.exceptions.Answer;
import todolist.repository.TodoRepository;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final TodoRepository todoRepository;

    public UserService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Answer updateComments(Long id, Todo path) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todo.map(t -> {
                if (!t.getExecutor().equalsIgnoreCase(path.getAuthor())) {
                    t.setComments(path.getComments());
                    todoRepository.save(t);
                    return new Answer(1, "Комментарии обновлены", todo);
                } else {
                    return new Answer(-1, "Комментарии не были обновлены", null);
                }
            });
        }
        return new Answer(-1, "Такой задачи нет", null);
    }

    public Answer updateStatus(Long id, Todo path) {
        Optional<Todo> todo = todoRepository.findById(id);
        if (todo.isPresent()) {
            todo.map(t -> {
                if (!t.getExecutor().equalsIgnoreCase(path.getAuthor())) {
                    t.setStatus(path.getStatus());
                    todoRepository.save(t);
                    return new Answer(1, "Статус обновлены", todo);
                }
                return new Answer(-1, "Статус не обновлены", todo);
            });
        }
        return new Answer(-1, "Такой задачи нет", null);
    }
}
