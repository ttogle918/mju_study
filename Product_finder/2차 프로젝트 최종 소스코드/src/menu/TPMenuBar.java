package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import control.AuthenticationManager;
import control.AuthenticationManager.EAutheticationState;
import control.EventData;
import control.InsertProduct;
import control.SearchData;
import frames.TPGoogleMap;
import frames.TPMainFrame;
import model.UserInfo;

public class TPMenuBar extends JMenuBar {
	private TPMainFrame tpMainFrame;
	private JMenuItem Menu1;
	private JMenuItem Menu2;
	private JMenuItem Menu3;
	private JMenuItem Menu4;
	private JMenuItem Menu5;
	private JMenuItem Menu6;
	private JMenuItem Menu7;
	private JMenuItem Menu8;
	private SearchData SData;
	private EventData EData;
	private EAutheticationState autheticationState = null;
	private AuthenticationManager authenticationManager;
	private UserInfo userInfo;
	private InsertProduct insertProduct;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public TPMenuBar(SearchData Data, EventData eData) {
		this.userInfo = new UserInfo();
		authenticationManager = new AuthenticationManager();
		
		SData = Data;
		EData = eData;
		
		Menu1 = new JMenuItem("�α���");
		Menu1.addActionListener(new ButtonListener());
		this.add(Menu1);
		Menu2 = new JMenuItem("ȸ������");
		Menu2.addActionListener(new ButtonListener());
		this.add(Menu2);
		Menu3 = new JMenuItem("�˻����");
		Menu3.addActionListener(new ButtonListener());
		this.add(Menu3);
		Menu4 = new JMenuItem("�α�˻�");
		Menu4.addActionListener(new ButtonListener());
		this.add(Menu4);
		Menu5 = new JMenuItem("����ǰ��");
		Menu5.addActionListener(new ButtonListener());
		this.add(Menu5);
		Menu6 = new JMenuItem("��ǰ �߰� ");
		Menu6.addActionListener(new ButtonListener());
		this.add(Menu6);
		Menu8 = new JMenuItem("��ǰ ����");
		Menu8.addActionListener(new ButtonListener());
		this.add(Menu8);
		Menu7 = new JMenuItem("�α׾ƿ�");
		Menu7.addActionListener(new ButtonListener());
		this.add(Menu7);
		
		this.Menu6.setVisible(false);
		this.Menu8.setVisible(false);
		this.Menu7.setVisible(false);
	}

	private void authenticate(UserInfo userInfo) {
		try {
			autheticationState = authenticationManager.authenticate(userInfo);
			if (autheticationState != null) {
				if(autheticationState == EAutheticationState.authenticatedbuyer){
					JOptionPane.showMessageDialog(null, "�ȳ��ϼ���!" + userInfo.getId() + "! �α��� �Ǿ����ϴ�.",
							"Login", JOptionPane.INFORMATION_MESSAGE);
					this.userInfo = userInfo;
						TPMainFrame.getInstance().setUserInfo(userInfo);
						userInfo.setAddress(authenticationManager.getAddress());
						System.out.println(userInfo.getAddress());
						this.Menu1.setVisible(false);
						this.Menu2.setVisible(false);
						this.Menu6.setVisible(false);
						this.Menu8.setVisible(false);
						this.Menu7.setVisible(true);
						this.updateUI();
				}else if(autheticationState == EAutheticationState.authenticatedseller){
					JOptionPane.showMessageDialog(null, "�ȳ��ϼ���! " + userInfo.getId() + "! �α��� �Ǿ����ϴ�..",
							"Login", JOptionPane.INFORMATION_MESSAGE);
						this.userInfo = userInfo;
						TPMainFrame.getInstance().setUserInfo(userInfo);
						this.Menu1.setVisible(false);
						this.Menu2.setVisible(false);
						this.Menu3.setVisible(false);
						this.Menu4.setVisible(false);
						this.Menu5.setVisible(false);
						this.Menu6.setVisible(true);
						this.Menu7.setVisible(true);
						this.Menu8.setVisible(true);
						this.updateUI();
				}

			} else {
				JOptionPane.showMessageDialog(this, "Login Fail!!", "LoginFail", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void signUp(UserInfo userInfo) {
		try {
			autheticationState = authenticationManager.signUp(userInfo);
			if (autheticationState == EAutheticationState.signUpComplete) {
				JOptionPane.showMessageDialog(this, "Hi " + userInfo.getId() + "! ������ �Ϸ� �Ǿ����ϴ�!.",
						"SignUp", JOptionPane.INFORMATION_MESSAGE);
				this.userInfo = userInfo;
				TPMainFrame.getInstance().setUserInfo(userInfo);
			} else {
				JOptionPane.showMessageDialog(this, "���� ����!", "���� ����!", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void logOut() {
		JOptionPane.showMessageDialog(null, "�α� �ƿ� �Ǿ����ϴ�!!", "�α� �ƿ�", JOptionPane.INFORMATION_MESSAGE);
		TPMainFrame.getInstance().setUserInfo(null);
		this.Menu1.setVisible(true);
		this.Menu2.setVisible(true);
		this.Menu3.setVisible(true);
		this.Menu4.setVisible(true);
		this.Menu5.setVisible(true);
		
		this.Menu6.setVisible(false);
		this.Menu7.setVisible(false);
		this.Menu8.setVisible(false);
		updateUI();
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				if (event.getSource() == Menu1) {
					System.out.println("�α���");
					TPLoginDialog loginDialog = new TPLoginDialog(tpMainFrame);
					loginDialog.setLocationRelativeTo(null);
					loginDialog.setVisible(true);
					if (loginDialog.isLoginOK()) {
						authenticate(loginDialog.getUserInfo());
					}
				} else if (event.getSource() == Menu2) {
					System.out.println("ȸ������");
					TPSignUp signupDialog = new TPSignUp(tpMainFrame);
					signupDialog.setLocationRelativeTo(null);
					signupDialog.setVisible(true);
					if (signupDialog.isLoginOK()) {
						signUp(signupDialog.getUserInfo());
					}

				} else if (event.getSource() == Menu3) {
					System.out.println("�˻����(���ã�� ǰ��)");
					TPFavorites favorites = new TPFavorites(tpMainFrame, SData);
					favorites.setLocationRelativeTo(null);
					favorites.setVisible(true);
					
				} else if (event.getSource() == Menu4) {
					System.out.println("�α�˻�");
					TPMostSearched MostSearched = new TPMostSearched(tpMainFrame, SData);
					MostSearched.setLocationRelativeTo(null);
					MostSearched.setVisible(true);
						
				} else if (event.getSource() == Menu5) {
					System.out.println("����ǰ��");
					TPSaleProduct SaleProduct = new TPSaleProduct(tpMainFrame, EData);
					SaleProduct.setLocationRelativeTo(null);
					SaleProduct.setVisible(true);
					
				}else if(event.getSource() == Menu6 || event.getSource() == Menu8){
					TPProductID productIO = new TPProductID(insertProduct);
					if(event.getSource() == Menu6){
						productIO.setModeInsert();
						productIO.setLocationRelativeTo(null);
						productIO.setVisible(true);
						
					}else if(event.getSource() == Menu8){
						productIO.setModeDelete();
						productIO.setLocationRelativeTo(null);
						productIO.setVisible(true);
					}
				}else if(event.getSource() == Menu7){
					logOut();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void init(InsertProduct insertProduct) {
		this.insertProduct = insertProduct;
	}


}
