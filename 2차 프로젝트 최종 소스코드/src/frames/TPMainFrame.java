package frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import constants.TPConstants;
import control.EventData;
import control.Hash;
import control.InsertProduct;
import control.SearchData;
import menu.TPMenuBar;
import model.UserInfo;

public class TPMainFrame extends JFrame {
	private TPSearchBar sBar;
	private TPResultTable rTable;
	private TPMenuBar MenuBar;
	private Hash hash;
	private SearchData SData;
	private EventData EData;
	private InsertProduct insertProduct;
	private UserInfo userInfo;
	private static TPMainFrame uniqueMainFrame = new TPMainFrame(TPConstants.TITLE_MAINFRAME);
	
	public UserInfo getUserInfo() {return userInfo;}
	public void setUserInfo(UserInfo userInfo) {this.userInfo = userInfo;}
	public TPMainFrame(String title) {
		super(title);
		hash = new Hash(223);
		SData = new SearchData();
		EData = new EventData(hash);
		insertProduct = new InsertProduct(hash, EData);
		EData.init();
		MenuBar = new TPMenuBar(SData, EData);
		this.setJMenuBar(MenuBar);
		rTable = new TPResultTable(insertProduct.getHash());
		this.add(BorderLayout.CENTER, rTable);
		sBar = new TPSearchBar(hash, rTable, SData);
		this.add(BorderLayout.NORTH, sBar);
	}

	public static TPMainFrame getInstance() {
		return uniqueMainFrame;
	}

	public void init() {
		sBar.init(this);
		MenuBar.init(insertProduct);
		this.setSize(TPConstants.WIDTH_MAINFRAME, TPConstants.HEIGHT_MAINFRAME);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((dim.width/2)-(this.getWidth()/2), (dim.height/2)-(this.getHeight()/2));
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}