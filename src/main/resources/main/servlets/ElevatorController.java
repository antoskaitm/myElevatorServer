package main.servlets;


import main.entities.Building;
import main.entities.ElevatorCondition;
import main.entities.ElevatorRoom;
import main.entities.ElevatorThread;
import main.helpers.SessionHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class ElevatorController {

    private static Building building;

    private static ElevatorRoom<ElevatorCondition> room;

    static {
        building = new Building(7, 40);
        ElevatorCondition elevator = new ElevatorCondition(building.getFloorCount());
        room = new ElevatorRoom<ElevatorCondition>(elevator);
        ElevatorThread emulation = new ElevatorThread(room.getElevatorAutomate(), building);
        emulation.run();
    }

    @RequestMapping(value = {"call"}, method = RequestMethod.POST)
    public String callupElevator(Integer floor, Model model, HttpSession session) {
        SessionHelper helper = new SessionHelper(session);
        if (!building.hasFloor(floor)) {
            model.addAttribute("errorMessage", "Error!This floor doesn't exist");
        } else {
            Integer id = helper.getPersonId();
            if (room.isSendElevator(id) || id == null) {
                id = room.callElevator(floor);
                helper.setPersonId(id);
            } else if (room.isInElevator(id)) {
                model.addAttribute("errorMessage", "Error!You are in elevator");
            } else if (room.isCallElevator(id)) {
                model.addAttribute("errorMessage", "Error!You are wait elevator");
            } else {
                setPersonCondition(model, id);
                helper.setPage("callPanel");
                setModelData(model);
                return "callPanel";
            }
        }
        setPersonCondition(model, helper.getPersonId());
        helper.setPage("sendPanel");
        setModelData(model);
        return "sendPanel";
    }

    @RequestMapping(value = {"getInfo"}, method = RequestMethod.POST)
    public String getInfo(Model model, HttpSession session) {

        SessionHelper helper = new SessionHelper(session);
        setModelData(model);
        setPersonCondition(model, helper.getPersonId());
        return helper.getPage();
    }

    @RequestMapping(value = {"main"}, method = RequestMethod.GET)
    public String begin(Model model, HttpSession session) {
        SessionHelper helper = new SessionHelper(session);
        helper.setPage("main");
        setModelData(model);
        setPersonCondition(model, helper.getPersonId());
        return "main";
    }

    @RequestMapping(value = {"send"}, method = RequestMethod.POST)
    public String send(int floor, Model model, HttpSession session) {
        SessionHelper helper = new SessionHelper(session);
        if (!building.hasFloor(floor)) {
            model.addAttribute("errorMessage", "Error!This floor doesn't exist");
        } else if (room.getCurrentFloor().equals(floor)) {
            model.addAttribute("errorMessage", "Error!This is current floor");
        } else {
            Integer id = helper.getPersonId();
            setPersonCondition(model, id);
            if (room.isInElevator(id)) {
                room.sendElevator(floor, id);
                helper.setPersonId(null);
            } else if (room.isCallElevator(id)) {
                model.addAttribute("errorMessage", "Error!You are not in elevator");
                setModelData(model);
                helper.setPage("sendPanel");
                return "sendPanel";
            }
        }
        helper.setPage("callPanel");
        setModelData(model);
        return "callPanel";
    }

    private void setModelData(Model model) {
        model.addAttribute("currentFloor", room.getCurrentFloor());
        model.addAttribute("lastFloor", building.getLastFloor());
        model.addAttribute("groundFloor", building.getGroundFloor());
    }

    private void setPersonCondition(Model model, Integer id) {
        model.addAttribute("personCondition", room.getPersonCondition(id));
        model.addAttribute("id", id);
    }
}