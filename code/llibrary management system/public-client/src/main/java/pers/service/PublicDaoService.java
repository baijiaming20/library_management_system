package pers.service;

import org.springframework.stereotype.Service;
import pers.dao.PublicDao;
import pers.util.ObjectUtil;
import pers.util.OtherUtil;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class PublicDaoService<T> {

	private String tableName;
	private String idName;
	private String[] fields; // 除id以外的字段名
	private int len = 0;
	private Class<T> tClass;

	@Resource
	public PublicDao publicDao;

	public PublicDaoService() {}

	public PublicDaoService(String tableName, String idName, Class<T> c) {
		this.tableName = tableName;
		this.idName = idName;

		List<String> nameList = new LinkedList<>();
		for (String name : ObjectUtil.getPublicAttrNames(c)) {
			if (!name.equals(idName))
				nameList.add(name);
		}
		fields = nameList.toArray(new String[0]);
		len = fields.length;
		tClass = c;
	}

	/**
	 * 查询全部数据
	 */
	public List<Map<String, Object>> selectAll() {
		return publicDao.select("SELECT * FROM `" + tableName + "`");
	}

	/**
	 * 查询开始数目到记录数目个数的数据
	 */
	public List<Map<String, Object>> selectLimit(int start, int num) {
		return publicDao.select("SELECT * FROM `" + tableName + "` LIMIT " + start + ", " + num);
	}

	/**
	 * 查询记录数目的数据
	 */
	public List<Map<String, Object>> selectLimitRecord(int num) {
		return publicDao.select("SELECT * FROM `" + tableName + "` LIMIT " + num);
	}

	/**
	 * 查询数据数量
	 */
	public int selectLength() {
		return publicDao.selectInt("SELECT COUNT(*) FROM `" + tableName + "`");
	}

	/**
	 * 查询指定id的数据
	 */
	public Map<String, Object> selectById(int id) {
		return publicDao.selectOne("SELECT * FROM `" + tableName + "` WHERE `" + idName + "` = " + id);
	}

	/**
	 * 查询指定列名的指定数据
	 */
	public List<Map<String, Object>> selectByCol(String key, String value) {
		return publicDao.select("SELECT * FROM `" + tableName + "` WHERE " + key + " = " + value);
	}

	/**
	 * 查询指定列名指定位置的指定数据
	 */
	public List<Map<String, Object>> selectByColLimit(String key,
	                                                  String value,
	                                                  int start,
	                                                  int end) {
		if (!OtherUtil.isNumber(value)) {
			value = OtherUtil.str(value);
		}
		return publicDao.select("SELECT * FROM `" + tableName + "` WHERE " + key + " = " + value + " LIMIT " + start + ", " + end);
	}

	/**
	 * 删除指定id的数据
	 */
	public void deleteById(int id) {
		publicDao.delete("DELETE FROM `" + tableName + "` WHERE `" + idName + "` = " + id);
	}

	/**
	 * 添加新的信息
	 */
	public void insert(T entity, String[] originKeys) {
		Map<String, Object> map = ObjectUtil.getAttrsMap(entity);
		insert(map, originKeys);
	}

	/**
	 * 插入新的信息
	 */
	public void insert(Map<String, Object> map, String[] originKeys) {
		StringBuilder sql = new StringBuilder("INSERT INTO `" + tableName + "` (");

		int len = originKeys.length - 1;
		map.remove(idName);
		originKeys = OtherUtil.removeStr(originKeys, idName);

		for (int i = 0; i < len; i++) {
			sql.append("`").append(originKeys[i]).append("`");
			if (i < len - 1) {
				sql.append(", ");
			}
		}

		sql.append(") VALUES (");

		for (int i = 0; i < len; i++) {
			String value = map.get(originKeys[i]).toString();
			if (!OtherUtil.isNumber(value)) {
				value = OtherUtil.str(value);
			}
			sql.append(value);
			if (i < len - 1) {
				sql.append(", ");
			}
		}

		sql.append(")");
		publicDao.insert(sql.toString());
	}

	/**
	 * 更新一行内容的所有字段
	 */
//	public void update(T entity) {
//		Map<String, Object> map = ObjectUtil.getAttrsMap(entity);
//
//		update(map);
//	}
//
//	public void update(T entity, String[] originKeys) {
//		Map<String, Object> map = new HashMap<>();
//		for (String key : originKeys) {
//			map.put(key, ObjectUtil.getAttrValue(entity, key));
//		}
//
//		update(map);
//	}

	public void update(Map<String, Object> map, String[] originKeys) {
		StringBuilder sql = new StringBuilder("UPDATE `" + tableName + "` SET ");

		for (int i = 0; i < originKeys.length; i++) {
			if (originKeys[i].equals(idName)) {
				continue;
			}
			String value = map.get(originKeys[i]).toString();
			if (!OtherUtil.isNumber(value)) {
				value = OtherUtil.str(value);
			}
			sql.append("`").append(originKeys[i]).append("` = ").append(value);
			if (i < originKeys.length - 1) {
				sql.append(", ");
			}
		}

		sql.append(" WHERE `").append(idName).append("` = ").append(map.get(idName));
		publicDao.update(sql.toString());
	}

	/**
	 * 更新数据指定内容
	 */
	public void updateUnique(int id, String key, Object value) {
		String str = value.toString();
		if (!OtherUtil.isNumber(str)) {
			str = OtherUtil.str(str);
		}
		publicDao.update("UPDATE `" + tableName + "` SET " +
				key + " = " + str + " WHERE `" + idName + "` = " + id);
	}
}

//	/**
//	 * 查询全部数据
//	 */
//	@Select("SELECT * FROM `" + TABLE_NAME + "`")
//	List<Admin> selectAll();
//
//	/**
//	 * 查询开始数目到记录数目个数的数据
//	 */
//	@Select("SELECT * FROM `" + TABLE_NAME + "` LIMIT #{start}, #{num}")
//	List<Admin> selectLimit(@Param("start") int start, @Param("num") int num);
//
//	/**
//	 * 查询记录数目的数据
//	 */
//	@Select("SELECT * FROM `" + TABLE_NAME + "` LIMIT #{num}")
//	List<Admin> selectLimitRecord(int num);
//
//	/**
//	 * 查询数据数量
//	 */
//	@Select("SELECT COUNT(*) FROM `" + TABLE_NAME + "`")
//	int selectLength();
//
//	/**
//	 * 查询指定id的数据
//	 */
//	@Select("SELECT * FROM `" + TABLE_NAME + "` WHERE " + ID + " = #{id}")
//	Admin selectById(int id);
//
//	/**
//	 * 查询指定列名的指定数据
//	 */
//	@Select("SELECT * FROM `" + TABLE_NAME + "` WHERE #{key} = #{value}")
//	Admin selectByCol(String key, String value);
//
//	/**
//	 * 删除指定id的数据
//	 */
//	@Delete("DELETE FROM `" + TABLE_NAME + "` WHERE `" + ID + "` = #{id}")
//	void deleteById(int id);
//
//	/**
//	 * 添加新的博客信息
//	 */
//	@Insert("INSERT INTO `" + TABLE_NAME + "` " +
//			"(`admin_name`, `admin_pwd`, `admin_email`) " +
//			"VALUES (#{admin_name}, #{admin_pwd}, #{admin_email})")
//	void insert(Admin blog);
//
//	/**
//	 * 更新数据所有内容
//	 */
//	@Update("UPDATE `" + TABLE_NAME + "` SET " +
//			"`admin_name` = #{admin_name}, `admin_pwd` = #{admin_pwd}, `admin_email` = #{admin_email}" +
//			" WHERE `" + ID + "` = #{" + ID + "}")
//	int updateAll(Admin blog);
//
//	/**
//	 * 更新数据指定内容
//	 */
//	@Update("UPDATE `" + TABLE_NAME + "` SET " +
//			"`#{key}` = #{value}" +
//			" WHERE `" + ID + "` = #{id}")
//	int update(@Param("key") String key, @Param("value") Object value, @Param("id") int id);