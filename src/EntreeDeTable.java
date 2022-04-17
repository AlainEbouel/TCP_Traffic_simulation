
public class EntreeDeTable
{
	int identifiantExtremite;
	EtatDeConnexion etatDeConnexion;

	public EntreeDeTable(int numeroDeConnexion, EtatDeConnexion etatDeConnexion)
	{
		this.identifiantExtremite = numeroDeConnexion;
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

	public int getIdentifiantExtremiteConnexion()
	{
		return identifiantExtremite;
	}

}
