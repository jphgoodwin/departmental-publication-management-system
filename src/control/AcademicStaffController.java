package control;

import java.time.LocalDate;
import java.util.ArrayList;

import model.AcademicStaff;
import model.Book;
import model.ConferencePaper;
import model.DPMSystemException;
import model.DataStorage;
import model.JournalPaper;
import model.Publication;

/**
 * 
 * 
 *
 */
public class AcademicStaffController {
	
	private DataStorage dataStore;
	private AcademicStaff staffMember;
	
	public AcademicStaffController(DataStorage dataStore) {
		this.dataStore = dataStore;
	}
	
	/**
	 * Takes an email address and password as inputs and compares them against those
	 * contained in staff list. If a match is found, the method returns a reference
	 * to the academic staff class instance, at the same time storing this reference 
	 * for future use.
	 * 
	 * @param emailAddress
	 * @param password
	 * @return Reference to academic staff class instance.
	 * @throws DPMSystemException If no matching email address and password are found.
	 */
	public AcademicStaff login(String emailAddress, String password) throws DPMSystemException {
		Boolean staffMemberFound = false;
		
		for (AcademicStaff a : dataStore.getAcademicStaffList()) {
			if (a.getEmailAddress().equals(emailAddress) && a.getPassword().equals(password)) {
				staffMember = a;
				staffMemberFound = true;
			}
		}
		
		if (!staffMemberFound) {
			throw new DPMSystemException("Email address and/or password not found.");
		}
		else {
			return staffMember;
		}
	}
	
	/**
	 * Adds publication to data store and staff member's publication lists.
	 * 
	 * @param publication
	 * @throws DPMSystemException Passed to it from data storage or academic staff classes.
	 */
	public void addPublication(Publication publication) throws DPMSystemException {
		dataStore.addPublication(publication);
		staffMember.addPublication(publication);
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Removes publication from data store and staff member's publication lists.
	 * 
	 * @param publication
	 * @throws DPMSystemException Passed to it from data storage or academic staff classes.
	 */
	public void removePublication(Publication publication) throws DPMSystemException {
		dataStore.removePublication(publication);
		staffMember.removePublication(publication);
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Replaces specified variables of journal paper with given parameters.
	 * 
	 * @param publicationID
	 * @param title
	 * @param authorNames
	 * @param aBstract
	 * @param journalName
	 * @param pageNumber
	 * @throws DPMSystemException If the publication is not found in the publication list.
	 */
	public void editPublication(String publicationID, String title, ArrayList<String> authorNames, String aBstract, 
			String journalName, int pageNumber) throws DPMSystemException {
		boolean edited = false;
		
		for (Publication p : dataStore.getPublicationList()) {
			if (p.getPublicationID().equals(publicationID)) {
				JournalPaper paper = (JournalPaper) p;
				paper.setTitle(title);
				paper.setAuthorNames(authorNames);
				paper.setaBstract(aBstract);
				paper.setJournalName(journalName);
				paper.setPageNumber(pageNumber);
				edited = true;
			}
		}
		
		if (!edited) {
			throw new DPMSystemException("Publication not found in list.");
		}
		
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Replaces specified variables of conference paper with given parameters.
	 * 
	 * @param publicationID
	 * @param title
	 * @param authorNames
	 * @param aBstract
	 * @param conferenceName
	 * @param conferenceLocation
	 * @param conferenceDate
	 * @throws DPMSystemException If the publication is not found in the publication list.
	 */
	public void editPublication(String publicationID, String title, ArrayList<String> authorNames, String aBstract,
			String conferenceName, String conferenceLocation, LocalDate conferenceDate) throws DPMSystemException {
		boolean edited = false;
		
		for (Publication p : dataStore.getPublicationList()) {
			if (p.getPublicationID().equals(publicationID)) {
				ConferencePaper paper = (ConferencePaper) p;
				paper.setTitle(title);
				paper.setAuthorNames(authorNames);
				paper.setaBstract(aBstract);
				paper.setConferenceName(conferenceName);
				paper.setConferenceLocation(conferenceLocation);
				paper.setConferenceDate(conferenceDate);
				edited = true;
			}
		}
		
		if (!edited) {
			throw new DPMSystemException("Publication not found in list.");
		}
		
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Replaces specified variables of book with given parameters.
	 * 
	 * @param publicationID
	 * @param title
	 * @param authorNames
	 * @param aBstract
	 * @param iSBN
	 * @param publisher
	 * @throws DPMSystemException
	 */
	public void editPublication(String publicationID, String title, ArrayList<String> authorNames, String aBstract,
			String iSBN, String publisher) throws DPMSystemException {
		boolean edited = false;
		
		for (Publication p : dataStore.getPublicationList()) {
			if (p.getPublicationID().equals(publicationID)) {
				Book paper = (Book) p;
				paper.setTitle(title);
				paper.setAuthorNames(authorNames);
				paper.setaBstract(aBstract);
				paper.setiSBN(iSBN);
				paper.setPublisher(publisher);
				edited = true;
			}
		}
		
		if (!edited) {
			throw new DPMSystemException("Publication not found in list.");
		}
		
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Sends the request for support as an email to the system administrator.
	 * 
	 * @param message Message to system administrator from user.
	 */
	public void requestSupport(String message) {
		String emailMessage = new String(staffMember.getName() + "\n\n" + staffMember.getEmailAddress() + 
				"\n\n" + message);
		
		EmailSystem.sendEmail(dataStore.getSystemAdmin().getEmailAddress(), emailMessage);
	}
}
