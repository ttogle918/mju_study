package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import frames.TPMainFrame;
import model.UserInfo;

public class TPLoginDialog extends JDialog {
	// attributes
	private static final long serialVersionUID = 1L;
	private boolean bloginOK = false; // boolean에는 b를 붙여아함
	private UserInfo userInfo = new UserInfo();
	// components
	private LoginPanel loginpanel;

	// getters & setters
	public boolean isLoginOK() {
		return this.bloginOK;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	// constructor
	public TPLoginDialog(TPMainFrame tpMainFrame) {
		super(tpMainFrame, "Login", true); // 부모의 생성자 호출
		this.setLocationRelativeTo(null);
		this.setSize(250, 150);
		this.loginpanel = new LoginPanel();
		this.getContentPane().add(this.loginpanel, BorderLayout.CENTER);
		this.setResizable(false);
	}

	// associate to "AutheticationManager" // 아는 사람(자식보다 연관성이 훨씬 약함)

	// inner class
	private void login() throws Exception {
		bloginOK = true;
		userInfo.setId(this.loginpanel.tfUserName.getText());
		userInfo.setPassword(this.loginpanel.tfPassword.getText());
		this.dispose();
	}
	private void cancel(){
		this.dispose();
	}
	public class LoginPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		// components
		private JLabel lbUserName;
		private JLabel lbPassword;
		private JTextField tfUserName;
		private JTextField tfPassword;
		private JButton btnOK;
		private JButton btnCancel;
		
		//constructor
		public LoginPanel() {
			lbUserName = new JLabel("사용자이름 : ");
			this.add(lbUserName);
			tfUserName = new JTextField(10);
			this.add(tfUserName);
			lbPassword = new JLabel("     비밀번호 : ");
			this.add(lbPassword);
			tfPassword = new JTextField(10);
			this.add(tfPassword);
			
			
			this.setBorder(new LineBorder(Color.red));
			btnOK = new JButton("OK");
			btnOK.addActionListener(new ButtonListener());
			btnOK.setActionCommand(btnOK.getText());
			this.add(btnOK);
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(new ButtonListener());
			btnCancel.setActionCommand(btnCancel.getText());
			this.add(btnCancel);
		}
		
		private class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if(event.getActionCommand().equals(btnOK.getText())) {
						if(loginpanel.tfUserName.getText().isEmpty()){
							JOptionPane.showMessageDialog(null,
									"아이디와 비밀번호를 확인해주세요.","로그인 실패",
									JOptionPane.INFORMATION_MESSAGE);
						}else if(loginpanel.tfPassword.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"아이디와 비밀번호를 확인해주세요.","로그인 실패",
									JOptionPane.INFORMATION_MESSAGE);
						}else {
							login();
						}
					} else {
						cancel();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}// end Login