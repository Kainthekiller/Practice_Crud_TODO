package BackEndPractice.demo.Repo;

import BackEndPractice.demo.Model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepo extends JpaRepository <Todo, Long>
{


}
