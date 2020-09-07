package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import control.Hash;
import control.SearchData;

public class TPSearchBar extends JPanel {
	// textbox 필요, button, radiobutton 3개(추가),
	private JButton sButton;
	private JTextField textField;
	private JRadioButton rB1;
	private JRadioButton rB2;
	private JRadioButton rB3;
	private JRadioButton rB4;
	private JRadioButton rB5;
	private ButtonGroup rbGroup;
	private TPResultTable rTable;
	private Hash hash;
	private SearchData SData;
	private TPMainFrame tpMainFrame;
	
	public TPSearchBar(Hash hash, TPResultTable rTable, SearchData data) {
		this.hash = hash;
		this.rTable = rTable;
		this.SData = data
				;
		sButton = new JButton("검색");
		textField = new JTextField(15);
		textField.setText("상품명을 입력하세요");

		rB1 = new JRadioButton("상품", true);
		rB2 = new JRadioButton("가격");
		rB3 = new JRadioButton("재고");
		rB4 = new JRadioButton("유통기한");
		rB5 = new JRadioButton("위치");

		rbGroup = new ButtonGroup();

		rbGroup.add(rB1);
		rbGroup.add(rB2);
		rbGroup.add(rB3);
		rbGroup.add(rB4);
		rbGroup.add(rB5);

		this.add(textField);
		this.add(sButton);
		sButton.addActionListener(new ButtonListener());
		this.add(rB1);
		this.add(rB2);
		this.add(rB3);
		this.add(rB4);
		this.add(rB5);
	}

	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			try {

				if (event.getSource() == sButton) {
					if(!(textField.getText().equals("상품명을 입력하세요")))
					if (rB1.isSelected()) {
						if (textField.getText().isEmpty()) {
						} else {
							
							hash.searchProduct(textField.getText(), rTable);
			
							// 검색시간을 계산한다.
							SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date currentTime = new Date();
							String mTime = mSimpleDateFormat.format(currentTime);

							// 상품명을 전달 -> 인기검색(많이 검색한 상품)
							SData.setMostData(textField.getText());		
							
							// 상품명, 검색시간을 전달. -> 즐겨찾는 품목(검색기록)
							SData.setFavoritesData(textField.getText(), mTime);
							
						}
					}else if(rB2.isSelected()){
						if (textField.getText().isEmpty()) {
							
						} else {
							
							hash.PriceSort(textField.getText(), rTable);
							// 검색시간을 계산한다.
							SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date currentTime = new Date();
							String mTime = mSimpleDateFormat.format(currentTime);

							// 상품명을 전달 -> 인기검색(많이 검색한 상품)
							SData.setMostData(textField.getText());		
							
							// 상품명, 검색시간을 전달. -> 즐겨찾는 품목(검색기록)
							SData.setFavoritesData(textField.getText(), mTime);
						}
						
					}else if(rB3.isSelected()){
						if (textField.getText().isEmpty()) {
							
						} else {
							hash.StockSort(textField.getText(), rTable);
							// 검색시간을 계산한다.
							SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date currentTime = new Date();
							String mTime = mSimpleDateFormat.format(currentTime);

							// 상품명을 전달 -> 인기검색(많이 검색한 상품)
							SData.setMostData(textField.getText());		
							
							// 상품명, 검색시간을 전달. -> 즐겨찾는 품목(검색기록)
							SData.setFavoritesData(textField.getText(), mTime);
						}
						
					}else if(rB4.isSelected()){
						if (textField.getText().isEmpty()) {
							
						} else {
							hash.ShelflifeSort(textField.getText(), rTable);
							// 검색시간을 계산한다.
							SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
							Date currentTime = new Date();
							String mTime = mSimpleDateFormat.format(currentTime);

							// 상품명을 전달 -> 인기검색(많이 검색한 상품)
							SData.setMostData(textField.getText());		
							
							// 상품명, 검색시간을 전달. -> 즐겨찾는 품목(검색기록)
							SData.setFavoritesData(textField.getText(), mTime);
						}
						
					}else if(rB5.isSelected()){
						if (textField.getText().isEmpty()) {
							
						} else {
							if(tpMainFrame.getUserInfo() != null){
								TPGoogleMap googleMap = new TPGoogleMap(tpMainFrame.getUserInfo().getAddress(),hash.distanceSort(textField.getText(), rTable, tpMainFrame.getUserInfo().getAddress()));
								googleMap.setLocationRelativeTo(null);
								googleMap.setVisible(true);
								// 검색시간을 계산한다.
								SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
								Date currentTime = new Date();
								String mTime = mSimpleDateFormat.format(currentTime);
								// 상품명을 전달 -> 인기검색(많이 검색한 상품)
								SData.setMostData(textField.getText());		
								// 상품명, 검색시간을 전달. -> 즐겨찾는 품목(검색기록)
								SData.setFavoritesData(textField.getText(), mTime);
								
								
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void init(TPMainFrame tpMainFrame) {
		this.tpMainFrame = tpMainFrame;
	}
}
