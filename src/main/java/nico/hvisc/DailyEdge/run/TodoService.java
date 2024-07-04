package nico.hvisc.DailyEdge.run;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    public Iterable<Todo> findAll() {
        return todoRepository.findAllBy();
    }
    public Todo findById(int id) {
        return todoRepository.findById(id).orElseThrow(() -> new RuntimeException("Todo not found"));
    }
    public Todo save(Todo todo) {
        if (todo.getId() == null) {
            todoRepository.create(todo);
        } else {
            todoRepository.update(todo, todo.getId());
        }
        return todo;
    }
    public void deleteById(int id) {
        todoRepository.deleteById(id);
    }

    public List<Todo> findByName(String name) {
        return todoRepository.findByUserName(name);
    }
}
