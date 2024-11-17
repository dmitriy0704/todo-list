package todolist.entity;


import jakarta.persistence.*;
import lombok.Data;

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
}
