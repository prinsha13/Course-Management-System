package management;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modetype.Student;
import modetype.User;
import modetype.Teacher;
import modetype.Administrator;
import database.Retrive;

public class Users {
	private static PreparedStatement checkEmailStmt;
	private static PreparedStatement retrieveRoleStmt;
	private static PreparedStatement checkEmailExistenceStmt;
	private static PreparedStatement addCredentialStmt;

	public Users(database.Database db) throws SQLException {
		checkEmailStmt = db.getConnection().prepareStatement("SELECT count(*) FROM user WHERE email=?");
		retrieveRoleStmt = db.getConnection()
				.prepareStatement("SELECT id, name,role FROM user WHERE email=? AND BINARY password=?");
		checkEmailExistenceStmt = db.getConnection().prepareStatement("SELECT email FROM user WHERE email=?");
		addCredentialStmt = db.getConnection()
				.prepareStatement("INSERT INTO user (name, email, password, role) VALUES (?,?,?, 'Teacher')");
	}

	public static User returnUser(String email, String password) throws Exception {
		try {
			checkEmailStmt.setString(1, email);
			ResultSet rs = checkEmailStmt.executeQuery();
			int count = 0;
			if (rs.next())
				count = rs.getInt(1);
			if (count == 0)
				throw new InvalidEmail("User with this email not found!");
			else {
				retrieveRoleStmt.setString(1, email);
				retrieveRoleStmt.setString(2, password);
				rs = retrieveRoleStmt.executeQuery();
				if (rs.next()) {
					switch (rs.getString("role")) {
						case "Student":
							Student student = Retrive.getStudents(rs.getInt("id"));
							if (student == null)
								return new Student(rs.getInt("id"), rs.getString("name"));
							return student;
						case "Teacher":
							return new Teacher(rs.getString("name"));
						case "Administrator":
							return new Administrator(rs.getString("name"));
						default:
							throw new Exception("An error occurred while retrieving the role!");
					}
				} else
					throw new InvalidPassword("Invalid Password!");
			}
		} catch (SQLException e) {
			throw new Exception("Error occurred while checking the email and password!");
		}
	}

	public static void addUsers(String name, String email, String password) throws Exception {
		try {
			checkEmailExistenceStmt.setString(1, email);
			ResultSet rs = checkEmailExistenceStmt.executeQuery();
			if (rs.next()) {
				throw new InvalidEmail("Email already in use!");
			} else {
				addCredentialStmt.setString(1, name);
				addCredentialStmt.setString(2, email);
				addCredentialStmt.setString(3, password);
				addCredentialStmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}