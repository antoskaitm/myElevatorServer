package main.emulator.panel.contract;

public class PersonInfo {
    private String personConditionMessage;
    private Integer personId;
    private String errorMessage;

    public String getPersonConditionMessage() {
        return personConditionMessage;
    }

    public void setPersonConditionMessage(String personConditionMessage) {
        this.personConditionMessage = personConditionMessage;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
