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

import control.InsertProduct;
import frames.TPMainFrame;

public class TPProductID extends JDialog {
	private IPanel iPanel;
	private DPanel dPanel;
	private InsertProduct insertProduct;
	public TPProductID(InsertProduct insertProduct) {
		super(TPMainFrame.getInstance(), "ProductID", true); // 부모의 생성자 호출
		this.insertProduct = insertProduct;
		this.setLocationRelativeTo(null);
		this.setSize(300, 330);
		this.setResizable(false);
		this.iPanel = new IPanel();
		this.dPanel = new DPanel();
	}
	
	public void setModeInsert(){
		this.setSize(300, 330);
		this.getContentPane().removeAll();
		this.getContentPane().add(this.iPanel, BorderLayout.CENTER);
	}
	public void setModeDelete(){
		this.setSize(300, 150);
		this.getContentPane().removeAll();
		this.getContentPane().add(this.dPanel, BorderLayout.CENTER);
	}
	private void cancel() {
		this.dispose();
	}
	
	private void insert() {
		insertProduct.getHash().add(iPanel.tfdata.getText(), Integer.parseInt(iPanel.tfprice.getText()), 
				iPanel.tftype.getText(), iPanel.tfshop.getText(), Integer.parseInt(iPanel.tfstock.getText()), 
				Integer.parseInt(iPanel.tfshelflife.getText()), iPanel.tfshopAddress.getText());
		JOptionPane.showMessageDialog(iPanel, "상품이 추가 되었습니다", "상품 추가",
				JOptionPane.INFORMATION_MESSAGE);
		
		this.dispose();
	}
	
	private void delete() {
		if(insertProduct.getHash().remove(dPanel.tfdata.getText(),dPanel.tfshop.getText())){
			JOptionPane.showMessageDialog(dPanel, "상품이 삭제 되었습니다", "상품 삭제",
					JOptionPane.INFORMATION_MESSAGE);
		}else {
			JOptionPane.showMessageDialog(dPanel, "상품이 존재 하지 않습니다", "상품 없음",
					JOptionPane.INFORMATION_MESSAGE);
		}
		this.dispose();
	}
	
	public class IPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		// components
		private JLabel lbdate;
		private JLabel lbtype;
		private JLabel lbprice;
		private JLabel lbshop;
		private JLabel lbshopAddress;
		private JLabel lbstock;
		private JLabel lbshelflife;
		private JTextField tfdata;
		private JTextField tftype;
		private JTextField tfprice;
		private JTextField tfshop;
		private JTextField tfshopAddress;
		private JTextField tfstock;
		private JTextField tfshelflife;
		private JButton btnOK;
		private JButton btnCancel;
		
		// constructor
		public IPanel() {
			lbdate = new JLabel("     상품 이름 : ");
			this.add(lbdate);
			tfdata = new JTextField(15);
			this.add(tfdata);
			
			lbtype = new JLabel("     상품 종류 : ");
			this.add(lbtype);
			tftype = new JTextField(15);
			this.add(tftype);
			
			lbprice = new JLabel("     상품 가격 : ");
			this.add(lbprice);
			tfprice = new JTextField(15);
			this.add(tfprice);
			
			lbshop = new JLabel("     상점 이름 : ");
			this.add(lbshop);
			tfshop = new JTextField(15);
			this.add(tfshop);
			
			lbshopAddress = new JLabel("     상점 주소 : ");
			this.add(lbshopAddress);
			tfshopAddress = new JTextField(15);
			this.add(tfshopAddress);
			
			lbstock = new JLabel("     상품 재고 : ");
			this.add(lbstock);
			tfstock = new JTextField(15);
			this.add(tfstock);
			
			lbshelflife = new JLabel("     상품 기한 : ");
			this.add(lbshelflife);
			tfshelflife = new JTextField(15);
			this.add(tfshelflife);
		
			
			this.setBorder(new LineBorder(Color.red));

			btnOK = new JButton("확인");
			btnOK.addActionListener(new ButtonListener());
			btnOK.setActionCommand(btnOK.getText());
			this.add(btnOK);

			btnCancel = new JButton("취소");
			btnCancel.addActionListener(new ButtonListener());
			btnCancel.setActionCommand(btnCancel.getText());
			this.add(btnCancel);
		}
		
		private class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if (event.getActionCommand().equals(btnOK.getText())) {
						if (iPanel.tfdata.getText().isEmpty()) {
							
							JOptionPane.showMessageDialog(iPanel, "상품명을 입력해주세요.",
									"등록 실패", JOptionPane.INFORMATION_MESSAGE);
							
						} else if (iPanel.tftype.getText().isEmpty()) {
							
							JOptionPane.showMessageDialog(iPanel, "상품 종류를 입력해주세요.",
									"등록 실패", JOptionPane.INFORMATION_MESSAGE);
							
						} else if (iPanel.tfprice.getText().isEmpty()) {
							
							JOptionPane.showMessageDialog(iPanel, "상품 가격을 입력해주세요", "등록 실패",
									JOptionPane.INFORMATION_MESSAGE);
							

						} else if (iPanel.tfshop.getText().isEmpty()) {
							
							JOptionPane.showMessageDialog(iPanel, "상점 이름을 입력해주세요", "등록 실패",
									JOptionPane.INFORMATION_MESSAGE);

						}else if (iPanel.tfshopAddress.getText().isEmpty()) {
							
							JOptionPane.showMessageDialog(iPanel, "상점 주소를 입력해주세요",
									"등록 실패", JOptionPane.INFORMATION_MESSAGE);
						}else if (iPanel.tfstock.getText().isEmpty()) {
							
							JOptionPane.showMessageDialog(iPanel, "상품 재고를 입력해주세요.",
									"등록 실패", JOptionPane.INFORMATION_MESSAGE);
						}else if (iPanel.tfshelflife.getText().isEmpty()) {
							
							JOptionPane.showMessageDialog(iPanel, "상품 기한을 입력해주세요",
									"등록 실패", JOptionPane.INFORMATION_MESSAGE);
						}
						insert();
						
					}else {
						cancel();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	public class DPanel extends JPanel{
		private JLabel lbdate;
		private JLabel lbshop;
		private JTextField tfdata;
		private JTextField tfshop;
		private JButton btnOK;
		private JButton btnCancel;
		
		public DPanel(){
			lbdate = new JLabel("     상품 이름 : ");
			this.add(lbdate);
			tfdata = new JTextField(15);
			this.add(tfdata);
			
			lbshop = new JLabel("     상점 이름 : ");
			this.add(lbshop);
			tfshop = new JTextField(15);
			this.add(tfshop);
			
			btnOK = new JButton("확인");
			btnOK.addActionListener(new ButtonListener());
			btnOK.setActionCommand(btnOK.getText());
			this.add(btnOK);

			btnCancel = new JButton("취소");
			btnCancel.addActionListener(new ButtonListener());
			btnCancel.setActionCommand(btnCancel.getText());
			this.add(btnCancel);
			
		}
		
		private class ButtonListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					if (event.getActionCommand().equals(btnOK.getText())) {
						if (dPanel.tfdata.getText().isEmpty()) {
							JOptionPane.showMessageDialog(iPanel, "삭제할 상품명을 입력해주세요.",
									"등록 실패", JOptionPane.INFORMATION_MESSAGE);
						} else if (dPanel.tfshop.getText().isEmpty()) {
							JOptionPane.showMessageDialog(iPanel, "삭제할 상점 이름을 입력해주세요", "등록 실패",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							delete();
						}
					}else {
						cancel();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
