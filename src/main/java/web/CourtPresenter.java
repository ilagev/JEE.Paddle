package web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import business.controllers.CourtController;
import business.wrapper.CourtState;

@Controller
public class CourtPresenter {
    
    private CourtController courtController;
    
    @Autowired
    public void setcourtController(CourtController courtController) {
        this.courtController = courtController;
    }
    
    @RequestMapping("/court-list")
    public ModelAndView listCourts() {
        ModelAndView modelAndView = new ModelAndView("/courtList");
        modelAndView.addObject("courtList", courtController.showCourts());
        return modelAndView;
    }
    
    @RequestMapping(value = "/create-court", method = RequestMethod.GET)
    public String createcourt(Model model) {
        model.addAttribute("court", new CourtState());
        return "/courtCreate";
    }

    @RequestMapping(value = "/create-court", method = RequestMethod.POST)
    public String createcourtSubmit(@Valid CourtState court, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            if (courtController.createCourt(court.getCourtId())) {
                return "redirect:/court-list";
            } else {
                bindingResult.rejectValue("id", "error.court", "Pista ya existente");
            }
        }
        return "redirect:/court-list";
    }
    
    @RequestMapping(value = {"/delete-court/{id}"})
    public String deletecourt(@PathVariable int id, Model model) {
        courtController.deleteCourt(id);
        return "redirect:/court-list";
    }
    
}
