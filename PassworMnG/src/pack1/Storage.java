package pack1;

public class Storage {

	private static final String StorageFile = "data.txt";

	private String data = "";

	protected Storage(Authenticator auth) throws Exception {
		auth.authenticate(StorageFile);
	}

	public void save(String location, String username, String password) {
		data += location + "," + username + "," + password + '\n';
		try {
			Encryptor.encryptStringToFile(data, StorageFile, password);
		} catch (Exception e) {
			System.out.println("Failed to save into file.");
			e.printStackTrace();
		}
		
	}

	public void read() {
		if (data.length() <= 0) {
			System.out.println("No entries.");
			return;
		}
		for (String line : data.split("\n")) {
			String[] parts = line.split(",");
			if (parts.length == 3) {
				System.out.println("Location: " + parts[0] + ", Username: " + parts[1] + ", Password: " + parts[2]);
			} else {
				System.out.println("Invalid entry found in file.");
			}
		}
	}
}