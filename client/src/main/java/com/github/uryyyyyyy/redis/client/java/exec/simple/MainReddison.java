package com.github.uryyyyyyy.redis.client.java.exec.simple;

import com.github.uryyyyyyy.redis.client.java.client.reddison.RedisClusterClientReddison;
import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainReddison {

	public static void main(String[] args) {
		List<String> redisUris = Arrays.asList("172.17.0.3:7000", "172.17.0.4:7000");
		RedisClusterClient_ client = new RedisClusterClientReddison(redisUris);
		try {
			client.set(1, "key", "value");
			client.set(1, "key2", "value2");
			client.set(2, "key2", "value2");
			System.out.println(client.get(1, "key"));
			System.out.println(client.getMulti(1, new String[]{"key", "key2"}));
			System.out.println(client.getMulti(2, new String[]{"key2"}));

			client.setex(1, "key3", "value3", 1);
			System.out.println(client.get(1, "key3"));
			sleep(1000);
			System.out.println(client.get(1, "key3"));
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			client.close();
		}
	}

	private static void sleep(int mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}