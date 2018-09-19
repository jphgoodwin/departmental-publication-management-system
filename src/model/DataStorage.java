package model;

import org.w3c.dom.*;

import model.GroupMember.memberType;
import model.Publication.publicationType;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import view.Viewer;

/**
 * The DataStorage class holds the lists of publications, research groups, academic staff,
 * and research students, and a reference to the system administrator. As well a list registered views,
 * which it notifies when changes are made.
 *
 */
public class DataStorage implements Viewable {
	private ArrayList<Viewer> registeredViews;
	private ArrayList<Publication> publicationList;
	private ArrayList<ResearchGroup> researchGroupList;
	private ArrayList<AcademicStaff> academicStaffList;
	private ArrayList<ResearchStudent> researchStudentList;
	private SystemAdmin systemAdmin;
	private File file;
	
	public DataStorage() {
		registeredViews = new ArrayList<Viewer>();
		publicationList = new ArrayList<Publication>();
		researchGroupList = new ArrayList<ResearchGroup>();
		academicStaffList = new ArrayList<AcademicStaff>();
		researchStudentList = new ArrayList<ResearchStudent>();
		
		file = new File("DPMSystemData.xml");
	}
	
	public File getFile() {
		return this.file;
	}
	
	/**
	 * Adds the publication passed to it to the publication list.
	 * 
	 * @param publication
	 * @throws DPMSystemException If publication with the same title already exists in list.
	 */
	public void addPublication(Publication publication) throws DPMSystemException {
		for (Publication p : publicationList) {
			if (p.getTitle().equals(publication.getTitle()) || p.getPublicationID().equals(publication.getPublicationID())) {
				throw new DPMSystemException("Publication with this publicationID or title already exists");
			}
		}
		publicationList.add(publication);
		
		notifyViews();
	}
	
	/**
	 * Removes from the publication list, the publication passed to it.
	 * 
	 * @param publication
	 * @throws DPMSystemException If publication does not exist in list.
	 */
	public void removePublication(Publication publication) throws DPMSystemException {
		if (!publicationList.remove(publication)) {
			throw new DPMSystemException("Publication not present in list.");
		}
		
		notifyViews();
	}
	
	/**
	 * Adds research group to research group list.
	 * 
	 * @param group
	 * @throws DPMSystemException If group is already in list, 
	 * or if there are already 10 groups in the list.
	 */
	public void addResearchGroup(ResearchGroup group) throws DPMSystemException {
		if (researchGroupList.contains(group)) {
			throw new DPMSystemException("Group already in list.");
		}
		else if (researchGroupList.size() == 10) {
			throw new DPMSystemException("There are already 10 groups registered to department.");
		}
		else {
			researchGroupList.add(group);
		}
		
		notifyViews();
	}
	
	/**
	 * Removes research group from research group list.
	 * 
	 * @param group
	 * @throws DPMSystemException If group is not in list.
	 */
	public void removeResearchGroup(ResearchGroup group) throws DPMSystemException {
		if (researchGroupList.contains(group)) {
			researchGroupList.remove(group);
		}
		else {
			throw new DPMSystemException("Group not in list.");
		}
		
		notifyViews();
	}
	
	/**
	 * Adds academic staff member to academic staff list.
	 * 
	 * @param staff
	 * @throws DPMSystemException If staff member is already in staff list.
	 */
	public void addAcademicStaff(AcademicStaff staff) throws DPMSystemException {
		if (academicStaffList.contains(staff)) {
			throw new DPMSystemException("Staff member already exists in staff list.");
		}
		else {
			academicStaffList.add(staff);
		}
		
		notifyViews();
	}
	
	/**
	 * Removes academic staff member from academic staff list.
	 * 
	 * @param staff
	 * @throws DPMSystemException If staff member is not in staff list.
	 */
	public void removeAcademicStaff(AcademicStaff staff) throws DPMSystemException {
		if (academicStaffList.contains(staff)) {
			academicStaffList.remove(staff);
		}
		else {
			throw new DPMSystemException("Staff member is not in staff list.");
		}
		
		notifyViews();
	}
	
	/**
	 * Adds research student to research student list.
	 * 
	 * @param student
	 * @throws DPMSystemException If student is already listed.
	 */
	public void addResearchStudent(ResearchStudent student) throws DPMSystemException {
		if (researchStudentList.contains(student)) {
			throw new DPMSystemException("Student already in list.");
		}
		else {
			researchStudentList.add(student);
		}
		
		notifyViews();
	}
	
	/**
	 * Removes research student from research student list.
	 * 
	 * @param student
	 * @throws DPMSystemException If student is not in list.
	 */
	public void removeResearchStudent(ResearchStudent student) throws DPMSystemException {
		if (researchStudentList.contains(student)) {
			researchStudentList.remove(student);
		}
		else {
			throw new DPMSystemException("Student is not in list.");
		}
		
		notifyViews();
	}

	public ArrayList<Viewer> getRegisteredViews() {
		return registeredViews;
	}

	public ArrayList<Publication> getPublicationList() {
		return publicationList;
	}

	public ArrayList<ResearchGroup> getResearchGroupList() {
		return researchGroupList;
	}

	public ArrayList<AcademicStaff> getAcademicStaffList() {
		return academicStaffList;
	}

	public ArrayList<ResearchStudent> getResearchStudentList() {
		return researchStudentList;
	}

	public SystemAdmin getSystemAdmin() {
		return systemAdmin;
	}

	public void setSystemAdmin(SystemAdmin systemAdmin) {
		this.systemAdmin = systemAdmin;
	}
	
	/**
	 * Saves system data to an xml file, allowing it to be recovered later.
	 * 
	 * @param file xml file
	 */
	public void saveData(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			//Root element
			Document doc = builder.newDocument();
			Element rootEl = doc.createElement("dataStore");
			doc.appendChild(rootEl);
			
			//System administrator
			Element sysAdmin = doc.createElement("systemAdministrator");
			rootEl.appendChild(sysAdmin);
			
			if (systemAdmin != null) {
				Element adminName = doc.createElement("name");
				adminName.appendChild(doc.createTextNode(systemAdmin.getName()));
				sysAdmin.appendChild(adminName);
				
				Element officeNumber = doc.createElement("officeNumber");
				officeNumber.appendChild(doc.createTextNode(systemAdmin.getOfficeNumber()));
				sysAdmin.appendChild(officeNumber);
				
				Element phoneNumber = doc.createElement("phoneNumber");
				phoneNumber.appendChild(doc.createTextNode(systemAdmin.getPhoneNumber()));
				sysAdmin.appendChild(phoneNumber);
				
				Element adminEmail = doc.createElement("adminEmail");
				adminEmail.appendChild(doc.createTextNode(systemAdmin.getEmailAddress()));
				sysAdmin.appendChild(adminEmail);
				
				Element adminPassword = doc.createElement("password");
				adminPassword.appendChild(doc.createTextNode(systemAdmin.getPassword()));
				sysAdmin.appendChild(adminPassword);
			}
			
			//Research group list.
			Element rGList = doc.createElement("researchGroupList");
			rootEl.appendChild(rGList);
			
			for (ResearchGroup g : researchGroupList) {
				
				Element researchGroup = doc.createElement("researchGroup");
				rGList.appendChild(researchGroup);
				researchGroup.setAttribute("groupID", String.valueOf(g.getGroupID()));
				
				Element name = doc.createElement("name");
				name.appendChild(doc.createTextNode(g.getName()));
				researchGroup.appendChild(name);
				
				//Member list.
				Element memberList = doc.createElement("memberList");
				researchGroup.appendChild(memberList);
				
				for (GroupMember m : g.getMembers()) {
					
					Element member = doc.createElement("member");
					memberList.appendChild(member);
					
					Element memberName = doc.createElement("name");
					memberName.appendChild(doc.createTextNode(m.getName()));
					member.appendChild(memberName);
					
					Element memberTitle = doc.createElement("title");
					memberTitle.appendChild(doc.createTextNode(m.getTitle()));
					member.appendChild(memberTitle);
					
					Element memberEmail = doc.createElement("memberEmail");
					memberEmail.appendChild(doc.createTextNode(m.getEmailAddress()));
					member.appendChild(memberEmail);
					
					Element memberType = doc.createElement("memberType");
					memberType.appendChild(doc.createTextNode(String.valueOf(m.getType())));
					member.appendChild(memberType);
					
					if (m.getType() == GroupMember.memberType.STUDENT) {
						ResearchStudent student = (ResearchStudent) m;
						
						Element studentID = doc.createElement("studentID");
						studentID.appendChild(doc.createTextNode(student.getStudentID()));
						member.appendChild(studentID);
					} else if (m.getType() == GroupMember.memberType.STAFF) {
						AcademicStaff staff = (AcademicStaff) m;
						
						Element password = doc.createElement("password");
						password.appendChild(doc.createTextNode(staff.getPassword()));
						member.appendChild(password);
						
						Element isCoordinator = doc.createElement("isCoordinator");
						isCoordinator.appendChild(doc.createTextNode(String.valueOf(staff.coordinator())));
						member.appendChild(isCoordinator);
						
						//Publication list.
						Element pubList = doc.createElement("publicationList");
						member.appendChild(pubList);
						
						for (Publication p : staff.getPublicationList()) {
							
							Element publication = doc.createElement("publication");
							pubList.appendChild(publication);
							publication.setAttribute("publicationID", p.getPublicationID());
							
							Element title = doc.createElement("title");
							title.appendChild(doc.createTextNode(p.getTitle()));
							publication.appendChild(title);
							
							Element authorNames = doc.createElement("authorNames");
							publication.appendChild(authorNames);
							
							for (String n : p.getAuthorNames()) {
								Element authorName = doc.createElement("name");
								authorName.appendChild(doc.createTextNode(n));
								authorNames.appendChild(authorName);
							}
							
							Element aBstract = doc.createElement("abstract");
							aBstract.appendChild(doc.createTextNode(p.getaBstract()));
							publication.appendChild(aBstract);
							
							Element groupID = doc.createElement("groupID");
							groupID.appendChild(doc.createTextNode(String.valueOf(p.getGroupID())));
							publication.appendChild(groupID);
							
							Element staffEmail = doc.createElement("staffEmail");
							staffEmail.appendChild(doc.createTextNode(p.getStaffEmail()));
							publication.appendChild(staffEmail);
							
							Element type = doc.createElement("type");
							type.appendChild(doc.createTextNode(String.valueOf(p.getPublicationType())));
							publication.appendChild(type);
							
							Element publicationDate = doc.createElement("publicationDate");
							LocalDate date = p.getPublicationDate();
							String dateString = new String("" + date.getYear() + "," + 
							date.getMonthValue() + "," + date.getDayOfMonth());
							publicationDate.appendChild(doc.createTextNode(dateString));
							publication.appendChild(publicationDate);
							
							if (p.getPublicationType() == Publication.publicationType.JOURNALPAPER) {
								JournalPaper journal = (JournalPaper) p;
								
								Element journalName = doc.createElement("journalName");
								journalName.appendChild(doc.createTextNode(journal.getJournalName()));
								publication.appendChild(journalName);
								
								Element pageNumber = doc.createElement("pageNumber");
								pageNumber.appendChild(doc.createTextNode(String.valueOf(journal.getPageNumber())));
								publication.appendChild(pageNumber);
							} 
							else if (p.getPublicationType() == Publication.publicationType.CONFERENCEPAPER) {
								ConferencePaper conference = (ConferencePaper) p;
								
								Element conferenceName = doc.createElement("conferenceName");
								conferenceName.appendChild(doc.createTextNode(conference.getConferenceName()));
								publication.appendChild(conferenceName);
								
								Element conferenceLocation = doc.createElement("conferenceLocation");
								conferenceLocation.appendChild(doc.createTextNode(conference.getConferenceLocation()));
								publication.appendChild(conferenceLocation);
								
								Element conferenceDate = doc.createElement("conferenceDate");
								LocalDate cDate = conference.getConferenceDate();
								String cDateString = new String("" + cDate.getYear() + "," + 
										cDate.getMonthValue() + "," + cDate.getDayOfMonth());
								conferenceDate.appendChild(doc.createTextNode(cDateString));
								publication.appendChild(conferenceDate);
							}
							else if (p.getPublicationType() == Publication.publicationType.BOOK) {
								Book book = (Book) p;
								
								Element iSBN = doc.createElement("ISBN");
								iSBN.appendChild(doc.createTextNode(book.getiSBN()));
								publication.appendChild(iSBN);
								
								Element publisher = doc.createElement("publisher");
								publisher.appendChild(doc.createTextNode(book.getPublisher()));
								publication.appendChild(publisher);
							}
						}
					}
				}
				
				Element seminarCoordinator = doc.createElement("seminarCoordinator");
				if (g.getSeminarCoordinator() != null) {
					seminarCoordinator.appendChild(doc.createTextNode(g.getSeminarCoordinator().getEmailAddress()));
				}
				researchGroup.appendChild(seminarCoordinator);
				
				//Seminar list.
				Element seminarList = doc.createElement("seminarList");
				researchGroup.appendChild(seminarList);
				
				for (Seminar s : g.getSeminarList()) {
					
					Element seminar = doc.createElement("seminar");
					seminarList.appendChild(seminar);
					
					Element topic = doc.createElement("topic");
					topic.appendChild(doc.createTextNode(s.getTopic()));
					seminar.appendChild(topic);
					
					Element location = doc.createElement("location");
					location.appendChild(doc.createTextNode(s.getLocation()));
					seminar.appendChild(location);
					
					Element seminarDateTime = doc.createElement("seminarDateTime");
					LocalDateTime dateTime = s.getDateTime();
					String dateTimeString = new String("" + dateTime.getYear() + "," + 
							dateTime.getMonthValue() + "," + dateTime.getDayOfMonth() + "," +
							dateTime.getHour() + "," + dateTime.getMinute());
					seminarDateTime.appendChild(doc.createTextNode(dateTimeString));
					seminar.appendChild(seminarDateTime);
				}
				
				
				
			}
			
			//Write content into xml file;
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}
	
	/**
	 * Loads data from xml file.
	 * 
	 * @param file xml file written to previously
	 */
	public void loadData(File file) {
		//Empty all lists except the registered views list.
		publicationList = new ArrayList<Publication>();
		researchGroupList = new ArrayList<ResearchGroup>();
		academicStaffList = new ArrayList<AcademicStaff>();
		researchStudentList = new ArrayList<ResearchStudent>();
		
		try {
			//Build document and read from file.
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			
			//Remove spaces etc.
			doc.getDocumentElement().normalize();
			
			//Retrieve system administrator
			NodeList sysAdminList = doc.getElementsByTagName("systemAdministrator");
			
			Element sysAdmin = (Element) sysAdminList.item(0);
			
			String adminName = sysAdmin.getElementsByTagName("name").item(0).getTextContent();
			String officeNumber = sysAdmin.getElementsByTagName("officeNumber").item(0).getTextContent();
			String phoneNumber = sysAdmin.getElementsByTagName("phoneNumber").item(0).getTextContent();
			String adminEmail = sysAdmin.getElementsByTagName("adminEmail").item(0).getTextContent();
			String adminPassword = sysAdmin.getElementsByTagName("password").item(0).getTextContent();
			
			SystemAdmin sA = new SystemAdmin(adminName, officeNumber, phoneNumber, adminEmail, adminPassword);
			this.setSystemAdmin(sA);
			
			//Get list of research groups.
			NodeList rGList = doc.getElementsByTagName("researchGroup");
			
			for (int i=0; i < rGList.getLength(); i++) {
				Element group = (Element) rGList.item(i);
				
				int groupID = Integer.parseInt(group.getAttribute("groupID"));
				String groupName = group.getElementsByTagName("name").item(0).getTextContent();
				
				ResearchGroup researchGroup = new ResearchGroup(groupName, groupID);
				this.addResearchGroup(researchGroup);
				
				//Group member list.
				NodeList gMList = group.getElementsByTagName("member");
				
				for (int x=0; x < gMList.getLength(); x++) {
					Element member = (Element) gMList.item(x);
					
					String memberName = member.getElementsByTagName("name").item(0).getTextContent();
					String memberTitle = member.getElementsByTagName("title").item(0).getTextContent();
					String memberEmail = member.getElementsByTagName("memberEmail").item(0).getTextContent();
					String memberType = member.getElementsByTagName("memberType").item(0).getTextContent();
					
					if (memberType.equals("STUDENT")){
						String studentID = member.getElementsByTagName("studentID").item(0).getTextContent();
						
						ResearchStudent student = new ResearchStudent(memberName, memberTitle, researchGroup, 
								memberEmail, GroupMember.memberType.STUDENT, studentID);
						
						researchGroup.addMember(student);
						this.addResearchStudent(student);
					} else if (memberType.equals("STAFF")) {
						String password = member.getElementsByTagName("password").item(0).getTextContent();
						String isCoordinator = member.getElementsByTagName("isCoordinator").item(0).getTextContent();
						
						AcademicStaff staff = new AcademicStaff(memberName, memberTitle, researchGroup, memberEmail, 
								GroupMember.memberType.STAFF, password);
						researchGroup.addMember(staff);
						this.addAcademicStaff(staff);
						
						if (isCoordinator.equals("true")) {
							staff.setCoordinator(true);
						}
						
						//Publication list.
						NodeList pubList = member.getElementsByTagName("publication");
						
						for (int y=0; y < pubList.getLength(); y++) {
							Element publication = (Element) pubList.item(y);
							
							String publicationID = publication.getAttribute("publicationID");
							String publicationTitle = publication.getElementsByTagName("title").item(0).getTextContent();
							
							ArrayList<String> authorNames = new ArrayList<String>();
							NodeList nameList = publication.getElementsByTagName("name");
							for (int z=0; z < nameList.getLength(); z++) {
								Element authName = (Element) nameList.item(z);
								authorNames.add(authName.getTextContent());
							}
							
							String aBstract = publication.getElementsByTagName("abstract").item(0).getTextContent();
							int grpID = Integer.parseInt(publication.getElementsByTagName("groupID").item(0).getTextContent());
							String staffEmail = publication.getElementsByTagName("staffEmail").item(0).getTextContent();
							String pubType = publication.getElementsByTagName("type").item(0).getTextContent();
							String pubDate = publication.getElementsByTagName("publicationDate").item(0).getTextContent();
							LocalDate publicationDate = LocalDate.of(Integer.parseInt(pubDate.split(",")[0]), 
									Integer.parseInt(pubDate.split(",")[1]), Integer.parseInt(pubDate.split(",")[2]));
							
							if (pubType.equals("JOURNALPAPER")) {
								String journalName = publication.getElementsByTagName("journalName").item(0).getTextContent();
								int pageNumber = Integer.parseInt(publication.getElementsByTagName("pageNumber").item(0).getTextContent());
								
								JournalPaper jPaper = new JournalPaper(publicationID, publicationTitle, authorNames, aBstract, 
										grpID, staffEmail, Publication.publicationType.JOURNALPAPER, publicationDate, journalName, pageNumber);
								staff.addPublication(jPaper);
								this.addPublication(jPaper);
							} else if (pubType.equals("CONFERENCEPAPER")) {
								String conferenceName = publication.getElementsByTagName("conferenceName").item(0).getTextContent();
								String conferenceLocation = publication.getElementsByTagName("conferenceLocation").item(0).getTextContent();
								String confDate = publication.getElementsByTagName("conferenceDate").item(0).getTextContent();
								LocalDate conferenceDate = LocalDate.of(Integer.parseInt(confDate.split(",")[0]), 
									Integer.parseInt(confDate.split(",")[1]), Integer.parseInt(confDate.split(",")[2]));
								
								ConferencePaper cPaper = new ConferencePaper(publicationID, publicationTitle, authorNames, aBstract, 
										grpID, staffEmail, Publication.publicationType.CONFERENCEPAPER, publicationDate, conferenceName, 
										conferenceLocation, conferenceDate);
								staff.addPublication(cPaper);
								this.addPublication(cPaper);
							} else if (pubType.equals("BOOK")) {
								String iSBN = publication.getElementsByTagName("ISBN").item(0).getTextContent();
								String publisher = publication.getElementsByTagName("publisher").item(0).getTextContent();
								
								Book book = new Book(publicationID, publicationTitle, authorNames, aBstract, 
										grpID, staffEmail, Publication.publicationType.BOOK, publicationDate, iSBN, publisher);
								staff.addPublication(book);
								this.addPublication(book);
							}
						}
					}
				}
				
				String sCEmail = group.getElementsByTagName("seminarCoordinator").item(0).getTextContent();
				for (GroupMember g : researchGroup.getMembers()) {
					if (g.getEmailAddress().equals(sCEmail)) {
						researchGroup.setSeminarCoordinator((AcademicStaff) g);
						break;
					}
				}
				
				//Seminar list.
				NodeList semList = group.getElementsByTagName("seminar");
				
				for (int b=0; b < semList.getLength(); b++) {
					Element seminar = (Element) semList.item(b);
					
					String topic = seminar.getElementsByTagName("topic").item(0).getTextContent();
					String location = seminar.getElementsByTagName("location").item(0).getTextContent();
					String semDateTime = seminar.getElementsByTagName("seminarDateTime").item(0).getTextContent();
					LocalDateTime seminarDateTime = LocalDateTime.of(Integer.parseInt(semDateTime.split(",")[0]), 
							Integer.parseInt(semDateTime.split(",")[1]), Integer.parseInt(semDateTime.split(",")[2]), 
							Integer.parseInt(semDateTime.split(",")[3]), Integer.parseInt(semDateTime.split(",")[4]));
					
					Seminar s = new Seminar(seminarDateTime, location, topic, researchGroup);
					researchGroup.addOldSeminar(s);
				}
			}
		}catch (DPMSystemException sE) {
			System.out.println(sE.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void addView(Viewer view) throws DPMSystemException {
		if (registeredViews.contains(view)) {
			throw new DPMSystemException("View already registered.");
		}
		else {
			registeredViews.add(view);
		}
	}

	@Override
	public void removeView(Viewer view) throws DPMSystemException {
		boolean removed = false;
		for (Viewer v : registeredViews) {
			if (v.equals(view)) {
				registeredViews.remove(view);
				removed = true;
			}
		}
		if (!removed) {
			throw new DPMSystemException("View is not listed in registered views.");
		}
	}

	@Override
	public void notifyViews() {
		//System.out.println("notify method called");
		for (Viewer v : registeredViews) {
			v.update();
		}
	}
	
	public static void main(String[] args) {
		DataStorage ds = new DataStorage();
		/*ResearchGroup group = new ResearchGroup("group", 1);
		AcademicStaff aS = new AcademicStaff("name", "title", group, "emailAddress", memberType.STAFF, "password");
		SystemAdmin sysAd = new SystemAdmin("jim", "121", "01923756473", "help@admin.com", "pass");
		try {
			ds.setSystemAdmin(sysAd);
			ds.addResearchGroup(group);
			ds.addAcademicStaff(aS);
			group.addMember(aS);
		} catch (DPMSystemException e) {
			System.out.println(e.getLocalizedMessage());
		}
		
		File file = new File("test1.xml");
		ds.saveData(file);
		*/
		//ds = new DataStorage();
		
		File file = new File("DPMSystemData.xml");
		ds.loadData(file);
		
		/*
		ResearchGroup group2 = new ResearchGroup("group2", 2);
		AcademicStaff aS2 = new AcademicStaff("name", "title", group2, "emailAddress2", memberType.STAFF, "password2");
		//SystemAdmin sysAd = new SystemAdmin("jim", "121", "01923756473", "help@admin.com", "pass");
		try {
			//ds.setSystemAdmin(sysAd);
			ds.addResearchGroup(group2);
			ds.addAcademicStaff(aS2);
			ds.getResearchGroupList().get(0).addMember(aS2);
		} catch (DPMSystemException e) {
			System.out.println(e.getLocalizedMessage());
		}
		*/
		
		//File file2 = new File("test2.xml");
		
		ArrayList<String> authorNames = new ArrayList<String>();
		authorNames.add("dan");
		
		ConferencePaper cp = new ConferencePaper("cp1", "random paper", authorNames, "random paper", 0, 
				"jgcogs@gmail.com", publicationType.CONFERENCEPAPER, LocalDate.now(), "special conference", 
				"special location", LocalDate.now().minusDays(30));
		try {
			ds.getAcademicStaffList().get(0).addPublication(cp);
			ds.addPublication(cp);
		} catch (DPMSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ds.saveData(file);
	}
}
