package cn.ipaynow.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by ipaynow1130 on 2017/8/10.
 */
public class PropertiesLoader {

    private static final ReadWriteLock rwl = new ReentrantReadWriteLock();

    private static final String path = "ipaynow-identity-sdk.properties";
    private static Map<String,Properties> propertiesMap = new HashMap<String,Properties>();


    public static String getAppId(){
        return (String)(getProperties().get("appId"));
    }
    public static String get3Des(){
        return (String)(getProperties().get("des"));
    }
    public static String getMd5(){
        return (String)(getProperties().get("md5"));
    }

    private static Properties getProperties(){
        try{
            rwl.readLock().lock();
            if(propertiesMap.get(path) == null){
                Properties properties = new Properties();
                ClassLoader  loader  =  Thread.currentThread().getContextClassLoader();
                properties.load(new InputStreamReader(loader.getResourceAsStream(path), "UTF-8"));
                propertiesMap.put(path, properties);
            }
            return propertiesMap.get(path);
        }catch(IOException e){
            return null;
        }finally{
            rwl.readLock().unlock();
        }
    }
}
