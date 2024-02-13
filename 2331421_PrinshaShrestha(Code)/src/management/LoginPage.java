package management;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import modetype.User;
import database.Format;
import database.Images;

public class LoginPage extends JPanel {

	private static final long serialVersionUID = -2934127260269983979L;

	private JTextField emailText;
	private JPasswordField passwordText;

	public LoginPage(JFrame frame, Images logo) {
		JPanel login = new JPanel();
		login.setBackground(new Color(230, 230, 250));
		login.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(login);
		login.setVisible(true);
		login.setLayout(null);

		JLabel logoImg = new JLabel(logo.getImage(100, 100));
		logoImg.setBounds(520, 43, 100, 100);
		login.add(logoImg);
		
		JLabel title = new JLabel(" Login Panel");
		title.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
		title.setBounds(650, 77, 200, 54);
		login.add(title);

		emailText = new JTextField();
		emailText.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				passwordText.requestFocusInWindow();
			}
		});
		emailText.setFont(new Font("Times New Roman", Font.BOLD, 15));
		emailText.setColumns(10);
		emailText.setBounds(520, 210, 332, 43);
		emailText.setBorder(
				BorderFactory.createCompoundBorder(emailText.getBorder(), BorderFactory.createEmptyBorder(0, 5, 0, 5)));
		login.add(emailText);

		JLabel emailLbl = new JLabel("Enter Email: ");
		emailLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		emailLbl.setBounds(520, 180, 212, 20);
		login.add(emailLbl);

		JLabel passwordLbl = new JLabel("Enter Password: ");
		passwordLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		passwordLbl.setBounds(520, 280, 212, 21);
		login.add(passwordLbl);

		JLabel bottomLbl = new JLabel("Don't have an account ?");
		bottomLbl.setEnabled(false);
		bottomLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 12));
		bottomLbl.setBounds(600, 444, 152, 27);
		login.add(bottomLbl);

		JLabel signUpBtn = new JLabel("Sign In");
		signUpBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new SignInPage(frame, logo);
				login.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				signUpBtn.setForeground(new Color(242, 147, 179));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				signUpBtn.setForeground(Color.BLACK);
			}
		});
		signUpBtn.setFont(new Font("Times New Roman", Font.BOLD, 13));
		signUpBtn.setBounds(750, 444, 43, 27);
		login.add(signUpBtn);

		JLabel errorEmailLbl = new JLabel("");
		errorEmailLbl.setForeground(new Color(255, 0, 7));
		errorEmailLbl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		errorEmailLbl.setBounds(520, 250, 322, 27);
		login.add(errorEmailLbl);

		JLabel errorPasswordLbl = new JLabel("");
		errorPasswordLbl.setForeground(new Color(255, 0, 7));
		errorPasswordLbl.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		errorPasswordLbl.setBounds(523, 350, 322, 27);
		login.add(errorPasswordLbl);

		ActionListener loginAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				errorEmailLbl.setText("");
				errorPasswordLbl.setText("");

				String email = emailText.getText().strip().toLowerCase();
				String password = new String(passwordText.getPassword()).strip();

				if (!Format.format(email, errorEmailLbl, "Email")) {
					return;
				}

				try {
					User user = Users.returnUser(email, password);

					emailText.setText("");
					passwordText.setText("");

					new HomePage(frame, user, login);

					login.setVisible(false);

				} catch (InvalidEmail | InvalidPassword invalidException) {
				    if (invalidException instanceof InvalidEmail) {
				        errorEmailLbl.setText(invalidException.getMessage());
				    } else {
				        errorPasswordLbl.setText(invalidException.getMessage());
				    }
				} catch (Exception er) {
				    er.printStackTrace();
				}

			}
		};

		passwordText = new JPasswordField();
		passwordText.setColumns(10);
		passwordText.setFont(new Font("Times New Roman", Font.BOLD, 15));
		passwordText.setBounds(520, 310, 332, 43);
		passwordText.addActionListener(loginAction);
		passwordText.setBorder(BorderFactory.createCompoundBorder(passwordText.getBorder(),
				BorderFactory.createEmptyBorder(0, 5, 0, 38)));
		login.add(passwordText);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(loginAction);
		btnLogin.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		btnLogin.setForeground(new Color(242, 252, 255));
		btnLogin.setOpaque(true);
		btnLogin.setBorderPainted(false);
		btnLogin.setBackground(new Color(0, 0, 0));
		btnLogin.setBounds(520, 384, 332, 43);
		login.add(btnLogin);
	}

}