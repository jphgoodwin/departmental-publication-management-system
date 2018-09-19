package model;

public class ResearchStudent extends GroupMember {
	private String studentID;

	public ResearchStudent(String name, String title, ResearchGroup group, String emailAddress,
			memberType type, String studentID) {
		super(name, title, group, emailAddress, type);
		this.studentID = studentID;
	}
	
	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	
	/**
	 * Compares given object to research student, returning true if the research students
	 * are equal, and false if not.
	 * 
	 * @return True if equal, false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ResearchStudent) {
			ResearchStudent student = (ResearchStudent) obj;
			if (student.getEmailAddress().equals(this.getEmailAddress())
				&& student.getStudentID().equals(this.studentID)) {
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
