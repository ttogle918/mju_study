package menu;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import control.SearchData;
import frames.TPMainFrame;

public class TPFavorites extends JDialog
{	
	public TPFavorites(TPMainFrame tpMainFrame, SearchData SData)
	{
		super(tpMainFrame, "�α� �˻�", true);
		
		String DataArray[][] = new String[10][2];
		DataArray = SData.getFavoritesData();
		
	    String cNames[] = { "��ǰ", "�˻� ��¥"};
		Object data[][] = { {DataArray[0][0], DataArray[0][1]}, 
							{DataArray[1][0], DataArray[1][1]},
							{DataArray[2][0], DataArray[2][1]},
							{DataArray[3][0], DataArray[3][1]},
							{DataArray[4][0], DataArray[4][1]},
							{DataArray[5][0], DataArray[5][1]},
							{DataArray[6][0], DataArray[6][1]},
							{DataArray[7][0], DataArray[7][1]},
							{DataArray[8][0], DataArray[8][1]},
							{DataArray[9][0], DataArray[9][1]}};					
		
		DefaultTableModel model = new DefaultTableModel(data, cNames);
		
		JTable jTable = new JTable(model);
		JScrollPane jScollPane = new JScrollPane(jTable);
		jTable.getColumnModel().getColumn(0).setPreferredWidth(30); // column �ʺ� ���� 
		jTable.getTableHeader().setReorderingAllowed(false); // �̵� �Ұ���
		jTable.getTableHeader().setResizingAllowed(false);   // ũ�� ���� �Ұ��� 
		this.add(jScollPane);
		
		this.setLocationRelativeTo(null);
		this.setSize(250, 250);
	}
}
