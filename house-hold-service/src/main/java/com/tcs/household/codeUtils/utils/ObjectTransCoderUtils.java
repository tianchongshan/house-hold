package com.tcs.household.codeUtils.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author lushaozhong
 * @version 1.0
 * @date 2018-06-26 15:49
 */
@Slf4j
public class ObjectTransCoderUtils {
    /**
     * 序列化对象
     * @param value
     * @return
     */
    public static byte[] serialize(Object value) {
        if (value == null) {
            throw new NullPointerException("Can't serialize null");
        }
        byte[] rv=null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        try {
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(value);
            os.close();
            bos.close();
            rv = bos.toByteArray();
        } catch (IOException e) {
            log.error("Non-serializable object", e);
        } finally {
            try {
                if(os!=null){
                    os.close();
                }
                if(bos!=null) {
                    bos.close();
                }
            }catch (Exception e2) {
                log.error("io close exception");
            }
        }
        return rv;
    }

    /**
     * 反序列化对象
     * @param in
     * @return
     */
    public static Object deserialize(byte[] in) {
        Object rv=null;
        ByteArrayInputStream bis = null;
        ObjectInputStream is = null;
        try {
            if(in != null) {
                bis=new ByteArrayInputStream(in);
                is=new ObjectInputStream(bis);
                rv=is.readObject();
                is.close();
                bis.close();
            }
        } catch (Exception e) {
            log.error("io read exception", e);
        }finally {
            try {
                if(is!=null) {
                    is.close();
                }
                if(bis!=null) {
                    bis.close();
                }
            } catch (IOException ioe) {
                log.error("io close exception");
            }
        }
        return rv;
    }
}
