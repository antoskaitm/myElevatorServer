package main.servlets;


import main.entities.Building;
import main.entities.ElevatorCondition;
import main.entities.ElevatorRoom;
import main.entities.ElevatorThread;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class ElevatorController  {
    private static Building building;
    private static ElevatorRoom<ElevatorCondition> queue;

    static {
        building = new Building(7, 40);
        ElevatorCondition elevator = new ElevatorCondition(building.getFloorCount());
        queue = new ElevatorRoom<ElevatorCondition>(elevator);
        ElevatorThread emulation = new ElevatorThread(queue.getElevatorAutomate(),building);
        emulation.run();
    }

    @RequestMapping(value = {"callup"}, method = RequestMethod.POST)
    public String callupElevator(Integer floor, Model model,HttpSession session) {
        if (!building.hasFloor(floor)) {
            model.addAttribute("errorMessage", "Error!This floor doesn't exist");
        } else {
            Integer id = getId(session);
            if(!(queue.isInElevator(id)||queue.isCallElevator(id))) {
                id = queue.callElevator(floor);
                setId(session, id);
            }
            setPersonCondition(model,session);
        }
        setModelData(model, "main");
        return "main";
    }

    @RequestMapping(value = {"getCurrentFloor"}, method = RequestMethod.POST)
    public String getCurrentFloor(Model model,String page,HttpSession session) {
        setModelData(model, page);
        setPersonCondition(model,session);
        return page;
    }

    @RequestMapping(value = {"main"}, method = RequestMethod.GET)
    public String begin(Model model,HttpSession session) {
        setPersonCondition(model,session);
        setModelData(model, "main");
        return "main";
    }

    @RequestMapping(value = {"send"}, method = RequestMethod.POST)
    public String send(int floor, Model model,HttpSession session)  {
        if (!building.hasFloor(floor)) {
            model.addAttribute("errorMessage", "Error!This floor doesn't exist");
        } else if (queue.getCurrentFloor().equals(floor)) {
            model.addAttribute("errorMessage", "Error!This is current floor");
        } else {
            Integer id = getId(session);
            queue.sendElevator(floor,id);
            setPersonCondition(model,session);
        }
        setModelData(model, "main");
        return "main";
    }

    private void setModelData(Model model, String page) {
        model.addAttribute("page", page);
        model.addAttribute("currentFloor", queue.getCurrentFloor());
        model.addAttribute("lastFloor", building.getLastFloor());
        model.addAttribute("groundFloor", building.getGroundFloor());
    }

    private void setId(HttpSession session, Integer id)
    {
        session.setAttribute("id",id);
    }

    private Integer getId(HttpSession session)
    {
        return (Integer)session.getAttribute("id");
    }

    private void setPersonCondition(Model model,HttpSession session)
    {
        Integer id = getId(session);
        model.addAttribute("personCondition", queue.getPersonCondition(id));
        model.addAttribute("id", id);
    }
}
