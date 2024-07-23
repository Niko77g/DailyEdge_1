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
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new MyUser());
        return "home";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute MyUser user, Model model) {
        System.out.println("Registering user: " + user);
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            model.addAttribute("error", "Password cannot be empty");
            return "home";
        }
        if(userRepository.findByUsername(user.getUsername()).isPresent()){
            model.addAttribute("error", "Username is already use.");
            return "home";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        MyUser newUser = userRepository.save(user);
        model.addAttribute("user", newUser);
        return "redirect:/user/home";
    }
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute MyUser user, Model model){
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
    @PostMapping("/user/delete")
    public String removerun(@RequestParam int id){
        runService.deleteById(id);
        return "redirect:/user/home";

    }
    @PostMapping("/user/update")
    public String updaterun(@ModelAttribute Run run, @RequestParam int id){
        runService.update(run,id);
        return "redirect:/user/home";
    }

}
