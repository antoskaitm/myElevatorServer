package main.helpers;

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

	public void setRequestId(Integer id) {
		session.setAttribute("id", id);
	}

	public Integer getRequestId() {
		return (Integer) session.getAttribute("id");
	}
}
