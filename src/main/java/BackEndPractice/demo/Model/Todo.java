package BackEndPractice.demo.Model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo
{

    //VARIABLES--------------------------
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Content")
    String content = "Empty Todo Item";

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("Completed")
    boolean completed = false;

    //GETTERS _______ SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
