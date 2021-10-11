package pers.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.entity.Borrow;
import pers.service.BorrowService;
import pers.service.PublicDaoService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 借阅信息控制器
 */
@RestController
public class BorrowController {

	@Resource
	private BorrowService borrowService;

	@Autowired
	@Qualifier("BorrowPublicService")
	private PublicDaoService<Borrow> borrowPublicDaoService;

	/**
	 * @return 参数数目借阅信息
	 */
	@PostMapping("/borrows")
	public List<Map<String, Object>> borrows(@RequestParam int start,
	                                     @RequestParam int end) {
		return borrowService.process(borrowPublicDaoService.selectLimit(start, end));
	}

	/**
	 * @return 总条数
	 */
	@GetMapping("/borrowsTotal")
	public int length() {
		return borrowPublicDaoService.selectLength();
	}

	/**
	 * @return 查询指定条件数据
	 */
	@PostMapping("/borrowsCondition")
	public List<Map<String, Object>> conditionBorrows(@RequestParam String key,
	                                                  @RequestParam String value,
	                                                  @RequestParam int start,
	                                                  @RequestParam int end) {
		return borrowService.process(borrowPublicDaoService.selectByColLimit(key, value, start, end));
	}

	/**
	 * 可被查询的列
	 */
	@GetMapping("/borrowQueryCols")
	public Map<String, String> borrowQueryCols() {
		return new HashMap<String, String>(){{
			put("id", "ID");
			put("user_id", "用户ID");
			put("book_id", "书籍ID");
			put("date", "借书时间");
			put("return_date", "还书时间");
		}};
	}

	/**
	 * 删除指定ID数据
	 */
	@PostMapping("/removeBorrow")
	public boolean removeBorrow(@RequestParam int id) {
		try {
			borrowPublicDaoService.deleteById(id);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * 借阅对象原始键数组
	 */
	@GetMapping("/borrowOriginKeys")
	public String[] originKeys() {
		return Borrow.originKeys;
	}

	/**
	 * 更新信息
	 */
	@PostMapping("/updateBorrow")
	public boolean updateBorrow(@RequestParam Map<String, Object> borrow) {
		try {
			borrowPublicDaoService.update(borrow, Borrow.originKeys);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
