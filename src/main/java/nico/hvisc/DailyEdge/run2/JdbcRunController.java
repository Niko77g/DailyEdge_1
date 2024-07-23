package nico.hvisc.DailyEdge.run2;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcRunController implements RunRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    public JdbcRunController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("run").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Run> findAllBy() {
        return jdbcTemplate.query("select * from run",
                (rs, rowNum) -> new Run(rs.getInt("id"), rs.getString("title"),rs.getInt("kilometers"),rs.getTimestamp("created_at")));
    }

    @Override
    public Optional<Run> findById(Integer id) {
        return jdbcTemplate.query("select * from run where id = ?", new Object[]{id}, rs -> {
            if (rs.next()) { return Optional.of(new Run(rs.getInt("id"),
                    rs.getString("title"), rs.getInt("kilometers"),rs.getTimestamp("created_at")
            ));
            } else {
                return Optional.empty();
            }
        });
    }

    @Override
    public void create(Run run) {
        Map<String, Object> params = new HashMap<>();
        params.put("title", run.getTitle());
        params.put("kilometers", run.getKilometers());
        params.put("created_at", run.getCreated_at());

        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        run.setId(newId.intValue());
    }

    @Override
    public Run update(Run run, Integer id) {
        jdbcTemplate.update("update run set title =?, kilometers =?, created_at =? where id = ?",run.getTitle(),run.getKilometers(),run.getCreated_at(),id);
        return run;
    }
/*
    @Override
    public Run save(Run run) {
        return null;
    }
*/
    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("delete from run where id = ?", id);
    }

    @Override
    public List<Run> findByRunName(String userName) {
         return jdbcTemplate.query("select * from run where title=?", new Object[]{userName},
                (rs, rowNum) -> new Run(rs.getInt("id"),rs.getString("title"),rs.getInt("kilometers"),rs.getTimestamp("created_at")));

    }

    @Override
    public Integer compareRun(Run run1, Run run2) {
        List<Integer> runs = jdbcTemplate.query("SELECT kilometers FROM run WHERE id IN (?, ?)", new Object[]{run1.getId(),run2.getId()},
                (rs, rowNum) -> rs.getInt("kilometers"));
        if(runs.size() < 2){
            throw new RuntimeException("Runs have less than two runs!");
        }
        int kilometers = runs.get(0);
        int kilometers2 = runs.get(1);

         int comparebetter = ((kilometers2 - kilometers) / kilometers) * 100 ;
         return comparebetter;
    }



}

