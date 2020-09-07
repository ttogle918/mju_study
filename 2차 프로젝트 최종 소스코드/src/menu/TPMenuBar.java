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
		
		Menu1 = new JMenuItem("로그인");
		Menu1.addActionListener(new ButtonListener());
		this.add(Menu1);
		Menu2 = new JMenuItem("회원가입");
		Menu2.addActionListener(new ButtonListener());
		this.add(Menu2);
		Menu3 = new JMenuItem("검색기록");
		Menu3.addActionListener(new ButtonListener());
		this.add(Menu3);
		Menu4 = new JMenuItem("인기검색");
		Menu4.addActionListener(new ButtonListener());
		this.add(Menu4);
		Menu5 = new JMenuItem("할인품목");
		Menu5.addActionListener(new ButtonListener());
		this.add(Menu5);
		Menu6 = new JMenuItem("상품 추가 ");
		Menu6.addActionListener(new ButtonListener());
		this.add(Menu6);
		Menu8 = new JMenuItem("상품 삭제");
		Menu8.addActionListener(new ButtonListener());
		this.add(Menu8);
		Menu7 = new JMenuItem("로그아웃");
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
					JOptionPane.showMessageDialog(null, "안녕하세요!" + userInfo.getId() + "! 로그인 되었습니다.",
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
					JOptionPane.showMessageDialog(null, "안녕하세요! " + userInfo.getId() + "! 로그인 되었습니다..",
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
				JOptionPane.showMessageDialog(this, "Hi " + userInfo.getId() + "! 가입이 완료 되었습니다!.",
						"SignUp", JOptionPane.INFORMATION_MESSAGE);
				this.userInfo = userInfo;
				TPMainFrame.getInstance().setUserInfo(userInfo);
			} else {
				JOptionPane.showMessageDialog(this, "가입 실패!", "가입 실패!", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void logOut() {
		JOptionPane.showMessageDialog(null, "로그 아웃 되었습니다!!", "로그 아웃", JOptionPane.INFORMATION_MESSAGE);
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
					System.out.println("로그인");
					TPLoginDialog loginDialog = new TPLoginDialog(tpMainFrame);
					loginDialog.setLocationRelativeTo(null);
					loginDialog.setVisible(true);
					if (loginDialog.isLoginOK()) {
						authenticate(loginDialog.getUserInfo());
					}
				} else if (event.getSource() == Menu2) {
					System.out.println("회원가입");
					TPSignUp signupDialog = new TPSignUp(tpMainFrame);
					signupDialog.setLocationRelativeTo(null);
					signupDialog.setVisible(true);
					if (signupDialog.isLoginOK()) {
						signUp(signupDialog.getUserInfo());
					}

				} else if (event.getSource() == Menu3) {
					System.out.println("검색목록(즐겨찾는 품목)");
					TPFavorites favorites = new TPFavorites(tpMainFrame, SData);
					favorites.setLocationRelativeTo(null);
					favorites.setVisible(true);
					
				} else if (event.getSource() == Menu4) {
					System.out.println("인기검색");
					TPMostSearched MostSearched = new TPMostSearched(tpMainFrame, SData);
					MostSearched.setLocationRelativeTo(null);
					MostSearched.setVisible(true);
						
				} else if (event.getSource() == Menu5) {
					System.out.println("할인품목");
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
