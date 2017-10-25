package main.servlets;


import main.entities.Building;
import main.entities.ElevatorCondition;
import main.entities.ElevatorThread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;

@Controller
public class ElevatorController extends HttpServlet {
    private static Building building = new Building(7,40);
    private static ElevatorCondition elevator = new ElevatorCondition(building);

    static {
        ElevatorThread emulation = new ElevatorThread(elevator);
        emulation.run();
    }

    @RequestMapping(value = {"callup"}, method = RequestMethod.POST)
    public String callupElevator(Integer floor,Model model)
    {
        if (isExist(floor, model)) {
            elevator.callup(floor);
        }
        setModelData(model);
        return "main";
    }

    @RequestMapping(value = {"main"}, method = RequestMethod.GET)
    public String begin(Model model)
    {
        setModelData(model);
        return "main";
    }

    @RequestMapping(value = {"send"}, method = RequestMethod.POST)
    public String send(Integer floor,Model model) {
        if (isExist(floor, model)) {
            if (floor.equals(elevator.getCurrentFloor())) {
                model.addAttribute("errorMessage", "Error!This is current floor");
            } else {
                elevator.send(floor);
            }
        }
        setModelData(model);
        return "main";
    }

    private Boolean isExist(Integer floor,Model model)
    {
        Boolean isExist = building.hasFloor(floor);
        if(!isExist) {
            model.addAttribute("errorMessage","Error!This floor doesn't exist");
        }
        return isExist;
    }

    private void setModelData(Model model)
    {
        model.addAttribute("currentFloor", elevator.getCurrentFloor());
        model.addAttribute("maxFloor", building.getLastFloor());
        model.addAttribute("minFloor", building.getGroundFloor());
    }
}
