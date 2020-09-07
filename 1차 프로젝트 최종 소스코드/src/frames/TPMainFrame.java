package frames;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import constants.TPConstants;
import control.Hash;
import control.InsertProduct;
import menu.TPMenuBar;
import model.UserInfo;

public class TPMainFrame extends JFrame {
	private TPToolBar toolBar;
	private TPSearchBar sBar;
	private TPResultTable rTable;
	private TPMenuBar MenuBar;
	private Hash hash;
	private InsertProduct insertProduct;
	private UserInfo userInfo;
	private static TPMainFrame uniqueMainFrame = new TPMainFrame(TPConstants.TITLE_MAINFRAME);

	public void setUserInfo(UserInfo userInfo) {this.userInfo = userInfo;}
	public TPMainFrame(String title) {
		super(title);
		hash = new Hash(223);
		insertProduct = new InsertProduct(hash);
		MenuBar = new TPMenuBar();
		this.setJMenuBar(MenuBar);
		rTable = new TPResultTable(insertProduct.getHash());
		this.add(BorderLayout.CENTER, rTable);
		sBar = new TPSearchBar(hash, rTable);
		this.add(BorderLayout.NORTH, sBar);
		System.out.println((int) '»ï'); // 44032
//		System.out.println(hash.toString());
	}

	public static TPMainFrame getInstance() {
		return uniqueMainFrame;
	}

	public void init() {
		this.setSize(TPConstants.WIDTH_MAINFRAME, TPConstants.HEIGHT_MAINFRAME);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}