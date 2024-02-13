package management;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import database.Retrive;
import database.DatabaseSystem;
import database.Database;
import database.Images;

public class CMS {
	private JFrame CourseManagementSystem;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CMS screen = new CMS();
					screen.CourseManagementSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public CMS() {
		initialize();
	}

	private void initialize() {
		CourseManagementSystem = new JFrame();
		CourseManagementSystem.setBackground(new Color(230, 230, 250));
		CourseManagementSystem.getContentPane().setBackground(new Color(230, 230, 250));
		CourseManagementSystem.setTitle("Course Management System");
		CourseManagementSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CourseManagementSystem.getContentPane().setLayout(null);
		CourseManagementSystem.setSize(1480, 720);
		CourseManagementSystem.setResizable(false);
		Images logo = new Images("../logo.png");
		new Launch(CourseManagementSystem, logo);

		try {
			Database db = Database.getInstance();
			new Users(db);
			new Retrive();
			new DatabaseSystem();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null,
					"Cannot connect to database!\nPlease make sure mySQL is correctly setup and running!",
					"Error 500: Server Communication Failed", JOptionPane.ERROR_MESSAGE);
			System.exit(500);
		}
	}
}