package menu;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import control.SearchData;
import frames.TPMainFrame;

public class TPMostSearched extends JDialog
{
	public TPMostSearched(TPMainFrame tpMainFrame, SearchData SData)
	{
		super(tpMainFrame, "�α�˻�", true);
		
		String DataArray[][] = new String[10][2];
		DataArray = SData.getMostData();
		
	    String cNames[] = {"����", "��ǰ"};
	    Object data[][] = { {1, DataArray[0][1]}, 
							{2, DataArray[1][1]},
							{3, DataArray[2][1]},
							{4, DataArray[3][1]},
							{5, DataArray[4][1]},
							{6, DataArray[5][1]},
							{7, DataArray[6][1]},
							{8, DataArray[7][1]},
							{9, DataArray[8][1]},
							{10, DataArray[9][1]}};
	    
		DefaultTableModel model = new DefaultTableModel(data, cNames);
		
		JTable jTable = new JTable(model);
		JScrollPane jScollPane = new JScrollPane(jTable);
		jTable.getColumnModel().getColumn(0).setPreferredWidth(10); // column �ʺ� ���� 
		jTable.getTableHeader().setReorderingAllowed(false); // �̵� �Ұ���
		jTable.getTableHeader().setResizingAllowed(false);   // ũ�� ���� �Ұ��� 
		this.add(jScollPane);
		
		this.setLocationRelativeTo(null);
		this.setSize(250, 250);
	}
}
