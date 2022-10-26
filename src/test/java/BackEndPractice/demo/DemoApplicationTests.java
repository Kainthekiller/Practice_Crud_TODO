package BackEndPractice.demo;

import BackEndPractice.demo.Model.Todo;
import BackEndPractice.demo.Repo.TodoRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {


	//Variables

	@Autowired
	TodoRepo todoRepo;

	ObjectMapper mapper = JsonMapper.builder().build();

	@Autowired
	MockMvc mvc;

	Todo todoOne;
	Todo todoTwo;
	Todo todoThree;

	@BeforeEach
	void init()
	{
		todoOne = new Todo();
		todoTwo = new Todo();
		todoThree = new Todo();

		//One
		todoOne.setContent("Walk Dog");
		todoTwo.setContent("Wash Car");
		todoThree.setContent("Cook Dinner");
		todoRepo.save(todoOne);
		todoRepo.save(todoTwo);
		todoRepo.save(todoThree);
	}


	//GET ALL
	@Test
	@Transactional
	@Rollback
	void getAllTodo() throws Exception
	{
		MockHttpServletRequestBuilder request = get("/api/items");

		this.mvc.perform(request)
				.andExpect(jsonPath("$[0].Content").value("Walk Dog"))
				.andExpect(jsonPath("$[1].Content").value("Wash Car"))
				.andExpect(jsonPath("$[2].Content").value("Cook Dinner"));
	}

	//Delete

	@Test
	@Transactional
	@Rollback
	void deleteTodo() throws Exception
	{
		MockHttpServletRequestBuilder requestNotFound = delete(String.format("/api/items/%d",this.todoRepo.count() + 1));
		MockHttpServletRequestBuilder request = delete(String.format("/api/item/%d", todoOne.getId()));

		this.mvc.perform(request)
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.Content").doesNotExist());

		this.mvc.perform(requestNotFound)
				.andExpect(status().isNotFound());




	}

	//Patch
	@Test
	@Transactional
	@Rollback
	void patchTodo() throws Exception
	{
		String jsonTest = mapper.writeValueAsString(todoOne);

		MockHttpServletRequestBuilder request = patch(String.format("/api/items/%d", todoOne.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTest);


		this.mvc.perform(request)
				.andExpect(jsonPath("$.Content").value("Walk Dog"));


		todoOne.setContent("Test");
		String jsonTestTwo = mapper.writeValueAsString(todoOne);

		MockHttpServletRequestBuilder requestTwo = patch(String.format("/api/items/%d", todoOne.getId()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonTestTwo);


		this.mvc.perform(requestTwo)
				.andExpect(jsonPath("$.Content").value("Test"));
	}

	//CREATE

	@Test
	@Transactional
	@Rollback
	void createTodo() throws Exception
	{
			todoOne.setContent("Updated Todo");
			String json = mapper.writeValueAsString(todoOne);

			MockHttpServletRequestBuilder request = post("/api/items")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json);

			mvc.perform(request)
					.andExpect(jsonPath("$.Content").value("Updated Todo"));
	}

}
