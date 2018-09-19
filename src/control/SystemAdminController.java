package control;

import model.AcademicStaff;
import model.DPMSystemException;
import model.DataStorage;
import model.ResearchGroup;
import model.ResearchStudent;
import model.SystemAdmin;

public class SystemAdminController {
	
	private DataStorage dataStore;
	private SystemAdmin systemAdmin;
	
	public SystemAdminController(DataStorage dataStore) {
		this.dataStore = dataStore;
		this.systemAdmin = dataStore.getSystemAdmin();
	}
	
	/**
	 * Takes an email address and password and compares them to those of the 
	 * system administrator.
	 * 
	 * @param emailAddress
	 * @param password
	 * @return True if email and password match, False if not.
	 */
	public boolean login(String emailAddress, String password) {
		if (systemAdmin.getEmailAddress().equals(emailAddress) &&
				systemAdmin.getPassword().equals(password)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Registers a new staff member with their research group and the system.
	 * 
	 * @param staffMember
	 * @throws DPMSystemException If their group is not listed.
	 */
	public void registerStaffMember(AcademicStaff staffMember) throws DPMSystemException {
		boolean registered = false;
		for (ResearchGroup g : dataStore.getResearchGroupList()) {
			if (g.equals(staffMember.getGroup())) {
				dataStore.addAcademicStaff(staffMember);
				g.addMember(staffMember);
				registered = true;
			}
		}
		
		if (!registered) {
			throw new DPMSystemException("Group not listed.");
		}
		
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Removes a staff member from their research group and the system.
	 * 
	 * @param staffMember
	 * @throws DPMSystemException If their group is not listed.
	 */
	public void removeStaffMember(AcademicStaff staffMember) throws DPMSystemException {
		boolean removed = false;
		for (ResearchGroup g : dataStore.getResearchGroupList()) {
			if (g.equals(staffMember.getGroup())) {
				dataStore.removeAcademicStaff(staffMember);
				g.removeMember(staffMember);
				removed = true;
			}
		}
		
		if (!removed) {
			throw new DPMSystemException("Group not listed.");
		}
		
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Replaces the details of the academic staff member with the given parameters.
	 * 
	 * @param staffMember
	 * @param name
	 * @param title
	 * @param emailAddress
	 * @param password
	 * @throws DPMSystemException If staff member is not found.
	 */
	public void editStaffDetails(AcademicStaff staffMember, String name, String title, String emailAddress, 
			String password) throws DPMSystemException {
		boolean edited = false;
		for (AcademicStaff a : dataStore.getAcademicStaffList()) {
			if (a.equals(staffMember)) {
				a.setName(name);
				a.setTitle(title);
				a.setEmailAddress(emailAddress);
				a.setPassword(password);
				edited = true;
			}
		}
		
		if (!edited) {
			throw new DPMSystemException("Staff member not found.");
		}
		
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Registers a new research student with their research group and the system.
	 * 
	 * @param student
	 * @throws DPMSystemException If their group is not listed.
	 */
	public void registerResearchStudent(ResearchStudent student) throws DPMSystemException {
		boolean registered = false;
		for (ResearchGroup g : dataStore.getResearchGroupList()) {
			if (g.equals(student.getGroup())) {
				dataStore.addResearchStudent(student);
				g.addMember(student);
				registered = true;
			}
		}
		
		if (!registered) {
			throw new DPMSystemException("Group not listed.");
		}
		
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Removes a research student from their research group and the system.
	 * 
	 * @param student
	 * @throws DPMSystemException If their group is not listed.
	 */
	public void removeResearchStudent(ResearchStudent student) throws DPMSystemException {
		boolean removed = false;
		for (ResearchGroup g : dataStore.getResearchGroupList()) {
			if (g.equals(student.getGroup())) {
				dataStore.removeResearchStudent(student);
				g.removeMember(student);
				removed = true;
			}
		}
		
		if (!removed) {
			throw new DPMSystemException("Group not listed.");
		}
		
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Replaces the details of the research student with the given parameters.
	 * 
	 * @param student
	 * @param name
	 * @param title
	 * @param emailAddress
	 * @param studentID
	 * @throws DPMSystemException If research student is not found.
	 */
	public void editStudentDetails(ResearchStudent student, String name, String title, String emailAddress,
			String studentID) throws DPMSystemException {
		boolean edited = false;
		for (ResearchStudent r : dataStore.getResearchStudentList()) {
			if (r.equals(student)) {
				r.setName(name);
				r.setTitle(title);
				r.setEmailAddress(emailAddress);
				r.setStudentID(studentID);
				edited = true;
			}
		}
		
		if (!edited) {
			throw new DPMSystemException("Research student not found.");
		}
		
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Registers a new research group with the system.
	 * 
	 * @param group
	 * @throws DPMSystemException
	 */
	public void registerResearchGroup(ResearchGroup group) throws DPMSystemException {
		dataStore.addResearchGroup(group);
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Removes a research group from the system.
	 * 
	 * @param group
	 * @throws DPMSystemException
	 */
	public void removeResearchGroup(ResearchGroup group) throws DPMSystemException {
		dataStore.removeResearchGroup(group);
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Replaces the details of the research group with those given as parameters.
	 * 
	 * @param group
	 * @param name
	 * @param groupID
	 * @throws DPMSystemException If research group is not found.
	 */
	public void editResearchGroup(ResearchGroup group, String name, int groupID) throws DPMSystemException {
		boolean edited = false;
		for (ResearchGroup g : dataStore.getResearchGroupList()) {
			if (g.equals(group)) {
				g.setName(name);
				g.setGroupID(groupID);
				edited = true;
			}
		}
		
		if (!edited) {
			throw new DPMSystemException("Research group not found.");
		}
		
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
	
	/**
	 * Makes the given staff member the seminar coordinator for their group.
	 * 
	 * @param staffMember
	 * @throws DPMSystemException If staff member is not found.
	 */
	public void makeSeminarCoordinator(AcademicStaff staffMember) throws DPMSystemException {
		boolean setCoord = false;
		for (AcademicStaff a : dataStore.getAcademicStaffList()) {
			if (a.equals(staffMember)) {
				a.setCoordinator(true);
				a.getGroup().setSeminarCoordinator(a);
				setCoord = true;
			}
		}
		
		if (!setCoord) {
			throw new DPMSystemException("Staff member not found.");
		}
		
		dataStore.notifyViews();
		dataStore.saveData(dataStore.getFile());
	}
}
