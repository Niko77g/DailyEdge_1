package nico.hvisc.DailyEdge.run;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository {
    List<Todo> findAllBy();
    Optional<Todo> findById(Integer id);
    void create(Todo todo);
    void update(Todo todo, Integer id);
    void deleteById(Integer id);
    List<Todo> findByUserName(String userName);
}
