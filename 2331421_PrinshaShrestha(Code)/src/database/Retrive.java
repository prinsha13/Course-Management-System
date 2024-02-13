package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modetype.Course;
import modetype.Module;
import modetype.Student;
import modetype.Teacher;

public class Retrive {
    private static PreparedStatement retrieveCourses;
    private static Database db;

    public Retrive() throws SQLException {
        db = Database.getInstance();
    }

    public static ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        try {
            retrieveCourses = db.getConnection().prepareStatement("SELECT * FROM courses");
            java.sql.ResultSet rs = retrieveCourses.executeQuery();
            while (rs.next()) {
                courses.add(Course.fromSql(rs.getInt("courseID"), rs.getString("courseName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Course course : courses) {
            course.setModules(getModules(course.getId()));
        }
        return courses;
    }

    public static Course getCourses(int courseId) throws SQLException {
        try {
            String sql = "SELECT * FROM courses WHERE courseID = ?";
            PreparedStatement ps = db.getConnection().prepareStatement(sql);
            ps.setInt(1, courseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Course course = Course.fromSql(rs.getInt("courseID"), rs.getString("courseName"));
                course.setModules(getModules(course.getId()));
                return course;
            }
        } catch (SQLException e) {
            throw e;
        }
        return null;
    }

    public static ArrayList<Module> getAllModules() {
        ArrayList<Module> allModules = new ArrayList<Module>();
        try {
            PreparedStatement retrieveModules = db.getConnection()
                    .prepareStatement("SELECT * FROM modules");
            java.sql.ResultSet rs = retrieveModules.executeQuery();
            while (rs.next()) {
                allModules.add(Module.fromSql(rs.getInt("moduleID"), rs.getString("moduleName"),
                        rs.getString("moduleType")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allModules;
    }

    public static ArrayList<Module> getModules(int courseId) {
        ArrayList<Module> modules = new ArrayList<Module>();
        try {
            PreparedStatement retrieveModules = db.getConnection()
                    .prepareStatement("SELECT * FROM modules WHERE courseID = ?");
            retrieveModules.setInt(1, courseId);
            java.sql.ResultSet rs = retrieveModules.executeQuery();
            while (rs.next()) {
                modules.add(Module.fromSql(rs.getInt("moduleID"), rs.getString("moduleName"),
                        rs.getString("moduleType")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modules;
    }

    public static Module getModuleById(int moduleId) {
        ArrayList<Module> allModules = getAllModules();
        for (Module module : allModules) {
            if (module.getId() == moduleId) {
                return module;
            }
        }
        return null;
    }

    public static ArrayList<Teacher> getTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        try {
            PreparedStatement retrieveTeachers = db.getConnection()
                    .prepareStatement(
                            "SELECT teachers.teacherID, teachers.teacherName, teachers.teacherPhone, user.email FROM teachers LEFT JOIN user ON teachers.userID = user.id");
            java.sql.ResultSet rs = retrieveTeachers.executeQuery();
            while (rs.next()) {
                int teacherId = rs.getInt("teacherID");
                ArrayList<Module> modulesTaught = getModulesTaught(teacherId);

                teachers.add(Teacher.fromSql(teacherId, rs.getString("teacherName"),
                        rs.getString("teacherPhone"), rs.getString("email"), modulesTaught));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    private static ArrayList<Module> getModulesTaught(int teacherId) {
        ArrayList<Module> allModules = getAllModules();
        ArrayList<Module> modulesTaught = new ArrayList<Module>();
        try {
            PreparedStatement retrieveModules = db.getConnection()
                    .prepareStatement("SELECT moduleID FROM teachersModules WHERE teacherID = ?");
            retrieveModules.setInt(1, teacherId);
            java.sql.ResultSet rs = retrieveModules.executeQuery();
            while (rs.next()) {
                allModules.forEach(module -> {
                    try {
                        if (module.getId() == rs.getInt("moduleID")) {
                            modulesTaught.add(module);
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return modulesTaught;
    }

    public static ArrayList<Student> getStudents() {
        ArrayList<Student> students = new ArrayList<Student>();
        try {
            PreparedStatement retrieveStudents = db.getConnection()
                    .prepareStatement(
                            "SELECT students.studentID, students.studentName, students.stdPhone, students.level, students.courseID, user.email FROM students LEFT JOIN user ON students.userID = user.id");
            java.sql.ResultSet rs = retrieveStudents.executeQuery();
            while (rs.next()) {
                int enrolledCourseId = rs.getInt("courseID");
                ArrayList<Course> allCourses = Retrive.getCourses();
                Course enrolledCourse = null;
                for (Course course : allCourses) {
                    if (course.getId() == enrolledCourseId) {
                        enrolledCourse = course;
                    }
                }
                students.add(Student.fromSql(rs.getInt("studentID"), rs.getString("studentName"),
                        rs.getString("stdPhone"), rs.getString("email"), rs.getInt("level"), enrolledCourse));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    public static Student getStudents(int studentId) {
        try {
            PreparedStatement retrieveStudents = db.getConnection()
                    .prepareStatement(
                            "SELECT students.studentID, students.studentName, students.stdPhone, students.level, students.courseID, user.email FROM students LEFT JOIN user ON students.userID = user.id WHERE studentID = ?");
            retrieveStudents.setInt(1, studentId);
            java.sql.ResultSet rs = retrieveStudents.executeQuery();
            if (rs.next()) {
                int enrolledCourseId = rs.getInt("courseID");
                ArrayList<Course> allCourses = Retrive.getCourses();
                Course enrolledCourse = null;
                for (Course course : allCourses) {
                    if (course.getId() == enrolledCourseId) {
                        enrolledCourse = course;
                    }
                }

                return Student.fromSql(rs.getInt("studentID"), rs.getString("studentName"),
                        rs.getString("stdPhone"), rs.getString("email"), rs.getInt("level"), enrolledCourse);
            }
        } catch (

        SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}