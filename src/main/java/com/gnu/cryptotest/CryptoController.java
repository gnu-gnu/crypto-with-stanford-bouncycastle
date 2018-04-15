package com.gnu.cryptotest;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;

import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.generators.RSAKeyPairGenerator;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CryptoController {
	private static final int CERTAINTY = 80;
	private static final int KEY_BITS = 2048;
	private static RSAKeyParameters privatekey; // static variable is only for test purposes. mutable values SHOULD NOT be used in servlet.  

	@PostConstruct
	public void init(){
		Security.addProvider(new BouncyCastleProvider());
	}
	/**
	 * 
	 * generate RSA keypair, returns public key and store private key
	 * 
	 * @return publickey's n(p*q) and exp
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchProviderException
	 */
	@GetMapping("/generateKey")
	public @ResponseBody Map<String, String> generateKey() throws NoSuchAlgorithmException, NoSuchProviderException{
		RSAKeyPairGenerator keygen = new RSAKeyPairGenerator();
		SecureRandom sRandom = new SecureRandom();
		sRandom.nextBytes(new byte[8]);
		keygen.init(new RSAKeyGenerationParameters(new BigInteger("3", 16), sRandom, KEY_BITS, CERTAINTY));
		AsymmetricCipherKeyPair keypair = keygen.generateKeyPair();
		RSAKeyParameters pubkey = (RSAKeyParameters) keypair.getPublic();
		privatekey = (RSAKeyParameters) keypair.getPrivate();
		Map<String, String> map = new HashMap<String, String>();
		map.put("n", pubkey.getModulus().toString());
		map.put("e", pubkey.getExponent().toString());
		return map;
		
	}

	@PostMapping("/decrypt")
	public @ResponseBody String crypto(String message) throws InvalidCipherTextException {
		AsymmetricBlockCipher rsaEngine = new RSAEngine();
		rsaEngine = new PKCS1Encoding(rsaEngine);
		rsaEngine.init(false, privatekey);
		byte[] messageBytes = DatatypeConverter.parseHexBinary(message); // TODO sometimes, using new BigInteger(message, 16).toByteArray() in this feature causes 1byte length problem.
		return new String(rsaEngine.processBlock(messageBytes, 0, messageBytes.length));
	}
}
