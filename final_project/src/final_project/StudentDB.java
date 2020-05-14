package final_project;

import java.sql.*;

public class StudentDB {
	private Connection m_connection;
	
	StudentDB(Connection connection) {
		this.m_connection = connection;
	}
	
	public boolean exists(String studentID) {
		String sql = "select StudentID from STUDENT where StudentID = ?";
		int count = 0;
		
		try(PreparedStatement ps = this.m_connection.prepareStatement(sql);) {
			ps.setString(1, studentID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				count++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// count = 0 means there is no such student id in database
		if(count == 0)
			return false;
		else
			return true;
	}
	
	public void addStudent(String studentID, String classID, String fullName, String DateOfBirth, 
						   String sex, String email) {
		String sql = "exec spInsertStudent ?, ?, ?, ?, ?, ?";
		ClassDB cl = new ClassDB(this.m_connection);
		
		// insert a new student into database
		try(Statement st = this.m_connection.createStatement();
			CallableStatement proc = this.m_connection.prepareCall(sql);) {
			// verify if this student already exists 
			if(this.exists(studentID)) 
				throw new MyException("This student already exists in database.");
			// verify if this class does not exist
			if(cl.exists(classID) == false) 
				throw new MyException("This class does not exist in database.");
			
			proc.setString(1, studentID);
			proc.setString(2, classID);
			proc.setString(3, fullName);
			proc.setDate(4, Date.valueOf(DateOfBirth));
			proc.setString(5, sex);
			proc.setString(6, email);
			proc.execute();
			
			System.out.println("Insert a new student successfully.");
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteStudent(String studentID) {
		String sql = "exec spDeleteStudent ?"; 
		
		try(Statement st = this.m_connection.createStatement();
			CallableStatement proc = this.m_connection.prepareCall(sql);) {
			if(this.exists(studentID) == false) 
				throw new MyException("This student does not exist in database.");
			
			proc.setString(1, studentID);
			proc.execute();
			System.out.println("Delete student successfully.");
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showStudentList() {
		String sql = "exec spReadStudent";
		
		try(
			Statement st = this.m_connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(sql);
			) {
			while(rs.next()) {
				System.out.println("Student ID: " + rs.getString(1));
				System.out.println("Class ID: " + rs.getString(2));
				System.out.println("Full name: " + rs.getString(3));
				System.out.println("Date of birth: " + rs.getDate(4));
				System.out.println("Sex: " + rs.getString(5));
				System.out.println("Email: " + rs.getString(6));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void search(String studentID) {
		String sql = "select * from STUDENT where StudentID = ?";
		
		try(
			PreparedStatement ps = this.m_connection.prepareStatement(sql);
			) {
			ps.setString(1, studentID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("Student ID: " + rs.getString(1));
				System.out.println("Class ID: " + rs.getString(2));
				System.out.println("Full name: " + rs.getString(3));
				System.out.println("Date of birth: " + rs.getDate(4));
				System.out.println("Sex: " + rs.getString(5));
				System.out.println("Email: " + rs.getString(6));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
