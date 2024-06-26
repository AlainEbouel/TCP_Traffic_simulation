package couches;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import interfaces.IPaquet;
import paquets.PaquetAcquittement;
import paquets.PaquetAppel;
import paquets.PaquetComEtablie;
import paquets.PaquetDeDonnees;
import paquets.PaquetIndLiberation;

public class LiaisonDeDonnees // Couche liaison de donn�es
{
	private static File L_ecr = new File("fichiers/L_ecr.txt");
	private static File L_lec = new File("fichiers/L_lec.txt");
	private static boolean grosPaquet;
	private static String grosData = "";

	// R�ception des paquets d'appel. La liason de donn�es �crira dans L_ecr
	public static IPaquet appel(PaquetAppel paqueAppel) throws IOException
	{
		String ecriture = paqueAppel.toString();

		ecrireDansFichiers(L_ecr, ecriture);

		return reponseDemandeDeConnexion(paqueAppel.getAddresseSource(), paqueAppel);
	}

	// Reception des demande de connexion
	private static IPaquet reponseDemandeDeConnexion(int nbr, PaquetAppel paqueAppel)
	{
		int numConexion = paqueAppel.getNumConnexion();
		int addrSource = paqueAppel.getAddresseSource();
		int addrDest = paqueAppel.getAddresseDest();

		if (estMultipleDe19(nbr))
		{
			return null;
		}
		if (estMultipleDe13(nbr))
		{
			return new PaquetIndLiberation(numConexion, addrSource, addrDest, 0b00000001);
		}

		return new PaquetComEtablie(numConexion, addrSource, addrDest);
	}

	// R�ception des paquets de donn�es
	public static PaquetAcquittement envoisPaquetDeDonnees(PaquetDeDonnees paquetDeDonnees, int addrSource)
			throws IOException
	{
		// Ecriture dans L_ecr
		ecrireDansFichiers(L_ecr, paquetDeDonnees.getDonnees());

		// Acquittement
		char bitM = paquetDeDonnees.getTypeDePaquet().charAt(3);

		if (bitM == '0' && !grosPaquet)
			return acquittement(paquetDeDonnees, addrSource);

		else if (bitM == '1' && !grosPaquet)
		{
			grosPaquet = true;
			grosData = new String(paquetDeDonnees.getDonnees());
		}

		else if (bitM == '1' && grosPaquet)
			grosData.concat(paquetDeDonnees.getDonnees());

		else if (bitM == '0' && grosPaquet)
		{
			grosPaquet = false;
			grosData.concat(paquetDeDonnees.getDonnees());
			paquetDeDonnees.setData(grosData);

			return acquittement(paquetDeDonnees, addrSource);
		}
		return null;
	}

	// Acquittement des paquets de donnees
	private static PaquetAcquittement acquittement(PaquetDeDonnees paquetDeDonnees, int addrSource) throws IOException
	{
		if (estMultipleDe15(addrSource))
			return null;

		String typeAcquittement;

		if (addrSource == new Random().nextInt(7))
			typeAcquittement = ProcessusET.getPr() + "01001";

		else
			typeAcquittement = ProcessusET.getPr() + "00001";
		PaquetAcquittement pAcquittement = new PaquetAcquittement(paquetDeDonnees.getNumeroDeConnexion(),
				typeAcquittement);

		ecrireDansFichiers(L_lec, pAcquittement.toString());
		return pAcquittement;

	}

	// reception de donn�es retransmis(apr�s acquittement n�gatif)
	public static PaquetAcquittement retransmissionDonn�es(PaquetDeDonnees paquetDeDonnees, int addrSource)
			throws IOException
	{
		return envoisPaquetDeDonnees(paquetDeDonnees, addrSource);
	}

	// R�ception de demandes de lib�ration
	public static void envoisPaquetLiberation(IPaquet paquetLib) throws IOException
	{
		ecrireDansFichiers(L_ecr, paquetLib.toString());
	}

	// Ecriture dans le fichier L_ecr
	private static void ecrireDansFichiers(File file, String ecriture) throws IOException
	{
		FileOutputStream fos = null;
		fos = new FileOutputStream(file, true);
		fos.write(ecriture.concat("\n").getBytes());
		fos.close();
	}

	private static boolean estMultipleDe13(int nbr)
	{
		if (nbr != 0)
			return nbr % 13 == 0;
		else
			return false;
	}

	private static boolean estMultipleDe15(int nbr)
	{
		if (nbr != 0)
			return nbr % 15 == 0;
		else
			return false;
	}

	private static boolean estMultipleDe19(int nbr)
	{
		if (nbr != 0)
			return nbr % 19 == 0;
		else
			return false;
	}

}
