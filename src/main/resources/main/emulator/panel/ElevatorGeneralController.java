package main.emulator.panel;

import main.dao.IDaoObject;
import main.emulator.ElevatorThread;
import main.emulator.panel.contract.PageInfo;
import main.entities.interfaces.primitive.IElevator;
import main.entities.primitive.Building;
import main.helpers.ISessionHelper;

/**
 * all information for user send into PageInfo
 */
public class ElevatorGeneralController {
	private IElevator room;

	public ElevatorGeneralController(IDaoObject<Building> dao) {
		try {
			this.room = dao.load().getElevator(0);
			ElevatorThread emulation = new ElevatorThread(dao, 0);
			emulation.run();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String callupElevator(Integer floor, PageInfo pageInfo, ISessionHelper helper) {
		String resultPage = null;
		if (!room.getFloorsRange().hasFloor(floor)) {
			pageInfo.getPersonInfo().setErrorMessage("Error!This floor doesn't exist");
		} else {
			Integer id = helper.getRequestId();
			if (room.isSendElevator(id) || id == null) {
				id = room.callElevator(floor).getId();
				helper.setRequestId(id);
			} else if (room.isInElevator(id)) {
				pageInfo.getPersonInfo().setErrorMessage("Error!You are in elevator");
			} else if (room.isCallElevator(id)) {
				pageInfo.getPersonInfo().setErrorMessage("Error!You are wait elevator");
			} else {
				resultPage = "callPanel";
			}
		}
		flushPageInfo(pageInfo, helper.getRequestId());
		resultPage = (resultPage == null) ? "sendPanel" : resultPage;
		helper.setPage(resultPage);
		return resultPage;
	}

	public String getInfo(PageInfo pageInfo, ISessionHelper session) {
		flushPageInfo(pageInfo, session.getRequestId());
		return session.getPage();
	}

	public String begin(PageInfo pageInfo, ISessionHelper session) {
		session.setPage("main");
		flushPageInfo(pageInfo, session.getRequestId());
		return "main";
	}

	public String send(int floor, PageInfo pageInfo, ISessionHelper session) {
		String resultPage = null;
		if (!room.getFloorsRange().hasFloor(floor)) {
			pageInfo.getPersonInfo().setErrorMessage("Error!This floor doesn't exist");
		} else {
			Integer id = session.getRequestId();
			if (room.isInElevator(id)) {
				room.sendElevator(floor, id);
				session.setRequestId(null);
			} else if (room.isCallElevator(id)) {
				pageInfo.getPersonInfo().setErrorMessage("Error!You are not in elevator");
				resultPage = "sendPanel";
			}
		}
		resultPage = (resultPage == null) ? "callPanel" : resultPage;
		session.setPage(resultPage);
		flushPageInfo(pageInfo, session.getRequestId());
		return resultPage;
	}

	private void flushPageInfo(PageInfo pageInfo, Integer personId) {
		pageInfo.getServerInfo().setCurrentFloor(room.getCurrentFloor());
		pageInfo.getServerInfo().setLastFloor(room.getFloorsRange().getLastFloor());
		pageInfo.getServerInfo().setGroundFloor(room.getFloorsRange().getGroundFloor());
		pageInfo.getPersonInfo().setPersonConditionMessage(room.getRequestCondition(personId).getMessage());
		pageInfo.getPersonInfo().setRequestId(personId);
	}
}
