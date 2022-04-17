
public class EntreeDeTable
{
	int numeroDeConnexion;
	EtatDeConnexion etatDeConnexion;

	public EntreeDeTable(int numeroDeConnexion, EtatDeConnexion etatDeConnexion)
	{
		this.numeroDeConnexion = numeroDeConnexion;
		this.etatDeConnexion = etatDeConnexion;
	}

	public EtatDeConnexion getEtatDeConnexion()
	{
		return etatDeConnexion;
	}

	public void setEtatDeConnexion(EtatDeConnexion etatDeConnexion)
	{
		this.etatDeConnexion = etatDeConnexion;
	}

	public int getNumeroDeConnexion()
	{
		return numeroDeConnexion;
	}

}
