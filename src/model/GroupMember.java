package model;

public abstract class GroupMember {
	public enum memberType {
		STUDENT,
		STAFF;
	}
	
	private String name;
	private String title;
	private ResearchGroup group;
	private String emailAddress;
	private memberType type;
	
	public GroupMember(String name, String title, ResearchGroup group, String emailAddress, 
			memberType type) {
		this.name = name;
		this.title = title;
		this.group = group;
		this.emailAddress = emailAddress;
		this.setType(type);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ResearchGroup getGroup() {
		return group;
	}
	public void setGroup(ResearchGroup group) {
		this.group = group;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public memberType getType() {
		return type;
	}
	public void setType(memberType type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GroupMember) {
			GroupMember member = (GroupMember) obj;
			if (member.getEmailAddress().equals(this.emailAddress)) {
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
