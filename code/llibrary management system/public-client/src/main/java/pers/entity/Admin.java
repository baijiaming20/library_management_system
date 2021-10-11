package pers.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Admin {
	private int admin_id;
	private String admin_name, admin_pwd, admin_email;

	public Admin(){}

	public Admin(String name, String password) {
		this.admin_name = name;
		this.admin_pwd = password;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Admin)) {
			return false;
		}
		Admin admin = (Admin) obj;
		return admin_name.equals(admin.getAdmin_name()) && admin_pwd.equals(admin.getAdmin_pwd());
	}
}
