package com.foodapp.paymentservice.utils.vault;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@Slf4j
public class DecryptionService {

    @Value("${encryption.key}")
    private String encryptionKey;

    @Value("${encryption.cipherMode}")
    private String cipherMode;

    private static final String ALGORITHM = "AES";

    public String decrypt(String encryptedValue) {

        try {
            Cipher cipher = Cipher.getInstance(cipherMode);
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptionKey.getBytes(), ALGORITHM);

            byte[] encryptedData = Base64.getDecoder().decode(encryptedValue);

            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedData);
            return new String(decryptedBytes);

        } catch (Exception e) {
            log.info("error : " + e.getMessage());
            return "";
        }
    }
}
