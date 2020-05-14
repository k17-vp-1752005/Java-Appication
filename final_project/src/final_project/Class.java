package final_project;

public class Class {
	private String m_classID;
	private int m_numberOfPeople;
	
	Class() {
		this.m_classID = null;
		this.m_numberOfPeople = 0;
	}
	
	Class(String classID, int NumberOfPeople) {
		this.m_classID = classID;
		this.m_numberOfPeople = NumberOfPeople;
	}
	
	Class(Class cl) {
		this.m_classID = cl.m_classID;
		this.m_numberOfPeople = cl.m_numberOfPeople;
	}
	
	String getID() {
		return this.m_classID;
	}
	
	int getNumberOfPeople() {
		return this.m_numberOfPeople;
	}
	
	void ShowInfo() {
		System.out.println("Class ID: " + this.m_classID);
		System.out.println("Number of people: " + this.m_numberOfPeople);
	}
}
