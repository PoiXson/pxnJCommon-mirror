package com.poixson.utils;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public final class CryptUtils {
	private CryptUtils() {}



	public static enum CryptType {
		MD5(        "MD5"),
		SHA1(       "SHA1"),
		SHA256(     "SHA256"),
		SHA512(     "SHA512"),
		HMAC_MD5(   "HmacMD5"),
		HMAC_SHA1(  "HmacSHA1"),
		HMAC_SHA256("HmacSHA256");

		public final String key;

		private CryptType(final String key) {
			this.key = key;
		}

		@Override
		public String toString() {
			return this.key;
		}

	}



	// md5
	public static String MD5(final String data) {
		return Crypt(CryptType.MD5, data);
	}
	// sha1
	public static String SHA1(final String data) {
		return Crypt(CryptType.SHA1, data);
	}
	// sha256
	public static String SHA256(final String data) {
		return Crypt(CryptType.SHA256, data);
	}
	// sha512
	public static String SHA512(final String data) {
		return Crypt(CryptType.SHA512, data);
	}



	// perform crypt
	public static String Crypt(final CryptType type, final String data) {
		return Crypt(type.toString(), data);
	}
	public static String Crypt(final String typeStr, final String data) {
		try {
			final MessageDigest md = MessageDigest.getInstance(typeStr);
			if (md == null) throw new NoSuchAlgorithmException(typeStr);
			md.update( data.getBytes() );
			return toHex( md.digest() );
		} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
//			log().trace(e);
		}
		return null;
	}



	// crypt with key
	public static String Crypt(final String typeStr, final String key, final String data) {
		try {
			final Mac mac = Mac.getInstance(typeStr);
			if (mac == null) throw new NoSuchAlgorithmException(typeStr);
			mac.init(new SecretKeySpec(key.getBytes(), typeStr));
			return toHex( mac.doFinal( data.getBytes() ) );
		} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
//			log().trace(e);
		} catch (InvalidKeyException e) {
e.printStackTrace();
//			log().trace(e);
		}
		return null;
	}



	// encrypted data checksum
	public static String HMacMD5(final String key, final String data) {
		return HMac(CryptType.HMAC_MD5, key, data);
	}
	public static String HMacSHA1(final String key, final String data) {
		return HMac(CryptType.HMAC_SHA1, key, data);
	}
	public static String HMacSHA256(final String key, final String data) {
		return HMac(CryptType.HMAC_SHA256, key, data);
	}
	public static String HMac(final CryptType type, final String key, final String data) {
		return HMac(type.toString(), key, data);
	}



	public static String HMac(final String typeStr, final String key, final String data) {
		try {
			final Mac mac = Mac.getInstance(typeStr);
			if (mac == null) throw new NoSuchAlgorithmException(typeStr);
			mac.init(new SecretKeySpec(key.getBytes(), typeStr));
			return toHex( mac.doFinal( data.getBytes() ) );
		} catch (NoSuchAlgorithmException e) {
e.printStackTrace();
//			log().trace(e);
		} catch (InvalidKeyException e) {
e.printStackTrace();
//			log().trace(e);
		}
		return null;
	}



	// base64 encode
	public static String Base64Encode(final String data) {
		if (Utils.isEmpty(data))
			return data;
		return new String(Base64Encode(data.getBytes()));
	}
	public static byte[] Base64Encode(final byte[] data) {
		if (Utils.isEmpty(data))
			return data;
		return Base64.getEncoder().encode(data);
	}
	// base64 decode
	public static String Base64Decode(final String data) {
		if (Utils.isEmpty(data))
			return data;
		return new String(Base64Decode(data.getBytes()));
	}
	public static byte[] Base64Decode(final byte[] data) {
		if (Utils.isEmpty(data))
			return data;
		return Base64.getDecoder().decode(data);
	}



	// hex encode
	public static String toHex(final String data) {
		return toHex(data.getBytes());
	}
	public static String toHex(final byte[] data) {
		if (data == null || data.length == 0) return null;
		final StringBuilder str = new StringBuilder(data.length * 2);
		final Formatter formatter = new Formatter(str);
		for (final byte b : data) {
			formatter.format("%02x", Byte.valueOf(b));
		}
		Utils.SafeClose(formatter);
		return str.toString();
//TODO: is this useful?
//		byte[] byteData = md.digest();
//		StringBuffer hexString = new StringBuffer();
//		for (int i = 0; i < byteData.length; i++) {
//			String hex = Integer.toHexString(0xFF & byteData[i]);
//			if (hex.length() == 1) {
//				hexString.append('0');
//			}
//			hexString.append(hex);
//		}
//		return hexString.toString();
	}
	// hex decode
	public static byte[] fromHex(final String hex) {
		return fromHex(hex.toCharArray());
	}
	public static byte[] fromHex(final char[] hex) {
		if (hex == null || hex.length == 0) return null;
		final int length = hex.length / 2;
		byte[] out = new byte[length];
		for (int i=0; i<length; i++) {
			final int high = Character.digit(hex[i * 2], 16);
			final int low = Character.digit(hex[(i * 2) + 1], 16);
			int value = (high << 4) | low;
			if (value > 127) {
				value -= 256;
			}
			out[i] = (byte) value;
		}
		return out;
	}



}
