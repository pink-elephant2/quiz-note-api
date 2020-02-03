package com.api.note.quiz.util;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Advanced Encryption Standard ユーティリティ
 */
public class AesUtils {

	/**
	 * キーを生成する
	 */
	public static String createKey() throws NoSuchAlgorithmException {
		// 生成key
		KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		keyGenerator.init(128);
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] keybytes = secretKey.getEncoded();
		return Base64.encodeBase64String(keybytes);
	}

	/**
	 * 暗号化
	 */
	public static String encrypt(String key, String data) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] keybytes = Base64.decodeBase64(key);
		Key aesKey = new SecretKeySpec(keybytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] result = cipher.doFinal(data.getBytes());
		return Base64.encodeBase64String(result);
	}

	/**
	 * 複合
	 */
	public static String decrypt(String key, String data) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		byte[] keybytes = Base64.decodeBase64(key);
		Key aesKey = new SecretKeySpec(keybytes, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, aesKey);
		byte[] result = cipher.doFinal(Base64.decodeBase64(data));
		return new String(result);
	}
}
