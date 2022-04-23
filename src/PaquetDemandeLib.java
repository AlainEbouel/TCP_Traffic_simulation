
public class PaquetDemandeLib implements IPaquet
{

	private final int NUMERO_CONNEXION;
	private final int typeDePaquet;
	private int addresseSource;
	private int addresseDest;
	private Primitive primitive;

	public PaquetDemandeLib(int numeroDeConnexion, int addresseSource, int addresseDest)
	{
		this.NUMERO_CONNEXION = numeroDeConnexion;
		this.typeDePaquet = 0b00010011;
		this.addresseSource = addresseSource;
		this.addresseDest = addresseDest;
		this.primitive = Primitive.N_DISCONNECT_ind;
	}

	@Override
	public Primitive getPrimitive()
	{
		return primitive;
	}

	@Override
	public String toString()
	{
		return "******PAQUET DE DEMANDE DE LIBÉRATION*****==>[NUMERO_CONNEXION=" + NUMERO_CONNEXION
				+ ", typeDePaquet=" + typeDePaquet + ", addresseSource=" + addresseSource + ", addresseDest="
				+ addresseDest + ", primitive=" + primitive + "]";
	}

}
