package com.github.uryyyyyyy.redis.client.java.exec.asyncRequest;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;

import java.io.IOException;

public class Util {

	public static void execute(RedisClusterClient_ client) {

		try {

			System.out.print("sync: ");
			sync(client);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			client.close();
		}
	}

	private static void sync(RedisClusterClient_ client) throws IOException {
		long start2 = System.currentTimeMillis();
		for(int i = 0; i< 100; i++){
			String is = i + "";
			client.set("hogeeeeeeee", is, is);
		}
		System.out.println(System.currentTimeMillis() - start2);
	}
}
