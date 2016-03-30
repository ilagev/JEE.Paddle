package web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.UserController;

@Controller
public class Presenter {
    
    private UserController userController;
    
    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }
    
    @RequestMapping("/user-list")
    public ModelAndView listUsers() {
        ModelAndView modelAndView = new ModelAndView("/userList");
        modelAndView.addObject("userList", userController.findAll());
        return modelAndView;
    }
    
    @RequestMapping("/home")
    public String home() {
        return "redirect:/user-list";
    }
    
    @RequestMapping("/")
    public String root() {
        return "redirect:/user-list";
    }
    
}
