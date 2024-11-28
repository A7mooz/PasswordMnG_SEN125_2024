package pack1;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Base64.Decoder;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class Encryptor {
	private static Encoder encoder = Base64.getEncoder();
	private static Decoder decoder = Base64.getDecoder();
	

    // Method to encrypt a file
    public static void encryptFile(String inputFilePath, String outputFilePath, String key) throws Exception {
        // Create SecretKeySpec from the provided key (AES)
        SecretKeySpec secretKey = new SecretKeySpec(getKeyFromPassword(key), "AES");

        // Get AES cipher instance
        Cipher cipher = Cipher.getInstance("AES");

        // Initialize cipher for encryption
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Read the input file
        FileInputStream inputFile = new FileInputStream(inputFilePath);
        FileOutputStream outputFile = new FileOutputStream(outputFilePath);
        CipherOutputStream cipherOutputStream = new CipherOutputStream(outputFile, cipher);

        // Encrypt the file and write it to the output file
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputFile.read(buffer)) != -1) {
            cipherOutputStream.write(buffer, 0, bytesRead);
        }

        // Close all streams
        cipherOutputStream.close();
        inputFile.close();
        outputFile.close();
    }
    
    // Method to encrypt a file
    public static void encryptStringToFile(String input, String outputFilePath, String key) throws Exception {
    	// Create AES key specification
        SecretKeySpec secretKey = new SecretKeySpec(getKeyFromPassword(key), "AES");

        // Create a Cipher object for AES encryption
        Cipher cipher = Cipher.getInstance("AES");

        // Initialize the Cipher with the encryption mode and key
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt the string (convert the string to bytes, then encrypt)
        byte[] encryptedData = cipher.doFinal(input.getBytes());
        
        
        FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath);
        
        fileOutputStream.write(encryptedData);
        
        fileOutputStream.close();
    }

    // Method to decrypt a file and store the result in a variable
    public static String decryptFileToString(String inputFilePath, String key) throws Exception {
        // Create SecretKeySpec from the provided key (AES)
        SecretKeySpec secretKey = new SecretKeySpec(getKeyFromPassword(key), "AES");

        // Get AES cipher instance
        Cipher cipher = Cipher.getInstance("AES");

        // Initialize cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Read the encrypted input file
        FileInputStream inputFile = new FileInputStream(inputFilePath);
        CipherInputStream cipherInputStream = new CipherInputStream(inputFile, cipher);

        // Use ByteArrayOutputStream to store the decrypted data
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        // Convert the byte array to a string (assuming it's text-based data)
        String decryptedData = outputStream.toString();

        // Close streams
        cipherInputStream.close();
        inputFile.close();
        outputStream.close();

        return decryptedData;
    }
    
    private static byte[] getKeyFromPassword(String password) throws Exception {
        // Use SHA-256 to hash the password
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        return sha.digest(password.getBytes());
    }
    
	// Method to generate a cryptographic key from a password
	private static SecretKey generateKeyFromPassword(String password, byte[] salt) throws Exception {
		// Set PBKDF2 parameters
		int iterations = 65536; // Iteration count for PBKDF2
		int keyLength = 256; // Length of the derived key (in bits)

		// Create the PBKDF2 specification
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

		// Create a SecretKeyFactory for PBKDF2
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

		// Generate the key from the password and salt
		SecretKey secretKey = keyFactory.generateSecret(keySpec);

		return secretKey;
	}

	// Method to generate a random salt
	private static byte[] generateSalt() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] salt = new byte[16]; // 16 bytes salt
		secureRandom.nextBytes(salt);
		return salt;
	}

	public static String cipher(String password) throws Exception {
		byte[] salt = generateSalt();
		SecretKey secretKey = generateKeyFromPassword(password, salt);

		return encoder.encodeToString(secretKey.getEncoded()) + encoder.encodeToString(salt);
	}

	public static boolean compare(String hashed, String input) {
		try {
			int i = hashed.indexOf("=");

			if (i == -1) {
				return false;
			}

			String saltBase64 = hashed.substring(i + 1);

			byte[] salt = decoder.decode(saltBase64);

			SecretKey hashedInput = generateKeyFromPassword(input, salt);

			String encodedHashedInput = encoder.encodeToString(hashedInput.getEncoded()) + saltBase64;

			if (hashed.equals(encodedHashedInput)) {
				return true;
			}

			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
