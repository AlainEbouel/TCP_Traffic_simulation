
public class PaquetStandard // appel, confirmation, libération,
{
	private final int NUMERO_CONNEXION;
	private final int typeDePaquet;
	private int addresseSource;
	private int addresseDest;

	public PaquetStandard(int numeroDeConnexion, int typeDePaquet, int addresseSource, int addresseDest)
	{
		this.NUMERO_CONNEXION = numeroDeConnexion;
		this.typeDePaquet = typeDePaquet;
		this.addresseSource = addresseSource;
		this.addresseDest = addresseDest;
	}

	public int getNumeroDeConnexion()
	{
		return NUMERO_CONNEXION;
	}

	public int getTypeDePaquet()
	{
		return typeDePaquet;
	}

	public int getAddresseSource()
	{
		return addresseSource;
	}

	public int getAddresseDest()
	{
		return addresseDest;
	}

	@Override
	public String toString()
	{
		return "[NUMERO_CONNEXION = " + NUMERO_CONNEXION + ", typeDePaquet = "
				+ String.format("%8s", Integer.toBinaryString(typeDePaquet)).replace(' ', '0')
				+ ", addresseSource = " + addresseSource + " , addresseDest = " + addresseDest + "]";
	}

}
