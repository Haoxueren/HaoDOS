package hoasir.hoaxue.diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Diary
{
	public static int addDiary(String diary)
	{
		// 从数据库连接池获取数据库连接对象；
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = Jdbcs.getConnection();
			// 准备sql命令模板；
			String sql = "insert into diary (diary) values(?)";
			// 准备sql声明对象；
			statement = connection.prepareStatement(sql);
			// 为sql语句赋值；
			statement.setString(1, diary);
			// 执行sql语句；
			return statement.executeUpdate();
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
		} finally
		{
			// 把连接归还给数据库连接池；
			try
			{
				if (statement != null)
				{
					statement.close();
				}
				if (connection != null)
				{
					connection.close();
				}
			} catch (SQLException e)
			{
				System.out.println(e.getMessage());
			}
		}
		return 0;
	}
}
