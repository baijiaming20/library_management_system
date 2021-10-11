package pers.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
	private int user_id;
	private String user_name,
		user_pwd,
		user_email;

	public User(String name, String password) {
		this.user_name = name;
		this.user_pwd = password;
	}

	// 不可以删除，后面有用到反射的地方需要这个无参构造
	public User() {}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User)) {
			return false;
		}
		User user = (User) obj;
		return user.user_name.equals(this.user_name) && user.user_pwd.equals(this.user_pwd);
	}

	public static final String[] originKeys = new String[] {
			"user_id",
			"user_name",
			"user_pwd",
			"user_email"
	};
}
