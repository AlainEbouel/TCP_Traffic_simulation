import java.util.ArrayList;

public class ProcessusER
{
	private ArrayList<SauvegardeInfos> sauvegardeInfos;
	private static int count = 0;// génères les numéros de connexions

	public ProcessusER()
	{
		sauvegardeInfos = new ArrayList<SauvegardeInfos>();
	}

	public Primitive DemandeDeConnexion(int numIdentifiant, int addrSource, int addrDestination)
	{
		if (estMultipleDe27(addrSource))
		{
			return Primitive.N_DISCONNECT_ind;
		} else
		{
			PaquetStandard paqueAppel = preparationPaquetAppel(addrSource, addrDestination, count++);
			int numConnexion = count++;
			sauvegardeInfos.add(new SauvegardeInfos(numConnexion, addrSource, addrDestination,
					EtatDeConnexion.attenteDeConfirmation, numIdentifiant));
			return N_ConnectInd(addrSource, addrDestination);
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
}
