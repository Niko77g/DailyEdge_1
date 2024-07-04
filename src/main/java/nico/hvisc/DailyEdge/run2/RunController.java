package nico.hvisc.DailyEdge.run2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RunController {
    private RunService runService;
    @Autowired
    public RunController(RunService runService) {
        this.runService = runService;
    }
    @GetMapping("/run")
    public List<Run> run() {
        return (List<Run>) runService.findAll();
    }
    @GetMapping("/{id}")
    public Run run(@PathVariable int id) {
        return runService.findById(id);
    }
    @PostMapping("/run")
    public Run save(@RequestBody Run run) {
        return runService.save(run);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Run run) {
        Run temprun = runService.findById(id);
        temprun.setTitle(run.getTitle());
        temprun.setKilometers(run.getKilometers());
        temprun.setCreated_at(run.getCreated_at());
        runService.save(temprun);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        runService.deleteById(id);

    }
    @GetMapping("/search")
    public List<Run> findByTitle(@RequestParam String title) {
        return runService.findByRunName(title);
    }

}
