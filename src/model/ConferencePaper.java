package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ConferencePaper extends Publication {
	private String conferenceName;
	private String conferenceLocation;
	private LocalDate conferenceDate;
	
	public ConferencePaper(String publicationID, String title, ArrayList<String> authorNames, String aBstract,
			int groupID, String staffEmail, publicationType type, LocalDate publicationDate, String conferenceName, 
			String conferenceLocation, LocalDate conferenceDate) {
		super(publicationID, title, authorNames, aBstract, groupID, staffEmail, type, publicationDate);
		this.conferenceName = conferenceName;
		this.conferenceLocation = conferenceLocation;
		this.conferenceDate = conferenceDate;
	}

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getConferenceLocation() {
		return conferenceLocation;
	}

	public void setConferenceLocation(String conferenceLocation) {
		this.conferenceLocation = conferenceLocation;
	}

	public LocalDate getConferenceDate() {
		return conferenceDate;
	}

	public void setConferenceDate(LocalDate conferenceDate) {
		this.conferenceDate = conferenceDate;
	}
}
