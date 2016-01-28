package com.github.uryyyyyyy.redis.client.java.exec.failover;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;

import java.io.IOException;

public class Util {

	public static void execute(RedisClusterClient_ client) {

		try {
			for(int i = 0; i< 10000; i++){
				retryMode(client, i, 15, 1000);
				//exceptionMode(client, i);
				sleep(50);
			}
		}catch (IOException e){
			e.printStackTrace();
		}finally {
			client.close();
		}

	}

	/**
	 * when command timeout (by master down/network trouble),
	 * retry the connection.
	 * (WARNING: it related to `refreshClusterView` time cycle.)
	 *
	 * retryTime -> how many times you want to retry.
	 * sleepMillis -> when timeout, how long time you want to wait until next retry start.
	 */
	private static void retryMode(RedisClusterClient_ client2, int i, int retryTime, int sleepMillis) throws IOException {
		String is = i + "";
		setWithRetry(client2, i, is, i + " value", retryTime, sleepMillis);
		System.out.println(getWithRetry(client2, i, is, retryTime, sleepMillis));
	}

	private static void setWithRetry(RedisClusterClient_ client2, long hash, String key, String value, int retryTime, int sleepMillis) throws IOException {
		try {
			client2.set(hash, key, value);
		}catch(IOException e){
			if(retryTime == 0) throw e;
			System.out.println("fail. retry #" + retryTime);
			sleep(sleepMillis);
			setWithRetry(client2, hash, key, value, retryTime -1, sleepMillis);
		}
	}

	private static String getWithRetry(RedisClusterClient_ client2, long hash, String key, int retryTime, int sleepMillis) throws IOException {
		try {
			return client2.get(hash, key);
		}catch(IOException e){
			if(retryTime == 0) throw e;
			sleep(sleepMillis);
			return getWithRetry(client2, hash, key, retryTime -1, sleepMillis);
		}
	}

	/**
	 * when command timeout (by master down/network trouble),
	 * throw exception directly for performance
	 */
	private static void exceptionMode(RedisClusterClient_ client2, int i) throws IOException {
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
