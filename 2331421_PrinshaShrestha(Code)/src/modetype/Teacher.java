package modetype;
import java.util.ArrayList;

public class Teacher extends User {
	private int id;
	private String name;
	private String phone;
	private String email;
	private ArrayList<Module> module;

	public Teacher(int id, String name, String phone, String email, ArrayList<Module> module) {
		super(name, "Teacher");
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.module = module;
	}
	public Teacher(String name) {
		super(name, "Teacher");
	}

	public static Teacher fromSql(int id, String name, String phone, String email, ArrayList<Module> module) {
		return new Teacher(id, name, phone, email, module);
	}

	public ArrayList<Module> getModules() {
		return module;
	}

	public String getModuleString() {
		String moduleString = "";
		for (Module module : module) {
			moduleString += module.getName() + ", ";
		}
		if (moduleString.length() < 3) {
			return "No modules";
		}
		return moduleString.substring(0, moduleString.length() - 2);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

}