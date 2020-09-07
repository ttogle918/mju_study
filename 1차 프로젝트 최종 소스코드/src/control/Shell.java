package control;

import model.Product;

public class Shell {
	private static Product product[];
	private static int size;
	public Shell(){
		product = new Product[10];
		size = 0;
	}
	public static void intervalPriceSort(int begin, int end, int interval) {
        Product p;
        int j = 0;
        for (int i = begin + interval; i <= end; i = i + interval) {
            p = product[0];
            for (j = i - interval; j >= begin && p.getPrice() < product[j].getPrice(); j -= interval)
                product[j + interval] = product[j];
            product[j + interval] = p;
        }
    }
	// ���Ḯ��Ʈ�� �迭�� �ٲ���.
	public void setArray(Product p, String str){
		if(p == null){	return;	}
		while(p.next != null){
			if(p.getData() == str){
				product[size] = p;	size++;
			}
			p = p.next;
		}
		int i = 0;
		while(i < size){
			System.out.println(product[i++].toString()+i+"");	
		}
	}
	public void intervalNumSort(int begin, int end, int interval){
		Product p;
	    int j = 0;
	    for (int i = begin + interval; i <= end; i = i + interval) {
	        p = product[i];
	        for (j = i - interval; j >= begin && p.getPrice() < product[j].getPrice(); j -= interval)
	        	product[j + interval] = product[j];
	        product[j + interval] = p;
	    }
	}
    public static void shellSort(String str) {
        int interval = 0;
        int t = 1;		// �ܰ� ��
        interval = size / 2;
        while (interval >= 1) {
            for (int i = 0; i < interval; i++)
            	if(str.equals("price")){
            		intervalPriceSort(i, size-1, interval);	
            	}
            System.out.println("�� ���� " + t++ + " �ܰ�:  interval => " + interval);
            PricePrint();
            interval /= 2;
        }
    }
	public static void PricePrint(){
		System.out.println("��ǰ :"+ product[0].getData()+"���� :"+product[0].getType()+"���� :"+size);	
		for(int i = 0 ; i < size; i++){
			System.out.print(product[i].getPrice() + " ");			
		}
	}
}