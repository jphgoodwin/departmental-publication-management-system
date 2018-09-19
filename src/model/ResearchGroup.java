package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ResearchGroup {
	private String name;
	private ArrayList<GroupMember> members;
	private int groupID;
	private AcademicStaff seminarCoordinator;
	private ArrayList<Seminar> seminarList;
	
	public ResearchGroup(String name, int groupID) {
		this.members = new ArrayList<GroupMember>();
		this.seminarList = new ArrayList<Seminar>();
		this.name = name;
		this.groupID = groupID;
	}
	
	/**
	 * Adds member to research group.
	 * 
	 * @param member
	 * @throws DPMSystemException If member is already listed in group.
	 */
	public void addMember(GroupMember member) throws DPMSystemException {
		if (members.contains(member)) {
			throw new DPMSystemException("Member already exists in group.");
		}
		else {
			members.add(member);
		}
	}
	
	/**
	 * Removes member from research group.
	 * 
	 * @param member
	 * @throws DPMSystemException If they are not listed in group.
	 */
	public void removeMember(GroupMember member) throws DPMSystemException {
		if (members.contains(member)) {
			members.remove(member);
		}
		else {
			throw new DPMSystemException("They are not a member of this group.");
		}
	}
	
	/**
	 * Adds new seminar to research group.
	 * 
	 * @param seminar
	 * @throws DPMSystemException If the seminar is already present in the list,
	 * or if the seminar is set for a date that has already passed.
	 */
	public void addNewSeminar(Seminar seminar) throws DPMSystemException {
		if (seminar.getDateTime().isBefore(LocalDateTime.now())) {
			throw new DPMSystemException("Date has already passed.");
		}
		else if (seminarList.contains(seminar)) {
			throw new DPMSystemException("Seminar already contained in list.");
		}
		else {
			seminarList.add(seminar);
		}
	}
	
	/**
	 * Adds previous seminar to research group.
	 * 
	 * @param seminar
	 * @throws DPMSystemException If the seminar is already present in the list.
	 */
	public void addOldSeminar(Seminar seminar) throws DPMSystemException {
		if (seminarList.contains(seminar)) {
			throw new DPMSystemException("Seminar already contained in list.");
		}
		else {
			seminarList.add(seminar);
		}
	}
	
	/**
	 * Removes seminar from research group.
	 * 
	 * @param seminar
	 * @throws DPMSystemException If seminar is not in list.
	 */
	public void removeSeminar(Seminar seminar) throws DPMSystemException {
		if (seminarList.contains(seminar)) {
			seminarList.remove(seminar);
		}
		else {
			throw new DPMSystemException("Seminar not in list.");
		}
	}
	
	public ArrayList<Seminar> getSeminarList() {
		return seminarList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<GroupMember> getMembers() {
		return members;
	}

	public int getGroupID() {
		return groupID;
	}

	public void setGroupID(int groupID) {
		this.groupID = groupID;
	}

	public AcademicStaff getSeminarCoordinator() {
		return seminarCoordinator;
	}

	public void setSeminarCoordinator(AcademicStaff seminarCoordinator) {
		this.seminarCoordinator = seminarCoordinator;
	}
	
	/**
	 * Compares given object to research group, returning true if the research groups
	 * are equal, and false if not.
	 * 
	 * @return True if equal, false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ResearchGroup) {
			ResearchGroup group = (ResearchGroup) obj;
			if (group.getGroupID() == this.groupID) {
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
