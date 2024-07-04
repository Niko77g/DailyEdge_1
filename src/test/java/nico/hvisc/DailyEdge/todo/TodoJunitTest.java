package nico.hvisc.DailyEdge.todo;

import nico.hvisc.DailyEdge.run.JdbcTodoController;
import nico.hvisc.DailyEdge.run.Todo;
import nico.hvisc.DailyEdge.run.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TodoJunitTest {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private JdbcTodoController jdbcTodoController;

    @Test
    void createNewTodo() {
        Todo todo = new Todo();
        todo.setName("TEST");
        todo.setDescription("Testing created new todo");
        todo.setPriority(2);
        todoRepository.create(todo);

        List<Todo> todos = todoRepository.findAllBy();
        assertThat(todos).isNotEmpty();
        assertThat(todos).anyMatch(t -> t.getName().equals("TEST"));
    }
    @Test
    void findbyIdTodo() {
        Todo todo = new Todo();
        todo.setName("TEST");
        todo.setDescription("Testing created new todo");
        todo.setPriority(2);
        todoRepository.create(todo);

        Optional<Todo> found = todoRepository.findById(todo.getId());
        assertThat(found.isPresent()).isTrue();
        assertThat(found.get().getName()).isEqualTo("TEST");

    }
    @Test
    void updateTodo() {
        Todo todo = new Todo();
        todo.setName("TEST");
        todo.setDescription("Testing created new todo");
        todo.setPriority(3);
        todoRepository.create(todo);

        todo.setName("NEW TEST");
        todoRepository.update(todo, todo.getId());

        Optional<Todo> todoupdated = todoRepository.findById(todo.getId());
        assertThat(todoupdated.isPresent()).isTrue();
        assertThat(todoupdated.get().getName()).isEqualTo("NEW TEST");
    }
    @Test
    void deleteTodo() {
        Todo todo = new Todo();
        todo.setName("TEST");
        todo.setDescription("Testing created new todo");
        todo.setPriority(1);
        todoRepository.create(todo);

        todoRepository.deleteById(todo.getId());

        Optional<Todo> tododeleted = todoRepository.findById(todo.getId());
        assertThat(tododeleted).isNotPresent();
    }
}
