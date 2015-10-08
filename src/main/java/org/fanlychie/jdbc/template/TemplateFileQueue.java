package org.fanlychie.jdbc.template;

import java.io.File;
import java.util.LinkedList;

/**
 * 模板文件队列
 * 
 * @author fanlychie
 */
public class TemplateFileQueue {

	// 全局私有链表
	private static LinkedList<Entry> entries = new LinkedList<Entry>();

	/**
	 * 添加条目到队列
	 * 
	 * @param entry
	 *            Entry
	 */
	public static void push(Entry entry) {
		entries.add(entry);
	}
	
	/**
	 * 条目出队
	 * 
	 * @return Entry
	 */
	public static Entry pop() {
		return entries.pop();
	}
	
	/**
	 * 队列中是否还有条目
	 * 
	 * @return
	 */
	public static boolean hasNext() {
		return entries.size() > 0;
	}
	
	public static class Entry {

		private File file;
		
		private String content;

		public Entry(String content, File file) {
			this.file = file;
			this.content = content;
		}

		public File getFile() {
			return file;
		}

		public String getContent() {
			return content;
		}

	}

}