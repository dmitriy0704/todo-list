package todolist.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {


    private int status;
    private String message;
    private Object obj;

    public Answer(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Answer(int status, String message, Object obj) {
        this.status = status;
        this.message = message;
        this.obj = obj;
    }
}
