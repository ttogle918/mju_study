package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.Hash;

public class TPSearchBar extends JPanel {
	// textbox 필요, button, radiobutton 3개(추가),
	private JButton sButton;
	private JTextField textField;
	private JRadioButton rB1;
	private JRadioButton rB2;
	private JRadioButton rB3;
	private JRadioButton rB4;
	private ButtonGroup rbGroup;
	private TPResultTable rTable;
	private Hash hash;

	public TPSearchBar(Hash hash, TPResultTable rTable) {
		this.hash = hash;
		this.rTable = rTable;
		sButton = new JButton("검색");
		textField = new JTextField(15);
		textField.setText("상품명을 입력하세요");

		rB1 = new JRadioButton("상품", true);
		rB2 = new JRadioButton("가격");
		rB3 = new JRadioButton("재고");
		rB4 = new JRadioButton("유통기한");

		rbGroup = new ButtonGroup();

		rbGroup.add(rB1);
		rbGroup.add(rB2);
		rbGroup.add(rB3);
		rbGroup.add(rB4);

		this.add(textField);
		this.add(sButton);
		sButton.addActionListener(new ButtonListener());
		this.add(rB1);
		this.add(rB2);
		this.add(rB3);
		this.add(rB4);
	}

	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {

				if (event.getSource() == sButton) {
					if (rB1.isSelected()) {
						if (textField.getText().isEmpty()) {
							
						} else {
							
							hash.searchProduct(textField.getText(), rTable);
						}
					}else if(rB2.isSelected()){
						if (textField.getText().isEmpty()) {
							
						} else {
							
							hash.PriceSort(textField.getText(), rTable);
						}
						
					}else if(rB3.isSelected()){
						if (textField.getText().isEmpty()) {
							
						} else {
							hash.StockSort(textField.getText(), rTable);
						}
						
					}else if(rB4.isSelected()){
						if (textField.getText().isEmpty()) {
							
						} else {
							hash.ShelflifeSort(textField.getText(), rTable);
						}
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
