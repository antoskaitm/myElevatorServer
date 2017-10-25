package main.servlets;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;

@Controller
public class ElevatorController extends HttpServlet {

    @RequestMapping(value = {"callup"}, method = RequestMethod.POST)
    public String callupElevator(Integer floor,Model model)
    {
        model.addAttribute("currentFloor", floor);
        setModelData( model);
        return "main";
    }

    @RequestMapping(value = {"main"}, method = RequestMethod.GET)
    public String begin(Model model)
    {
        setModelData( model);
        return "main";
    }

    @RequestMapping(value = {"send"}, method = RequestMethod.POST)
    public String send(Integer floor,Model model)
    {
        model.addAttribute("currentFloor", floor);
        setModelData(model);
        return "main";
    }


    private void setModelData(Model model)
    {
        model.addAttribute("maxFloor", 7);
        model.addAttribute("minFloor", 1);
    }
}
