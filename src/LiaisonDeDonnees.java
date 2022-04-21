import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class LiaisonDeDonnees
{
	private static File L_ecr = new File("L_ecr.txt");
	private static File L_lec = new File("L_lec.txt");

	// Methode pour les paquet d'appels//La liason de données écrira dans L_ecr
	public static Primitive appel(PaquetStandard paqueAppel) throws IOException
	{
		String ecriture = new String("Paquet d'appel : ").concat(paqueAppel.toString());

		ecrireDansFichiers(L_ecr, ecriture);

		return reponseDemandeDeConnexion(paqueAppel.getAddresseSource());
	}

	private static Primitive reponseDemandeDeConnexion(int nbr)
	{
		if (estMultipleDe19(nbr))
		{
			return null;
		}
		if (estMultipleDe13(nbr))
		{
			return Primitive.N_DISCONNECT_req;
		}

		return Primitive.N_CONNECT_resp;
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

	public static PaquetAcquittement envoisPaquetDeDonnees(PaquetDeDonnees paquetDeDonnees, int adrrSource)
			throws IOException
	{
		// Ecriture dans L_ecr
		ecrireDansFichiers(L_ecr, paquetDeDonnees.getDonnees());

		// Acquittement
		char bitM = paquetDeDonnees.getTypeDePaquet().charAt(3);

		if (bitM == '0')
			return acquittement(paquetDeDonnees, adrrSource);

		else
			return null;
	}

	// Acquittement des paquets de donnees
	private static PaquetAcquittement acquittement(PaquetDeDonnees paquetDeDonnees, int addrSource) throws IOException
	{
		if (estMultipleDe15(addrSource))
			return null;

		String typeDePaquet;

		if (addrSource == new Random().nextInt(7))
			typeDePaquet = String.format("%3s", Integer.toBinaryString(ProcessusET.getPr()).replace(' ', '0'))
					+ "01001";

		else
			typeDePaquet = String.format("%3s", Integer.toBinaryString(ProcessusET.getPr()).replace(' ', '0'))
					+ "00001";
		PaquetAcquittement pAcquittement = new PaquetAcquittement(paquetDeDonnees.getNumeroDeConnexion(),
				typeDePaquet);

		ecrireDansFichiers(L_lec, pAcquittement.toString());

		return pAcquittement;
	}

	// Ecriture dans le fichier L_ecr
	private static void ecrireDansFichiers(File file, String ecriture) throws IOException
	{
		FileOutputStream fos = null;
		fos = new FileOutputStream(file, true);
		fos.write(ecriture.concat("\n").getBytes());
		fos.close();

	}

}
