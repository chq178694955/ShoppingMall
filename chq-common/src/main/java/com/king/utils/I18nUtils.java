package com.king.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @创建人 chq
 * @创建时间 2019/12/3
 * @描述
 */
@Component
public class I18nUtils {

    private static MessageSource messageSource;

    public I18nUtils(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    public static String get(String msgKey,Object ...args){
        try{
            return messageSource.getMessage(msgKey,args, LocaleContextHolder.getLocale());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return msgKey;
    }

}
