/**
* <p>文件名: Sendemail.java</p>
* <p>描述：发送邮件</p>
* <p>版权: Copyright (c) 2010 Beijing Cosmosource Co. Ltd.</p>
* <p>公司: Cosmosource Beijing Office</p>
* <p>All right reserved.</p>
* @创建时间： 2010-12-23 下午04:29:03 
* @作者：sjy
* @版本： V1.0
* <p>类修改者		 修改日期			修改说明   </p>   
* 
*/
package com.cosmosource.base.util;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
/**
 * @类描述: 邮件发送 ---- 一期直接移植代码
 * @作者： sjy
 */
public class Sendemail {
	/**
	 * 李荣春 2010年11月28日 15:58:00 增加，发送html内容的邮件
	 * @param smtp
	 * @param from
	 * @param fromuser
	 * @param frompass
	 * @param to
	 * @param cc
	 * @param bcc
	 * @param subject
	 * @param body
	 * @throws java.lang.Exception
	 */
	public static void sendHTML(String smtp, // SMTP主机地址 
		String from, // 发信人邮件地址 
		String fromuser, //发信人登录smtp的用户名
		String frompass, //登录smtp的密码
		String to, // 收信人 
		String cc, // 抄送人 
		String bcc, // 暗送人 
		String subject, // 主题 
		String body // 内容 
		) throws java.lang.Exception {
		// 变量声明
		java.util.Properties props; // 系统属性
		javax.mail.Session mailSession; // 邮件会话对象
		javax.mail.internet.MimeMessage mimeMsg; // MIME邮件对象

		// 设置系统属性
		props = java.lang.System.getProperties(); // 获得系统属性对象
		props.put("mail.smtp.host", smtp); // 设置SMTP主机
		props.put("mail.smtp.auth", "true");       
		props.put("mail.transport.protocol", "smtp");   

		// 必须通过一个Authenticator的子类SmtpAuth的对象auth来进行用户名和密码验证       
		SmtpAuth auth = new SmtpAuth();       
		auth.setUserinfo(fromuser, frompass);      
		
		// 获得邮件会话对象
		mailSession = Session.getInstance(props, auth);
		mailSession.setPasswordAuthentication(new URLName(smtp), auth.getPasswordAuthentication());    

		// 创建MIME邮件对象
		mimeMsg = new javax.mail.internet.MimeMessage(mailSession);

		// 设置发信人
		mimeMsg.setFrom(new javax.mail.internet.InternetAddress(from));
		
		// 设置收信人
		if (to != null) {
			mimeMsg.setRecipients(javax.mail.Message.RecipientType.TO,
					javax.mail.internet.InternetAddress.parse(to));
		}

		// 设置抄送人
		if (cc != null) {
			mimeMsg.setRecipients(javax.mail.Message.RecipientType.CC,
					javax.mail.internet.InternetAddress.parse(cc));
		}

		// 设置暗送人
		if (bcc != null) {
			mimeMsg.setRecipients(javax.mail.Message.RecipientType.BCC,
					javax.mail.internet.InternetAddress.parse(bcc));
		}

		// 设置邮件主题
		mimeMsg.setSubject(subject, "UTF-8");

		// 设置邮件内容
		Multipart mp = new MimeMultipart("related");// related意味着可以发送html格式的邮件    
        BodyPart bodyPart = new MimeBodyPart();// 正文    
        bodyPart.setDataHandler(new DataHandler(body, "text/html;charset=UTF-8"));// 网页格式    
        mp.addBodyPart(bodyPart);
        mimeMsg.setContent(mp);// 设置邮件内容对象
        
		// 发送邮件
		javax.mail.Transport.send(mimeMsg);
	} 
}
