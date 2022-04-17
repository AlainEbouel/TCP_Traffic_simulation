import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Session
{

	private FileReader S_lec;
	private ProcessusET ET;
	private ProcessusER ER;
	public static int count = 0;

	String s1 = "bonjour les amis\r\r";
	String s2 = "Comment ca va les copains?";

	public Session() throws IOException, InterruptedException
	{

		S_lec = new FileReader("S_lec.txt");
		ET = new ProcessusET();
		ER = new ProcessusER();

		start();
	}

	// Démarrage de la boucle de lecture des données d'applications
	public void start() throws InterruptedException
	{
		Scanner scan = new Scanner(S_lec);
		String data;

		while (scan.hasNextLine())
		{
			data = scan.nextLine();
			ET.traitement(ER, data);
		}
		scan.close();
	}

	public void lecture() throws IOException
	{

//		file.write(s1.getBytes());
//		file.write(s2.getBytes());

	}

	public ProcessusET getET()
	{
		return ET;
	}

	public ProcessusER getER()
	{
		return ER;
	}
}
