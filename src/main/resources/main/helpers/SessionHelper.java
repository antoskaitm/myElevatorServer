package main.helpers;

import main.entities.primitive.Person;

import javax.servlet.http.HttpSession;

public class SessionHelper implements ISessionHelper {
	public final HttpSession session;

	public SessionHelper(HttpSession session) {
		this.session = session;
	}

	public void setPage(String page) {
		session.setAttribute("page", page);
	}

	public String getPage() {
		return (String) session.getAttribute("page");
	}

	public void setPerson(Person person) {
		session.setAttribute("person", person);
	}

	public Person getPerson() {
		return (Person) session.getAttribute("person");
	}
}
