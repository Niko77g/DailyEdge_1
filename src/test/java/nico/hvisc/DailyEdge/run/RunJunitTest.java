package nico.hvisc.DailyEdge.run;

import nico.hvisc.DailyEdge.run2.JdbcRunController;
import nico.hvisc.DailyEdge.run2.Run;
import nico.hvisc.DailyEdge.run2.RunRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RunJunitTest {
    @Autowired
    private RunRepository runRepository;
    @Autowired
    private JdbcRunController jdbcRunController;

    @Test
    void CreateNewRun(){
        Run run = new Run();
        run.setTitle("TEST");
        run.setKilometers(5);
        run.setCreated_at(new Timestamp(System.currentTimeMillis()));
        runRepository.create(run);

        Optional<Run> runs = runRepository.findById(run.getId());
        assertThat(runs.isPresent()).isTrue();
        assertThat(runs.get().getTitle()).isEqualTo("TEST");

    }
    @Test
    void UpdateRun(){
        Run run = new Run();
        run.setTitle("TEST");
        run.setKilometers(5);
        run.setCreated_at(new Timestamp(System.currentTimeMillis()));
        runRepository.create(run);
        run.setTitle("NEW TEST");
        run.setKilometers(6);
        run.setCreated_at(new Timestamp(System.currentTimeMillis()));
        runRepository.update(run, run.getId());
        Optional<Run> runs = runRepository.findById(run.getId());
        assertThat(runs.isPresent()).isTrue();
        assertThat(runs.get().getTitle()).isEqualTo("NEW TEST");
    }
    @Test
    void DeleteRun(){
        Run run = new Run();
        run.setTitle("TEST");
        run.setKilometers(5);
        run.setCreated_at(new Timestamp(System.currentTimeMillis()));
        runRepository.create(run);
        runRepository.deleteById(run.getId());
        Optional<Run> runs = runRepository.findById(run.getId());
        assertThat(runs).isNotPresent();
    }
    @Test
    void CompareRun(){
        Run run = new Run();
        run.setTitle("TEST");
        run.setKilometers(5);
        run.setCreated_at(new Timestamp(System.currentTimeMillis()));
        runRepository.create(run);

        Run run2 = new Run();
        run2.setTitle("TEST2");
        run2.setKilometers(10);
        run2.setCreated_at(new Timestamp(System.currentTimeMillis()));
        runRepository.create(run2);

        int comparebetter = runRepository.compareRun(run,run2);
        Optional<Run> runs1 = runRepository.findById(run.getId());
        Optional<Run> runs2 = runRepository.findById(run2.getId());
        assertThat(runs1).isPresent();
        assertThat(runs2).isPresent();
        assertThat(comparebetter).isEqualTo(100);


    }
}
