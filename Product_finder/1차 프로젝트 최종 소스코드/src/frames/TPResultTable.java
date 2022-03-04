package frames;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import control.Hash;

public class TPResultTable extends JPanel {
	private JTable jTable;
	private JScrollPane jScollPane;
	private DefaultTableModel model;
	private Hash hash;
	private String inputStr[] = new String[6];

	public TPResultTable(Hash hash) {
		this.hash = hash;
		String cNames[] = { "��ǰ��", "��ǰ����", "��ǰ����", "�Ǹ�ó", "���", "�������"};
		Object data[][] = {};
		DefaultTableModel model = new DefaultTableModel(data, cNames);
		jTable = new JTable(model);
		jScollPane = new JScrollPane(jTable);
//		jTable.getTableHeader().addMouseListener(new TableHeadListener());
		this.add(jScollPane);
	}

	public void tableSetEmpty() {
		((DefaultTableModel) jTable.getModel()).setNumRows(0);
	}

	public void tableUpdate(String name, int price, String type, String shop, int stock, int shelflife) {
		inputStr[0] = name;
		inputStr[1] = Integer.toString(price);
		inputStr[2] = type;
		inputStr[3] = shop;
		inputStr[4] = Integer.toString(stock);
		inputStr[5] = Integer.toString(shelflife);
		((DefaultTableModel) jTable.getModel()).addRow(inputStr);
		((AbstractTableModel) jTable.getModel()).fireTableDataChanged();
	}

//	private class TableHeadListener extends MouseAdapter {
//
//		@Override
//		public void mouseClicked(MouseEvent e) {
//			int col = jTable.columnAtPoint(e.getPoint());
//			String name = jTable.getColumnName(col);
//			if(name.equals("��ǰ��")){
//				System.out.println("0");
//			}else if(name.equals("��ǰ����")){
//				hash.productSort(((DefaultTableModel) jTable.getModel()).getValueAt(row, column), this, col);
//				System.out.println("1");
//				
//			}else if(name.equals("��ǰ����")){
//				hash.productSort(productname, this, col);
//				System.out.println("2");
//			}else if(name.equals("�Ǹ�ó")){
//				hash.productSort(productname, this, col);
//				System.out.println("3");
//			}else if(name.equals("���")){
//				hash.productSort(productname, this, col);
//				System.out.println("4");
//			}
//
//		}
//	}
}
