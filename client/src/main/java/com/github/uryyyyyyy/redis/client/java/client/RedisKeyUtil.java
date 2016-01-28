package com.github.uryyyyyyy.redis.client.java.client;

public class RedisKeyUtil {

	public static String generateKey(long hash, String key){
		return  "{" + hash + "}" + key;
	}

	public static String[] generateKeys(long hash, String[] keys){
		String[] newKeys = new String[keys.length];
		for(int i = 0; i < keys.length; i++){
			newKeys[i] = generateKey(hash, keys[i]);
		}
		return  newKeys;
	}
}
