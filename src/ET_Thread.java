import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class ET_Thread extends Thread // Threadind des opérations de la couche transport ET
{
	private ProcessusER ER;
	private String data;
	private File S_ecr;
	private int idConnexion;

	public ET_Thread(ProcessusER ER, String data, int idConnexion)
	{
		this.ER = ER;
		this.data = data;
		this.idConnexion = idConnexion;
		S_ecr = new File("S_ecr.txt");
	}

	@Override
	public void run()
	{
		IPaquet reponse = null;
		int addrSource = generationAddrSource();
		PaquetAcquittement resultEnvois;
		EtatDeConnexion etatConnexion = ProcessusET.getEntreeDeTable(idConnexion).getEtatDeConnexion();

		// Si la connexion n'est pas encore établie, tentative d'établissement de
		// connnexion
		if (etatConnexion == EtatDeConnexion.attenteDeConfirmation)
		{
			try
			{
				// Demande de connexion
				reponse = ER.DemandeDeConnexion(idConnexion, addrSource, 'B');

				// ecriture dans S_ecr du resultat de la demande de connexion
				if (reponse != null)
					ecrireDansS_ecr(reponse.toString());

			} catch (IOException e1)
			{
				e1.printStackTrace();
			}

			// En cas de refus de connexion
			if (reponse != null && reponse.getPrimitive() == Primitive.N_DISCONNECT_ind)
				liberationDesRessources(idConnexion);

			// En cas de connexion acceptée
			else if (reponse != null && reponse.getPrimitive() == Primitive.N_CONNECT_resp)
			{
				ProcessusET.getEntreeDeTable(idConnexion).setEtatDeConnexion(EtatDeConnexion.connexionEtablie);

				try
				{
					resultEnvois = ER.preparationPaquetDeDonnees(data, Primitive.N_DATA_req, idConnexion,
							addrSource);

				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		// Si la connexion est établie on directement tranferts des données
		else
		{
			try
			{
				ER.preparationPaquetDeDonnees(data, Primitive.N_DATA_req, idConnexion, addrSource);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// Libération des ressouces
	private void liberationDesRessources(int idConnexion)
	{
		for (EntreeDeTable entree : ProcessusET.getTable())
		{
			if (entree.getIdConnexion() == idConnexion)
			{
				ProcessusET.getTable().remove(entree);
				data = null;
				break;
			}
		}
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

}
