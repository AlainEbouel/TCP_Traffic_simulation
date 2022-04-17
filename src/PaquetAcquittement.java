
public class PaquetAcquittement
{
	private int numeroDeConnexion;
	private int typeDePaquet;
	
	public PaquetAcquittement(int numeroDeConnexion, int typeDePaquet)
	{
		this.numeroDeConnexion = numeroDeConnexion;
		this.typeDePaquet = typeDePaquet;
	}

	public int getNumeroDeConnexion()
	{
		return numeroDeConnexion;
	}

	public int getTypeDePaquet()
	{
		return typeDePaquet;
	}	
	
}
