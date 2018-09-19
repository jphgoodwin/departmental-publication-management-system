package control;

import java.util.ArrayList;

import model.DPMSystemException;
import model.DataStorage;
import model.Publication;

/**
 * The UserController class acts provides methods for the UserView class to
 * allow it pass on requests from the user.
 *
 */
public class UserController {
	
	private DataStorage dataStore;
	
	public UserController(DataStorage dataStore) {
		this.dataStore = dataStore;
	}
	
	/**
	 * Sends the publication request as an email to the respective author.
	 * 
	 * @param publication Publication being requested.
	 * @param userName Name of user making request.
	 * @param userEmail Email of user making request.
	 * @param message Message to author from user.
	 */
	public void requestPublication(Publication publication, String userName, String userEmail, String message) {
		String emailMessage = new String(userName + "\n\n" + userEmail + "\n\n" + publication.getTitle() + 
				"\n\n" + message);
		
		EmailSystem.sendEmail(publication.getStaffEmail(), emailMessage);
	}
	
	/**
	 * Sends the request for support as an email to the system administrator.
	 * 
	 * @param userName Name of user making request.
	 * @param userEmail Email of user making request.
	 * @param message Message to system administrator from user.
	 */
	public void requestSupport(String userName, String userEmail, String message) {
		String emailMessage = new String(userName + "\n\n" + userEmail + 
				"\n\n" + message);
		
		EmailSystem.sendEmail(dataStore.getSystemAdmin().getEmailAddress(), emailMessage);
	}
	
	/**
	 * Searches publication list for a publication with the given title.
	 * 
	 * @param title
	 * @return Returns the publication with the given title.
	 * @throws DPMSystemException If no publication title matches search.
	 */
	public Publication searchByTitle(String title) throws DPMSystemException {
		int index = -1;
		
		for (int i=0; i < dataStore.getPublicationList().size(); i++) {
			if (dataStore.getPublicationList().get(i).getTitle().equals(title)) {
				index = i;
				break;
			}
		}
		
		if (index == -1 ) {
			throw new DPMSystemException("No search result found.");
		}
		else {
			return dataStore.getPublicationList().get(index);
		}
	}
	
	/**
	 * Searches publication list for publications with the given author.
	 * 
	 * @param author
	 * @return Returns a list of publications by that author.
	 * @throws DPMSystemException If no publications by that author were found.
	 */
	public ArrayList<Publication> searchByAuthor(String author) throws DPMSystemException {
		ArrayList<Publication> pubList = new ArrayList<Publication>();
		
		for (Publication p : dataStore.getPublicationList()) {
			for (String a : p.getAuthorNames()) {
				if (a.equals(author)) {
					pubList.add(p);
					break;
				}
			}
		}
		
		if (pubList.isEmpty()) {
			throw new DPMSystemException("No search result found.");
		}
		else {
			return pubList;
		}
	}
	
	/**
	 * Searches publication list for publications of the given type.
	 * 
	 * @param type
	 * @return Returns a list of publications of the given type.
	 * @throws DPMSystemException If no publications of the given type are found.
	 */
	public ArrayList<Publication> searchByType(Publication.publicationType type) throws DPMSystemException {
		ArrayList<Publication> pubList = new ArrayList<Publication>();
		
		for (Publication p : dataStore.getPublicationList()) {
			if (p.getPublicationType().equals(type)) {
				pubList.add(p);
			}
		}
		
		if (pubList.isEmpty()) {
			throw new DPMSystemException("No publications of that type found.");
		}
		else {
			return pubList;
		}
	}
}
