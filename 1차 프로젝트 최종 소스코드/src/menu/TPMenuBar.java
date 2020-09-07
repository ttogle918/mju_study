package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import control.AuthenticationManager;
import control.AuthenticationManager.EAutheticationState;
import frames.TPMainFrame;
import model.UserInfo;

public class TPMenuBar extends JMenuBar {
	private TPMainFrame tpMainFRame;
	private JMenuItem Menu1;
	private JMenuItem Menu2;
	private JMenuItem Menu3;
	private JMenuItem Menu4;
	private JMenuItem Menu5;
	private EAutheticationState autheticationState = null;
	private AuthenticationManager authenticationManager;
	private UserInfo userInfo;

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public TPMenuBar() {
		this.userInfo = new UserInfo();
		authenticationManager = new AuthenticationManager();
		
		Menu1 = new JMenuItem("로그인");
		Menu1.addActionListener(new ButtonListener());
		this.add(Menu1);
		Menu2 = new JMenuItem("회원가입");
		Menu2.addActionListener(new ButtonListener());
		this.add(Menu2);
		Menu3 = new JMenuItem("장바구니");
		Menu3.addActionListener(new ButtonListener());
		this.add(Menu3);
		Menu4 = new JMenuItem("할인품목");
		Menu4.addActionListener(new ButtonListener());
		this.add(Menu4);
		Menu5 = new JMenuItem("로그아웃");
		Menu5.addActionListener(new ButtonListener());
		this.add(Menu5);
		this.Menu5.setVisible(false);
	}

	private void authenticate(UserInfo userInfo) {
		try {
			autheticationState = authenticationManager.authenticate(userInfo);
			if (autheticationState == EAutheticationState.authenticated) {
				JOptionPane.showMessageDialog(this, "Hi " + userInfo.getId() + "! You have successfully logged in.",
						"Login", JOptionPane.INFORMATION_MESSAGE);
				this.userInfo = userInfo;
				TPMainFrame.getInstance().setUserInfo(userInfo);
				this.Menu1.setVisible(false);
				this.Menu2.setVisible(false);
				this.Menu5.setVisible(true);
				this.updateUI();
				
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
				JOptionPane.showMessageDialog(this, "Hi " + userInfo.getId() + "! You have successfully Sign Up.",
						"SignUp", JOptionPane.INFORMATION_MESSAGE);
				this.userInfo = userInfo;
				TPMainFrame.getInstance().setUserInfo(userInfo);
			} else {
				JOptionPane.showMessageDialog(this, "Sign Fail!!", "SignFail", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {
				if (event.getSource() == Menu1) {
					System.out.println("로그인");
					TPLoginDialog loginDialog = new TPLoginDialog(tpMainFRame);
					loginDialog.setLocationRelativeTo(null);
					loginDialog.setVisible(true);
					if (loginDialog.isLoginOK()) {
						authenticate(loginDialog.getUserInfo());
					}
				} else if (event.getSource() == Menu2) {
					System.out.println("회원가입");
					TPSignUp signupDialog = new TPSignUp(tpMainFRame);
					signupDialog.setLocationRelativeTo(null);
					signupDialog.setVisible(true);
					if (signupDialog.isLoginOK()) {
						signUp(signupDialog.getUserInfo());
					}

				} else if (event.getSource() == Menu3) {
					System.out.println("사용법");
				} else if(event.getSource() == Menu5){
					Menu1.setVisible(true);
					Menu2.setVisible(true);
					Menu5.setVisible(false);
					updateUI();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
