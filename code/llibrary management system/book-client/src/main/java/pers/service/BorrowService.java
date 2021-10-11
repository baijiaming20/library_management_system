package pers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pers.entity.Book;
import pers.entity.Borrow;
import pers.entity.User;

import java.util.List;
import java.util.Map;

/**
 * 借阅信息服务
 */
@Service
public class BorrowService {

	@Autowired
	@Qualifier("BookPublicService")
	PublicDaoService<Book> bookPublicDaoService;

	@Autowired
	@Qualifier("UserPublicService")
	PublicDaoService<User> userPublicDaoService;

	@Autowired
	@Qualifier("BorrowPublicService")
	PublicDaoService<Borrow> borrowPublicDaoService;

	/**
	 * 对借阅信息处理
	 */
	public List<Map<String, Object>> process(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			process(map);
		}
		return list;
	}

	public Map<String, Object> process(Map<String, Object> info) {
		String bookName = bookPublicDaoService.selectByCol(Borrow.bookIdName, info.get(Borrow.bookIdName).toString()).get(0).get(Borrow.bookName).toString();
		info.put(Borrow.bookName, bookName);
		// 确定借阅书的人的名字
		String userName = userPublicDaoService.selectByCol(Borrow.userIdName, info.get(Borrow.userIdName).toString()).get(0).get(Borrow.userName).toString();
		info.put(Borrow.userName, userName);
		return info;
	}
}
