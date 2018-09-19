package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Book extends Publication {
	private String iSBN;
	private String publisher;
	
	public Book(String publicationID, String title, ArrayList<String> authorNames, String aBstract, int groupID,
			String staffEmail, publicationType type, LocalDate publicationDate, String iSBN, String publisher) {
		super(publicationID, title, authorNames, aBstract, groupID, staffEmail, type, publicationDate);
		this.iSBN = iSBN;
		this.publisher = publisher;
	}

	public String getiSBN() {
		return iSBN;
	}

	public void setiSBN(String iSBN) {
		this.iSBN = iSBN;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
}
