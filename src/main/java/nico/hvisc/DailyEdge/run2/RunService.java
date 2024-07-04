package nico.hvisc.DailyEdge.run2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunService {
    @Autowired
    private RunRepository runRepository;
    public RunService(RunRepository runRepository) {
        this.runRepository = runRepository;
    }
    public Iterable<Run> findAll() {
        return runRepository.findAllBy();
    }
    public Run findById(int id) {
        return runRepository.findById(id).orElseThrow(() -> new RuntimeException("Run not found"));
    }
    public Run save(Run run) {
        if (run.getId() == 0) {
            runRepository.create(run);
        } else {
            runRepository.update(run, run.getId());
        }
        return run;
    }
    public void deleteById(int id) {
        runRepository.deleteById(id);
    }
    public List<Run> findByRunName(String runName) {
        return runRepository.findByRunName(runName);
    }

}
