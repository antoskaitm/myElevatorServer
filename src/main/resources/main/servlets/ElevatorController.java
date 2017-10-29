package main.servlets;


import main.emulator.panel.ElevatorGeneralController;
import main.emulator.panel.PageInfo;
import main.helpers.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class ElevatorController {
    private ElevatorGeneralController panel;

    public ElevatorController(ElevatorGeneralController panel) {
        this.panel = panel;
    }

    @RequestMapping(value = {"call"}, method = RequestMethod.POST)
    public String callupElevator(Integer floor, Model model, HttpSession session) {
        return panel.callupElevator(floor, getCurrentCondition(model), new SessionHelper(session));
    }

    @RequestMapping(value = {"getInfo"}, method = RequestMethod.POST)
    public String getInfo(Model model, HttpSession session) {
        return panel.getInfo(getCurrentCondition(model),new SessionHelper(session));
    }

    @RequestMapping(value = {"main","","/"}, method = RequestMethod.GET)
    public String begin(Model model, HttpSession session) {
        return panel.begin(getCurrentCondition(model),new SessionHelper(session));
    }

    @RequestMapping(value = {"send"}, method = RequestMethod.POST)
    public String send(int floor, Model model, HttpSession session) {
       return panel.send(floor,getCurrentCondition(model),new SessionHelper(session));
    }

    private PageInfo getCurrentCondition(Model model) {
        PageInfo condition = new PageInfo();
        model.addAttribute("condition", condition);
        return condition;
    }
}