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
//		초기에 데이터를 "-" 로 입력(기록 없음을 뜻)
		for(int i = 0 ; i<10 ; i++)
		{
			favoritesData[i][1] = "-";
			favoritesData[i][0] = "-";	
		}
		
		// 일단 50개로 지었는데 자료의 수 만큼 늘려줘야 합니다. (연결리스트를 검색부분에 쓰면 오류가 나서 배열로 단순하게 했습니다..) 
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
				 System.out.println("몇이냐?" + temp);
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
		
		// 같은 품목 검색 갱신
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
		
		// 10개 초과시 가장 오래된 기록부터 삭제
		if(data==false)
		{
			// 인덱스 9에 자료가 있으면 그 때부터 가장 오래된 기록부터 삭제
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
			// 인덱스 0~9까지는 데이터 저장
			else if(favoritesNum <= 9)
			{
				favoritesData[favoritesNum][0] = Product;
				favoritesData[favoritesNum][1] = Date;
				favoritesNum++;
			}
			
		}
	}
}
