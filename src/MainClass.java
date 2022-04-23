import java.io.File;
import java.io.IOException;

public class MainClass
{

	public static void main(String[] args) throws IOException, InterruptedException
	{
		File L_lec = new File("L_lec.txt");
		File L_ecr = new File("L_ecr.txt");
		File S_ecr = new File("S_ecr.txt");

		L_lec.delete();
		L_lec.createNewFile();
		L_ecr.delete();
		L_ecr.createNewFile();
		S_ecr.delete();
		S_ecr.createNewFile();
		String data = "1";
		// System.out.println(Integer.parseInt(data.substring(0, 3)));

		Session session = new Session();
		session.start();
//		String ps = String.format("%3s", Integer.toBinaryString(3)).replace(' ', '0');
//		String g = String.format("%3s", data).replace(' ', '0');
//		System.out.println("test = " + g);

	}

}
