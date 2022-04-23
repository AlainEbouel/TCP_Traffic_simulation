import java.io.IOException;
import java.util.ArrayList;

public class ProcessusET
{
	private static ArrayList<EntreeDeTable> table = new ArrayList<>();
	private static int ps = -1, pr = -1;
	private static int countIndentifiant = 0;

	public void traitement(ProcessusER ER, String data) throws IOException
	{
		EntreeDeTable entree;
		int idApplication;
		int idConnexion;

		if ((entree = getEntreeDeTable(data.substring(0, 4))) != null)
		{
			idApplication = entree.getNumApplication();
			idConnexion = entree.getIdConnexion();

		} else
		{
			idApplication = Integer.parseInt(data.substring(0, 4));
			idConnexion = countIndentifiant++;

			table.add(entree = new EntreeDeTable(idConnexion, EtatDeConnexion.attenteDeConfirmation,
					idApplication));
			System.out.println("etat = " + entree.getEtatDeConnexion());
		}

		ET_Thread Et_thread;

		if (data.charAt(4) == '0')
		{
			Et_thread = new ET_Thread(ER, data.substring(5), idConnexion);
			Et_thread.run();
		} else
			ER.liberation(idConnexion, Primitive.N_DISCONNECT_req);

	}

	public static String getPs()
	{
		return String.format("%3s", Integer.toBinaryString(ps)).replace(' ', '0');
	}

	public static String getPr()
	{
		incrementePr();
		return String.format("%3s", Integer.toBinaryString(pr)).replace(' ', '0');
	}

	private static void incrementePr()
	{
		if (pr == 7)
			pr = 0;
		else
			ProcessusET.pr += 1;
	}

	public static ArrayList<EntreeDeTable> getTable()
	{
		return table;
	}

	public static EntreeDeTable getEntreeDeTable(int numConnexion)
	{
		for (EntreeDeTable entree : ProcessusET.getTable())
			if (entree.getIdConnexion() == numConnexion)
				return entree;
		return null;
	}

	public static EntreeDeTable getEntreeDeTable(String idApplication)
	{
		for (EntreeDeTable entree : ProcessusET.getTable())
			if (entree.getNumApplication() == Integer.parseInt(idApplication))
				return entree;
		return null;
	}

	public void liberation()
	{

	}

}
