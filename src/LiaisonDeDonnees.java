import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class LiaisonDeDonnees
{
	private static File L_ecr = new File("L_ecr.txt");;
	private FileReader L_lec;

	public LiaisonDeDonnees() throws FileNotFoundException
	{
		// L_ecr = new File("L_ecr.txt");
		L_lec = new FileReader("L_lec.txt");
	}

	public static File getL_ecr()
	{
		return L_ecr;
	}

	public FileReader getL_lec()
	{
		return L_lec;
	}

	// Methode pour les paquet d'appels//La liason de données écrira dans L_ecr
	public static Primitive appel(PaquetStandard paqueAppel) throws IOException
	{
		String ecriture = new String("Paquet d'appel : ").concat(paqueAppel.toString());
		FileOutputStream file = null;

		ecrireDansL_ecr(ecriture);

		return reponseDemandeDeConnexion(paqueAppel.getAddresseSource());
	}

	private static Primitive reponseDemandeDeConnexion(int nbr)
	{
		if (estMultipleDe19(nbr))
			return null;
		if (estMultipleDe13(nbr))
			return Primitive.N_DISCONNECT_req;

		return Primitive.N_CONNECT_resp;
	}

	private static boolean estMultipleDe13(int nbr)
	{
		return false;
	}

	private static boolean estMultipleDe15(int nbr)
	{
		return false;
	}

	private static boolean estMultipleDe19(int nbr)
	{
		return false;
	}

	public static PaquetAcquittement envoisPaquetDeDonnees(PaquetDeDonnees paquetDeDonnees, int adrrSource)
			throws IOException
	{
		// Ecriture dans L_ecr
		ecrireDansL_ecr(paquetDeDonnees.getDonnees());

		// Acquittement
		char bitM = paquetDeDonnees.getTypeDePaquet().charAt(3);

		if (bitM == '0')
			return acquittement(paquetDeDonnees, adrrSource);

		else
			return null;
	}

	// Acquittement des paquets de donnees
	private static PaquetAcquittement acquittement(PaquetDeDonnees paquetDeDonnees, int addrSource)
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

		return new PaquetAcquittement(paquetDeDonnees.getNumeroDeConnexion(), typeDePaquet);
	}

	// Ecriture dans le fichier L_ecr
	private static void ecrireDansL_ecr(String ecriture) throws IOException
	{
		FileOutputStream file = null;
		file = new FileOutputStream(L_ecr, true);
		file.write(ecriture.concat("\n").getBytes());
		file.close();

	}

}
