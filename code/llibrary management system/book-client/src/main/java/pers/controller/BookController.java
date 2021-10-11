package pers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.entity.Book;
import pers.entity.BookType;
import pers.entity.Borrow;
import pers.service.BookService;
import pers.service.PublicDaoService;
import pers.util.OtherUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 书籍信息控制器
 */
@RestController
public class BookController {

	@Resource
	private BookService bookService;

	@Resource
	private PublicDaoService<Book> bookPublicDaoService;

	@Resource
	private PublicDaoService<BookType> typePublicDaoService;

	@Resource
	private PublicDaoService<Borrow> borrowPublicDaoService;

	/**
	 * 查询指定位置的书籍信息
	 */
	@PostMapping("/books")
	public List<Map<String, Object>> books(@RequestParam int start,
	                                       @RequestParam int end) {
		return bookService.process(bookPublicDaoService.selectLimit(start, end));
	}

	/**
	 * 查询书籍总数
	 */
	@GetMapping("/booksTotal")
	public int length() {
		return bookPublicDaoService.selectLength();
	}

	/**
	 * 查询指定条件书籍信息
	 */
	@PostMapping("/booksCondition")
	public List<Map<String, Object>> conditionBooks(@RequestParam String key,
	                                                  @RequestParam String value,
	                                                  @RequestParam int start,
	                                                  @RequestParam int end) {
		if (!OtherUtil.isNumber(value)) {
			value = OtherUtil.str(value);
		}
		return bookService.process(bookPublicDaoService.selectByColLimit(key, value, start, end));
	}

	/**
	 * 可被查询的列
	 */
	@GetMapping("/bookQueryCols")
	public Map<String, String> bookQueryCols() {
		return new HashMap<String, String>(){{
			put("book_id", "ID");
			put("book_type", "类别ID");
			put("book_price", "价格");
			put("book_name", "名称");
			put("book_author", "作者");
			put("book_publish", "出版社");
		}};
	}

	/**
	 * 删除指定ID数据
	 */
	@PostMapping("/removeBook")
	public boolean removeBook(@RequestParam int id) {
		try {
			bookPublicDaoService.deleteById(id);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * 借阅对象原始键数组
	 */
	@GetMapping("/bookOriginKeys")
	public String[] originKeys() {
		return Book.originKeys;
	}

	/**
	 * 更新信息
	 */
	@PostMapping("/updateBook")
	public boolean updateBook(@RequestParam Map<String, Object> book) {
		try {
			bookPublicDaoService.update(book, Book.originKeys);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	/**
	 * 插入信息
	 */
	@GetMapping("/insertBook")
	public boolean insertBook(@RequestParam Map<String, Object> book) {
		try {
			bookPublicDaoService.insert(book, Book.originKeys);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 新建类别
	 */
	@GetMapping("/insertType")
	public boolean insertType(@RequestParam Map<String, Object> type) {
		try {
			typePublicDaoService.insert(type, BookType.originKeys);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 书籍类别原始原始键
	 */
	@GetMapping("/typeOriginKeys")
	public String[] typeOriginKeys() {
		return BookType.originKeys;
	}

	/**
	 * 查询类别
	 */
	@PostMapping("/types")
	public List<Map<String, Object>> types(@RequestParam int start,
										   @RequestParam int end) {
		return typePublicDaoService.selectLimit(start, end);
	}

	/**
	 * 查询类别总数
	 */
	@GetMapping("/typesTotal")
	public int typesLength() {
		return typePublicDaoService.selectLength();
	}

	/**
	 * 查询指定条件类别信息
	 */
	@PostMapping("/typesCondition")
	public List<Map<String, Object>> conditionTypes(@RequestParam String key,
													@RequestParam String value,
													@RequestParam int start,
													@RequestParam int end) {
		if (!OtherUtil.isNumber(value)) {
			value = OtherUtil.str(value);
		}
		return typePublicDaoService.selectByColLimit(key, value, start, end);
	}

	/**
	 * 可被查询的列
	 */
	@GetMapping("/typeQueryCols")
	public Map<String, String> typeQueryCols() {
		return new HashMap<String, String>(){{
			put("type_id", "ID");
			put("type_name", "类别名");
		}};
	}

	/**
	 * 删除指定ID数据
	 */
	@PostMapping("/removeType")
	public boolean removeType(@RequestParam int id) {
		try {
			typePublicDaoService.deleteById(id);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * 更新信息
	 */
	@PostMapping("/updateType")
	public boolean updateType(@RequestParam Map<String, Object> type) {
		try {
			typePublicDaoService.update(type, BookType.originKeys);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 借书
	 */
	@PostMapping("/borrowBook")
	public boolean borrowBook(@RequestParam int bookId,
							  @RequestParam int userId) {

		Map<String, Object> borrow = new HashMap<>();
		borrow.put("user_id", userId);
		borrow.put("book_id", bookId);
		borrow.put("date", OtherUtil.getDateNow());
		borrow.put("return_date", OtherUtil.getDateMonthLater());

		if (borrowPublicDaoService.selectByCol("book_id", bookId + "").size() > 0) {
			return false;
		}

		try {
			borrowPublicDaoService.insert(borrow, Borrow.originKeys);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
