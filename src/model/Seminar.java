package model;

import java.time.LocalDateTime;

public class Seminar {
	private LocalDateTime dateTime;
	private String location;
	private String topic;
	private ResearchGroup group;
	
	public Seminar(LocalDateTime dateTime, String location, String topic, ResearchGroup group) {
		this.dateTime = dateTime;
		this.location = location;
		this.topic = topic;
		this.group = group;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	/**
	 * Sets the date and time for the seminar to the specified value.
	 * 
	 * @param dateTime
	 * @throws DPMSystemException If DateTime has already passed.
	 */
	public void setDateTime(LocalDateTime dateTime) throws DPMSystemException {
		if (dateTime.isBefore(LocalDateTime.now())) {
			throw new DPMSystemException("Date has already passed.");
		} else {
			this.dateTime = dateTime;
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public ResearchGroup getGroup() {
		return group;
	}

	public void setGroup(ResearchGroup group) {
		this.group = group;
	}
	
	/**
	 * Compares given object to seminar, returning true if the seminars
	 * are equal, and false if not.
	 * 
	 * @return True if equal, false if not.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Seminar) {
			Seminar seminar = (Seminar) obj;
			if (seminar.getDateTime().isEqual(this.dateTime) &&
					seminar.getTopic().equals(this.topic)) {
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
