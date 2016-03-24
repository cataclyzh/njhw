package com.cosmosource.base.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
/**
* @类描述: 发送邮件
* @作者： WXJ
*/
@SuppressWarnings("rawtypes")
public class MailUtil {

	private PopupAuthenticator popAuthenticator;
	private MimeMessage message;
	private Session session;
	private boolean hasSended;
	private Properties props;

	private List messages;
	private Transport transport;
	private boolean hasAttachment;
	private BodyPart messageBodyPart;
	private Multipart multipart;

	public static void main(String[] args) {
//		MailUtil aa = new MailUtil();
//		aa.setProps("220.194.46.71");
//		aa.setSessionInstance();
//		aa.setAuthentication("service", "123456");
//		aa.setConnectArgs("220.194.46.71", "service", "123456", "service@Cosmosource.com", "lightfever@163.com");
//		aa.setSubject("titleXXXXXXX");
//		aa.setMessage("aacdd");
//		aa.setFromAndToAddress("service@Cosmosource.com", "lightfever@163.com");
//		aa.sendEmail();
//		System.out.println("VVV");
	}
	
	public MailUtil() {
		popAuthenticator = this.new PopupAuthenticator();
	}

	/**
	 * 设置发送主机参数
	 * @param hostName 发送服务器名
	 */

	private void setProps(String hostName) {
		props = new Properties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", hostName);
		props.put("mail.smtp.auth", "true");
	}

	/**
	 * 设置用户协议
	 */

	private void setAuthentication(String user, String password) {
		popAuthenticator.getPasswordAuthentication(user, password);
	}

	private void setSessionInstance() {
		session = Session.getInstance(props, popAuthenticator);
	}

	/**
	 * 设置传输协议
	 * @param hostName
	 * @param user
	 * @param password
	 */
	private void setTransportProtocol(String hostName, String user, String password) {
		try {
			transport = session.getTransport("smtp");
			transport.connect(hostName, user, password);
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置发送和接受的地址
	 */

	private void setFromAndToAddress(String from, String to) {
		try {
			InternetAddress fromAddress = new InternetAddress(from);
			InternetAddress toAddress;
			message.setFrom(fromAddress);
			// changed by xiangmeng start
			String[] toList = to.split(";");
			for (int i = 0; i < toList.length; i++) {
				toAddress = new InternetAddress(toList[i]);
				message.addRecipient(Message.RecipientType.TO, toAddress);
			}
			// changed by xiangmeng end

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 设置服务器连接参数
	 */

	public void setConnectArgs(String hostName, String user, String password, String from, String to) {
		setProps(hostName);
		setAuthentication(user, password);
		setSessionInstance();
		setTransportProtocol(hostName, user, password);
		if (message == null) {
			message = new MimeMessage(session);
		}
		setFromAndToAddress(from, to);
	}

	/**
	 * 设置发送附件
	 */

	public void addAttachment(String filename, String filepath) {
		hasAttachment = true;
		messageBodyPart = new MimeBodyPart();
		multipart = new MimeMultipart();
		File file = new File(filepath);
		file.renameTo(file);
		DataSource source = new FileDataSource(file);
		try {
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(MimeUtility.encodeText(filename, "gb2312", "B"));
			multipart.addBodyPart(messageBodyPart);// Put parts in message

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置抄送地址
	 * @param Address
	 */
	public void setCCAddress(String Address) {
		try {
			InternetAddress ccAddress = new InternetAddress(Address);
			message.addRecipient(Message.RecipientType.CC, ccAddress);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setCCAddress(String[] Addresses) {
		for (int i = 0; i < Addresses.length; i++) {
			setCCAddress(Addresses[i]);
		}
	}

	/**
	 * 设置暗送地址
	 * @param Address
	 */
	public void setBCCAddress(String Address) {
		try {
			InternetAddress bccAddress = new InternetAddress(Address);
			message.addRecipient(Message.RecipientType.BCC, bccAddress);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void setBCCAddress(String[] Addresses) {
		for (int i = 0; i < Addresses.length; i++) {
			setBCCAddress(Addresses[i]);
		}
	}

	/**
	 * 添加消息
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public void setMessage(String msg) {
		if (messages == null) {
			messages = new ArrayList();
		}
		messages.add(msg);
	}

	/**
	 * 设置发送的消息数组
	 * @param msgs
	 */
	public void setMessages(String[] msgs) {

		for (int i = 0; i > msgs.length; i++) {
			setMessage(msgs[i]);
		}

	}

	/**
	 * 设置主题
	 * @param subject
	 */
	public void setSubject(String subject) {
		try {
			message.setSubject(subject);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	private void setMsgs() {
		try {
			if (!hasAttachment) {
				for (int i = 0; i < messages.size(); i++) {
					message.setText((String) messages.get(0));
				}
			} else {
				for (int i = 0; i < messages.size(); i++) {
					messageBodyPart = new MimeBodyPart();
					messageBodyPart.setText((String) messages.get(0));
					multipart.addBodyPart(messageBodyPart);
				}
				message.setContent(multipart);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送Email
	 */
	public void sendEmail() {
        
		if (!hasSended) {
			hasSended = true;
			setMsgs();
			try {
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 用户POP密碼协议内部类
	 */

	private class PopupAuthenticator extends Authenticator {
		public PopupAuthenticator() {
			super();
		}

		protected PasswordAuthentication getPasswordAuthentication(String user, String pass) {
			return new PasswordAuthentication(user, pass);
		}

	}
}
