package main.emulator.panel;

public class PageInfo {
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
