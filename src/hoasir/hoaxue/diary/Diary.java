package hoasir.hoaxue.diary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Diary
{
	public static int addDiary(String diary)
	{
		// �����ݿ����ӳػ�ȡ���ݿ����Ӷ���
		Connection connection = null;
		PreparedStatement statement = null;
		try
		{
			connection = Jdbcs.getConnection();
			// ׼��sql����ģ�壻
			String sql = "insert into diary (diary) values(?)";
			// ׼��sql��������
			statement = connection.prepareStatement(sql);
			// Ϊsql��丳ֵ��
			statement.setString(1, diary);
			// ִ��sql��䣻
			return statement.executeUpdate();
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
		} finally
		{
			// �����ӹ黹�����ݿ����ӳأ�
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
