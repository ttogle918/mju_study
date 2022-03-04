package frames;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.UIManager;

public class TPLauncher {
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
		}
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("icon.jpg");
		TPMainFrame frame = TPMainFrame.getInstance();
		frame.setIconImage(img);
		frame.init();
	}
}
