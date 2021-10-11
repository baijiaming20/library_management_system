package pers.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.entity.Admin;
import pers.entity.User;
import pers.service.LoginService;
import pers.service.PublicDaoService;
import pers.util.ConstUtil;
import pers.util.OtherUtil;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

	@Resource
	LoginService loginService;

	@Autowired
	@Qualifier("AdminPublicService")
	PublicDaoService<Admin> adminPublicDaoService;

	@Resource
	PublicDaoService<User> userPublicDaoService;

	@PostMapping("/login")
	public Map<String, Object> login(@RequestParam String name,
	                 @RequestParam String password,
	                 @RequestParam String type) {

		//登录成功后生成JWT
		Calendar instance = Calendar.getInstance();
		instance.add(Calendar.MINUTE,1000);
		String token = JWT.create()
				.withHeader(new HashMap<>())
				.withClaim("account",	name)
				.withClaim("account-type", type)
				.withExpiresAt(instance.getTime())
				.sign(Algorithm.HMAC256(ConstUtil.TOKEN_KEY));

		Map<String, Object> map = new HashMap<>();

		switch (type) {
		case "管理员":
			if (loginService.verifyAdmin(new Admin(name, password))) {
				map.put("state", 1);
				map.put("name", name);
				map.put("token", token);
				return map;
			}
			break;
		case "用户":
			if (loginService.verifyUser(new User(name, password))) {
				map.put("state", 2);
				map.put("name", name);
				map.put("token", token);
				if (!OtherUtil.isNumber(name)) {
					name = OtherUtil.str(name);
				}
				map.put("user_id", OtherUtil.getColOfRow(userPublicDaoService.selectByCol("user_name", name), "user_id"));
				return map;
			}
		}

		map.put("state", 0);
		return map;
	}

	@PostMapping("/verifyToken")
	public Map<String, Object> verifyToken(@RequestParam String token) {

		Map<String, Object> resMap = new HashMap<>();
		String account, accountType;

		try {
			JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(ConstUtil.TOKEN_KEY)).build();
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			account = decodedJWT.getClaim("account").asString();
			accountType = decodedJWT.getClaim("account-type").asString();

			resMap.put("state", 1);
			resMap.put("name", account);
			resMap.put("accountType", accountType);

			if (accountType.equals("用户")) {
				if (!OtherUtil.isNumber(account)) {
					account = OtherUtil.str(account);
				}
				resMap.put("user_id", OtherUtil.getColOfRow(userPublicDaoService.selectByCol("user_name", account), "user_id"));
			}

			return resMap;
		}
		catch (Exception e) {
			e.printStackTrace();
			resMap.put("state", 0);
			return resMap;
		}
	}

	/**
	 * 修改管理员账户密码
	 */
	@PostMapping("/updateAdminPassWord")
	public boolean updateAdminPassword(@RequestParam String username,@RequestParam String pwd) {
		try {
			adminPublicDaoService.updateUnique(
					Integer.parseInt(adminPublicDaoService.selectByColLimit("admin_name", username, 0, 1).get(0).get("admin_id").toString()),
					"admin_pwd",
					pwd
			);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
