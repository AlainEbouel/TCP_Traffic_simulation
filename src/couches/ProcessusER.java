package couches;
import java.io.IOException;
import java.util.ArrayList;

import enumurations.EtatDeConnexion;
import enumurations.Primitive;
import interfaces.IPaquet;
import paquets.PaquetAcquittement;
import paquets.PaquetAppel;
import paquets.PaquetDeDonnees;
import paquets.PaquetDemandeLib;
import paquets.PaquetIndLiberation;
import services.SauvegardeInfos;

public class ProcessusER // Couche Reseau
{
	private ArrayList<SauvegardeInfos> sauvegardeInfos;
	private static int count = 0;// génères les numéros de connexions

	public ProcessusER()
	{
		sauvegardeInfos = new ArrayList<SauvegardeInfos>();
	}

	// Demande de connexion
	public IPaquet DemandeDeConnexion(int numIdentifiant, int addrSource, int addrDestination) throws IOException
	{
		int numConnexion;

		if (estMultipleDe27(addrSource))
		{
			return new PaquetIndLiberation(-1, addrSource, addrDestination, 0b00000010);
		} else
		{
			numConnexion = count++;
			PaquetAppel paquetAppel = preparationPaquetAppel(addrSource, addrDestination, numConnexion);

			// sauvegarde info necessaire à cette connexion
			sauvegardeInfos.add(new SauvegardeInfos(numConnexion, addrSource, addrDestination,
					EtatDeConnexion.attenteDeConfirmation, numIdentifiant));

			return LiaisonDeDonnees.appel(paquetAppel);// Transmission du paquet d'appel a la liaison
											// de données
		}
	}

	// Préparation de paquet d'appel
	private PaquetAppel preparationPaquetAppel(int addrSource, int addrDestination, int numeroConnexion)
	{
		return new PaquetAppel(numeroConnexion, addrSource, addrDestination);
	}

	// Preparation de paquets de données
	public PaquetAcquittement preparationPaquetDeDonnees(String data, Primitive nDataReq, int numConnexion,
			int addrSource) throws IOException
	{
		PaquetAcquittement resultEnvois;

		if (data.length() > 128)
			return traitementGrosPaquet(numConnexion, data, addrSource);
		else
		{
			String typePaquet = formatTypeDePaquet(0);

			return resultEnvois = LiaisonDeDonnees
					.envoisPaquetDeDonnees(new PaquetDeDonnees(numConnexion, typePaquet, data), addrSource);
		}

	}

	// Traitement des grosses données(>128o)
	private PaquetAcquittement traitementGrosPaquet(int numConnexion, String data, int addrSource) throws IOException
	{
		byte[] dataBytes = data.getBytes();
		int nbrPaquet = dataBytes.length % 128 == 0 ? dataBytes.length / 128 : dataBytes.length / 128 + 1;
		int index = 0;
		PaquetAcquittement paquetAcq;
		while (nbrPaquet > 1)
		{
			String typePaquet = formatTypeDePaquet(1);
			String dataPartiel = data.substring(index, index + 127);
			paquetAcq = LiaisonDeDonnees.envoisPaquetDeDonnees(
					new PaquetDeDonnees(numConnexion, typePaquet, dataPartiel), addrSource);
			index += 128;
			nbrPaquet--;
		}

		String typePaquet = formatTypeDePaquet(0);
		String dataPartiel = data.substring(index, dataBytes.length);
		paquetAcq = LiaisonDeDonnees
				.envoisPaquetDeDonnees(new PaquetDeDonnees(numConnexion, typePaquet, dataPartiel), addrSource);

		return null;
	}

	// Demande de Libération de connexion
	public void liberation(int idConnexion, Primitive nDisconnectReq) throws IOException
	{
		int addrSource = getAddrSource(idConnexion);
		int addrDest = getAdrrDest(idConnexion);
		int numConnexion = getNumConnexion(idConnexion);
		PaquetDemandeLib paquetLib = new PaquetDemandeLib(numConnexion, addrSource, addrDest);
		LiaisonDeDonnees.envoisPaquetLiberation(paquetLib);
	}

	// Formatage du champ type de paquet
	private String formatTypeDePaquet(int bitM)
	{
		String ps = ProcessusET.getPs();
		String pr = ProcessusET.getPr();
		return pr + bitM + ps + 0;
	}

	private boolean estMultipleDe27(int numeroDemande)
	{
		if (numeroDemande != 0)
			return numeroDemande % 15 == 0;
		else
			return false;
	}

	// Accesseur de numéro d'indentifiant d'extrémité de connexion
	private int getNumConnexion(int idConnexion)
	{
		for (SauvegardeInfos save : sauvegardeInfos)
			if (save.getNumeroDemande() == idConnexion)
				return save.getNumeroConnexion();
		return -1;
	}

	// Accesseur d'addresse source
	private int getAddrSource(int idConnexion)
	{
		for (SauvegardeInfos save : sauvegardeInfos)
			if (save.getNumeroDemande() == idConnexion)
				return save.getAddrSource();
		return -1;
	}

	// Accesseur d'addresse destination
	private int getAdrrDest(int idConnexion)
	{
		for (SauvegardeInfos save : sauvegardeInfos)
			if (save.getNumeroDemande() == idConnexion)
				return save.getAddrDestination();
		return -1;
	}

}
