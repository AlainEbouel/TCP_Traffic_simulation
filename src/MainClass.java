import java.io.IOException;

public class MainClass
{

	public static void main(String[] args) throws IOException, InterruptedException
	{
//
		Session session = new Session();
//		ProcessusER er = new ProcessusER();
//		String data = "Suite à une demande de la couche supérieure, une entité de couche transport entre en\r\n"
//				+ "communication avec une lecture du fichierSuite à une demande de la couche supérieure, une entité de couche transport entre en\\r\\n\"\r\n"
//				+ "				+ \"communication avec une lecture du fichier\r\n";

		String data = "01234567";
		System.out.println(data.substring(3));

		/// er.preparationPaquetDeDonnees(data, Primitive.N_CONNECT_conf, 1);

		session.start();

	}

}
