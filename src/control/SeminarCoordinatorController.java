package control;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import model.DPMSystemException;
import model.DataStorage;
import model.GroupMember;
import model.Publication;
import model.ResearchGroup;
import model.Seminar;

public class SeminarCoordinatorController {
	
	private DataStorage dataStore;
	private ResearchGroup group;
	private ArrayList<Publication> lastMonthsPublications;
	private ArrayList<String> memberEmailList;
	
	public SeminarCoordinatorController(DataStorage dataStore, ResearchGroup group) {
		this.lastMonthsPublications = new ArrayList<Publication>();
		this.memberEmailList = new ArrayList<String>();
		this.dataStore = dataStore;
		this.group = group;
	}
	
	/**
	 * Adds seminar to research group's seminar list.
	 * 
	 * @param seminar
	 * @throws DPMSystemException
	 */
	public void addSeminar(Seminar seminar) throws DPMSystemException {
		group.addNewSeminar(seminar);
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Removes seminar from research groups's seminar list.
	 * 
	 * @param seminar
	 * @throws DPMSystemException
	 */
	public void removeSeminar(Seminar seminar) throws DPMSystemException {
		group.removeSeminar(seminar);
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Replaces the seminar details with those given as parameters.
	 * 
	 * @param seminar
	 * @param dateTime
	 * @param location
	 * @param topic
	 * @throws DPMSystemException If seminar is not listed.
	 */
	public void editSeminarDetails(Seminar seminar, LocalDateTime dateTime, String location, String topic)
			throws DPMSystemException {
		boolean edited = false;
		for (Seminar s : group.getSeminarList()) {
			if (s.equals(seminar)) {
				s.setDateTime(dateTime);
				s.setLocation(location);
				s.setTopic(topic);
				edited = true;
			}
		}
		
		if (!edited) {
			throw new DPMSystemException("Seminar not in list.");
		}

		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	//Fills monthly publication list with publications authored by members of the group in the last 30 days.
	private void fillPublicationList() {
		for (Publication p : dataStore.getPublicationList()) {
			if (p.getGroupID() == group.getGroupID() && 
					p.getPublicationDate().isAfter(LocalDate.now().minusDays(30))) {
				lastMonthsPublications.add(p);
			}
		}
	}
	
	//Fills email list with email addresses of all group members.
	private void fillEmailList() {
		for (GroupMember m : group.getMembers()) {
			memberEmailList.add(m.getEmailAddress());
		}
	}
	
	/**
	 * Publishes seminar details via email to members of research group, along with a list of publications
	 * authored in the last 30 days by members of the group.
	 * 
	 * @param seminar
	 * @throws DPMSystemException
	 */
	public void publishSeminar(Seminar seminar) throws DPMSystemException {
		if (group.getSeminarList().contains(seminar) && seminar.getDateTime().isAfter(LocalDateTime.now())) {
			fillPublicationList();
			fillEmailList();
			String message = new String("Hello,\nThis week's seminar on the topic of: " + seminar.getTopic() + 
					", is in " + seminar.getLocation() + " at " + seminar.getDateTime().getHour() + 
					":" + seminar.getDateTime().getMinute() + " on " + seminar.getDateTime().getDayOfWeek() + 
					"\n\nHere is a list of this months publications:\n");
			for (Publication p : lastMonthsPublications) {
				message = message.concat("\n" + p.getTitle() + "\t" + p.getAuthorNames().get(0));
				if (p.getAuthorNames().size() > 1) {
					message = message.concat(" et al.");
				}
			}
			
			EmailSystem.sendEmail(memberEmailList, message);
		} else {
			throw new DPMSystemException("Seminar either not in list or is scheduled for a date that has already passed.");
		}
	}
}
