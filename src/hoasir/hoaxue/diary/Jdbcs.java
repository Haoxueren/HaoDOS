package hoasir.hoaxue.diary;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @class
 *        这是Jdbc的工具类；
 * @author
 *         好学人
 */
public class Jdbcs
{
	// 为数据库连接的四大参数创建引用；
	private static String driverClassName = null;
	private static String url = null;
	private static String username = null;
	private static String password = null;
	// 用静态代码块加载properties配置文件中的数据；
	static
	{
		// 加载配置文件；
		InputStream inStream = Jdbcs.class.getResourceAsStream("jdbcs.properties");
		// 新建Properties对象；
		Properties properties = new Properties();
		// 将配置文件中的数据映射到集合中；
		try
		{
			properties.load(inStream);
		} catch (IOException e)
		{
			throw new RuntimeException(e);
		}
		// 获取连接数据库的四大参数；
		driverClassName = properties.getProperty("driverClassName");
		url = properties.getProperty("url");
		username = properties.getProperty("username");
		password = properties.getProperty("password");

		// 用反射技术加载驱动类，只需要加载一次；
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
	 *         这个方法用来得到连接mysql数据库的Connection对象；
	 *         需要先在jdbcs.properties中配置四大参数；
	 * @return
	 *         连接mysql数据库的Connection对象；
	 */
	public static Connection getConnection()
	{
		// 得到连接数据库的Connection对象；
		Connection connection = null;
		try
		{
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		// 把得到的Connection对象返回给调用者；
		return connection;
	}
}
