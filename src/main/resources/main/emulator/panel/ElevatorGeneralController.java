package main.emulator.panel;

import main.dao.IDaoObject;
import main.emulator.ElevatorThread;
import main.emulator.panel.contract.ElevatorInfo;
import main.emulator.panel.contract.PageInfo;
import main.entities.interfaces.primitive.IElevator;
import main.entities.primitive.Building;
import main.entities.primitive.ElevatorRequest;
import main.entities.primitive.Person;
import main.entities.primitive.abstractclass.AbstractBuilding;
import main.helpers.ISessionHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * all information for user send into PageInfo
 */
public class ElevatorGeneralController {
	//TODO наверное уже здание
	private AbstractBuilding<Person> building;
	private Integer defaultFloorNumber = 0;
	private Integer elevatorsCount = 2;
	private Integer personCounter = 0;

	public ElevatorGeneralController(IDaoObject<Building> dao) {
		try {
			building = dao.load();
			for (Integer id = 0; id < elevatorsCount; id++) {
				ElevatorThread emulation = new ElevatorThread(dao, building.getElevator(id).getElevatorAutomate(), building);
				emulation.run();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public String callupElevatorNumber(Integer elevatorNumber, PageInfo pageInfo, ISessionHelper session) {
		Person person = session.getPerson();
		Integer floor = getCurrentFloor(person);
		return callupElevator(floor, elevatorNumber, pageInfo, session);
	}

	private Integer getCurrentFloor(Person person) {
		Integer floor = defaultFloorNumber;
		if (person != null) {
			ElevatorRequest request = person.getRequest();
			if (request != null) {
				floor = request.getSendFloor() != null ? request.getSendFloor() : request.getCallFloor();
			}
		}
		return floor;
	}

	public String callupElevator(Integer floor, Integer elevatorNumber, PageInfo pageInfo, ISessionHelper session) {
		Person person = session.getPerson();
		if (person == null) {
			person = new Person(personCounter++);
			session.setPerson(person);
		}
		ElevatorRequest request = person.getRequest();
		String error = chackAtErrors(floor, request, elevatorNumber);
		pageInfo.getPersonInfo().setErrorMessage(error);
		if (error == null) {
			building.getElevator(elevatorNumber).callElevator(floor, person);
		}
		if (error == null) {
			flushPageInfo(pageInfo, person, elevatorNumber);
		} else {
			flushPageInfo(pageInfo, person, null);
		}

		String resultPage = (error == null) ? "sendPanel" : "callPanel";
		session.setPage(resultPage);
		return resultPage;
	}

	private String chackAtErrors(Integer floor, ElevatorRequest request, Integer elevatorNumber) {
		if (floor == null) {
			return "Floor was not pointed";
		}
		if (request != null && !building.getElevator(elevatorNumber).getFloorsRange().hasFloor(floor)) {
			return "Error!This floor doesn't exist";
		} else {
			if (request == null) {
				if (!floor.equals(defaultFloorNumber)) {
					return "Error!You are at floor number:" + defaultFloorNumber;
				}
			} else if (request.withoutState()) {
				if (request.getSendFloor() != null) {
					if (!request.getSendFloor().equals(floor)) {
						return "Error!You are at floor number:" + request.getSendFloor();
					}
				} else if (!request.getCallFloor().equals(floor)) {
					return "Error!You are at floor number:" + request.getCallFloor();
				}
			} else if (request.isInElevator()) {
				return "Error!You are in elevator";
			} else if (request.isCallElevator()) {
				return "Error!You are wait elevator";
			}
		}
		return null;
	}


	public String getInfo(PageInfo pageInfo, ISessionHelper session) {
		Person person = session.getPerson();
		Boolean isOneElevator = person != null && person.getRequest() != null
				&& (person.getRequest().isInElevator() || person.getRequest().isCallElevator());
		if (isOneElevator) {
			flushPageInfo(pageInfo, person, person.getRequest().elevatorId);
		} else {
			flushPageInfo(pageInfo, person, null);
		}
		return session.getPage();
	}

	public String begin(PageInfo pageInfo, ISessionHelper session) {
		session.setPage("main");
		flushPageInfo(pageInfo, session.getPerson(), null);
		return "main";
	}

	private IElevator getElevator(Person person) {
		return building.getElevator(person.getRequest().elevatorId);
	}

	public String send(int floor, int elevatorNumber, PageInfo pageInfo, ISessionHelper session) {
		String resultPage = null;
		Person person = session.getPerson();
		if (person == null || person.getRequest().withoutState()) {
			pageInfo.getPersonInfo().setErrorMessage("Error!Elevator wasn't called");
		} else if (!building.getElevator(elevatorNumber).getFloorsRange().hasFloor(floor)) {
			pageInfo.getPersonInfo().setErrorMessage("Error!This floor doesn't exist");
		} else {
			if (person.getRequest().isInElevator()) {
				getElevator(person).sendElevator(floor, person);
			} else if (person.getRequest().isCallElevator()) {
				pageInfo.getPersonInfo().setErrorMessage("Error!You are not in elevator");
				resultPage = "sendPanel";
			}
		}
		if (resultPage == null) {
			flushPageInfo(pageInfo, session.getPerson(), null);
		} else {
			flushPageInfo(pageInfo, session.getPerson(), elevatorNumber);
		}
		resultPage = (resultPage == null) ? "callPanel" : resultPage;
		session.setPage(resultPage);
		return resultPage;
	}

	private void flushPageInfo(PageInfo pageInfo, Person person, Integer elevatorNumber) {
		Integer floor = getCurrentFloor(person);
		List<ElevatorInfo> elevators = new ArrayList<>();
		if (elevatorNumber == null) {
			for (Integer id = 0; id < elevatorsCount; id++) {
				if(building.getElevator(id).getFloorsRange().hasFloor(floor)) {
					elevators.add(new ElevatorInfo(building.getElevator(id), id));
				}
			}
		} else {
			elevators.add(new ElevatorInfo(building.getElevator(elevatorNumber), null));
		}
		pageInfo.getServerInfo().setElevatorsId(elevators.toArray(new ElevatorInfo[0]));

		if (person != null && person.getRequest() != null) {
			pageInfo.getPersonInfo().setPersonConditionMessage(person.getRequest().getCondition().getMessage());
			pageInfo.getPersonInfo().setRequestId(person.getRequest().getId());
		}
	}
}