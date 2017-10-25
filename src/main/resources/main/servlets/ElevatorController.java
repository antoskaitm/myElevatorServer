package main.servlets;


import main.entities.Elevator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;

@Controller
public class ElevatorController extends HttpServlet {

    @RequestMapping(value = "callup", method = RequestMethod.POST)
    public String callupElevator(Model model)
    {
        model.addAttribute("currentFloor", 1);
        return "index";
    }
}
