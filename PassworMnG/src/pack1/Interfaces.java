package pack1;

import java.util.Scanner;

public class Interfaces {

	static Scanner scanner = new Scanner(System.in);

	public static void drawInterface() {

		System.out.print("____________________\n" + "/// Password M&G \\\\\\\n\n" + "1- Manager\n" + "2- Generator\n\n"
				+ "0- Exit" + "\n\nInput: ");

	}

	public static void exitPrompt() {
		System.out.println("######\nExited");
	}

	public static void drawManager() {
		Authenticator authenticator = new Authenticator();
		try {
			Storage storage = new Storage(authenticator);
			
			int input = -1;
			while (input != 0) {
				System.out.println();

				System.out.print("____________________\n" + "/// Manager \\\\\\\n\n"  +  "1- Add\n" + "2- Read\n" + "0- Menu" + "\n\nInput: ");

				input = scanner.nextInt();

				switch (input) {
				case 1:
					System.out.println();
					System.out.print("\nEnter the name/website: ");
					String location = scanner.next();
					System.out.print("\nEnter the email: ");
					String email = scanner.next();
					System.out.print("\nEnter the password: ");
					String password = scanner.next();
					
					storage.save(location, email, password);
					break;
				case 2:
					System.out.println();
					storage.read();
				case 0:
					break;
				default:
					System.out.println("\nInvalid Input!");
				}
			}
			
		} catch (Exception e) {
			
		}
	}

	public static void drawGenerator() {
		int input = -1;
		while (input != 0) {
			System.out.println();

			System.out.print("____________________\n" + "/// Generator \\\\\\\n\n"  + "1- Generate\n" + "0- Menu" + "\n\nInput: ");

			input = scanner.nextInt();

			switch (input) {
			case 1:
				System.out.println();
				Generator.deliverPassword();
				break;
			case 0:
				break;
			default:
				System.out.println("\nInvalid Input!");
			}
		}
	}

	/*
	 * public static void spacer() { for (int i = 0; i < 50; i++) {
	 * System.out.println(); } }
	 */

}