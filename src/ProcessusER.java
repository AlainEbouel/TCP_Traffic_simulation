import java.io.IOException;
import java.util.ArrayList;

public class ProcessusER
{
	private ArrayList<SauvegardeInfos> sauvegardeInfos;
	private static int count = 0;// génères les numéros de connexions

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
		if (data.length() > 128)
			return traitementGrosPaquet(numConnexion, data, addrSource);
		else
		{
			String typePaquet = formatTypeDePaquet(0);

			return LiaisonDeDonnees.envoisPaquetDeDonnees(new PaquetDeDonnees(numConnexion, typePaquet, data),
					addrSource);
		}

	}

	// Traitement des grosses données(>128o)
	private PaquetAcquittement traitementGrosPaquet(int numConnexion, String data, int addrSource) throws IOException
	{
		byte[] dataBytes = data.getBytes();
		int nbrPaquet = dataBytes.length % 128 == 0 ? dataBytes.length / 128 : dataBytes.length / 128 + 1;
		int index = 0;
		while (nbrPaquet > 1)
		{

			String typePaquet = formatTypeDePaquet(1);
			String dataPartiel = data.substring(index, index + 127);
			LiaisonDeDonnees.envoisPaquetDeDonnees(new PaquetDeDonnees(numConnexion, typePaquet, dataPartiel),
					addrSource);
			index += 128;
			nbrPaquet--;
		}

		String typePaquet = formatTypeDePaquet(0);
		String dataPartiel = data.substring(index, dataBytes.length);
		LiaisonDeDonnees.envoisPaquetDeDonnees(new PaquetDeDonnees(numConnexion, typePaquet, dataPartiel),
				addrSource);

		return null;
	}

	private String formatTypeDePaquet(int bitM)
	{
		String ps = String.format("%3s", Integer.toBinaryString(ProcessusET.getPs()).replace(' ', '0'));
		String pr = String.format("%3s", Integer.toBinaryString(ProcessusET.getPr()).replace(' ', '0'));

		return pr + bitM + ps + 0;

	}

}
