package management;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modetype.Course;
import modetype.User;
import modetype.Administrator;
import database.DatabaseSystem;
import database.Retrive;

public class Courses extends JPanel {

	private static final long serialVersionUID = -4551964813659714257L;
	private static Courses instance;
	JPanel coursesGrid;

	private Courses(JPanel main, User user) {
		setBackground(new Color(230, 230, 250));
		main.add(this);
		this.setBounds(113, 0, 1341, 701);
		setLayout(null);

		JPanel courses = new JPanel();
		courses.setBackground(new Color(211, 211, 211));
		courses.setLayout(null);
		courses.setBounds(113, 0, 1222, 701);
		add(courses);

		String e = user instanceof Administrator ? "Manage" : "View";
		JLabel title = new JLabel(e + " Courses");
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 25));
		title.setBounds(5, 75, 236, 36);
		courses.add(title);

		JLabel addBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/add.png")));
		addBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 2));
				JLabel nameLabel = new JLabel("Enter course name: ");
				JTextField nameField = new JTextField();
				JLabel codeLabel = new JLabel("Enter course code: ");
				JTextField codeField = new JTextField();
				panel.add(codeLabel);
				panel.add(codeField);
				panel.add(nameLabel);
				panel.add(nameField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Add Course", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String courseName = nameField.getText();
					String courseId = codeField.getText();
					String[] column = { "courseID", "courseName" };
					String[] values = { courseId, courseName };
					try {
						DatabaseSystem.insert("courses", column, values);
						JOptionPane.showMessageDialog(null, "Course added successfully");
						courses.remove(coursesGrid);
						createCoursesGrid(courses);
						courses.revalidate();
						courses.repaint();

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error adding course", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		addBtnLbl.setBounds(854, 47, 45, 45);

		JLabel editBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/edit.png")));
		editBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 2));
				JLabel nameLabel = new JLabel("Enter course name: ");
				JTextField nameField = new JTextField();
				JLabel codeLabel = new JLabel("Enter course code: ");
				JTextField codeField = new JTextField();
				panel.add(codeLabel);
				panel.add(codeField);
				panel.add(nameLabel);
				panel.add(nameField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Edit Course", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String courseName = nameField.getText();
					String courseId = codeField.getText();
					try {
						DatabaseSystem.editCourse(courseId, courseName);
						JOptionPane.showMessageDialog(null, "Course updated successfully");
						courses.remove(coursesGrid);
						createCoursesGrid(courses);
						courses.revalidate();
						courses.repaint();

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Error updating course", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		editBtnLbl.setBounds(946, 43, 45, 45);

		JLabel removeBtnLbl = new JLabel(new ImageIcon(getClass().getResource("/remove.png")));
		removeBtnLbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 2));
				JLabel idLabel = new JLabel("Enter course id: ");
				JTextField idField = new JTextField();

				panel.add(idLabel);
				panel.add(idField);

				int result = JOptionPane.showConfirmDialog(null, panel, "Delete Course", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String courseId = idField.getText();

					try {
						DatabaseSystem.deleteCourse(courseId);
						JOptionPane.showMessageDialog(null, "Course deleted successfully");
						courses.remove(coursesGrid);
						createCoursesGrid(courses);
						courses.revalidate();
						courses.repaint();

					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Error deleting course", e1.getMessage(),
								JOptionPane.ERROR_MESSAGE);
					}
				}

			}
		});
		removeBtnLbl.setBounds(1036, 47, 45, 45);

		JLabel addLbl = new JLabel("Add");
		addLbl.setHorizontalAlignment(SwingConstants.CENTER);
		addLbl.setBounds(846, 103, 61, 16);

		JLabel editLbl = new JLabel("Edit");
		editLbl.setHorizontalAlignment(SwingConstants.CENTER);
		editLbl.setBounds(931, 104, 61, 16);

		JLabel removeLbl = new JLabel("Remove");
		removeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		removeLbl.setBounds(1029, 103, 61, 16);

		if (user instanceof Administrator) {
			courses.add(addLbl);
			courses.add(editLbl);
			courses.add(removeLbl);
			courses.add(addBtnLbl);
			courses.add(editBtnLbl);
			courses.add(removeBtnLbl);
		}

		createCoursesGrid(courses);

	}

	private void createCoursesGrid(JPanel courses) {
		ArrayList<Course> allCourses = Retrive.getCourses();

		coursesGrid = new JPanel();
		coursesGrid.setBackground(new Color(230, 230, 250));
		coursesGrid.setBounds(4, 207, 1046, 219);
		courses.add(coursesGrid);

		coursesGrid.setLayout(new GridLayout(0, 2, 0, 0));
		for (int i = 0; i < allCourses.size(); i++) {
			JPanel coursesGridBox = new JPanel();
			coursesGridBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			coursesGrid.add(coursesGridBox);
			coursesGridBox.setLayout(new BorderLayout(0, 0));

			JLabel coursesIdLbl = new JLabel(
					"<html><br><center><b>Course ID</b><br>" + allCourses.get(i).getId() + "</center></html>");
			coursesIdLbl.setHorizontalAlignment(SwingConstants.CENTER);
			coursesGridBox.add(coursesIdLbl, BorderLayout.NORTH);

			JLabel coursesNameLbl = new JLabel(
					"<html><center><b>Course Name</b><br>" + allCourses.get(i).getName() + "</center></html>");
			coursesNameLbl.setHorizontalAlignment(SwingConstants.CENTER);
			coursesGridBox.add(coursesNameLbl, BorderLayout.CENTER);
		}
	}

	// Singleton to ensure one and only instance
	public static Courses getInstance(JPanel main, User user) {
		if (instance == null) {
			instance = new Courses(main, user);
		}
		return instance;
	}

	public static void dispose() {
		instance = null;
	}
}