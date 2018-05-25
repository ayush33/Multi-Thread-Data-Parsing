package Thread;
import org.json.JSONArray;
import org.json.JSONException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import Connection.JDBCConnection;

public class Parser {
	public static void main(String[] args) throws SQLException, InterruptedException, JSONException 
	{
		long start = System.currentTimeMillis();

		int noOfConsumers = 15;
		BlockingQueue queue = new LinkedBlockingQueue();
		List<Thread> threads = new LinkedList<>();		

		Statement st = new JDBCConnection().confg();
		ResultSet rs = st.executeQuery("select * from details");
		Producer producer = new Producer(queue,rs);

		// Creating Producer Threads
		threads.add(new Thread(producer));
		Result res = new Result();

		// Creating Consumer Threads
		for(int i=0; i<noOfConsumers; i++){
			threads.add(new Thread(new Consumer(queue, res.getFinalJson())));
		}

		// Starting All Threads
		for(int i=0; i<threads.size(); i++){
			threads.get(i).start();
		}

		// Join all Threads
		for(int i=0; i<threads.size(); i++){
			threads.get(i).join();
		}

		// Printing Final Json
		JSONArray finalJson = res.getFinalJson();
		for(int i=0; i<finalJson.length(); i++){
			System.out.println(finalJson.get(i));
		}

		// Printing Total Time 
		long end = System.currentTimeMillis();
		System.out.println("Total Time Used: "+(end-start));
	}
}
