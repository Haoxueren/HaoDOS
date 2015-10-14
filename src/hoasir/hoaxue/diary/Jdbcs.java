package hoasir.hoaxue.diary;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @class
 *        ����Jdbc�Ĺ����ࣻ
 * @author
 *         ��ѧ��
 */
public class Jdbcs
{
	// Ϊ���ݿ����ӵ��Ĵ�����������ã�
	private static String driverClassName = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	// �þ�̬��������properties�����ļ��е����ݣ�
	static
	{
		// ���������ļ���
		InputStream inStream = Jdbcs.class.getResourceAsStream("jdbcs.properties");
		// �½�Properties����
		Properties properties = new Properties();
		// �������ļ��е�����ӳ�䵽�����У�
		try
		{
			properties.load(inStream);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		// ��ȡ�������ݿ���Ĵ������
		driverClassName = properties.getProperty("driverClassName");
		url = properties.getProperty("url");
		username = properties.getProperty("username");
		password = properties.getProperty("password");

		// �÷��似�����������ֻ࣬��Ҫ����һ�Σ�
		try
		{
			Class.forName(driverClassName);

		} catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @method
	 *         ������������õ�����mysql���ݿ��Connection����
	 *         ��Ҫ����jdbcs.properties�������Ĵ������
	 * @return
	 *         ����mysql���ݿ��Connection����
	 */
	public static Connection getConnection()
	{
		// �õ��������ݿ��Connection����
		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		// �ѵõ���Connection���󷵻ظ������ߣ�
		return connection;
	}
}
