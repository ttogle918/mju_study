package frames;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Product;

public class TPGoogleMap extends JDialog{
	String[] userAddress=null;
	String shopAddress="";
	String[] tempShopAddress=null;
	String color="yellow";
	public TPGoogleMap(String userAddress, Product[] products) {
		this.setLocationRelativeTo(null);
		this.setSize(500, 500);
		this.setResizable(false);
		this.userAddress = geoCoding(userAddress);
		try {
			for(int i=0; i < products.length; i++){
				tempShopAddress = geoCoding(products[i].getShopAddress());
				if(products[i].getShop().charAt(0)=='C'){
					color="purple";
				}else if(products[i].getShop().charAt(0)=='G'){
					color="blue";
				}else if(products[i].getShop().charAt(0)=='S'){
					color="green";
				}
				
				this.shopAddress =this.shopAddress +"&markers=color:"+color+"|label:"+products[i].getShop().charAt(0)+"|"+tempShopAddress[0]+","+tempShopAddress[1];
			}
			String imageUrl = "http://maps.google.com/maps/api/staticmap?center=" + this.userAddress[0]+","+this.userAddress[1]
					+ "&zoom=15&size=700x700&maptype=roadmap&markers=color:black|label:I|"+this.userAddress[0]+","+this.userAddress[1]+this.shopAddress+"&sensor=false";
			String destinationFile = "image.jpg";
			URL url = new URL(imageUrl);
			InputStream is = url.openStream();
			OutputStream os = new FileOutputStream(destinationFile);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		this.add(new JLabel(new ImageIcon(
				(new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600, java.awt.Image.SCALE_SMOOTH))));
	}
	public String[] geoCoding(String address) {
		String latitude = ""; // 위도
		String longitude = ""; // 경도
		String location[] = new String[2];
		String apiKey = "AIzaSyDKaKzfCBN51istGLDKqAZENbEn9AUHRFg";// AIzaSyBImu02E7b_WdhaEDnlKqAfDo-v4dcrhAM
		// UTF-8로 주소 인코딩
		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			// http://maps.googleapis.com/maps/api/geocode/xml?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=true
			// 구글 주소로 위경도 얻는 url(만약 프리미엄 API(유료고객)를 사용해야 한다면 아래 주소를 API에 맞게 변환해야
			// 한다.)
			// String googleUrl =
			// "http://maps.google.com/maps/api/geocode/xml?address="
			// + address + "&client="+apiKey+"&sensor=false&language=ko";
			String googleUrl = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + address + "&sensor=true";
			//System.out.println(googleUrl);

			DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = docBuilder.parse(googleUrl);
			doc.setDocumentURI(googleUrl);
			NodeList nodeMomList = doc.getDocumentElement().getChildNodes();
			if ("OK".equals(nodeMomList.item(1).getTextContent())) {
				for (int i = 0; i < nodeMomList.getLength(); i++) {
					Node row = nodeMomList.item(i);
					NodeList childList = row.getChildNodes();
					if ("result".equals(row.getNodeName())) {
						for (int a = 0; a < childList.getLength(); a++) {
							Node nodeList = childList.item(a);
							NodeList child1 = nodeList.getChildNodes();
							if ("geometry".equals(nodeList.getNodeName())) {
								for (int b = 0; b < child1.getLength(); b++) {
									Node nodeList1 = child1.item(b);
									NodeList child2 = nodeList1.getChildNodes();
									if ("location".equals(nodeList1.getNodeName())) {
										for (int c = 0; c < child2.getLength(); c++) {
											Node nodeList2 = child2.item(c);
											// NodeList child3 =
											// nodeList2.getChildNodes();
											if ("lat".equals(nodeList2.getNodeName())) {
												// 위도값
												latitude = nodeList2.getTextContent();
											} else if ("lng".equals(nodeList2.getNodeName())) {
												// 경도값
												longitude = nodeList2.getTextContent();
											}
										}
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
			} else {
				System.out.println("해당 주소의 좌표를 잃어오는 것에 실패함 에러코드" + nodeMomList.item(1).getTextContent());
				/////////////////////////////////////////////////////////////////////////////
				// 상태코드에 따른 실패처리를 개별적으로 해야함
				// -"OK"는 오류가 발생하지 않았음
				// -"ZERO_RESULTS"는 지오코딩이 성공했지만 반환된 결과가 없음
				// -"OVER_QUERY_LIMIT"는 할당량을 초과
				// -"REQUEST_DENIED"는 요청이 거부
				// -"INVALID_REQUEST"는 일반적으로 쿼리(address 또는 latlng)가 누락
				/////////////////////////////////////////////////////////////////////////////
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		location[0]=latitude;
		location[1]=longitude;
		return location;
	}
}
