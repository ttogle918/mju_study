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

public class TPSignUp extends JDialog {
	// attributes
	private static final long serialVersionUID = 1L;
	private boolean bloginOK = false; // boolean���� b�� �ٿ�����
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
		super(tpMainFrame, "SignUp", true); // �θ��� ������ ȣ��
		this.setLocationRelativeTo(null);
		this.setSize(250, 240);
		this.setResizable(false);
		this.loginpanel = new LoginPanel();
		this.getContentPane().add(this.loginpanel, BorderLayout.CENTER);
	}

	// associate to "AutheticationManager" // �ƴ� ���(�ڽĺ��� �������� �ξ� ����)

	// inner class
	private void login(String UserType) throws Exception {
		bloginOK = true;
		userInfo.setId(this.loginpanel.tfUserName.getText());
		userInfo.setPassword(this.loginpanel.tfPassword.getText());
		userInfo.setAddress(this.loginpanel.tfAddress.getText());
		if (UserType.equals("�Ǹ���")) {
			userInfo.setUserType("�Ǹ���");
		} else {
			userInfo.setUserType("������");
		}
		this.dispose();
	}

	private void cancel() {
		this.dispose();
	}

	public class LoginPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		// components
		private JLabel lbUserName;
		private JLabel lbPassword;
		private JLabel lbCheckPassword;
		private JLabel lbaddress;
		private JTextField tfUserName;
		private JTextField tfPassword;
		private JTextField tfCheckPassword;
		private JTextField tfAddress;
		private JLabel lbUserType;
		private ButtonGroup bGroup;
		private JRadioButton rButton1;
		private JRadioButton rButton2;
		private JButton btnOK;
		private JButton btnCancel;

		// constructor
		public LoginPanel() {
			lbUserName = new JLabel("     ������̸� : ");
			this.add(lbUserName);
			tfUserName = new JTextField(10);
			this.add(tfUserName);
			lbPassword = new JLabel("       ��� ��ȣ : ");
			this.add(lbPassword);
			tfPassword = new JTextField(10);
			this.add(tfPassword);
			lbCheckPassword = new JLabel("��й�ȣ Ȯ�� : ");
			this.add(lbCheckPassword);
			tfCheckPassword = new JTextField(10);
			this.add(tfCheckPassword);
			lbaddress = new JLabel("                �ּ� : ");
			this.add(lbaddress);
			tfAddress = new JTextField(10);
			this.add(tfAddress);

			lbUserType = new JLabel("���� ���� : ");
			this.add(lbUserType);
			bGroup = new ButtonGroup();
			rButton1 = new JRadioButton("�Ǹ���");
			this.add(rButton1);
			rButton2 = new JRadioButton("������");
			this.add(rButton2);
			bGroup.add(rButton1);
			bGroup.add(rButton2);

			this.setBorder(new LineBorder(Color.red));

			btnOK = new JButton("Ȯ��");
			btnOK.addActionListener(new ButtonListener());
			btnOK.setActionCommand(btnOK.getText());
			this.add(btnOK);

			btnCancel = new JButton("���");
			btnCancel.addActionListener(new ButtonListener());
			btnCancel.setActionCommand(btnCancel.getText());
			this.add(btnCancel);
		}

		private class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if (event.getActionCommand().equals(btnOK.getText())) {
						if (loginpanel.tfUserName.getText().isEmpty()) {
							JOptionPane.showMessageDialog(loginpanel, "Please enter your username and password.",
									"signupfail", JOptionPane.INFORMATION_MESSAGE);
						} else if (loginpanel.tfPassword.getText().isEmpty()) {
							JOptionPane.showMessageDialog(loginpanel, "Please enter your username and password.",
									"signupfail", JOptionPane.INFORMATION_MESSAGE);
						} else if (loginpanel.tfCheckPassword.getText().isEmpty()) {
							JOptionPane.showMessageDialog(loginpanel, "Please check your password.", "signupfail",
									JOptionPane.INFORMATION_MESSAGE);

						} else if (loginpanel.tfAddress.getText().isEmpty()) {
							JOptionPane.showMessageDialog(loginpanel, "Please check your address.", "signupfail",
									JOptionPane.INFORMATION_MESSAGE);

						} else if (loginpanel.tfCheckPassword.getText().equals(loginpanel.tfPassword.getText())) {
							if (rButton1.isSelected()) {
								login(rButton1.getText());
							} else if (rButton2.isSelected()) {
								login(rButton2.getText());
							} else {
								JOptionPane.showMessageDialog(loginpanel, "Please select your UserType",
										"don't selecte", JOptionPane.INFORMATION_MESSAGE);
							}
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