package BackEndPractice.demo.Repo;

import BackEndPractice.demo.Model.TodoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository <TodoModel, Long>
{


}
