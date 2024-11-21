package todolist.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.security.Principal;

@Data
@Entity
public class Todo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    private String title;
    private String description;
    private String user_email;
    private String status;
    private String priority;
    private String author;
    private String executor;
    private String comments;

    public Todo() {
    }

    public Todo(
            Long id,
            String title,
                String description,
                String user_email,
                String status,
                String priority,
                String author,
                String executor,
                String comments) {


        this.id = id;
        this.title = title;
        this.description = description;
        this.user_email = user_email;
        this.status = status;
        this.priority = priority;
        this.author = author;
        this.executor = executor;
        this.comments = comments;
    }
}
