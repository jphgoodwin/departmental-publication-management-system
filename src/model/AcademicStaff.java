package model;

import java.util.ArrayList;

public class AcademicStaff extends GroupMember {
	private String password;
	private Boolean isCoordinator;
	private ArrayList<Publication> publicationList;
	
	public AcademicStaff(String name, String title, ResearchGroup group, String emailAddress,
			memberType type, String password) {
		super(name, title, group, emailAddress, type);
		this.password = password;
		publicationList = new ArrayList<Publication>();
		isCoordinator = false;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean coordinator() {
		return this.isCoordinator;
	}
	
	public void setCoordinator(boolean value) {
		if (value) {
			this.getGroup().setSeminarCoordinator(this);
		}
		
		this.isCoordinator = value;
	}
	
	/**
	 * Adds the publication passed to it to the publication list.
	 * 
	 * @param publication
	 * @throws DPMSystemException If publication with the same title already exists in list.
	 */
	public void addPublication(Publication publication) throws DPMSystemException {
		if (publicationList.contains(publication)) {
			throw new DPMSystemException("Publication already exists.");
		}
		else {
			publicationList.add(publication);
		}
	}
	
	/**
	 * Removes from the publication list, the publication passed to it.
	 * 
	 * @param publication
	 * @throws DPMSystemException If publication does not exist in list.
	 */
	public void removePublication(Publication publication) throws DPMSystemException {
		if (publicationList.contains(publication)) {
			publicationList.remove(publication);
		}
		else {
			throw new DPMSystemException("Publication not in publication list.");
		}
	}

	public ArrayList<Publication> getPublicationList() {
		return publicationList;
	}
	
	/**
	 * Compares given object to academic staff, returning true if the academic staff
	 * are equal, and false if not.
	 * 
	 * @return True if equal, false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AcademicStaff) {
			AcademicStaff staffMember = (AcademicStaff) obj;
			if (staffMember.getEmailAddress().equals(this.getEmailAddress())
				&& staffMember.getPassword().equals(this.getPassword())) {
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
}
