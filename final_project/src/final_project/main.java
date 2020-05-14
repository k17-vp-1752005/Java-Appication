package final_project;

import java.sql.*;

public class main {

	public static void main(String[] args) {
		ConnectToSQL connectSQL = new ConnectToSQL();
		Connection con = connectSQL.getConnectToSQL("Admin1", "123");
		
		ClassDB cl = new ClassDB(con);
		cl.showClassList();
		cl.addClass("19VP");
		cl.showClassList();
		cl.deleteAClass("19VP");
		cl.showClassList();
		
		StudentDB st = new StudentDB(con);
		st.addStudent("1801230", "18VP", "ABCD", "2000-09-11", "Nam", "ABCD@gmail.com");
		cl.showClassList();
		st.showStudentList();
		
		TeacherDB tch = new TeacherDB(con);
		tch.addTeacher("100006", "AQWWE", "1970-01-01", "Nam", "AQWWE@gmail.com");
		tch.deleteTeacher("100006");
		tch.showTeacherList();
		tch.search("100001");
		
		CourseDB cs = new CourseDB(con);
		cs.addCourse("CSC006", "100001", "AWQFQ", 3);
		cs.deleteCourse("CSC006");
		cs.showCourseList();
		cs.search("CSC002");
		
		CourseResultDB p = new CourseResultDB(con);
		p.addStudent("CSC004", "1801230");
		p.setOnClass("CSC004", "1801230", 7);
		p.setPractice("CSC004", "1801230", 6);
		p.calTotal("CSC004", "1801230");
		p.showCourseResultList();
	}
}
