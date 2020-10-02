import javax.swing.*;


public class test {
	public test() {
		JFrame window = new JFrame();
		window.setSize(100,100);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		new test();
	}
}
