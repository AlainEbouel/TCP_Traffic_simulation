import java.util.ArrayList;

public class ProcessusET
{
	private static ArrayList<EntreeDeTable> table = new ArrayList<>();

	public void traitement(ProcessusER ER, String data)
	{
		ET_Thread Et_thread = new ET_Thread(this, ER, data);
		Et_thread.run();
	}

	public static ArrayList<EntreeDeTable> getTable()
	{
		return table;
	}

}
