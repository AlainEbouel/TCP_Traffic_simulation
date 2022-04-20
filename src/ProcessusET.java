import java.util.ArrayList;

public class ProcessusET
{
	private static ArrayList<EntreeDeTable> table = new ArrayList<>();
	private static int ps = 0, pr = 0;

	public void traitement(ProcessusER ER, String data)
	{
		ET_Thread Et_thread = new ET_Thread(this, ER, data);
		Et_thread.run();

	}

	public static int getPs()
	{
		return ps;
	}

	public static int getPr()
	{
		return pr;
	}

	public void IncrementePs()
	{
		if (ps == 7)
			ps = 0;
		else
			ProcessusET.ps += 1;
	}

	public static ArrayList<EntreeDeTable> getTable()
	{
		return table;
	}

}
