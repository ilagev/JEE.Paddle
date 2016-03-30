package web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.UserController;
import business.wrapper.UserWrapper;

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
    
    @RequestMapping(value = "/create-user", method = RequestMethod.GET)
    public String createUser(Model model) {
        model.addAttribute("user", new UserWrapper());
        return "/userCreate";
    }

    @RequestMapping(value = "/create-user", method = RequestMethod.POST)
    public String createUserSubmit(@Valid UserWrapper user, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            if (userController.registration(user)) {
                return "redirect:/user-list";
            } else {
                bindingResult.rejectValue("id", "error.user", "Usuario ya existente");
            }
        }
        return "redirect:/user-list";
    }
    
}
