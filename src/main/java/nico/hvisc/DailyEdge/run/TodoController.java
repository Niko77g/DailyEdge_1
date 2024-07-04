package nico.hvisc.DailyEdge.run;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoService todoService;

    public TodoController( TodoService todoService) {
        this.todoService = todoService;
    }
    @GetMapping()
    public List<Todo> todo() {
        return (List<Todo>) todoService.findAll();
    }
    @GetMapping("/{id}")
    public Todo todo(@PathVariable int id) {
        return todoService.findById(id);
    }
    @PostMapping()
    public Todo save(@RequestBody Todo todo) {
        return todoService.save(todo);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Todo todo) {
        Todo tempTodo = todoService.findById(id);
        tempTodo.setName(todo.getName());
        tempTodo.setDescription(todo.getDescription());
        tempTodo.setPriority(todo.getPriority());
        todoService.save(tempTodo);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        todoService.deleteById(id);
    }
    @GetMapping("/search")
    public List<Todo> findByTitle(String title) {
        return todoService.findByName(title);
    }

}
