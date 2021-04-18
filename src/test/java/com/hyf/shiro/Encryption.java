package com.hyf.shiro;

import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha512Hash;
import org.apache.shiro.util.ByteSource;
import org.junit.jupiter.api.Test;

/**
 * 加密测试
 *
 * @author baB_hyf
 * @date 2021/04/14
 */
public class Encryption {

    /**
     * 加密hash
     */
    @Test
    public void hash() {
        // hash
        String s = new Md5Hash("source").toHex();
        // String s = new Md5Hash(new File("/")).toHex();
        System.out.println(s);

        int hashTime = 2;
        String salt = "salt";
        ByteSource credentialsSalt = ByteSource.Util.bytes(salt);
        String s1 = new Sha512Hash("source", salt, hashTime).toBase64();
        System.out.println(s1);
    }

    /**
     * 加密密码
     */
    @Test
    public void cipher() {
        AesCipherService cipherService = new AesCipherService();
        cipherService.setKeySize(256);

        // 创建一个测试密钥
        byte[] testKey = cipherService.generateNewKey().getEncoded();

        // 加密文件的字节
        byte[] fileBytes = new byte[1];
        byte[] encrypted = cipherService.encrypt(fileBytes, testKey).getBytes();
        System.out.println(encrypted);
    }
}
