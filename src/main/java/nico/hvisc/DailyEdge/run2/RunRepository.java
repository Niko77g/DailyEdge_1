package nico.hvisc.DailyEdge.run2;

import java.util.List;
import java.util.Optional;

public interface RunRepository {
    List<Run> findAllBy();
    Optional<Run> findById(Integer id);
    void create(Run run);
    Run update(Run run, Integer id);
    //Run save(Run run);
    void deleteById(Integer id);
    List<Run> findByRunName(String userName);
    Integer compareRun(Run run1, Run run2);
}
