package todolist.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import todolist.entity.Todo;
import todolist.repository.TodoRepository;

@Service
public class AdminService {

    private final TodoRepository todoRepository;

    public AdminService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    public Todo createTodo(Todo todo){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        todo.setUser_email(username);
        return todo;
    }




}
