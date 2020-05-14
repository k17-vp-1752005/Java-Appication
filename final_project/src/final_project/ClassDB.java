package final_project;

import java.sql.*;
import java.util.ArrayList;

public class ClassDB {
	private Connection m_connection;
	private ArrayList<Class> m_classList;
	
	ClassDB(Connection connection) {
		this.m_connection = connection;
		this.m_classList = new ArrayList<Class>();
		
		String sql = "exec spReadClass";
		
		try(Statement st = this.m_connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(sql);) {
			while(rs.next()) {
				Class cl = new Class(rs.getString(1), rs.getInt(2));
				this.m_classList.add(cl);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// verify if this class exists in database
	public boolean exists(String ClassID) {
		String sql = "select CLassID from CLASS where ClassID = ?";
		int count = 0;
		try(PreparedStatement ps = this.m_connection.prepareStatement(sql);) {
			ps.setString(1, ClassID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
				count++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// count = 0 means there is no such class id in database
		if(count == 0)
			return false;
		else
			return true;
	}
	
	public void addClass(String ClassID) {
		// sql command
		String sql = "exec spInsertClass ?";
		
		// insert a new class to database
		try(Statement st = this.m_connection.createStatement();
			CallableStatement proc = this.m_connection.prepareCall(sql);) {
			// verify if class already exists
			if(this.exists(ClassID)) 
				throw new MyException("This class already exists in database.");
			
			proc.setString(1, ClassID);
			proc.execute();
			System.out.println("Insert a new class successfully.");
			
			// add new class into list
			Class cl = new Class(ClassID, 0);
			this.m_classList.add(cl);
		} catch (SQLException | MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Class Search(String ClassID) throws MyException {
		for(int i = 0; i < this.m_classList.size(); i++) {
			if(this.m_classList.get(i).getID().equals(ClassID))
				return this.m_classList.get(i);
		}
		throw new MyException("This class does not exist in list.");
	}
	
	public void deleteAClass(String ClassID) {
		String sql = "exec spDeleteClass ?";
		
		// delete class
		try(Statement st = this.m_connection.createStatement();
			CallableStatement proc = this.m_connection.prepareCall(sql);) {
			// verify if class already exists
			if(this.exists(ClassID) == false) 
				throw new MyException("This class does not exist in database.");
			
			proc.setString(1, ClassID);
			proc.execute();
			System.out.println("Delete class successfully.");
			
			// remove the class out of list
			this.m_classList.remove(this.Search(ClassID));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void showClassList() {
		// sql command
		String sql = "exec spReadClass";
		try(
			Statement st = this.m_connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery(sql);
			) {
			while(rs.next()) {
				System.out.println("Class ID: " + rs.getString(1));
				System.out.println("Number of people: " + rs.getInt(2));
				System.out.println("--------------");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
