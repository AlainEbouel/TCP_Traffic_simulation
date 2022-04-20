
public class PaquetDeDonnees
{
	private int numeroDeConnexion;
	private String typeDePaquet;
	private String donnees;

	public PaquetDeDonnees(int numeroDeConnexion, String typeDePaquet, String donnees)
	{
		this.numeroDeConnexion = numeroDeConnexion;
		this.typeDePaquet = typeDePaquet;
		this.donnees = donnees;
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

}
