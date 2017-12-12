package net.inforgyn.util;

import java.math.BigInteger;
import java.security.MessageDigest;

import net.inforgyn.exception.CriptografiaException;

public class Criptografar {
	public static String codificar(String criptografar) {
		try {
			// MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
			byte messageDigest[];

			messageDigest = algorithm.digest(criptografar.getBytes("UTF-8"));

			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}

			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new CriptografiaException("Erro ao criptografar senha");
		}
	}

	public static String gerarMD5(String senha){
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(senha.getBytes(),0,senha.length());
			return new BigInteger(1,md.digest()).toString(16);
		}catch (Exception e) {
			e.printStackTrace();
			throw new CriptografiaException("Erro ao criptografar senha");
		}
	}

	public static boolean equals(String senhaCodificada, String senhaSimples) {
		byte senha[] = codificar(senhaSimples).getBytes();
		byte senha2[] = senhaCodificada.getBytes();
		boolean result = MessageDigest.isEqual(senha, senha2);
		return result;
	}
}
