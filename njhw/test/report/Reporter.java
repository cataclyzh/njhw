package report;

import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class Reporter {

	public static void main(String[] args) throws Exception {
		Reporter reporter = new Reporter();
		reporter.sendReport();
	}
	
	public void sendReport() throws Exception {
		Report p = new Report();
		String s = p.getCurrentDayReport();
		System.out.println(s);
		mainSend(s);
	}
	
	public void mainSend(String msg){
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
		senderImpl.setHost("smtp.163.com");// 设定smtp邮件服务器
		SimpleMailMessage mailMessage = new SimpleMailMessage(); 
		mailMessage.setFrom("cataclyzh@163.com");
		mailMessage.setSubject("标题");
		mailMessage.setText(msg);
		senderImpl.setUsername("cataclyzh"); // 根据自己的情况,设置username
		senderImpl.setPassword("hero2111"); // 根据自己的情况, 设置password
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
		prop.put("mail.smtp.timeout", "25000");
		senderImpl.setJavaMailProperties(prop);
		// 发送邮件
		mailMessage.setTo("2716313167@qq.com");
		senderImpl.send(mailMessage);
		System.out.println("send OK by lkmgydx");
	}

}
