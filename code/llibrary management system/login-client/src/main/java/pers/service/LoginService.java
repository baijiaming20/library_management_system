package pers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.entity.Admin;
import pers.entity.User;
import pers.util.ObjectUtil;
import pers.util.OtherUtil;

@Service
public class LoginService {

	@Autowired
	@Qualifier("AdminPublicService")
	PublicDaoService<Admin> adminService;

	@Autowired
	@Qualifier("UserPublicService")
	PublicDaoService<User> userService;

	public boolean verifyAdmin(Admin admin) {
		return ObjectUtil.mapToObject(adminService.selectByCol("admin_name", OtherUtil.str(admin.getAdmin_name())), Admin.class).contains(admin);
	}

	public boolean verifyUser(User user) {
		return ObjectUtil.mapToObject(userService.selectByCol("user_name", OtherUtil.str(user.getUser_name())), User.class).contains(user);
	}
}
