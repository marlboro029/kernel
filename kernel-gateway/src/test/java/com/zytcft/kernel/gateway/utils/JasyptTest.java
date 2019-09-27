package com.zytcft.kernel.gateway.utils;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.inject.Inject;

/**
 * Created by wangliang on 2019/9/26.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
@EnableEncryptableProperties
public class JasyptTest {

    @Resource
    private StringEncryptor stringEncryptor;

    @Test
    public void encode() {
        String text = "pig";
        String encrypt = stringEncryptor.encrypt(text);
        log.info("encrypt = {}", encrypt);
    }

    @Test
    public void decode() {
        String text = "gPFcUOmJm8WqM3k3eSqS0Q==";
        String decrypt = stringEncryptor.decrypt(text);
        log.info("decrypt = {}", decrypt);
    }
}
