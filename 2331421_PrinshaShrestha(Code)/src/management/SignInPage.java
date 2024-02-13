package management;
import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;
import database.Format;
import database.Images;
public class SignInPage extends JPanel {

	private static final long serialVersionUID = 8409831875589206498L;
	private JTextField nameText;
	private JTextField emailText;
	private JPasswordField passwordText;

	public SignInPage(JFrame frame, Images logo) {
		JPanel signin = new JPanel();
		signin.setBackground(new Color(230, 230, 250));
		signin.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(signin);
		signin.setVisible(true);
		signin.setLayout(null);

		JLabel logoImg = new JLabel(logo.getImage(100, 100));
		logoImg.setBounds(520, 43, 100, 100);
		signin.add(logoImg);

		JLabel title = new JLabel("Sign In Panel ");
		title.setBackground(new Color(169, 169, 169));
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		title.setBounds(650, 88, 230, 54);
		signin.add(title);

		nameText = new JTextField();
		nameText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				emailText.requestFocusInWindow();
			}
		});
		nameText.setFont(new Font("Times New Roman", Font.BOLD, 15));
		nameText.setBounds(520, 230, 332, 43);
		nameText.setBorder(BorderFactory.createCompoundBorder(nameText.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		signin.add(nameText);
		nameText.setColumns(10);

		emailText = new JTextField();
		emailText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				passwordText.requestFocusInWindow();
			}
		});
		emailText.setFont(new Font("Times New Roman", Font.BOLD, 15));
		emailText.setColumns(10);
		emailText.setBounds(520, 317, 332, 43);
		emailText.setBorder(BorderFactory.createCompoundBorder(emailText.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		signin.add(emailText);

		JLabel nameLbl = new JLabel("Enter Username: ");
		nameLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		nameLbl.setBounds(520, 200, 212, 22);
		signin.add(nameLbl);

		JLabel emailLbl = new JLabel("Enter Email:");
		emailLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		emailLbl.setBounds(520, 290, 212, 22);
		signin.add(emailLbl);

		JLabel passwordLbl = new JLabel("Enter Password: ");
		passwordLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		passwordLbl.setBounds(520, 380, 212, 22);
		signin.add(passwordLbl);

		JLabel bottomLbl = new JLabel("Already have an account ?");
		bottomLbl.setEnabled(false);
		bottomLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		bottomLbl.setBounds(550, 550, 160, 27);
		signin.add(bottomLbl);

		JLabel loginBtn = new JLabel("Login");
		loginBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new LoginPage(frame, logo);
				signin.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				loginBtn.setForeground(new Color(242, 147, 179));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				loginBtn.setForeground(Color.BLACK);
			}
		});
		loginBtn.setFont(new Font("Times New Roman", Font.BOLD, 13));
		loginBtn.setBounds(730, 550, 42, 27);
		signin.add(loginBtn);

		JLabel errorNameLbl = new JLabel("");
		errorNameLbl.setForeground(new Color(255, 0, 7));
		errorNameLbl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		errorNameLbl.setBounds(520, 266, 322, 27);
		signin.add(errorNameLbl);

		JLabel errorEmailLbl = new JLabel("");
		errorEmailLbl.setForeground(new Color(255, 0, 7));
		errorEmailLbl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		errorEmailLbl.setBounds(520, 355, 360, 27);
		signin.add(errorEmailLbl);

		JLabel errorPasswordLbl = new JLabel("");
		errorPasswordLbl.setForeground(new Color(255, 0, 7));
		errorPasswordLbl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		errorPasswordLbl.setBounds(520, 460, 322, 27);
		signin.add(errorPasswordLbl);

		ActionListener signupAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Boolean> isValidSubmission = new ArrayList<Boolean>();
				String name = nameText.getText().strip();
				String email = emailText.getText().strip().toLowerCase();
				String password = new String(passwordText.getPassword()).strip();

				isValidSubmission.add(Format.format(name, errorNameLbl, "Name"));
				isValidSubmission.add(Format.format(email, errorEmailLbl, "Email"));
				isValidSubmission.add(Format.format(password, errorPasswordLbl, "Password"));

				if (!isValidSubmission.contains(false)) {
					try {
						Users.addUsers(name, email, password);

						JLabel label = new JLabel("Account created successfully");
						label.setHorizontalAlignment(JLabel.CENTER);

						JDialog dialog = new JDialog();
						dialog.setAlwaysOnTop(true);
						dialog.setSize(300, 75);
						dialog.getContentPane().add(label);
						dialog.setUndecorated(true);
						dialog.setLocationRelativeTo(null);

						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						dialog.setVisible(true);

						Timer timer = new Timer(2000, new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								dialog.dispose();
							}
						});
						timer.setRepeats(false);
						timer.start();

						nameText.setText("");
						emailText.setText("");
						passwordText.setText("");

						new LoginPage(frame, logo);
						signin.setVisible(false);
					} catch (Exception err) {
						System.out.println(err);
						errorEmailLbl.setText(err.getMessage());
					}
				}

			}
		};

		passwordText = new JPasswordField();
		passwordText.setColumns(10);
		passwordText.setFont(new Font("Times New Roman", Font.BOLD, 15));
		passwordText.setBounds(520, 410, 332, 43);
		passwordText.setBorder(BorderFactory.createCompoundBorder(passwordText.getBorder(),
				BorderFactory.createEmptyBorder(0, 5, 0, 38)));

		passwordText.addActionListener(signupAction);
		signin.add(passwordText);

		JButton createBtn = new JButton("Sign In");
		createBtn.addActionListener(signupAction);
		createBtn.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		createBtn.setForeground(new Color(242, 252, 255));
		createBtn.setOpaque(true);
		createBtn.setBorderPainted(false);
		createBtn.setBackground(new Color(0, 0, 0));
		createBtn.setBounds(520, 500, 332, 43);
		signin.add(createBtn);
	}
}
