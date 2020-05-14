package final_project;

import java.sql.*;

public class TeacherDB {
	private Connection m_connection;
	
	TeacherDB(Connection connection) {
		this.m_connection = connection;
	}
	
	public boolean exists(String teacherID) {
		String sql = "select TeacherID from TEACHER where TeacherID = ?";
		int count = 0;
		
		try(PreparedStatement ps = this.m_connection.prepareStatement(sql);) {
			ps.setString(1, teacherID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				count++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// count = 0 means there is no such teacher id in database
		if(count == 0)
			return false;
		else
			return true;
	}
	
	public void addTeacher(String teacherID, String fullName, String dateOfBirth, String sex, String email) {
		String sql = "exec spInsertTeacher ?, ?, ?, ?, ?";
		
		try(CallableStatement proc = this.m_connection.prepareCall(sql);) {
			// verify if this teacher already exists 
			if(this.exists(teacherID)) 
				throw new MyException("This teacher already exists in database.");
			
			proc.setString(1, teacherID);
			proc.setString(2, fullName);
			proc.setDate(3, Date.valueOf(dateOfBirth));
			proc.setString(4, sex);
			proc.setString(5, email);
			proc.execute();
			
			System.out.println("Insert a new teacher successfully.");
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteTeacher(String teacherID) {
		String sql = "exec spDeleteTeacher ?"; 
		
		try(Statement st = this.m_connection.createStatement();
			CallableStatement proc = this.m_connection.prepareCall(sql);) {
			if(this.exists(teacherID) == false) 
				throw new MyException("This teacher does not exist in database.");
			
			proc.setString(1, teacherID);
			proc.execute();
			System.out.println("Delete teacher successfully.");
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showTeacherList() {
		String sql = "exec spReadTeacher";
		
		try(
			Statement st = this.m_connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(sql);
			) {
			while(rs.next()) {
				System.out.println("--------------");
				System.out.println("Teacher ID: " + rs.getString(1));	
				System.out.println("Full name: " + rs.getString(2));
				System.out.println("Date of birth: " + rs.getDate(3));
				System.out.println("Sex: " + rs.getString(4));
				System.out.println("Email: " + rs.getString(5));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void search(String teacherID) {
		String sql = "select * from TEACHER where TeacherID = ?";
		
		try(
			PreparedStatement ps = this.m_connection.prepareStatement(sql);
			) {
			ps.setString(1, teacherID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("--------------");
				System.out.println("Teacher ID: " + rs.getString(1));
				System.out.println("Full name: " + rs.getString(2));
				System.out.println("Date of birth: " + rs.getDate(3));
				System.out.println("Sex: " + rs.getString(4));
				System.out.println("Email: " + rs.getString(5));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
