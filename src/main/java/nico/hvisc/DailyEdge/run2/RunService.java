package nico.hvisc.DailyEdge.run2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RunService {
    @Autowired
    private RunRepository runRepository;
    public RunService(RunRepository runRepository) {
        this.runRepository = runRepository;
    }
    public Iterable<Run> findAll() {
        List<Run> runs = runRepository.findAllBy();
        System.out.println("Runs found: " + runs.size());
        return runs;
    }
    public Run findById(int id) {
        return runRepository.findById(id).orElseThrow(() -> new RuntimeException("Run not found"));
    }
    public Run save(Run run) {
        if (run.getCreated_at() == null) {
            run.setCreated_at(new Timestamp(System.currentTimeMillis()));
        }
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
    public Run update( Run run, int id) {
        Run temprun = findById(id);
        temprun.setTitle(run.getTitle());
        temprun.setKilometers(run.getKilometers());
        temprun.setCreated_at(run.getCreated_at());
        return runRepository.update(temprun, id);
    }
    public List<Run> findByRunName(String runName) {
        return runRepository.findByRunName(runName);
    }

}
