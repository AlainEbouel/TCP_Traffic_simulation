
public class PaquetIndLiberation implements IPaquet
{
	private final int NUMERO_CONNEXION;
	private final int typeDePaquet;
	private int addresseSource;
	private int addresseDest;
	private int raison;
	private Primitive primitive;

	public PaquetIndLiberation(int nUMERO_CONNEXION, int addresseSource, int addresseDest, int raison)
	{
		super();
		NUMERO_CONNEXION = nUMERO_CONNEXION;
		this.typeDePaquet = 0b00010011;
		this.addresseSource = addresseSource;
		this.addresseDest = addresseDest;
		this.raison = raison;
		this.primitive = Primitive.N_DISCONNECT_ind;
	}

	@Override
	public String toString()
	{
		return "Paquet d'indication de liberation :[NUMERO_CONNEXION=" + NUMERO_CONNEXION + ", typeDePaquet="
				+ typeDePaquet + ", addresseSource=" + addresseSource + ", addresseDest=" + addresseDest
				+ ", Raison : " + raison + "]";
	}

	@Override
	public Primitive getPrimitive()
	{

		return primitive;
	}

}
