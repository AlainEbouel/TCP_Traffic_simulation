import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class LiaisonDeDonnees
{

	private File L_ecr;
	private FileReader L_lec;

	public LiaisonDeDonnees() throws FileNotFoundException
	{
		L_ecr = new File("L_ecr.txt");
		L_lec = new FileReader("L_lec.txt");
	}

}
