
public class PaquetDeDonnees implements IPaquet
{
	private int numeroDeConnexion;
	private String typeDePaquet;
	private String donnees;
	private Primitive primitive;

	public PaquetDeDonnees(int numeroDeConnexion, String typeDePaquet, String donnees)
	{
		this.numeroDeConnexion = numeroDeConnexion;
		this.typeDePaquet = typeDePaquet;
		this.donnees = donnees;
		this.primitive = Primitive.N_DATA_ind;
	}

	public int getNumeroDeConnexion()
	{
		return numeroDeConnexion;
	}

	public String getTypeDePaquet()
	{
		return typeDePaquet;
	}

	public String getDonnees()
	{
		return donnees;
	}

	public void setData(String grosData)
	{
		donnees = new String(grosData);
	}

	public int getPr()
	{
		return Integer.parseInt(typeDePaquet.substring(0, 3));
	}

	@Override
	public Primitive getPrimitive()
	{
		return primitive;
	}

}
