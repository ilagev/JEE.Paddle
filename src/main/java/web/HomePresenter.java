package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePresenter {
    
    @RequestMapping("/home")
    public String home() {
        return "/home";
    }
    
    @RequestMapping("/")
    public String root() {
        return "/home";
    }
    
}
