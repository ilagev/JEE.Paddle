package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Presenter {
    
    @RequestMapping("/user-list")
    public String listUsers() {
        return "userList";
    }
    
}
