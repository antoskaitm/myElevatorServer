package main.helpers;

public interface ISessionHelper {
	void setPage(String page);

	String getPage();

	void setRequestId(Integer id);

	Integer getRequestId();
}
