package nico.hvisc.DailyEdge.run;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTodoController implements TodoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    public JdbcTodoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("Todo").usingGeneratedKeyColumns("id");
    }
    @Override
    public List<Todo> findAllBy() {
        return jdbcTemplate.query("select * from todo",
                (rs, rowNum) -> new Todo(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("priority")));
    }

    @Override
    public Optional<Todo> findById(Integer id) {
        return jdbcTemplate.query("select * from todo where id = ?", new Object[]{id}, rs -> {
            if (rs.next()) { return Optional.of(new Todo(rs.getInt("id"),
                    rs.getString("name"), rs.getString("description"),rs.getInt("priority")
            ));
            } else {
                return Optional.empty();
            }
        });
    }

    @Override
    public void create(Todo todo) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", todo.getName());
        params.put("description", todo.getDescription());
        params.put("priority", todo.getPriority());

        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        todo.setId(newId.intValue());
    }

    @Override
    public void update(Todo todo, Integer id) {
        jdbcTemplate.update("update todo set name =?, description =?, priority =? where id = ?",todo.getName(), todo.getDescription(),todo.getPriority(),id);
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("delete from todo where id = ?", id);
    }

    @Override
    public List<Todo> findByUserName(String userName) {
        return jdbcTemplate.query("select * from todo where name=?", new Object[]{userName},
                (rs, rowNum) -> new Todo(rs.getInt("id"),rs.getString("name"),rs.getString("description"),rs.getInt("priority")));

    }
}

