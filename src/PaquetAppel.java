
public class PaquetAppel implements IPaquet
{

	private final int NUMERO_CONNEXION;
	private final int typeDePaquet;
	private int addresseSource;
	private int addresseDest;
	private Primitive primitive;

	public PaquetAppel(int numeroDeConnexion, int addresseSource, int addresseDest)
	{
		this.NUMERO_CONNEXION = numeroDeConnexion;
		this.typeDePaquet = 0b0001011;
		this.addresseSource = addresseSource;
		this.addresseDest = addresseDest;
		this.primitive = Primitive.N_CONNECT_req;
	}

	public int getNUMERO_CONNEXION()
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
	public Primitive getPrimitive()
	{
		return primitive;
	}

	@Override

	public String toString()
	{
		return "PaquetAppel [NUMERO_CONNEXION=" + NUMERO_CONNEXION + ", typeDePaquet=" + typeDePaquet
				+ ", addresseSource=" + addresseSource + ", addresseDest=" + addresseDest + ", primitive="
				+ primitive + "]";
	}

}
