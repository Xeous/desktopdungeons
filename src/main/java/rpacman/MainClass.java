package rpacman;



import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainClass extends JFrame {

	public static void main(String args[]) {
		new MainClass();
	}

	public MainClass() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(200, 10);
		setResizable(false);
		JPanel panel1 = new Board();
		add(panel1);
		setSize(889, 710);
		setVisible(true);
	}
}