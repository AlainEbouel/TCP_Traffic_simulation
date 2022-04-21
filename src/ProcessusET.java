import java.util.ArrayList;

public class ProcessusET
{
	private static ArrayList<EntreeDeTable> table = new ArrayList<>();
	private static int ps = 0, pr = 0;
	private static int countIndentifiant = 0;

	public void traitement(ProcessusER ER, String data)
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

		ET_Thread Et_thread = new ET_Thread(ER, data.substring(4), idConnexion);

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

}
