package menu;

import java.awt.BorderLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import menu.TPLoginDialog.LoginPanel;

public class TPGoogleMap extends JDialog{
	public TPGoogleMap() {
		this.setLocationRelativeTo(null);
		this.setSize(500, 500);
		this.setResizable(false);
		try {
			// String imageUrl =
			// "https://maps.googleapis.com/maps/api/staticmap?center=37.2255155,127.1874650&zoom=16&size=612x612&scale=2&maptype=roadmap&key=AIzaSyDH36ckWgiDfl_h9KhGu9mOpzeUoRSKDnQ";
			String imageUrl = "http://maps.google.com/maps/api/staticmap?center=" + "37.2343638,127.1896342"
					+ "&zoom=14&size=512x512&maptype=roadmap&markers=color:blue|label:S|37.2343638,127.1896342&markers=color:green|label:G|37.2342923,127.1955347&markers=color:red|color:red|label:C|40.718217,-73.998284&sensor=false";
			String destinationFile = "image.jpg";
			String str = destinationFile;
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.add(new JLabel(new ImageIcon(
				(new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH))));
	}

}
