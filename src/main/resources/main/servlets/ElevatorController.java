package main.servlets;


import main.dao.DaoState;
import main.emulator.ElevatorThread;
import main.entities.*;
import main.entities.interfaces.IBuilding;
import main.entities.interfaces.IElevatorRoom;
import main.helpers.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class ElevatorController {
    private IBuilding building;
    private static IElevatorRoom room;

    public ElevatorController(IBuilding building, IElevatorRoom room) {
        this.building = building;
        if (this.room == null)
            try {
                this.room = room;
                //DaoState dao = new DaoState();
                //room = dao.getElevatorRoom();
                ElevatorThread emulation = new ElevatorThread(room, building);
                emulation.run();
            } catch (Throwable e) {
                e.printStackTrace();
            }
    }

    @RequestMapping(value = {"call"}, method = RequestMethod.POST)
    public String callupElevator(Integer floor, Model model, HttpSession session) {
        SessionHelper helper = new SessionHelper(session);
        String resultPage = null;
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
                resultPage = "callPanel";
            }
        }
        setModelData(model, helper.getPersonId());
        resultPage = (resultPage == null) ? "sendPanel" : resultPage;
        helper.setPage(resultPage);
        return resultPage;
    }

    @RequestMapping(value = {"getInfo"}, method = RequestMethod.POST)
    public String getInfo(Model model, HttpSession session) {
        SessionHelper helper = new SessionHelper(session);
        setModelData(model, helper.getPersonId());
        return helper.getPage();
    }

    @RequestMapping(value = {"main"}, method = RequestMethod.GET)
    public String begin(Model model, HttpSession session) {
        SessionHelper helper = new SessionHelper(session);
        helper.setPage("main");
        setModelData(model, helper.getPersonId());
        return "main";
    }

    @RequestMapping(value = {"send"}, method = RequestMethod.POST)
    public String send(int floor, Model model, HttpSession session) {
        SessionHelper helper = new SessionHelper(session);
        String resultPage = null;
        if (!building.hasFloor(floor)) {
            model.addAttribute("errorMessage", "Error!This floor doesn't exist");
        } else if (room.getCurrentFloor().equals(floor)) {
            model.addAttribute("errorMessage", "Error!This is current floor");
        } else {
            Integer id = helper.getPersonId();
            if (room.isInElevator(id)) {
                room.sendElevator(floor, id);
                helper.setPersonId(null);
            } else if (room.isCallElevator(id)) {
                model.addAttribute("errorMessage", "Error!You are not in elevator");
                resultPage = "sendPanel";
            }
        }
        resultPage = (resultPage == null) ? "callPanel" : resultPage;
        helper.setPage(resultPage);
        setModelData(model, helper.getPersonId());
        return resultPage;
    }

    private void setModelData(Model model, Integer id) {
        model.addAttribute("currentFloor", room.getCurrentFloor());
        model.addAttribute("lastFloor", building.getLastFloor());
        model.addAttribute("groundFloor", building.getGroundFloor());
        model.addAttribute("personCondition", room.getPersonCondition(id));
        model.addAttribute("id", id);
    }
}