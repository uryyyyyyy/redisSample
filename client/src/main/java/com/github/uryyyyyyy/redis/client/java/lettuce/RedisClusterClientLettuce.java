package com.github.uryyyyyyy.redis.client.java.lettuce;

import com.github.uryyyyyyy.redis.client.java.spec.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.spec.RedisKeyUtil;
import com.lambdaworks.redis.ReadFrom;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * TODO: use try-with-resources when use connection
 */
public class RedisClusterClientLettuce implements RedisClusterClient_ {

	private RedisClusterClient client;

	public RedisClusterClientLettuce(Iterable<RedisURI> redisURIs){
		this.client = RedisClusterClient.create(redisURIs);
	}

	public RedisClusterClientLettuce(Iterable<RedisURI> redisURIs, int timeoutMillis, int poolNum){
		//TODO: set poolNum
		this.client = RedisClusterClient.create(redisURIs);
		client.setDefaultTimeout(timeoutMillis, TimeUnit.MILLISECONDS);
	}


	@Override
	public void close() {
		client.shutdown();
	}

	@Override
	public void set(long hash, String key, String value) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.sync().set(RedisKeyUtil.generateKey(hash, key), value);
		con.close();
	}

	public void setAsync(long hash, String key, String value) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.async().set(RedisKeyUtil.generateKey(hash, key), value);
		con.close();
	}

	@Override
	public void setex(long hash, String key, String value, int expireTimeSec) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.sync().setex(RedisKeyUtil.generateKey(hash, key), expireTimeSec, value);
		con.close();
	}

	public void setexAsync(long hash, String key, String value, int expireTimeSec) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.async().setex(RedisKeyUtil.generateKey(hash, key), expireTimeSec, value);
		con.close();
	}

	@Override
	public void delete(long hash, String key) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.sync().del(RedisKeyUtil.generateKey(hash, key));
		con.close();
	}

	@Override
	public String get(long hash, String key) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.setReadFrom(ReadFrom.SLAVE);
		String val = con.sync().get(RedisKeyUtil.generateKey(hash, key));
		con.close();
		return val;
	}

	@Override
	public Map<String, String> getMulti(long hash, String[] keys) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.setReadFrom(ReadFrom.SLAVE);
		List<String> values =  con.sync().mget(RedisKeyUtil.generateKeys(hash, keys));
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++){
			map.put(keys[i], values.get(i));
		}
		con.close();
		return map;
	}
}
