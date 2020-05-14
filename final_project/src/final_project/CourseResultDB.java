package final_project;

import java.sql.*;

public class CourseResultDB {
	private Connection m_connection;
	
	CourseResultDB(Connection connection) {
		this.m_connection = connection;
	}
	
	public void showCourseResultList() {
		String sql = "exec spReadParticipant";
		
		try(
			Statement st = this.m_connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(sql);
			) {
			while(rs.next()) {
				System.out.println("--------------");
				System.out.println("Course ID: " + rs.getString(1));
				System.out.println("Student ID: " + rs.getString(2));
				System.out.println("On class: " + rs.getFloat(3));
				System.out.println("Practice: " + rs.getFloat(4));
				System.out.println("Total: " + rs.getFloat(5));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean courseHasNoParticipant(String courseID) {
		String sql = "select CourseID from COURSERESULT where CourseID = ?";
		CourseDB cs = new CourseDB(this.m_connection);
		int count = 0;
		
		try(PreparedStatement ps = this.m_connection.prepareStatement(sql);) {
			if(cs.exists(courseID) == false) 
				throw new MyException("This course ID does not exist in database.");
			
			ps.setString(1, courseID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				count++;
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count == 0)
			return true;
		else
			return false;
	}
	
	public boolean participated(String courseID, String studentID) {
		String sql = "select CourseID, StudentID from COURSERESULT where CourseID = ? and StudentID = ?";
		CourseDB cs = new CourseDB(this.m_connection);
		int count = 0;
		
		try(PreparedStatement ps = this.m_connection.prepareStatement(sql);) {
			if(cs.exists(courseID) == false) 
				throw new MyException("This course ID does not exist in database.");
			
			ps.setString(1, courseID);
			ps.setString(2, studentID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				count++;
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(count == 0)
			return false;
		else
			return true;
	}
	
	public void addStudent(String courseID, String studentID) {
		String sql = "exec spInsertStudentToCourse ?, ?";
		StudentDB st = new StudentDB(this.m_connection);
		CourseDB cs = new CourseDB(this.m_connection);
		
		try(CallableStatement proc = this.m_connection.prepareCall(sql);) {
			if(st.exists(studentID) == false) 
				throw new MyException("This student id does not exist in database.");
			if(cs.exists(courseID) == false) 
				throw new MyException("This course ID does not exist in database.");
			if(this.participated(courseID, studentID)) 
				throw new MyException("This student already participated in the course.");
			
			proc.setString(1, courseID);
			proc.setString(2, studentID);
			proc.execute();
			
			System.out.println("Insert a student into course successfully.");
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setOnClass(String courseID, String studentID, float mark) {
		String sql = "exec spSetOnClass ?, ?, ?";
		StudentDB st = new StudentDB(this.m_connection);
		CourseDB cs = new CourseDB(this.m_connection);
		
		try(CallableStatement proc = this.m_connection.prepareCall(sql);) {
			if(st.exists(studentID) == false) 
				throw new MyException("This student id does not exist in database.");
			if(cs.exists(courseID) == false) 
				throw new MyException("This course ID does not exist in database.");
			
			proc.setString(1, courseID);
			proc.setString(2, studentID);
			proc.setFloat(3, mark);
			proc.execute();
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setPractice(String courseID, String studentID, float mark) {
		String sql = "exec spSetPratice ?, ?, ?";
		StudentDB st = new StudentDB(this.m_connection);
		CourseDB cs = new CourseDB(this.m_connection);
		
		try(CallableStatement proc = this.m_connection.prepareCall(sql);) {
			if(st.exists(studentID) == false) 
				throw new MyException("This student id does not exist in database.");
			if(cs.exists(courseID) == false) 
				throw new MyException("This course ID does not exist in database.");
			
			proc.setString(1, courseID);
			proc.setString(2, studentID);
			proc.setFloat(3, mark);
			proc.execute();
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void calTotal(String courseID, String studentID) {
		String sql = "exec spCalTotal ?, ?";
		StudentDB st = new StudentDB(this.m_connection);
		CourseDB cs = new CourseDB(this.m_connection);
		
		try(CallableStatement proc = this.m_connection.prepareCall(sql);) {
			if(st.exists(studentID) == false) 
				throw new MyException("This student id does not exist in database.");
			if(cs.exists(courseID) == false) 
				throw new MyException("This course ID does not exist in database.");
			
			proc.setString(1, courseID);
			proc.setString(2, studentID);
			proc.execute();
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
