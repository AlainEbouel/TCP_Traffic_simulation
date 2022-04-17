import java.util.ArrayList;

public class ProcessusER
{
	ArrayList<InfoCommunication> sauvegardeInfos;

	public ProcessusER()
	{
		sauvegardeInfos = new ArrayList<InfoCommunication>();
	}

	public Primitive DemandeDeConnexion(int numeroDemande, int addrSource, int addrDestination)
	{
		if (estMultipleDe27(addrSource))
		{
			return Primitive.N_DISCONNECT_ind;
		} else
		{
			// sauvegardeInfos.add(new InfoCommunication(numeroDemande, addrSource,
			// addrDestination, numeroConnexion))
			return N_ConnectInd(addrSource, addrDestination);
		}

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
