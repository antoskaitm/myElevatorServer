package main.emulator.panel;

import main.dao.IDaoObject;
import main.emulator.ElevatorThread;
import main.emulator.panel.contract.PageInfo;
import main.entities.primitive.Person;
import main.entities.interfaces.primitive.IBuilding;
import main.entities.interfaces.primitive.IPersonElevator;
import main.entities.primitive.Building;
import main.helpers.ISessionHelper;

/**
 * all information for user send into PageInfo
 */
public class ElevatorGeneralController {
	private IPersonElevator room;

	public ElevatorGeneralController(IDaoObject<Building> dao) {
		try {
			IBuilding building = dao.load();
			room = building.getElevator(0);
			ElevatorThread emulation = new ElevatorThread(dao, room.getElevatorAutomate(), building);
			emulation.run();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String callupElevator(Integer floor, PageInfo pageInfo, ISessionHelper session) {
		String resultPage = null;
		Person person = session.getPerson();
		if (person == null) {
			person = new Person();
			session.setPerson(person);
		}
		if (!room.getFloorsRange().hasFloor(floor)) {
			pageInfo.getPersonInfo().setErrorMessage("Error!This floor doesn't exist");
		} else {
			if (person.withoutState()) {
				room.callElevator(floor, person);
			} else if (person.isInElevator()) {
				pageInfo.getPersonInfo().setErrorMessage("Error!You are in elevator");
			} else if (person.isCallElevator()) {
				pageInfo.getPersonInfo().setErrorMessage("Error!You are wait elevator");
			} else {
				resultPage = "callPanel";
			}
		}
		flushPageInfo(pageInfo, person);
		resultPage = (resultPage == null) ? "sendPanel" : resultPage;
		session.setPage(resultPage);
		return resultPage;
	}

	public String getInfo(PageInfo pageInfo, ISessionHelper session) {
		flushPageInfo(pageInfo, session.getPerson());
		return session.getPage();
	}

	public String begin(PageInfo pageInfo, ISessionHelper session) {
		session.setPage("main");
		flushPageInfo(pageInfo, session.getPerson());
		return "main";
	}

	public String send(int floor, PageInfo pageInfo, ISessionHelper session) {
		String resultPage = null;
		Person person = session.getPerson();
		if (person == null || person.withoutState()) {
			pageInfo.getPersonInfo().setErrorMessage("Error!Elevator wasn't called");
		} else if (!room.getFloorsRange().hasFloor(floor)) {
			pageInfo.getPersonInfo().setErrorMessage("Error!This floor doesn't exist");
		} else {
			if (person.isInElevator()) {
				room.sendElevator(floor, person);
			} else if (person.isCallElevator()) {
				pageInfo.getPersonInfo().setErrorMessage("Error!You are not in elevator");
				resultPage = "sendPanel";
			}
		}
		resultPage = (resultPage == null) ? "callPanel" : resultPage;
		session.setPage(resultPage);
		flushPageInfo(pageInfo, session.getPerson());
		return resultPage;
	}

	private void flushPageInfo(PageInfo pageInfo, Person person) {
		pageInfo.getServerInfo().setCurrentFloor(room.getCurrentFloor());
		pageInfo.getServerInfo().setLastFloor(room.getFloorsRange().getLastFloor());
		pageInfo.getServerInfo().setGroundFloor(room.getFloorsRange().getGroundFloor());
		if (person != null && person.getRequest() != null) {
			pageInfo.getPersonInfo().setPersonConditionMessage(person.getRequest().getCondition().getMessage());
			pageInfo.getPersonInfo().setRequestId(person.getRequest().getId());
		}
	}
}
