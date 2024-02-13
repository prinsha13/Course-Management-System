package management;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import modetype.Course;
import modetype.Student;
import modetype.User;
import modetype.Teacher;
import database.CustomRenderer;
import database.Images;
import database.DatabaseSystem;
import database.Retrive;

class HomePage extends JPanel {

	private static final long serialVersionUID = -3421670490444154816L;
	private JTextField textField;

	public HomePage(JFrame frame, User user, JPanel login) {
		System.out.println(user.getRole());
		JPanel main = new JPanel();

		main.setBackground(new Color(238, 238, 238));
		main.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(main);
		main.setVisible(true);
		main.setLayout(null);
		Images logo = new Images("/logo.png");
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM dd");

		JPanel sidebar = new JPanel();
		sidebar.setBackground(new Color(230, 230, 250));
		sidebar.setBounds(0, 0, 134, 701);
		main.add(sidebar);
		sidebar.setLayout(null);
		JLabel logoImg = new JLabel(logo.getImage(50, 50));
		logoImg.setBounds(38, 41, 50, 50);
		sidebar.add(logoImg);

		JButton btnNewButton = new JButton("Logout");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Courses.dispose();
				Students.dispose();
				Teachers.dispose();
				login.setVisible(true);
				main.setVisible(false);
			}
		});
		btnNewButton.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		btnNewButton.setBounds(6, 590, 122, 37);
		sidebar.add(btnNewButton);

		JPanel coursesSidebar = new JPanel();
		coursesSidebar.setBackground(new Color(211, 211, 211));
		coursesSidebar.setBounds(15, 200, 109, 37);
		sidebar.add(coursesSidebar);
		coursesSidebar.setLayout(null);

		JLabel coursesLogo = new JLabel(new ImageIcon(getClass().getResource("/courses.png")));
		coursesLogo.setBounds(0, 0, 35, 35);
		coursesSidebar.add(coursesLogo);
		
		JLabel coursesLbl = new JLabel("Courses");
		coursesLbl.setBounds(34, 0, 75, 37);
		coursesSidebar.add(coursesLbl);
		coursesLbl.setHorizontalAlignment(SwingConstants.CENTER);
		coursesLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		JPanel studentsSidebar = new JPanel();

		studentsSidebar.setBackground(new Color(211, 211, 211));
		studentsSidebar.setBounds(15, 300, 109, 37);
		sidebar.add(studentsSidebar);
		studentsSidebar.setLayout(null);

		JLabel studentsLogo = new JLabel(new ImageIcon(getClass().getResource("/student.png")));
		studentsLogo.setBounds(0, 0, 35, 35);
		studentsSidebar.add(studentsLogo);

		JLabel studentsLbl = new JLabel("Students");
		studentsLbl.setBounds(30, 0, 79, 37);
		studentsSidebar.add(studentsLbl);
		studentsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		studentsLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		JPanel teachersSidebar = new JPanel();
		teachersSidebar.setBackground(new Color(211, 211, 211));
		teachersSidebar.setBounds(15, 400, 109, 37);
		sidebar.add(teachersSidebar);
		teachersSidebar.setLayout(null);

		JLabel teachersLogo = new JLabel(new ImageIcon(getClass().getResource("/teacher.png")));
		teachersLogo.setBounds(0, 0, 35, 35);
		teachersSidebar.add(teachersLogo);

		JLabel teacherLbl = new JLabel("Teachers");
		teacherLbl.setBounds(30, 0, 79, 37);
		teachersSidebar.add(teacherLbl);
		teacherLbl.setHorizontalAlignment(SwingConstants.CENTER);
		teacherLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));

		JSeparator separator = new JSeparator();
		separator.setBounds(6, 112, 122, 15);
		sidebar.add(separator);

		JPanel homepage = new JPanel();
		homepage.setBackground(new Color(211, 211, 211));
		homepage.setBounds(133, 0, 1341, 701);
		main.add(homepage);
		homepage.setLayout(null);

		JLabel title = new JLabel("Welcome!!");
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		title.setBounds(58, 39, 449, 37);
		homepage.add(title);

		JLabel subtitle = new JLabel(sdf.format(cal.getTime()));
		subtitle.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		subtitle.setEnabled(false);
		subtitle.setBounds(58, 71, 449, 21);
		homepage.add(subtitle);

		JPanel searchBar = new JPanel();
		searchBar.setLayout(null);
		searchBar.setBackground(new Color(230, 230, 250));
		searchBar.setBounds(806, 39, 418, 45);
		homepage.add(searchBar);

		JLabel searchIcon = new JLabel(new ImageIcon(getClass().getResource("/search_icon.png")));
		searchIcon.setBounds(382, 0, 30, 45);
		searchBar.add(searchIcon);

		textField = new JTextField(30);
		textField.setFont(new Font("Futura", Font.PLAIN, 15));
		textField.setBorder(null);
		textField.setBackground(new Color(230, 230, 250));
		textField.setBounds(21, 0, 360, 45);
		searchBar.add(textField);

		JLabel title2 = new JLabel("Available Courses");
		title2.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		title2.setBounds(58, 218, 280, 37);
		homepage.add(title2);

		JPanel coursesPanel = new JPanel();
		coursesPanel.setBackground(new Color(252, 255, 255));
		coursesPanel.setBounds(58, 267, 676, 370);
		homepage.add(coursesPanel);

		JLabel lblTeachers = new JLabel("Teachers");
		lblTeachers.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
		lblTeachers.setBounds(806, 218, 280, 37);
		homepage.add(lblTeachers);

		JPanel teachersPanel = new JPanel();
		teachersPanel.setBackground(Color.WHITE);
		teachersPanel.setBounds(806, 268, 418, 369);
		homepage.add(teachersPanel);

		DefaultListModel<Course> model = new DefaultListModel<Course>();
		model.addAll(Retrive.getCourses());
		coursesPanel.setLayout(new BorderLayout(0, 0));
		JList<Course> courses = new JList<Course>(model);
		courses.setBackground(new Color(230, 230, 250));
		courses.setCellRenderer(new CustomRenderer());
		courses.setBorder(null);
		courses.setBounds(0, 0, 418, 365);
		JScrollPane scrollPane = new JScrollPane(courses);
		coursesPanel.add(scrollPane);

		DefaultListModel<Teacher> teacherModel = new DefaultListModel<Teacher>();
		teacherModel.addAll(Retrive.getTeachers());
		JList<Teacher> teachers = new JList<Teacher>(teacherModel);
		teachers.setBackground(new Color(230, 230, 250));
		teachers.setCellRenderer(new CustomRenderer());
		teachers.setBorder(null);
		teachers.setBounds(0, 0, 418, 365);
		JScrollPane teacherScrollPane = new JScrollPane(teachers);
		scrollPane.setBounds(0, 0, 418, 369);
		teachersPanel.setLayout(new BorderLayout(0, 0));
		teachersPanel.add(teacherScrollPane);

		if (user instanceof Student) {
			Student student = (Student) user;
			JButton viewResultBtn = new JButton("View Your Result");
			viewResultBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JPanel panel = new JPanel(new GridLayout(1, 2));
					JLabel idLabel = new JLabel("Enter module id: ");
					JTextField idField = new JTextField();

					JLabel marksLabel = new JLabel("");

					panel.add(idLabel);
					panel.add(idField);

					panel.add(marksLabel);

					int result = JOptionPane.showConfirmDialog(null, panel, "View Results",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						String moduleId = idField.getText();

						try {
							int marks = DatabaseSystem.retrieveGrade(student.getId(), moduleId);
							String res = "You have scored " + marks + " marks in "
									+ Retrive.getModuleById(Integer.parseInt(moduleId)).getName();
							JOptionPane.showMessageDialog(null, res, "Your Result", JOptionPane.INFORMATION_MESSAGE);

						} catch (SQLException e1) {

							JOptionPane.showMessageDialog(null, "Results not available for the module",
									e1.getMessage(),
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			});
			viewResultBtn.setBounds(637, 39, 136, 45);
			homepage.add(viewResultBtn);
		}

		if (user instanceof Student) {
			Student student = (Student) user;
			String enrollmentStatus = student.getEnrolledCourse() == null ? "Not Enrolled"
					: student.getEnrolledCourse().getName();
			JLabel EnrollmentStatusLbl = new JLabel("Enrollment Status: " + enrollmentStatus);
			EnrollmentStatusLbl.setFont(new Font("Futura", Font.PLAIN, 20));
			EnrollmentStatusLbl.setBounds(58, 150, 1200, 16);
			homepage.add(EnrollmentStatusLbl);

			if (student.getEnrolledCourse() == null) {
				JButton enrollBtn = new JButton("Enroll Now");
				enrollBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int studentId = student.getId();
						JLabel courseIdLabel = new JLabel("Enter course id: ");
						JTextField courseIdField = new JTextField();
						JLabel phoneLabel = new JLabel("Enter phone number: ");
						JTextField phoneField = new JTextField();

						JPanel panel = new JPanel(new BorderLayout());
						JPanel inputPanel = new JPanel(new GridLayout(3, 2));

						inputPanel.add(courseIdLabel);
						inputPanel.add(courseIdField);

						inputPanel.add(phoneLabel);
						inputPanel.add(phoneField);

						String[] columnNames = { "Course ID", "Course Name" };

						ArrayList<Course> courses = Retrive.getCourses();

						int rowCount = courses.size();
						int columnCount = 2;

						Object[][] data = new Object[rowCount][columnCount];

						for (int i = 0; i < rowCount; i++) {
							data[i][0] = courses.get(i).getId();
							data[i][1] = courses.get(i).getName();
						}

						JTable modulesTable = new JTable(data, columnNames);
						JScrollPane scrollPane = new JScrollPane(modulesTable);

						panel.add(inputPanel, BorderLayout.NORTH);
						panel.add(scrollPane, BorderLayout.CENTER);

						int result = JOptionPane.showConfirmDialog(null, panel, "Assign Teacher",
								JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							int courseId = Integer.parseInt(courseIdField.getText());
							try {
								DatabaseSystem.enrollStudent(studentId, courseId, phoneField.getText(), student);
								JOptionPane.showMessageDialog(null, "Student enrolled successfully");
								student.setEnrolledCourse(Retrive.getCourses(courseId));
								EnrollmentStatusLbl
										.setText("Enrollment Status: " + student.getEnrolledCourse().getName());

							} catch (SQLException e1) {
								System.out.println(e1.getMessage());
								JOptionPane.showMessageDialog(null, "Error enrolling student", null,
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				enrollBtn.setBounds(48, 181, 117, 29);
				homepage.add(enrollBtn);

			}
		}

		logoImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				homepage.setVisible(true);
			}
		});

		coursesSidebar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Courses.getInstance(main, user).setVisible(true);
				Students.getInstance(main, user).setVisible(false);
				Teachers.getInstance(main, user).setVisible(false);
				homepage.setVisible(false);
			}
		});

		studentsSidebar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Courses.getInstance(main, user).setVisible(false);
				Teachers.getInstance(main, user).setVisible(false);
				Students.getInstance(main, user).setVisible(true);
				homepage.setVisible(false);
			}
		});

		teachersSidebar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Courses.getInstance(main, user).setVisible(false);
				Students.getInstance(main, user).setVisible(false);
				Teachers.getInstance(main, user).setVisible(true);
				homepage.setVisible(false);
			}
		});

	}
}