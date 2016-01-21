package com.github.uryyyyyyy.redis.client.java.exec.simple;

import com.github.uryyyyyyy.redis.client.java.lettuce.RedisClusterClientLettuce;
import com.github.uryyyyyyy.redis.client.java.spec.RedisClusterClient_;
import com.lambdaworks.redis.RedisURI;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class MainLettuce {

	public static void main(String[] args) {
		RedisURI uri1 = RedisURI.Builder.redis("172.17.0.3").withPort(7000).build();
		RedisURI uri2 = RedisURI.Builder.redis("172.17.0.4").withPort(7000).build();
		List<RedisURI> redisUri = Arrays.asList(uri1, uri2);

		RedisClusterClientLettuce client2 = new RedisClusterClientLettuce(redisUri);
		RedisClusterClient_ client = client2;

		try {
			client.set(1, "key", "value");
			client.set(1, "key2", "value2");
			client.set(2, "key2", "value2");
			System.out.println(client.get(1, "key"));
			System.out.println(client.getMulti(1, new String[]{"key", "key2"}));
			System.out.println(client.getMulti(2, new String[]{"key2"}));

			client.setex(1, "key3", "value3", 1);
			System.out.println(client.get(1, "key3"));
			sleep(1500);
			System.out.println(client.get(1, "key3"));


			client2.setAsync(1, "hei", "value");
			client2.setAsync(1, "hei2", "value");
			System.out.println(client2.get(1, "hei"));
			System.out.println(client2.getMulti(1, new String[]{"hei", "hei2"}));
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