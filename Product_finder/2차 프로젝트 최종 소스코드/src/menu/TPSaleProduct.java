package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import control.EventData;
import frames.TPMainFrame;

public class TPSaleProduct extends JDialog {
	private JButton rB1;
	private JButton rB2;
	private JButton rB3;
	private EventData eData;
	private ButtonGroup rbGroup;
	private static JTable jTable;
	private JScrollPane jScollPane;
	private DefaultTableModel model;
	
	public String cNames[] = {"상품", "가격", "유통기한"};
	
	public TPSaleProduct(TPMainFrame tpMainFrame, EventData eData){
		super(tpMainFrame, "할인품목", true);
		JPanel panel = new JPanel();
		this.eData = eData;
		rB1 = new JButton("유통기한임박");
		rB2 = new JButton("재고할인");
		rB3 = new JButton("마감할인");
		rbGroup = new ButtonGroup();
		
		rB1.addActionListener(new ButtonListener());
		rB2.addActionListener(new ButtonListener());
		rB3.addActionListener(new ButtonListener());
		
		rbGroup.add(rB1);
		rbGroup.add(rB2);
		rbGroup.add(rB3);
		
		panel.add(BorderLayout.NORTH, rB1);
		panel.add(BorderLayout.NORTH, rB2);
		panel.add(BorderLayout.NORTH, rB3);
		
		this.add(BorderLayout.NORTH, panel);
		
		setData(eData.getLifeSaleList());
		this.add(BorderLayout.CENTER, jTable);
		this.setSize(400, 400);
		 jTable.setGridColor(Color.red);
		//this.setForeground(Color.red);
		Object data[][] = {};
		DefaultTableModel model = new DefaultTableModel(data, cNames);
		
		jTable = new JTable(model);
		jScollPane = new JScrollPane(jTable);
		this.add(jScollPane);	
	}
	
	public void setData(String list[][]){
		String DataArray[][] = list;
		Object data[][] =
	    		  { {DataArray[0][0], DataArray[0][1], DataArray[0][2]}, 
	    			{DataArray[1][0], DataArray[1][1], DataArray[1][2]},
					{DataArray[2][0], DataArray[2][1], DataArray[2][2]},
					{DataArray[3][0], DataArray[3][1], DataArray[3][2]},
					{DataArray[4][0], DataArray[4][1], DataArray[4][2]},
					{DataArray[5][0], DataArray[5][1], DataArray[5][2]},
					{DataArray[6][0], DataArray[6][1], DataArray[6][2]},
					{DataArray[7][0], DataArray[7][1], DataArray[7][2]},
					{DataArray[8][0], DataArray[8][1], DataArray[8][2]},
					{DataArray[9][0], DataArray[9][1], DataArray[9][2]}};
	  
	    model = new DefaultTableModel(data, cNames);
		jTable = new JTable(model);
		JScrollPane jScollPane = new JScrollPane(jTable);
		jTable.getColumnModel().getColumn(0).setPreferredWidth(10); // column 너비 설정 
		jTable.getTableHeader().setReorderingAllowed(false); // 이동 불가능
		jTable.getTableHeader().setResizingAllowed(false);   // 크기 변경 불가능 
		this.add(jScollPane);
	}
	public void tableSetEmpty() {
		((DefaultTableModel) jTable.getModel()).setNumRows(0);
	}

	public void tableUpdate(String[][] list, String str) {
		if(list.length < 10){
			((DefaultTableModel) jTable.getModel()).addRow(list[0]);
			((AbstractTableModel) jTable.getModel()).fireTableDataChanged();	
		}
		// 테이블 헤드 바꾸기.
		this.cNames[2] = str;
		jTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue(cNames[2]);
		jTable.getTableHeader().repaint();
		for(int i =0; i<10; i++){
			((DefaultTableModel) jTable.getModel()).addRow(list[i]);
			((AbstractTableModel) jTable.getModel()).fireTableDataChanged();	
			jTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue(cNames[2]);	
		}
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			try {
				if (e.getSource()==rB1) {
					tableSetEmpty();
					tableUpdate(eData.getLifeSaleList(), "유통기한");
				}else if(e.getSource()==rB2){
					tableSetEmpty();
					tableUpdate(eData.getStockSaleList(), "재고");
					
				} else if(e.getSource()==rB3){
					tableSetEmpty();
					tableUpdate(eData.getTimeSaleList(), "-");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
