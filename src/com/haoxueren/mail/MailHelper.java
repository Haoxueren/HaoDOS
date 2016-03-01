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

/** JavaMail�İ����ࣻ */
public class MailHelper
{
	public static void main(String[] args) throws Exception
	{
		MailHelper.getInstance().smtp("smtp.exmail.qq.com").from("haomingliang@letou360.com", "060e0f01")
				.to("751850011@qq.com").subject("@letou360.com").content("����").send(true);

	}

	private String smtp, email, password, receivers, subject, content;

	/** ��ȡ��ǰ����� */
	public static MailHelper getInstance()
	{
		return new MailHelper();
	}

	/** SMTP HOST�� */
	public MailHelper smtp(String smtp)
	{
		this.smtp = smtp;
		return this;
	}

	/** ��������Ϣ�� */
	public MailHelper from(String email, String password)
	{
		this.email = email;
		this.password = password;
		return this;
	}

	/** ����ж���ռ��ˣ��м���Ӣ�Ķ��ŷָ��� */
	public MailHelper to(String receivers)
	{
		this.receivers = receivers;
		return this;
	}

	/** �ʼ����⣻ */
	public MailHelper subject(String subject)
	{
		this.subject = subject;
		return this;
	}

	/** �ʼ����ģ� */
	public MailHelper content(String content)
	{
		this.content = content;
		return this;
	}

	/** �Ƿ��ӡ������־�� */
	public void send(boolean debug) throws Exception
	{
		int at = email.indexOf("@");
		String username = email.substring(0, at);
		String smtpHost = smtp == null ? "smtp." + email.substring(at + 1) : smtp;
		sendEmail(smtpHost, email, username, password, receivers, subject, content, debug);
	}

	/**
	 * �����ʼ���
	 * 
	 * @param receiver
	 *            �ʼ������ˣ�����ж�������ߣ��м��ö��Ÿ�����
	 * @param subject
	 *            �ʼ����⣻
	 * @param content
	 *            �ʼ����ģ�
	 * @param fromEmail
	 * @param username
	 */
	public void sendEmail(String smtpHost, String fromEmail, String username, String password, String receivers,
			String subject, String content, boolean debug) throws Exception
	{
		// �����ʼ��������Ĳ�����
		Properties properties = new Properties();
		properties.put("mail.host", smtpHost);
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		// �����ʼ�ʱ����SSL���ܣ�
		MailSSLSocketFactory sslFactory = new MailSSLSocketFactory();
		sslFactory.setTrustAllHosts(true);
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", sslFactory);
		// �����շ��ʼ���Session��
		Session session = Session.getInstance(properties);
		// ����־����ӡ�ʼ����͹��̵�״̬��־��
		session.setDebug(debug);
		// ��ȡTransport���������ʼ���������
		Transport transport = session.getTransport();
		transport.connect(smtpHost, username, password);
		// �����ʼ�����
		MimeMessage message = new MimeMessage(session);
		// �趨�����ˣ�
		message.setFrom(new InternetAddress(fromEmail));
		// �趨�ռ���(֧�ֶ���ռ��ˣ��м���Ӣ�Ķ��Ÿ���)��
		InternetAddress[] addresses = InternetAddress.parse(receivers);
		message.setRecipients(Message.RecipientType.TO, addresses);
		// �趨�ʼ����⣻
		message.setSubject(subject);
		// �趨�ʼ����ģ�
		message.setContent(content, "text/html;charset=UTF-8");
		// �����ʼ���
		transport.sendMessage(message, message.getAllRecipients());
		// �ر����ӣ�
		transport.close();
	}

}
