package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modetype.Student;

public class DatabaseSystem {
    private static Database db;

    public DatabaseSystem() throws SQLException {
        db = Database.getInstance();
    }
    public static void insert(String table, String[] columns, String[] values) throws SQLException {
        String sql = "INSERT INTO " + table + " (";
        for (int i = 0; i < columns.length; i++) {
            sql += columns[i];
            if (i < columns.length - 1) {
                sql += ", ";
            }
        }
        sql += ") VALUES (";
        for (int i = 0; i < values.length; i++) {
            sql += "?";
            if (i < values.length - 1) {
                sql += ", ";
            }
        }
        sql += ")";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        for (int i = 0; i < values.length; i++) {
            ps.setString(i + 1, values[i]);
        }
        ps.executeUpdate();
    }

    public static void deleteCourse(String id) throws SQLException {
        String sql = "DELETE FROM courses WHERE courseID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();
    }

    public static void editCourse(String id, String name) throws SQLException {
        String sql = "UPDATE courses SET courseName = ? WHERE courseID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, Integer.parseInt(id));
        ps.executeUpdate();
    }

    public static void addTeacher(String name, String phone, String email, String password) throws SQLException {
        String insertUser = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, 'Teacher')";
        PreparedStatement stmtUser;

        stmtUser = db.getConnection().prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
        stmtUser.setString(1, name);
        stmtUser.setString(2, email);
        stmtUser.setString(3, password);
        stmtUser.executeUpdate();

        ResultSet generatedKeys = stmtUser.getGeneratedKeys();
        int userID = -1;
        if (generatedKeys.next()) {
        	userID = generatedKeys.getInt(1);
        }

        String insertTeacher = "INSERT INTO teachers (teacherName, teacherPhone, userID) VALUES (?, ?, ?)";
        PreparedStatement stmtTeacher = db.getConnection().prepareStatement(insertTeacher);
        stmtTeacher.setString(1, name);
        stmtTeacher.setString(2, phone);
        stmtTeacher.setInt(3, userID);
        stmtTeacher.executeUpdate();
    }

    public static void editTeacher(String id, String name, String phone, String email, String password)
            throws SQLException {
        try {
            int teacherID = Integer.parseInt(id);
            StringBuilder updateTeacherQuery = new StringBuilder("UPDATE teachers SET ");
            ArrayList<String> updates = new ArrayList<>();
            ArrayList<String> updateParams = new ArrayList<>();

            if (name != null && !name.equals("")) {
                updates.add("teacherName = ?");
                updateParams.add(name);
            }

            if (phone != null && !phone.equals("")) {
                updates.add("teacherPhone = ?");
                updateParams.add(phone);
            }

            if (updates.size() > 0) {
                updateTeacherQuery.append(String.join(", ", updates));
                updateTeacherQuery.append(" WHERE teacherID = ?");
                PreparedStatement updateTeacher = db.getConnection().prepareStatement(updateTeacherQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                    updateTeacher.setString(i + 1, updateParams.get(i));
                }
                updateTeacher.setInt(updateParams.size() + 1, teacherID);
                updateTeacher.executeUpdate();
            }
            StringBuilder updateUserQuery = new StringBuilder("UPDATE user SET ");
            updates.clear();
            updateParams.clear();

            if (email != null && !email.equals("")) {
                updates.add("email = ?");
                updateParams.add(email);
            }

            if (password != null && !password.equals("")) {
                updates.add("password = ?");
                updateParams.add(password);
            }

            if (updates.size() > 0) {
            	updateUserQuery.append(String.join(", ", updates));
            	updateUserQuery.append(" WHERE id = (SELECT userID FROM teachers WHERE teacherID = ?)");
                PreparedStatement updateUser = db.getConnection().prepareStatement(updateUserQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                	updateUser.setString(i + 1, updateParams.get(i));
                }
                updateUser.setInt(updateParams.size() + 1, teacherID);
                updateUser.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void assignModules(String teacherID, String moduleID) throws SQLException {
        String sql = "INSERT INTO teachersModules (teacherID, moduleID) VALUES (?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(teacherID));
        ps.setInt(2, Integer.parseInt(moduleID));
        ps.executeUpdate();
    }

    public static void unassignModules(String teacherID, String moduleID) throws SQLException {
        String sql = "DELETE FROM teachersModules WHERE teacherID = ? AND moduleID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(teacherID));
        ps.setInt(2, Integer.parseInt(moduleID));
        ps.executeUpdate();
    }

    public static void deleteTeacher(String id) throws SQLException {

        String sql = "SELECT userID FROM teachers WHERE teacherID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int userID = rs.getInt("userID");

        sql = "DELETE FROM teachers WHERE teacherID = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();

        sql = "DELETE FROM user WHERE id = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, userID);
        ps.executeUpdate();
    }

    public static void enrollStudent(int studentID, int courseID, String phone, Student student)
            throws SQLException {
        String sql = "INSERT INTO students (studentID, studentName, stdPhone, courseID, userID, level) VALUES (?, ?, ?, ?, ?, 4)";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, studentID);
        ps.setString(2, student.getName());
        ps.setString(3, phone);
        student.setPhone(phone);
        ps.setInt(4, courseID);
        ps.setInt(5, studentID);
        ps.executeUpdate();

        sql = "INSERT INTO enrollments (studentID, courseID) VALUES (?, ?)";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, studentID);
        ps.setInt(2, courseID);
        ps.executeUpdate();
    }

    public static void addStudent(String name, String phone, String email, String password, int courseId,
            int level)
            throws SQLException {
        String insertUser = "INSERT INTO user (name, email, password, role) VALUES (?, ?, ?, 'Student')";
        PreparedStatement stmtUser;

        stmtUser = db.getConnection().prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
        stmtUser.setString(1, name);
        stmtUser.setString(2, email);
        stmtUser.setString(3, password);
        stmtUser.executeUpdate();

        ResultSet generatedKeys = stmtUser.getGeneratedKeys();
        int userId = -1;
        if (generatedKeys.next()) {
            userId = generatedKeys.getInt(1);
        }

        String insertStudent = "INSERT INTO students (studentName, stdPhone, courseID, level, userID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmtStudent = db.getConnection().prepareStatement(insertStudent);
        stmtStudent.setString(1, name);
        stmtStudent.setString(2, phone);
        stmtStudent.setInt(3, courseId);
        stmtStudent.setInt(4, level);
        stmtStudent.setInt(5, userId);
        stmtStudent.executeUpdate();
    }

    public static void editStudent(String id, String name, String phone, String email, String password)
            throws SQLException {
        try {
            // Update information in the students table
            StringBuilder updateStudentQuery = new StringBuilder("UPDATE students SET ");
            ArrayList<String> updates = new ArrayList<>();
            ArrayList<String> updateParams = new ArrayList<>();

            if (name != null && !name.equals("")) {
                updates.add("studentName = ?");
                updateParams.add(name);
            }

            if (phone != null && !phone.equals("")) {
                updates.add("stdPhone = ?");
                updateParams.add(phone);
            }

            if (updates.size() > 0) {
                updateStudentQuery.append(String.join(", ", updates));
                updateStudentQuery.append(" WHERE studentID = ?");
                PreparedStatement updateStudent = db.getConnection().prepareStatement(updateStudentQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                    updateStudent.setString(i + 1, updateParams.get(i));
                }
                updateStudent.setInt(updateParams.size() + 1, Integer.parseInt(id));
                updateStudent.executeUpdate();
            }
            StringBuilder updateUserQuery = new StringBuilder("UPDATE user SET ");
            updates.clear();
            updateParams.clear();

            if (email != null && !email.equals("")) {
                updates.add("email = ?");
                updateParams.add(email);
            }

            if (password != null && !password.equals("")) {
                updates.add("password = ?");
                updateParams.add(password);
            }

            if (updates.size() > 0) {
            	updateUserQuery.append(String.join(", ", updates));
            	updateUserQuery.append(" WHERE id = (SELECT userID FROM students WHERE studentID= ?)");
                PreparedStatement updateUser = db.getConnection().prepareStatement(updateUserQuery.toString());

                for (int i = 0; i < updateParams.size(); i++) {
                	updateUser.setString(i + 1, updateParams.get(i));
                }
                updateUser.setInt(updateParams.size() + 1, Integer.parseInt(id));
                updateUser.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void deleteStudent(String id) throws SQLException {
        String sql = "SELECT userID FROM students WHERE studentID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int userID = rs.getInt("userID");

        sql = "DELETE FROM students WHERE studentID = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(id));
        ps.executeUpdate();

        sql = "DELETE FROM user WHERE id = ?";
        ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, userID);
        ps.executeUpdate();
    }

    public static void gradeStudent(String studentID, String moduleID, String grade) throws SQLException {
        String sql = "INSERT INTO results (studentID, moduleID, grade) VALUES (?, ?, ?)";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, Integer.parseInt(studentID));
        ps.setInt(2, Integer.parseInt(moduleID));
        ps.setInt(3, Integer.parseInt(grade));
        ps.executeUpdate();
    }

    public static int retrieveGrade(int studentID, String moduleID) throws SQLException {
        String sql = "SELECT grade FROM results WHERE studentID = ? AND moduleID = ?";
        PreparedStatement ps = db.getConnection().prepareStatement(sql);
        ps.setInt(1, studentID);
        ps.setInt(2, Integer.parseInt(moduleID));
        ResultSet rs = ps.executeQuery();
        rs.next();
        int grade = rs.getInt("grade");
        return grade;
    }
}