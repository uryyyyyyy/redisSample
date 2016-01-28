package com.github.uryyyyyyy.redis.client.java.client.lettuce;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.client.RedisKeyUtil;
import com.lambdaworks.redis.RedisException;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.ClusterClientOptions;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisClusterClientLettuce implements RedisClusterClient_ {

	private RedisClusterClient client;
	private StatefulRedisClusterConnection<String, String> connection;

	public RedisClusterClientLettuce(Iterable<RedisURI> redisURIs){
		this.client = RedisClusterClient.create(redisURIs);
		client.setDefaultTimeout(100, TimeUnit.MILLISECONDS);
		client.setOptions(new ClusterClientOptions.Builder()
				.refreshClusterView(true)
				.refreshPeriod(3, TimeUnit.SECONDS)
				.build());
		this.connection = client.connect();
	}

	public RedisClusterClientLettuce(Iterable<RedisURI> redisURIs, int timeoutMillis){
		this.client = RedisClusterClient.create(redisURIs);
		client.setDefaultTimeout(timeoutMillis, TimeUnit.MILLISECONDS);
		client.setOptions(new ClusterClientOptions.Builder()
				.refreshClusterView(true)
				.refreshPeriod(3, TimeUnit.SECONDS)
				.build());
		client.reloadPartitions();
	}


	@Override
	public void close() {
		connection.close();
		client.shutdown();
	}

	@Override
	public void set(long hash, String key, String value) throws IOException {
		connection.sync().set(RedisKeyUtil.generateKey(hash, key), value);
	}

	public void setWithRetry(long hash, String key, String value, int retryTime, int sleepMillis) throws IOException {
		try {
			connection.sync().set(RedisKeyUtil.generateKey(hash, key), value);
		}catch(RedisException e){
			if(retryTime == 0) throw e;
			sleep(sleepMillis);
			setWithRetry(hash, key, value, retryTime -1, sleepMillis);
		}
	}

	public void setAsync(long hash, String key, String value) throws IOException {
		connection.async().set(RedisKeyUtil.generateKey(hash, key), value);
	}

	@Override
	public void setex(long hash, String key, String value, int expireTimeSec) throws IOException {
		connection.sync().setex(RedisKeyUtil.generateKey(hash, key), expireTimeSec, value);
	}

	public void setexAsync(long hash, String key, String value, int expireTimeSec) throws IOException {
		connection.async().setex(RedisKeyUtil.generateKey(hash, key), expireTimeSec, value);
	}

	@Override
	public void delete(long hash, String key) throws IOException {
		connection.sync().del(RedisKeyUtil.generateKey(hash, key));
	}

	@Override
	public String get(long hash, String key) throws IOException {
		return connection.sync().get(RedisKeyUtil.generateKey(hash, key));
	}

	public String getWithRetry(long hash, String key, int retryTime, int sleepMillis) throws IOException {
		try {
			return connection.sync().get(RedisKeyUtil.generateKey(hash, key));
		}catch(RedisException e){
			if(retryTime == 0) throw e;
			sleep(sleepMillis);
			return getWithRetry(hash, key, retryTime -1, sleepMillis);
		}
	}

	@Override
	public Map<String, String> getMulti(long hash, String[] keys) throws IOException {
		List<String> values =  connection.sync().mget(RedisKeyUtil.generateKeys(hash, keys));
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++){
			map.put(keys[i], values.get(i));
		}
		return map;
	}

	private static void sleep(int mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
