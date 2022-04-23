
public class PaquetComEtablie implements IPaquet
{
	private final int NUMERO_CONNEXION;
	private final int typeDePaquet;
	private int addresseSource;
	private int addresseDest;
	private Primitive primitive;

	public PaquetComEtablie(int numeroDeConnexion, int addresseSource, int addresseDest)
	{
		this.NUMERO_CONNEXION = numeroDeConnexion;
		this.typeDePaquet = 0b00001111;
		this.addresseSource = addresseSource;
		this.addresseDest = addresseDest;
		this.primitive = Primitive.N_CONNECT_resp;
	}

	@Override
	public Primitive getPrimitive()
	{
		return primitive;
	}

	@Override
	public String toString()
	{
		return "Paquet de Communication établie [NUMERO_CONNEXION=" + NUMERO_CONNEXION + ", typeDePaquet="
				+ typeDePaquet + ", addresseSource=" + addresseSource + ", addresseDest=" + addresseDest
				+ ", primitive=" + primitive + "]";
	}

}
