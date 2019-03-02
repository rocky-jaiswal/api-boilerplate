package de.rockyj.configuration

import org.apache.commons.codec.binary.Base64
import org.yaml.snakeyaml.Yaml
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object Secret {
    private val environment = System.getProperty("application.environment")
    private val keyBytes = System.getProperty("application.key").toByteArray()
    private val ivBytes  = System.getProperty("application.iv").toByteArray()

    private val text = this::class.java.classLoader.getResource("secrets/secrets.enc").readBytes()

    private val iv = IvParameterSpec(ivBytes)
    private val keySpec = SecretKeySpec(keyBytes, "AES")
    private val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")

    fun decryptSecrets(): Map<String, String>? {
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv)

        val original = cipher.doFinal(Base64.decodeBase64(text))
        return Yaml().load<Map<String, Map<String, String>>>(String(original))[environment]
    }

}

