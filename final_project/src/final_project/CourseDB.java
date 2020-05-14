package final_project;

import java.sql.*;

public class CourseDB {
	private Connection m_connection;
	
	CourseDB(Connection  connection) {
		this.m_connection = connection;
	}
	
	public void showCourseList() {
		String sql = "exec spReadCourse";
		
		try(
			Statement st = this.m_connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(sql);
			) {
			while(rs.next()) {
				System.out.println("--------------");
				System.out.println("Course ID: " + rs.getString(1));
				System.out.println("Teacher ID: " + rs.getString(2));
				System.out.println("Course name: " + rs.getString(3));
				System.out.println("Credit: " + rs.getInt(4));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean exists(String courseID) {
		String sql = "select courseID from COURSE where courseID = ?";
		int count = 0;
		
		try(PreparedStatement ps = this.m_connection.prepareStatement(sql);) {
			ps.setString(1, courseID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				count++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// count = 0 means there is no such course id in database
		if(count == 0)
			return false;
		else
			return true;
	}
	
	public void addCourse(String courseID, String teacherID, String courseName, int credit) {
		String sql = "exec spInsertCourse ?, ?, ?, ?";
		TeacherDB tch = new TeacherDB(m_connection); 
		
		try(CallableStatement proc = this.m_connection.prepareCall(sql);) {
			// verify if this course id already exists
			if(this.exists(courseID)) 
				throw new MyException("This course ID already exists in database.");
			// verify if this teacher id  does not exist
			if(tch.exists(teacherID) == false) 
				throw new MyException("This teacher ID does not exist in database.");
			
			proc.setString(1, courseID);
			proc.setString(2, teacherID);
			proc.setString(3, courseName);
			proc.setInt(4, credit);
			proc.execute();
			
			System.out.println("Insert a new course successfully.");
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteCourse(String courseID) {
		String sql = "exec spDeleteCourse ?";
		
		try(Statement st = this.m_connection.createStatement();
			CallableStatement proc = this.m_connection.prepareCall(sql);) {
			// verify if this course id does not exist 
			if(this.exists(courseID) == false) 
				throw new MyException("This course ID does not exist in database.");
			
			proc.setString(1, courseID);
			proc.execute();
			System.out.println("Delete course successfully.");
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void search(String courseID) {
		String sql = "select * from COURSE where courseID = ?";
		
		try(
			PreparedStatement ps = this.m_connection.prepareStatement(sql);
			) {
			ps.setString(1, courseID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println("--------------");
				System.out.println("Course ID: " + rs.getString(1));
				System.out.println("Teacher ID: " + rs.getString(2));
				System.out.println("Course name: " + rs.getString(3));
				System.out.println("Credit: " + rs.getInt(4));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
