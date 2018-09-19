package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class JournalPaper extends Publication {
	private String journalName;
	private int pageNumber;
	public JournalPaper(String publicationID, String title, ArrayList<String> authorNames, String aBstract,
			int groupID, String staffEmail, publicationType type, LocalDate publicationDate, String journalName, int pageNumber) {
		super(publicationID, title, authorNames, aBstract, groupID, staffEmail, type, publicationDate);
		this.journalName = journalName;
		this.pageNumber = pageNumber;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
}
