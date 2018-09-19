package model;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Publication {
	public enum publicationType {
		JOURNALPAPER,
		CONFERENCEPAPER,
		BOOK;
	}
	
	private String publicationID;
	private String title;
	private ArrayList<String> authorNames;
	private String aBstract;
	private int groupID;
	private String staffEmail;
	private publicationType type;
	private LocalDate publicationDate;
	
	public Publication(String publicationID, String title, ArrayList<String> authorNames, String aBstract,
			int groupID, String staffEmail, publicationType type, LocalDate publicationDate) {
		this.publicationID = publicationID;
		this.title = title;
		this.authorNames = authorNames;
		this.aBstract = aBstract;
		this.groupID = groupID;
		this.staffEmail = staffEmail;
		this.type = type;
		this.setPublicationDate(publicationDate);
	}
	
	public publicationType getPublicationType() {
		return this.type;
	}

	public String getPublicationID() {
		return publicationID;
	}

	public void setPublicationID(String publicationID) {
		this.publicationID = publicationID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<String> getAuthorNames() {
		return authorNames;
	}
	
	public void addAuthorName(String name) {
		
	}
	
	public void removeAuthorName(String name) {
		
	}

	public void setAuthorNames(ArrayList<String> authorNames) {
		this.authorNames = authorNames;
	}

	public String getaBstract() {
		return aBstract;
	}

	public void setaBstract(String aBstract) {
		this.aBstract = aBstract;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public String getStaffEmail() {
		return staffEmail;
	}

	public void setStaffEmail(String staffEmail) {
		this.staffEmail = staffEmail;
	}
	
	/**
	 * Compares given object to publication, returning true if the publications
	 * are equal, and false if not.
	 * 
	 * @return True if equal, false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Publication) {
			Publication publication = (Publication) obj;
			if (publication.getTitle().equals(this.getTitle()) && 
					publication.getPublicationID().equals(this.getPublicationID())) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	public LocalDate getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(LocalDate publicationDate) {
		this.publicationDate = publicationDate;
	}
}
