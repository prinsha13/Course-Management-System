package database;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;

import modetype.Course;
import modetype.Module;
import modetype.User;
import modetype.Teacher;
import java.awt.Color;

public class CustomRenderer extends JPanel implements ListCellRenderer<Object> {
    private static final long serialVersionUID = 6422050714652017814L;
    private JLabel titleLabel;
    private JLabel subtitleLabel;

    public CustomRenderer() {
    	setBackground(new Color(230, 230, 250));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));
        titleLabel = new JLabel();
        subtitleLabel = new JLabel();
        add(titleLabel);
        add(subtitleLabel);
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        subtitleLabel.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 12));
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        removeAll();
        titleLabel.setText("");
        subtitleLabel.setText("");
        
        if (value instanceof User) {
            User user = (User) value;
            titleLabel.setText(user.getName());
            add(titleLabel);
            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                for (Module module : teacher.getModules()) {
                    JLabel moduleLabel = new JLabel("   " + module.getName());
                    moduleLabel.setFont(new Font("Futura", Font.ITALIC, 10));
                    add(moduleLabel);
                }
            }
        } else if (value instanceof Course) {
            Course course = (Course) value;
            titleLabel.setText("📌  " + course.getName());
            add(titleLabel);
            for (Module module : course.getModules()) {
                JLabel moduleLabel = new JLabel("           " + module.getName());
                moduleLabel.setFont(new Font("Futura", Font.ITALIC, 10));
                add(moduleLabel);
            }
        }

        setBackground(list.getBackground());
        setForeground(list.getForeground());
        return this;
    }
}
