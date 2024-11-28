package pack1;

import java.io.File;
import java.util.Scanner;

public class Authenticator {
	private String password = null;
	
	private Scanner scanner = new Scanner(System.in);

	protected Authenticator() {
		
	}
	
	public boolean authorized() {
		return password != null;
	}

	public String createPassword(String StorageFile) throws Exception {
		System.out.print("Please create a password: ");
		String pass = scanner.nextLine();
		File file = new File(StorageFile + "tmp");
		
		file.createNewFile();
		
		Encryptor.encryptFile(file.getPath(), StorageFile, pass);
		
		file.delete();
		
		return pass;
	}
	
	// Returns the decrypted file
	String authenticate(String StorageFile) throws Exception {
		File f = new File(StorageFile);
		
		String pass;
		
		try {
			if (!f.exists()) {
				pass = createPassword(StorageFile);
			} else {
				System.out.print("Please input your password: ");
				pass = scanner.nextLine();
			}
			
			
			this.password = pass;
			
			return Encryptor.decryptFileToString(StorageFile, pass);
		} catch (Exception e) {
			pass = null;
			System.out.println("Authentication failed.");
			
			throw e;
		}
	}
}