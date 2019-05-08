package com.fengjie.courseprogram.server;

import com.fengjie.courseprogram.constants.Constants;
import com.fengjie.courseprogram.model.param.EmailParam;
import com.fengjie.courseprogram.util.EmailClient;
import com.fengjie.courseprogram.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author fengjie
 * @date 2019:04:14
 */
@Service
public class EmailService {
    @Autowired
    @Qualifier("emailCache")
    private CaffeineCache emailCache;

    @Async("asyncServiceExecutor")
    public void sendTextEmail(EmailParam email) {
        EmailClient emailClient = EmailClient.getInstance();
        String emailCode;
        synchronized (emailClient) {
            Cache.ValueWrapper valueWrapper = emailCache.get(email.getEmail());
            if (valueWrapper == null) {
                emailCode = StringUtils.getEmailCode();
                emailCache.put(email.getEmail(), emailCode);
            } else {
                emailCode = (String) valueWrapper.get();
            }
        }
        emailClient.sendTextEmail(email.getEmail(), Constants.DEFAULT_SUB, emailCode);
    }

    public boolean checkEmailCode(String email, String code) {
        return emailCache.get(email) != null && code.equals((String) emailCache.get(email).get());
    }

}
