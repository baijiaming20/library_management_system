package pers.service;

import org.springframework.stereotype.Service;
import pers.entity.Book;
import pers.entity.BookType;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 书籍信息服务
 */
@Service
public class BookService {

	@Resource
	PublicDaoService<BookType> bookTypePublicDaoService;

	/**
	 * 对书籍信息进行处理
	 */
	public List<Map<String, Object>> process(List<Map<String, Object>> list) {

		for (Map<String, Object> map : list) {
			map.put(Book.typeNameName, bookTypePublicDaoService.selectByCol(BookType.idName, map.get(Book.typeIdName).toString()).get(0).get(BookType.typeName));
		}

		return list;
	}
}
