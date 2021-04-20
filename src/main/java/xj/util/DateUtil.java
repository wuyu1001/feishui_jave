package xj.util;

import java.text.SimpleDateFormat; //导入需要的SimpleDateFormat包
import java.util.Date; //导入需要的Date包

public class DateUtil { // 定义的MyDateDemo类
	private SimpleDateFormat sd = null; // 声明SimpleDateFormat对象sd

	public String gettimestamp() { // 定义getDate01方法
		this.sd = new SimpleDateFormat("yyyyMMddHHmmssSSS"); 
		return this.sd.format(new Date()); // 将当前日期进行格式化操作
	}
	
	public String getyyyyMMddHHmmss() { // 定义getDate01方法
		this.sd = new SimpleDateFormat("yyyyMMddHHmmss"); 
		return this.sd.format(new Date()); // 将当前日期进行格式化操作
	}
	
	public String getyyyyMMdd() { // 定义getDate01方法
		this.sd = new SimpleDateFormat("yyyyMMdd");
		return this.sd.format(new Date()); // 将当前日期进行格式化操作
	}
	
	public String getHHmmss() { // 定义getDate01方法
		this.sd = new SimpleDateFormat("HHmmss");
		return this.sd.format(new Date()); // 将当前日期进行格式化操作
	}
	
	public String getchinese() { // 定义getDate01方法
		this.sd = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss"); 
		return this.sd.format(new Date()); // 将当前日期进行格式化操作
	}
	
	public String getDate01() { // 定义getDate01方法
		this.sd = new SimpleDateFormat("yyyy-MM-dd HH:mm;ss.sss"); // 得到一个"yyyy-MM-dd
																	// HH:mm;ss.sss"格式日期
		return this.sd.format(new Date()); // 将当前日期进行格式化操作
	}

	public String getDate02() { // 定义getDate02方法
		this.sd = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒sss毫秒"); // 得到一个"yyyy年MM月dd日
																		// HH时mm分ss秒sss毫秒"格式日期
		return this.sd.format(new Date()); // 将当前日期进行格式化操作
	}

	public String getDate03() {// 定义getDate03方法
		this.sd = new SimpleDateFormat("yyyyMMddHHmmsssss");// 得到一个"yyyyMMddHHmmsssss"格式日期(也就是时间戳)
		return this.sd.format(new Date());// 将当前日期进行格式化操作
	}
	
	public static void main(String[] args) { // 主方法
		DateUtil dd = new DateUtil(); // 声明dd对象，并实例化
//		System.out.println("默认日期格式: " + new Date());// 分别调用方法输入不同格式的日期
//		System.out.println("英文日期格式: " + dd.getDate01());
//		System.out.println("中文日期格式: " + dd.getDate02());
//		System.out.println("时间戳: " + dd.getDate03());
		System.out.println("时间戳: " + dd.gettimestamp());
		System.out.println("时间戳: " + dd.getyyyyMMddHHmmss());
		System.out.println("时间戳: " + dd.getyyyyMMdd());
		System.out.println("时间戳: " + dd.getHHmmss());
		System.out.println("时间戳: " + dd.getchinese());

	}

}
