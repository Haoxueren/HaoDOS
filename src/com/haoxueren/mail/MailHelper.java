package com.haoxueren.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

/** JavaMail的帮助类； */
public class MailHelper
{
	public static void main(String[] args) throws Exception
	{
		MailHelper.getInstance().smtp("smtp.exmail.qq.com").from("haomingliang@letou360.com", "060e0f01")
				.to("751850011@qq.com").subject("@letou360.com").content("正文").send(true);

	}

	private String smtp, email, password, receivers, subject, content;

	/** 获取当前类对象； */
	public static MailHelper getInstance()
	{
		return new MailHelper();
	}

	/** SMTP HOST； */
	public MailHelper smtp(String smtp)
	{
		this.smtp = smtp;
		return this;
	}

	/** 发件人信息； */
	public MailHelper from(String email, String password)
	{
		this.email = email;
		this.password = password;
		return this;
	}

	/** 如果有多个收件人，中间用英文逗号分隔； */
	public MailHelper to(String receivers)
	{
		this.receivers = receivers;
		return this;
	}

	/** 邮件主题； */
	public MailHelper subject(String subject)
	{
		this.subject = subject;
		return this;
	}

	/** 邮件正文； */
	public MailHelper content(String content)
	{
		this.content = content;
		return this;
	}

	/** 是否打印发送日志； */
	public void send(boolean debug) throws Exception
	{
		int at = email.indexOf("@");
		String username = email.substring(0, at);
		String smtpHost = smtp == null ? "smtp." + email.substring(at + 1) : smtp;
		sendEmail(smtpHost, email, username, password, receivers, subject, content, debug);
	}

	/**
	 * 发送邮件；
	 * 
	 * @param receiver
	 *            邮件接收人，如果有多个接收者，中间用逗号隔开；
	 * @param subject
	 *            邮件标题；
	 * @param content
	 *            邮件正文；
	 * @param fromEmail
	 * @param username
	 */
	public void sendEmail(String smtpHost, String fromEmail, String username, String password, String receivers,
			String subject, String content, boolean debug) throws Exception
	{
		// 配置邮件服务器的参数；
		Properties properties = new Properties();
		properties.put("mail.host", smtpHost);
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		// 发送邮件时采用SSL加密；
		MailSSLSocketFactory sslFactory = new MailSSLSocketFactory();
		sslFactory.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sslFactory);
		// 创建收发邮件的Session；
		Session session = Session.getInstance(properties);
		// 打开日志：打印邮件发送过程的状态日志；
		session.setDebug(debug);
		// 获取Transport对象并连接邮件服务器；
		Transport transport = session.getTransport();
		transport.connect(smtpHost, username, password);
		// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
		// 设定发件人；
		message.setFrom(new InternetAddress(fromEmail));
		// 设定收件人(支持多个收件人，中间用英文逗号隔开)；
		InternetAddress[] addresses = InternetAddress.parse(receivers);
		message.setRecipients(Message.RecipientType.TO, addresses);
		// 设定邮件标题；
		message.setSubject(subject);
		// 设定邮件正文；
		message.setContent(content, "text/html;charset=UTF-8");
		// 发送邮件；
		transport.sendMessage(message, message.getAllRecipients());
		// 关闭连接；
		transport.close();
	}

}
