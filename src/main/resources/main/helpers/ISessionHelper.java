package main.helpers;

import main.entities.primitive.Person;

public interface ISessionHelper {
	void setPage(String page);

	String getPage();

	void setPerson(Person person);

	Person getPerson();
}
