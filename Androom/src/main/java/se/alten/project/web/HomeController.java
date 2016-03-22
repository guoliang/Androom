package se.alten.project.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String Home(Model model) {
        model.addAttribute(
                "user",
                new String(System.getProperty("user.name")));

        return "index";
    }
}
