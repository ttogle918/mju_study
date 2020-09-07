package control;

//import LinkedList.LinkedList;
//import LinkedList.ListNode;

public class SearchData
{
	private String mostData[][];
	private String favoritesData[][];
	private int mostNum, favoritesNum;
	
	public SearchData()
	{
		mostData = new String[50][2];
		favoritesData = new String[10][2];
//		LinkedList mostData = new LinkedList();
//		�ʱ⿡ �����͸� "-" �� �Է�(��� ������ ��)
		for(int i = 0 ; i<10 ; i++)
		{
			favoritesData[i][1] = "-";
			favoritesData[i][0] = "-";	
		}
		
		// �ϴ� 50���� �����µ� �ڷ��� �� ��ŭ �÷���� �մϴ�. (���Ḯ��Ʈ�� �˻��κп� ���� ������ ���� �迭�� �ܼ��ϰ� �߽��ϴ�..) 
		for(int i =0 ; i<50 ; i++)
		{
			mostData[i][1] = "-";
			mostData[i][0] = "0";
		}
		
		favoritesNum = 0;
		mostNum = 0;
	}
	
	public String[][] getMostData()
	{
		return mostData;
	}
	
	public void setMostData(String Product)
	{
		boolean data=false;
		for(int i=0 ; i < mostNum+1 ; i++)
		{
			if(mostData[i][1].equals(Product))
			{
				 int temp = Integer.parseInt(mostData[i][0]) + 1;
				 System.out.println("���̳�?" + temp);
				 mostData[i][0] = temp + "";
				 data = true;
			}
		}
		
		if(data==false)
		{
			mostData[mostNum][0] = "1";
			mostData[mostNum][1] = Product;
			if(mostNum<=50) { mostNum++;}
		}
	
		for(int i=0 ; i < mostNum ; i++)
		{
			for(int j=0 ; j < mostNum-i ; j++)
			{
				int tempA = Integer.parseInt(mostData[j][0]);
				int tempB = Integer.parseInt(mostData[j+1][0]);
			//	System.out.println("tempA " + tempA + " tempB "+ tempB);
				if(tempA < tempB)
				{
					String tempStr;
					tempStr = mostData[j][0];
					mostData[j][0] = mostData[j+1][0];
					mostData[j+1][0] = tempStr;
				
					tempStr = mostData[j][1];
					mostData[j][1] = mostData[j+1][1];
					mostData[j+1][1] = tempStr;	
				}
			}
		}
	}
	
	public String[][] getFavoritesData()
	{
		return favoritesData;
	}
	
	public void setFavoritesData(String Product, String Date)
	{
		boolean data=false;
		
		// ���� ǰ�� �˻� ����
		for(int i=0; ( (i<favoritesNum+1) && ((favoritesNum+1) < 11) ) ; i++)
		{
			if(favoritesData[i][0].equals(Product))
			{
				for(int j=i ; j<favoritesNum-1 ; j++)
				{
					String temp;
					temp = favoritesData[j][0];
					favoritesData[j][0] = favoritesData[j+1][0];
					favoritesData[j+1][0] = temp;
						
					temp = favoritesData[j][1];
					favoritesData[j][1] = favoritesData[j+1][1];
					favoritesData[j+1][1] = temp;
				}
				
				favoritesData[favoritesNum-1][0] = Product;
				favoritesData[favoritesNum-1][1] = Date;
				data=true;
			}
			else if(favoritesData[i][0].equals(Product) && i==9)
			{
				favoritesData[9][0] = Product;
				favoritesData[9][1] = Date;
				data=true;
			}
		}
		
		// 10�� �ʰ��� ���� ������ ��Ϻ��� ����
		if(data==false)
		{
			// �ε��� 9�� �ڷᰡ ������ �� ������ ���� ������ ��Ϻ��� ����
			if(!favoritesData[9][0].equals("-"))
			{	
				for(int i=0; i<9 ; i++)
				{
					favoritesData[i][0] = favoritesData[i+1][0];
					favoritesData[i][1] = favoritesData[i+1][1];
				}
				favoritesData[9][0] = Product;
				favoritesData[9][1] = Date;
				//FNum--;
			}
			// �ε��� 0~9������ ������ ����
			else if(favoritesNum <= 9)
			{
				favoritesData[favoritesNum][0] = Product;
				favoritesData[favoritesNum][1] = Date;
				favoritesNum++;
			}
			
		}
	}
}
