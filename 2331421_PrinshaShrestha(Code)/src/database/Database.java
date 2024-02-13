package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
	public static Database instance;
	private Connection conn;

	private Database(String path, String username, String password) throws SQLException {
		this.conn = DriverManager.getConnection(path, username, password);
		System.out.println("Connected to database");
		createDatabase("cms");
		String query = "USE cms";
		executeUpdate(query);
		createAndPopulateTables();
	}

	private void createDatabase(String dbName) throws SQLException {
		try {
			executeUpdate("CREATE DATABASE " + dbName);
			System.out.println("Database created!");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1007)
				System.out.println("Database exists!");
			else
				throw e;
		}
	}

	private void createAndPopulateTables() throws SQLException {
		try {
			String createAuthTableSQL = """
					CREATE TABLE user (
						id INT AUTO_INCREMENT PRIMARY KEY,
						name VARCHAR(50),
						email VARCHAR(50),
						password VARCHAR(30),
						role VARCHAR(20))
					""";
			executeUpdate(createAuthTableSQL);
			String coursesTableSQL = """
					CREATE TABLE courses (
						courseID INT PRIMARY KEY AUTO_INCREMENT,
						courseName VARCHAR(50) NOT NULL)
					""";
			executeUpdate(coursesTableSQL);
			String insertIntoCoursesSQL = """
					INSERT INTO courses(courseName) VALUES
					  ('Business and Management'),
					  ('Bsc (Hons) Computing'),
					  ('Cyber Security and Digital Forensics'),
					  ('Bsc (Hons) Data Science')
					""";
			executeUpdate(insertIntoCoursesSQL);
			String modulesTableSQL = """
					CREATE TABLE modules (
						moduleID INT PRIMARY KEY AUTO_INCREMENT,
						moduleName VARCHAR(100) NOT NULL,
						moduleType VARCHAR(100) NOT NULL,
						courseID INT NOT NULL,
						FOREIGN KEY (courseID) REFERENCES courses(courseID))
					""";
			executeUpdate(modulesTableSQL);
			String insertIntoModulesSQL = """
					INSERT INTO modules (moduleName,moduleType, courseID) VALUES
						('Academic English and Effective Communications','core', 1),
						('Business Statistics and Analytics','core', 1),
						('Business Decision Making for Management','core', 1),
						('Critical Business Enquiry Project','optional', 1),
						('International Business Management Simulation','optional', 1),
						('Academic English & Effective Communication','core', 2),
						('Project Based Learning','core', 2),
						('Digital Security Landscapes','optional', 2),
						('Web Application and Technology','optional', 2),
						('Computer and Network Systems','core', 3),
						('Law, Experts and Justice','core', 3),
						('Forensic Computing Practice','core', 3),
						('Digital System Project','optional', 3),
						('Information System Dissertation','optional', 3),
						('Introduction to Statistics and Software','core', 4),
						('Communication, Confidence and Competence','core', 4),
						('Professional Mathematics and Data Science','core', 4),
						('Advanced Databases and Applications','optional', 4)
					""";
			executeUpdate(insertIntoModulesSQL);
			String studentsTableSQL = """
					CREATE TABLE students (
						studentID INT PRIMARY KEY AUTO_INCREMENT,
						studentName VARCHAR(100) NOT NULL,
						courseID INT NOT NULL,
						stdPhone VARCHAR(100) NOT NULL,
						level INT NOT NULL,
						userID INT,
						FOREIGN KEY (courseID) REFERENCES courses(courseID),
						FOREIGN KEY (userID) REFERENCES user(id))
					""";
			executeUpdate(studentsTableSQL);
			String teachersTableSQL = """
					CREATE TABLE teachers (
						teacherID INT PRIMARY KEY AUTO_INCREMENT,
						teacherName VARCHAR(100) NOT NULL,
						teacherPhone VARCHAR(100) NOT NULL,
						userID INT,
						FOREIGN KEY (userID) REFERENCES user(id))
					""";
			executeUpdate(teachersTableSQL);
			String enrollmentTableSQL = """
					CREATE TABLE enrollments (
					    studentID INT NOT NULL,
					    courseID INT NOT NULL,
					    FOREIGN KEY (studentID) REFERENCES students(studentID),
					    FOREIGN KEY (courseID) REFERENCES courses(courseID),
					    PRIMARY KEY (studentID, courseID))
					""";
			executeUpdate(enrollmentTableSQL);
			String teacherModulesTableSQL = """
					CREATE TABLE teachersModules (
						teacherID INT NOT NULL,
						moduleID INT NOT NULL,
						FOREIGN KEY (teacherID) REFERENCES teachers(teacherID),
						FOREIGN KEY (moduleID) REFERENCES modules(moduleID),
						PRIMARY KEY (teacherID, moduleID))
					""";
			executeUpdate(teacherModulesTableSQL);
			String resultTableSQL = """
					CREATE TABLE results (
					    studentID INT NOT NULL,
					    moduleID INT NOT NULL,
					    grades INT NOT NULL,
					    FOREIGN KEY (studentID) REFERENCES students(studentID),
					    FOREIGN KEY (moduleID) REFERENCES modules(moduleID),
					    PRIMARY KEY (studentID, moduleID))
					""";
			executeUpdate(resultTableSQL);
		} catch (SQLException e) {
			if (e.getErrorCode() == 1050)
				System.out.println("Table exists!");
			else
				throw e;
		}
	}

	public static Database getInstance() throws SQLException {
		if (instance == null)
			instance = new Database("jdbc:mysql://localhost:3306", "root", "");
		return instance;
	}

	public Connection getConnection() {
		return conn;
	}

	public void executeUpdate(String sql) throws SQLException {
		try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
			preparedStatement.executeUpdate();
		}
	}

	public PreparedStatement getPreparedStatement(String sql) throws SQLException {
		return conn.prepareStatement(sql);
	}
}