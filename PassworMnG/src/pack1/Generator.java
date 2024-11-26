package pack1;

import java.security.SecureRandom;

public class Generator {

	private static final String ALL_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+[]{}|;:,.<>?";
	private SecureRandom random;

	private void randomNumberGenerator() {
        random = new SecureRandom();
    }

	private String generatePassword(int length) {
		String password = "";
		if (length <= 0) {
			System.out.println("Length must be greater than 0.");
			return "";
		}
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(ALL_CHARACTERS.length());
			password += ALL_CHARACTERS.charAt(index);
		}
		return password;
	}
	
	public static void deliverPassword() {
		Generator generator = new Generator();
        String myPassword = generator.generatePassword(12);
        System.out.println("Generated Password: " + myPassword);
	}
}