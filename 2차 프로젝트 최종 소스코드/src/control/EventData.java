package control;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import javax.swing.JEditorPane;

import model.Product;

public class EventData {
	private Vector<Integer> LifeData;	//product의 hashValue
	private Vector<Integer> StockData;	// hashValue of many stock
	private int LifeSale[];
	private int StockSale[];
	private int TimeSale[];
	
	private String[][] lifeSaleList;
	private String[][] timeSaleList;
	private String[][] stockSaleList;
	
	private Random random;
	private Hash hash;
	long curr;
	SimpleDateFormat sdf;
	String datetime;
	public EventData(Hash hash){
		random = new Random();
		LifeData = new Vector<Integer>();
		LifeSale = new int[10];
		StockSale = new int[10];
		TimeSale = new int[10];
		StockData = new Vector<Integer>();
		lifeSaleList = new String[10][3];
		stockSaleList = new String[10][3];
		timeSaleList = new String[10][3];
		this.hash = hash;
		curr = System.currentTimeMillis();
	    sdf = new SimpleDateFormat("yyyy/MM/dd");
	    datetime = sdf.format(new Date(curr));
	    datetime = datetime.substring(0, 4)+datetime.substring(5, 7)+datetime.substring(8, 10);
	    
	}
	public void init(){
		this.setLifeSale();
		this.setStockSale();
		this.setTimeSale();
	}
	public void setLifeData(int hashValue) {	
		LifeData.add(hashValue);  
	}
	public void setStockData(int hashValue) {
		StockData.add(hashValue);
	}
	public void setLifeSale(){
		Product p;
		if(LifeData.size()==0){
			this.lifeSaleList[0][0] = "유통기한이";
			this.lifeSaleList[0][1] = "다가오는 것이";
			this.lifeSaleList[0][2] = "없습니다";
			return;
		}
		
	    for(int i = 0; i < 10; i++){
			if(LifeData.size() > 10){
				this.LifeSale[i] = random.nextInt(LifeData.size());
				p = hash.getHashTable()[LifeData.elementAt(StockSale[i])];
			}else{
				if(LifeData.size() == i)	return;
				p = hash.getHashTable()[LifeData.elementAt(i)];
			}
			while(p != null){
				if(p.getShelfLife()-Integer.parseInt(datetime) > hash.getShelfLife()){
					this.lifeSaleList[i][0] = p.getData();
					this.lifeSaleList[i][1] = Integer.toString(p.getPrice()/10*7);
					this.lifeSaleList[i][2] = Integer.toString(p.getStock());
					break;
				}else{
					p = p.next;
				}
			}
		}    
	}
	public void setStockSale(){
		Product p;
		if(StockData.size()==0){
			this.stockSaleList[0][0] = "재고할인";
			this.stockSaleList[0][1] = "품목이";
			this.stockSaleList[0][2] = "없습니다";
			return;
		}
		for(int i = 0; i < 10; i++){
			if(StockData.size() > 10){
				this.StockSale[i] = random.nextInt(StockData.size());
				p = hash.getHashTable()[StockData.elementAt(StockSale[i])];
			}else{
				if(StockData.size() == i)	return;
				p = hash.getHashTable()[StockData.elementAt(i)];
			}
			while(p != null){
				if(p.getStock() > hash.getStock()){
					this.stockSaleList[i][0] = p.getData();
					this.stockSaleList[i][1] = Integer.toString(p.getPrice()/10*7);
					this.stockSaleList[i][2] = Integer.toString(p.getStock());
					break;
				}else{
					p = p.next;
				}
			}
		}	
	}
	public void setTimeSale(){
		Product p;
		long curr = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String date = sdf.format(new Date(curr));
		// 오후 8시 이후 타임세일.
		if(Integer.parseInt(date) < 20){
				timeSaleList[0][0] = "아직";
				timeSaleList[0][1] = "마감세일 시간이";
				timeSaleList[0][2] = "아닙니다";	
				return;
		}
		for(int i = 0; i < 10; i++){
			if(this.StockData.size() + this.LifeData.size() < 11){
				if(LifeData.size() > i){	
					p = hash.getHashTable()[LifeData.elementAt(i)];
				}else if(this.LifeData.size() >= i && this.StockData.size()!=0){
					p = hash.getHashTable()[StockData.elementAt(i)];
				}else if(this.LifeData.size() + this.StockData.size() ==i){
					return;
				}else{
					return;
				}
			}else{
				TimeSale[i] = random.nextInt(StockData.size()+LifeData.size());	
				if(StockData.size() > TimeSale[i]){
					p = hash.getHashTable()[StockData.elementAt(StockSale[i])];					
				}else{
					p = hash.getHashTable()[StockData.elementAt(LifeSale[i])];						
				}
			}
			while(p.next != null){
				if(p.getStock() > hash.getStock() ||
						Integer.parseInt(datetime)-p.getShelfLife() <= hash.getShelfLife()){
					this.timeSaleList[i][0] = p.getData();
					this.timeSaleList[i][1] = Integer.toString(p.getPrice()/10*7);
					this.timeSaleList[i][2] = "-";
					break;
				}else{
					p = p.next;
				}
			}
		}		
	}
	public Vector<Integer> getLifeData(){	return LifeData;	}
	public Vector<Integer> getStockData(){	return StockData;	}

	public String[][] getLifeSaleList() {	return lifeSaleList;	}
	public String[][] getTimeSaleList() {	return timeSaleList;	}
	public String[][] getStockSaleList() {	return stockSaleList;	}
}
