package management;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import database.Images;
public class Launch extends JPanel {

	private static final long serialVersionUID = -3421670490444154816L;
	private int currentProgress;
	private Timer timer;

	public Launch(JFrame frame, Images logo) {
		JPanel launchframe = new JPanel();
		launchframe.setBackground(new Color(230, 230, 250));
		launchframe.setBounds(0, -7, 1480, 701);
		frame.getContentPane().add(launchframe);
		launchframe.setVisible(true);
		launchframe.setLayout(null);

		JLabel logoImg = new JLabel(logo.getImage(100, 100));
		logoImg.setBounds(680, 259, 100, 100);
		logoImg.setBackground(new Color(211, 211, 211));
		launchframe.add(logoImg);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(605, 456, 259, 20);
		progressBar.setMaximum(100);
		launchframe.add(progressBar);

		currentProgress = 0;

		timer = new Timer(3, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentProgress++;
				progressBar.setValue((int) ((currentProgress / (float) progressBar.getMaximum()) * 100));
				if (currentProgress == 100) {
					timer.stop();
					new SignInPage(frame, logo);
					launchframe.setVisible(false);
				}
			}
		});
		timer.start();

	}
}