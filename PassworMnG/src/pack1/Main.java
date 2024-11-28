package pack1;

public class Main {

	public static void main(String[] args) {
		int userInput = -1;

		while (userInput != 0) {

			Interfaces.drawInterface();

			userInput = Interfaces.scanner.nextInt();

			switch (userInput) {
			case 1:
				Interfaces.drawManager();
				break;
			case 2:
				Interfaces.drawGenerator();
				break;
			case 0: 
				break;
			default:
				System.out.println("\nInvalid Input!");
			}

		}
		Interfaces.exitPrompt();

	}

}