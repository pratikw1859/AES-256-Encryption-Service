package com.app.encrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESEx {
	
	public static String plainText = "pratik";
	
	public static SecretKey key = keyGenerator();
	
	public static byte[] generateIV = generateIV();
	
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		System.out.println(keyGenerator());
		
		
		
		
		//System.out.println(encrypt(plainText, key, generateIV));		
		
		//String encrypt = encrypt(plainText, key, generateIV);
		
		//System.out.println(decrypt(encrypt, key, generateIV));		
		
		System.out.println(encrypt(plainText, key, generateIV));
		String encrypt = encrypt(plainText, key, generateIV);
		System.out.println(decrypt(encrypt, key, generateIV));
	}
	
	public static SecretKey keyGenerator() {
		KeyGenerator keyGenerator = null;
		SecretKey secretKey = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(256);
			secretKey = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return secretKey;
	}
	
	public static byte[] generateIV() {
		byte[] IV = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(IV);
		return IV;
	}
	
	public static String encrypt(String plaintext,SecretKey key,byte[] IV ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		
		IvParameterSpec ivSpec = new IvParameterSpec(IV);
		
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
		
		byte[] encryptedByte = cipher.doFinal(plaintext.getBytes());
		
		return Base64.getEncoder().encodeToString(encryptedByte);
	}
	
	public static String decrypt(String encryptedText, SecretKey key,byte[] IV ) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
		
		IvParameterSpec ivSpec = new IvParameterSpec(IV);
		
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
				
		byte[] decode = Base64.getDecoder().decode(encryptedText);

		byte[] decryptedByte = cipher.doFinal(decode);
		
		return new String(decryptedByte);
	}
}
