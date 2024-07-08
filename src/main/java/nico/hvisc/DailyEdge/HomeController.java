package nico.hvisc.DailyEdge;

import nico.hvisc.DailyEdge.run2.Run;
import nico.hvisc.DailyEdge.run2.RunRepository;
import nico.hvisc.DailyEdge.run2.RunService;
import org.springframework.ui.Model;
import nico.hvisc.DailyEdge.users.MyUser;
import nico.hvisc.DailyEdge.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RunService runService;

    @GetMapping("/home")
    public String home(Model model) {
        Iterable<Run> runs = runService.findAll();
        model.addAttribute("runs", runs);
        return "home";
    }
    @GetMapping("/admin/home")
    public String adminHome() {
        return "admin_home";
    }
    @GetMapping("/user/home")
    public String userHome(Model model) {
        Iterable<Run> runs = runService.findAll();
        model.addAttribute("runs", runs);
        return "user_home";
    }
    @GetMapping("/register")
    public String register(MyUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/user/home";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute MyUser user, Model model){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        MyUser newuser = userRepository.save(user);
        model.addAttribute("User registered successfully", newuser);
        return "redirect:/user/home";
    }
    @PostMapping("/user/run")
    public String run(@ModelAttribute Run run, Model model) {
        Run newrun = runService.save(run);
        Iterable<Run> runs = runService.findAll();
        System.out.println("Runs after saving: " + runs);
        model.addAttribute("runs", runs);
        return "redirect:/user/home";
    }

}
