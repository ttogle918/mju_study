package control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import model.UserInfo;

public class Member {
	private UserInfo userInfo = new UserInfo();
	public boolean assertUserID(String id) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		File file = new File("member");
		Scanner scanner = new Scanner(file);
		while(scanner.hasNext()){
			this.userInfo.setId(scanner.next());
			this.userInfo.setPassword(scanner.next());
			if(this.userInfo.getId().equals(id)){
				scanner.close();
				return true;
			}
		}
		scanner.close();
		return false;
	}
	public void SignUp(UserInfo userInfo){
		try {
			BufferedWriter fileWriter = new BufferedWriter(new FileWriter("member", true));
			fileWriter.newLine();
			fileWriter.write(userInfo.getId());
			fileWriter.write(" ");
			fileWriter.write(userInfo.getPassword());
			fileWriter.newLine();
			fileWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String getPassword(String id) {
		// TODO Auto-generated method stub
		return this.userInfo.getPassword();
	}

}
