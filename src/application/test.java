package application;
import java.security.SecureRandom;
public class test {
	//public static void main(String[] args) {
		SecureRandom rand = new SecureRandom();
		int number = rand.nextInt(2);
		for (int i=0; i<100; i++) {
			System.out.println(number);
		}
	}
}
