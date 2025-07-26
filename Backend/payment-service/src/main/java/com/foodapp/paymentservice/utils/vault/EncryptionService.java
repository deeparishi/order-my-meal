package com.foodapp.paymentservice.utils.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Service
@Slf4j
public class EncryptionService {

    @Value("${encryption.key}")
    private String encryptionKey;

    @Value("${encryption.cipherMode}")
    private String cipherMode;

    private static final String ALGORITHM = "AES";

    public String encrypt(String value) {
        try {
            Cipher cipher = Cipher.getInstance(cipherMode);
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            byte[] encryptedBytes = cipher.doFinal(value.getBytes());

            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("Encryption error: {}", e.getMessage());
            return null;
        }
    }
}