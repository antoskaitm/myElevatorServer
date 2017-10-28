package main.servlets;

import main.dao.IDaoObject;
import main.emulator.ElevatorThread;
import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IElevatorRoom;
import main.entities.primitive.Building;
import main.helpers.ISessionHelper;


public class GeneralServicePanel {
    private IBuilding building;
    private IElevatorRoom room;

    public GeneralServicePanel(IDaoObject<Building> dao) {
        try {
            this.building = dao.load();
            this.room = building.getElevator(0);
            ElevatorThread emulation = new ElevatorThread(dao, 0);
            emulation.run();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public String callupElevator(Integer floor, ServerCondition condition, ISessionHelper helper) {
        String resultPage = null;
        if (!building.hasFloor(floor)) {
            condition.errorMessage="Error!This floor doesn't exist";
        } else {
            Integer id = helper.getPersonId();
            if (room.isSendElevator(id) || id == null) {
                id = room.callElevator(floor);
                helper.setPersonId(id);
            } else if (room.isInElevator(id)) {
                condition.errorMessage="Error!You are in elevator";
            } else if (room.isCallElevator(id)) {
                condition.errorMessage="Error!You are wait elevator";
            } else {
                resultPage = "callPanel";
            }
        }
        flushServerCondition(condition, helper.getPersonId());
        resultPage = (resultPage == null) ? "sendPanel" : resultPage;
        helper.setPage(resultPage);
        return resultPage;
    }

    public String getInfo(ServerCondition condition, ISessionHelper session) {
        flushServerCondition(condition, session.getPersonId());
        return session.getPage();
    }

    public String begin(ServerCondition condition, ISessionHelper session) {
        session.setPage("main");
        flushServerCondition(condition, session.getPersonId());
        return "main";
    }

    public String send(int floor, ServerCondition condition, ISessionHelper session) {
        String resultPage = null;
        if (!building.hasFloor(floor)) {
            condition.errorMessage= "Error!This floor doesn't exist";
        } else if (room.getCurrentFloor().equals(floor)) {
            condition.errorMessage= "Error!This is current floor";
        } else {
            Integer id = session.getPersonId();
            if (room.isInElevator(id)) {
                room.sendElevator(floor, id);
                session.setPersonId(null);
            } else if (room.isCallElevator(id)) {
                condition.errorMessage= "Error!You are not in elevator";
                resultPage = "sendPanel";
            }
        }
        resultPage = (resultPage == null) ? "callPanel" : resultPage;
        session.setPage(resultPage);
        flushServerCondition(condition, session.getPersonId());
        return resultPage;
    }

    private void flushServerCondition(ServerCondition condition, Integer personId) {
        condition.currentFloor=room.getCurrentFloor();
        condition.lastFloor=building.getLastFloor();
        condition.groundFloor=building.getGroundFloor();
        condition.personConditionMessage=room.getPersonCondition(personId).getMessage();
        condition.personId = personId;
    }
}
