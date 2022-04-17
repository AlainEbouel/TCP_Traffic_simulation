
public class PaquetDeDonnees
{
	private int numeroDeConnexion;
	private int typeDePaquet;
	private int donnees;

	public PaquetDeDonnees(int numeroDeConnexion, int typeDePaquet, int donnees)
	{
		this.numeroDeConnexion = numeroDeConnexion;
		this.typeDePaquet = typeDePaquet;
		this.donnees = donnees;
	}

	public int getNumeroDeConnexion()
	{
		return numeroDeConnexion;
	}

	public int getTypeDePaquet()
	{
		return typeDePaquet;
	}

	public int getDonnees()
	{
		return donnees;
	}

}
