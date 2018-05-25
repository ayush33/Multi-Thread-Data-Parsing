package Thread;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Consumer implements Runnable{
	protected BlockingQueue queue;
	JSONArray finalJson;

	public Consumer(BlockingQueue queue, JSONArray finalJson) {
		this.queue = queue;
		this.finalJson = finalJson;
	}

	public void run() {
		JSONArray json = new JSONArray();
		JSONObject j = null;

		while(true){
			try {
				j = (JSONObject) queue.poll(3, TimeUnit.SECONDS); //Getting  Values From Blocking Queue
				if(j != null){
					json.put(j);
				} 
				else
					break;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		

		synchronized(finalJson){
			for(int i=0; i<json.length(); i++){
				try {
					finalJson.put(json.get(i));
				}
				catch (JSONException e) {
					e.printStackTrace();
				}	
			}
		}
	}
}