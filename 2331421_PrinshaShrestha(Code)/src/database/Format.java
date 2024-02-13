package database;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;

public class Format {
	public static boolean format(String text, JLabel label, String field) {
		switch (field) {
			case "Password":
				if (!text.matches(".*\\d+.*")) {
					label.setText(field + " must have atleast one number!");
					return false;
				}
				if (text.length() < 8) {
					label.setText(field + " must be atleast 10 characters long!");
					return false;
				}
				label.setText("");
				return true;
			case "Email":
				String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(text);
				if (!matcher.matches()) {
					label.setText("Invalid " + field.toLowerCase() + "!");
					return false;
				}
				label.setText("");
				return true;
			default:
				if (text.length() < 3) {
					label.setText("Invalid " + field.toLowerCase() + "!");
					return false;
				}
				label.setText("");
				return true;
				
		}
	}

}