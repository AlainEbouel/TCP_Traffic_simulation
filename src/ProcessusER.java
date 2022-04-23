import java.io.IOException;
import java.util.ArrayList;

public class ProcessusER
{
	private ArrayList<SauvegardeInfos> sauvegardeInfos;
	private static int count = 0;// génères les numéros de connexions
	private static PaquetDeDonnees SecondPaquet;

	public ProcessusER()
	{
		sauvegardeInfos = new ArrayList<SauvegardeInfos>();
	}

	public Primitive DemandeDeConnexion(int numIdentifiant, int addrSource, int addrDestination) throws IOException
	{
		if (estMultipleDe27(addrSource))
		{
			return Primitive.N_DISCONNECT_ind;
		} else
		{
			PaquetStandard paqueAppel = preparationPaquetAppel(addrSource, addrDestination, count++);
			int numConnexion = count++;

			// sauvegarde info necessaire à cette connexion
			sauvegardeInfos.add(new SauvegardeInfos(numConnexion, addrSource, addrDestination,
					EtatDeConnexion.attenteDeConfirmation, numIdentifiant));

			Primitive reponse = LiaisonDeDonnees.appel(paqueAppel);// Transmission du paquet d'appel a la liaison
													// de données

			if (reponse == null || reponse == Primitive.N_DISCONNECT_req)
				return Primitive.N_DISCONNECT_ind;
			else
				return Primitive.N_CONNECT_conf;
		}
	}

	private PaquetStandard preparationPaquetAppel(int addrSource, int addrDestination, int numeroConnexion)
	{
		int typePaquet = 0b00001011;
		return new PaquetStandard(numeroConnexion, typePaquet, addrSource, addrDestination);
	}

	public Primitive N_ConnectInd(int addrSource, int addrDestination)
	{
		return null;
	}

	boolean estMultipleDe27(int numeroDemande)
	{

		/// **********À coder*******************
		return false;
	}

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

	private String formatTypeDePaquet(int bitM)
	{
		String ps = ProcessusET.getPs();
		String pr = ProcessusET.getPr();
		return pr + bitM + ps + 0;
	}

	public void liberation(int idConnexion, Primitive nDisconnectReq) throws IOException
	{
		int addrSource = getAddrSource(idConnexion);
		int addrDest = getAdrrDest(idConnexion);
		int numConnexion = getNumConnexion(idConnexion);
		int typePaquet = 0b00010011;
		PaquetStandard paquetLib = new PaquetStandard(numConnexion, typePaquet, addrSource, addrDest);
		LiaisonDeDonnees.envoisPaquetLiberation(paquetLib);

	}

	private int getNumConnexion(int idConnexion)
	{
		for (SauvegardeInfos save : sauvegardeInfos)
			if (save.getNumeroDemande() == idConnexion)
				return save.getNumeroConnexion();
		return -1;
	}

	private int getAdrrDest(int idConnexion)
	{
		for (SauvegardeInfos save : sauvegardeInfos)
			if (save.getNumeroDemande() == idConnexion)
				return save.getAddrDestination();
		return -1;
	}

	private int getAddrSource(int idConnexion)
	{
		for (SauvegardeInfos save : sauvegardeInfos)
			if (save.getNumeroDemande() == idConnexion)
				return save.getAddrSource();
		return -1;
	}

}
