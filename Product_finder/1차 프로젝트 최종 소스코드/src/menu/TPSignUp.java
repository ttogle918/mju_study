package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import frames.TPMainFrame;
import model.UserInfo;

public class TPSignUp extends JDialog {
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
	public TPSignUp(TPMainFrame tpMainFrame) {
		super(tpMainFrame, "SignUp", true); // 부모의 생성자 호출
		this.setLocationRelativeTo(null);
		this.setSize(250, 150);
		this.loginpanel = new LoginPanel();
		this.getContentPane().add(this.loginpanel, BorderLayout.CENTER);
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
		private JLabel lbCheckPassword;
		private JTextField tfUserName;
		private JTextField tfPassword;
		private JTextField tfCheckPassword;
		private JButton btnOK;
		private JButton btnCancel;
		//constructor
		public LoginPanel() {
			lbUserName = new JLabel("UserName : ");
			this.add(lbUserName);
			tfUserName = new JTextField(10);
			this.add(tfUserName);
			lbPassword = new JLabel("Password : ");
			this.add(lbPassword);
			tfPassword = new JTextField(10);
			this.add(tfPassword);
			lbCheckPassword = new JLabel("CheckPassword : ");
			this.add(lbCheckPassword);
			tfCheckPassword = new JTextField(10);
			this.add(tfCheckPassword);
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
							JOptionPane.showMessageDialog(loginpanel,
									"Please enter your username and password.","signupfail",
									JOptionPane.INFORMATION_MESSAGE);
						}else if(loginpanel.tfPassword.getText().isEmpty()) {
							JOptionPane.showMessageDialog(loginpanel,
									"Please enter your username and password.","signupfail",
									JOptionPane.INFORMATION_MESSAGE);
						}else if(loginpanel.tfCheckPassword.getText().isEmpty()){
							JOptionPane.showMessageDialog(loginpanel,
									"Please check your password.","signupfail",
									JOptionPane.INFORMATION_MESSAGE);
							
						}else if(loginpanel.tfCheckPassword.getText().equals(loginpanel.tfPassword.getText())){
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