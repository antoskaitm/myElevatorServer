package main.emulator.panel.contract;

import java.io.Serializable;

public class PageInfo implements Serializable {
	private ServerInfo serverInfo;
	private PersonInfo personInfo;

	public PageInfo() {
		personInfo = new PersonInfo();
		serverInfo = new ServerInfo();
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public ServerInfo getServerInfo() {
		return serverInfo;
	}
}
