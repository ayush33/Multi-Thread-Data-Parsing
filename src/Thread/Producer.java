package Thread;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import org.json.JSONException;
import org.json.JSONObject;

public class Producer implements Runnable {
	ResultSet rs;
	ResultSetMetaData rsmd;
	int numColumns;
	protected BlockingQueue queue;

	public Producer(BlockingQueue queue, ResultSet r) throws SQLException {
		this.queue = queue;
		rs=r;
		rsmd = rs.getMetaData();
		numColumns = rsmd.getColumnCount();
	}

	@Override
	public void run() {
		try {
			while(rs.next())
			{
				JSONObject obj = new JSONObject();
				for (int i=1; i<=numColumns; i++) 
				{
					String columnName = rsmd.getColumnName(i);
					obj.put(columnName, rs.getObject(columnName));
				}
				queue.put(obj);
			}
		} 
		catch (InterruptedException | SQLException | JSONException e) 
		{
			System.out.println(e);
		}
	}



}
