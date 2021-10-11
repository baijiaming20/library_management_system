package pers.util;

import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	private FileUtil() {}

	/*
		文件操作分为字符流和字节流
		字符流分为输入和输出（输入和输出是针对程序而言）
		输入分为读取操作，读取操作分为单文件操作和多文件操作
		单文件操作分为多行读取、全部读取、，多文件操作功能包括获取多个文件内容到list之中
	*/

	/**
	 * 获取路径文件指定参数行数内容
	 * 以0为开始行号，相同开始和结束行号返回该行号内容
	 * @param path 文件路径
	 * @param start 开始行号
	 * @param end 结束行号
	 * @return 路径文件内容
	 */
	public static String readLines(String path, int start, int end) {

		if (end < start || start < 0) {
			return "";
		}

		if (!new File(path).exists()) {
			createFile(path);
			return "";
		}

		StringBuilder builder = new StringBuilder();

		try {
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));

			for (int i = 0; i < start; i++) {
				reader.readLine();
			}

			for (int i = 0, len = end - start + 1; i < len; i++) {

				String line = reader.readLine();

				if (line == null) {
					break;
				}
				builder.append(line).append(System.lineSeparator());
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	/**
	 * 获取路径文件从0行到指定参数的内容
	 * @param path 文件路径
	 * @param num 获取内容的行数
	 * @return 文件内容
	 */
	public static String readLines(String path, int num) {
		return readLines(path, 0, num);
	}

	/**
	 * 获取文件内容的行数
	 * 空行数不包括在内
	 * @param path 文件路径
	 * @return 文件内容行数
	 */
	public static long getFileLineNum(String path) {

		long num = 0;

		try {
			num = Files.lines(Paths.get(path)).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return num;
	}

	/**
	 * 获取路径文件所有内容（单个文件全部读取）
	 * @param path 内容文件路径
	 * @return 路径文件内容
	 */
	public static String readAll(String path) {

		StringBuilder builder = new StringBuilder();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line).append(System.lineSeparator());
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return builder.toString();
	}

	/**
	 * 获取多个文件的内容
	 * @param paths 要获取文件的文件路径
	 * @return 多个文件内容组成的list
	 */
	public static List<String> readFiles(String[] paths) {

		List<String> list = new ArrayList<>();

		for (String path : paths) {
			list.add(readAll(path));
		}
		return list;
	}

	/**
	 * 创建多级文件夹
	 * @param path 文件夹路径
	 */
	public static void createDirectories(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 创建文件
	 * 没有父目录则创建父目录
	 * @param path 文件路径
	 * @param isDelete 是否删除原来的文件
	 */
	public static boolean createFile(String path, boolean isDelete) {
		File file = new File(path);
		File fileParent = file.getParentFile();
		boolean flag = false;

		if (!fileParent.exists()) {
			fileParent.mkdirs();
		}

		try {
			if (isDelete) {
				file.delete();
			}
			flag = file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return flag;
	}

	/**
	 * 创建文件
	 * @param path 文件所在路径
	 * @param name 文件名
	 * @param isDelete 是否删除原来的文件
	 */
	public static boolean createFile(String path, String name, boolean isDelete) {
		return createFile(path + File.separatorChar + name, isDelete);
	}

	/**
	 * 创建文件
	 * @param path 文件路径
	 * @return 是否创建文件
	 */
	public static boolean createFile(String path) {
		return createFile(path, false);
	}

	/**
	 * 创建文件
	 * @param path 文件路径
	 * @param name 文件名
	 * @return 是否创建文件
	 */
	public static boolean createFile(String path, String name) {
		return createFile(path, name, false);
	}

	/**
	 * 删除文件
	 * @param path 文件路径
	 */
	public static boolean deleteFiles(String path) {
		boolean flag = false;
		File file = new File(path);

		if (file.isDirectory()) {

			File[] files = file.listFiles();

			if (files == null) {
				return flag;
			}

			for (File f : files) {
				deleteFiles(f.getPath());
			}

			flag = file.delete();
			return flag;
		}

		flag = file.delete();

		return flag;
	}

	/**
	 * 删除文件
	 * @param paths 文件路径
	 */
	public static void deleteFiles(String[] paths) {
		for (String path : paths) {
			deleteFiles(path);
		}
	}

	/**
	 * 删除文件
	 * @param paths 文件路径
	 */
	public static void deleteFiles(List<String> paths) {
		for (String path : paths) {
			deleteFiles(path);
		}
	}

	/**
	 * 写文件
	 * @param path 文件路径
	 * @param text 文件内容
	 * @param isAppend 是否追加
	 */
	public static void writeFile(String path, String text, boolean isAppend) {
		createFile(path);
		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(path, isAppend), StandardCharsets.UTF_8));

			writer.write(text);

			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 追加写文件
	 * @param path 文件路径
	 * @param text 添加内容
	 */
	public static void writeFile(String path, String text) {
		writeFile(path, text, true);
	}

	// 其他


	/**
	 * 将MultipartFile对象写入到本地文件
	 */
	public static void approvalFile(MultipartFile file, String path) {

		createDirectories(path);

		OutputStream os = null;
		InputStream inputStream = null;
		String fileName;

		try {
			inputStream = file.getInputStream();
			fileName = file.getOriginalFilename();

			byte[] bs = new byte[10240];

			createFile(path);

			File tempFile = new File(path);

			os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);

			int len;
			while ((len = inputStream.read(bs)) != -1)
				os.write(bs, 0, len);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * File转byte[]
	 * 用于在网页显示图片
	 */
	public static byte[] fileToByte(File img) {
		byte[] bytes = null;
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			BufferedImage bi;
			bi = ImageIO.read(img);
			ImageIO.write(bi, "png", baos);
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}
}




