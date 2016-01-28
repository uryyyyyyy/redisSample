package com.github.uryyyyyyy.redis.client.java.exec.asyncRequest;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;

import java.io.IOException;

public class Util {

	public static void execute(RedisClusterClient_ client) {

		try {
			long start = System.currentTimeMillis();
			for(int i = 0; i< 100000; i++){
				String is = i + "";
				client.setAsync(i, is, is + "value");
			}
			System.out.println(System.currentTimeMillis() - start);
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			client.close();
		}
	}
}
