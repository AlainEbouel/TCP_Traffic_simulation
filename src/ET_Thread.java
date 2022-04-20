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
		Primitive reponse = null;
		int addrSource = generationAddrSource();
		try
		{
			reponse = ER.DemandeDeConnexion(numConnexion, addrSource, 'B');
		} catch (IOException e1)
		{
			e1.printStackTrace();
		}

		if (reponse == Primitive.N_DISCONNECT_ind)
		{
			liberationDesRessources(numConnexion);
			try
			{// ecriture dans S_ecr du resultat de la demande de connexion
				ecrireDansS_ecr(reponse.toString());
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		} else
		{
			getEntreeDeTable(numConnexion).setEtatDeConnexion(EtatDeConnexion.connexionEtablie);

			try
			{
				// ecriture dans S_ecr du resultat de l'acquittement, s'il y a lieu
				PaquetAcquittement acquittement = ER.preparationPaquetDeDonnees(data, Primitive.N_DATA_req,
						numConnexion, addrSource);
				if (acquittement != null)
					ecrireDansS_ecr(acquittement.toString());
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		}

	}

	// Libération des ressouces
	private void liberationDesRessources(int numConnexion)
	{
		for (EntreeDeTable entree : ProcessusET.getTable())
		{
			if (entree.getIdentifiantExtremiteConnexion() == numConnexion)
			{
				ProcessusET.getTable().remove(entree);
				data = null;
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
				if (entree.getIdentifiantExtremiteConnexion() == numConnexion)
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
	private int generationAddrSource()
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

	// Retroune une entree de table en fonction du numero de connexion
	private EntreeDeTable getEntreeDeTable(int numConnexion)
	{
		for (EntreeDeTable entree : ProcessusET.getTable())
			if (entree.getIdentifiantExtremiteConnexion() == numConnexion)
				return entree;
		return null;
	}

}
