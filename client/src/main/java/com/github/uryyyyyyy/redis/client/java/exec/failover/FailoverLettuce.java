package com.github.uryyyyyyy.redis.client.java.exec.failover;

import com.github.uryyyyyyy.redis.client.java.lettuce.RedisClusterClientLettuce;
import com.lambdaworks.redis.RedisURI;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FailoverLettuce {

	public static void main(String[] args) {
		RedisURI uri1 = RedisURI.Builder.redis("172.17.0.3").withPort(7000).build();
		RedisURI uri2 = RedisURI.Builder.redis("172.17.0.4").withPort(7000).build();
		List<RedisURI> redisUri = Arrays.asList(uri1, uri2);

		RedisClusterClientLettuce client2 = new RedisClusterClientLettuce(redisUri);

		try {
			for(int i = 0; i< 10000; i++){
				retryMode(client2, i, 10);
//				exceptionMode(client2, i);
				sleep(50);
			}
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			client2.close();
		}

	}

	/**
	 * when command timeout (by master down/network trouble),
	 * retry
	 */
	private static void retryMode(RedisClusterClientLettuce client2, int i, int retry) throws IOException {
		String is = i + "";
		client2.setWithRetry(i, is, i + " value", retry, 1000);
		System.out.println(client2.getWithRetry(i, is, retry, 1000));
	}

	/**
	 * when command timeout (by master down/network trouble),
	 * throw exception directly for performance
	 */
	private static void exceptionMode(RedisClusterClientLettuce client2, int i) throws IOException {
		String is = i + "";
		client2.set(i, is, i + " value");
		System.out.println(client2.get(i, is));
	}

	private static void sleep(int mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}