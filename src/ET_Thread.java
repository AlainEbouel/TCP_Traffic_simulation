import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ET_Thread extends Thread // Threadind des opérations de la couche transport ET
{
	ProcessusET ET;
	ProcessusER ER;
	String data;
	private File S_ecr;

	public ET_Thread(ProcessusET ET, ProcessusER ER, String data)
	{
		this.ET = ET;
		this.ER = ER;
		this.data = data;
		S_ecr = new File("S_ecr.txt");

	}

	@Override
	public void run()
	{
		int numConnexion = gestionTableConnexion();
		Primitive reponse = ER.DemandeDeConnexion(numConnexion, addrRandom(), 'B');

		if (reponse == Primitive.N_DISCONNECT_ind)
		{
			liberationDesRessources(numConnexion);
			try
			{
				ecrireDansS_ecr(reponse.toString());
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		} else
		{

		}

	}

	// Libération des ressouces
	private void liberationDesRessources(int numConnexion)
	{
		for (EntreeDeTable entree : ProcessusET.getTable())
		{
			if (entree.getNumeroDeConnexion() == numConnexion)
			{
				ProcessusET.getTable().remove(entree);
				break;
			}
		}
	}

	// Génération d'un numéro de connexion(Retourne un numero de connexion)
	private int gestionTableConnexion()
	{
		Random rand = new Random();
		boolean entreeLibre = false;
		int numConnexion = -1;

		while (!entreeLibre)
		{
			entreeLibre = true;
			numConnexion = rand.nextInt(254);
			for (EntreeDeTable entree : ProcessusET.getTable())
			{
				if (entree.getNumeroDeConnexion() == numConnexion)
				{
					entreeLibre = false;
					break;
				}
			}

		}

		ProcessusET.getTable().add(new EntreeDeTable(numConnexion, EtatDeConnexion.attenteDeConfirmation));
		return numConnexion;
	}

	// Génération aléatoire d'addresse source
	private int addrRandom()
	{
		Random rand = new Random();
		return rand.nextInt(249);
	}

	// Écriture dans S_ecr
	private void ecrireDansS_ecr(String reponse) throws IOException
	{
		FileOutputStream file = null;
		file = new FileOutputStream(S_ecr, true);
		file.write(reponse.concat("\n").getBytes());
		file.close();
	}
}
