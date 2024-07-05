package nico.hvisc.DailyEdge;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "Hello World";
    }
    @GetMapping("/admin/home")
    public String adminHome() {
        return "Hello Admin";
    }
    @GetMapping("/user/home")
    public String userHome() {
        return "Hello User";
    }
}
