package pers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.entity.User;
import pers.service.PublicDaoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Resource
    private PublicDaoService<User> userPublicDaoService;

    /**
     * 书籍类别原始原始键
     */
    @GetMapping("/userOriginKeys")
    public String[] userOriginKeys() {
        return User.originKeys;
    }

    /**
     * 查询类别
     */
    @PostMapping("/users")
    public List<Map<String, Object>> users(@RequestParam int start,
                                           @RequestParam int end) {
        return userPublicDaoService.selectLimit(start, end);
    }

    /**
     * 查询类别总数
     */
    @GetMapping("/usersTotal")
    public int usersLength() {
        return userPublicDaoService.selectLength();
    }

    /**
     * 查询指定条件类别信息
     */
    @PostMapping("/usersCondition")
    public List<Map<String, Object>> conditionUsers(@RequestParam String key,
                                                    @RequestParam String value,
                                                    @RequestParam int start,
                                                    @RequestParam int end) {
        return userPublicDaoService.selectByColLimit(key, value, start, end);
    }

    /**
     * 可被查询的列
     */
    @GetMapping("/userQueryCols")
    public Map<String, String> userQueryCols() {
        return new HashMap<String, String>(){{
            put("user_id", "ID");
            put("user_name", "用户名");
            put("user_email", "邮箱");
        }};
    }

    /**
     * 删除指定ID数据
     */
    @PostMapping("/removeUser")
    public boolean removeUser(@RequestParam int id) {
        try {
            userPublicDaoService.deleteById(id);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * 更新信息
     */
    @PostMapping("/updateUser")
    public boolean updateUser(@RequestParam Map<String, Object> user) {
        try {
            userPublicDaoService.update(user, User.originKeys);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 创建用户
     */
    @GetMapping("/insertUser")
    public boolean insertUser(@RequestParam Map<String, Object> user) {
        try {
            userPublicDaoService.insert(user, User.originKeys);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
