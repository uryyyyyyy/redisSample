package com.github.uryyyyyyy.redis.client.java.lettuce;

import com.github.uryyyyyyy.redis.client.java.spec.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.spec.RedisKeyUtil;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.cluster.RedisClusterClient;
import com.lambdaworks.redis.cluster.api.StatefulRedisClusterConnection;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisClusterClientLettuce implements RedisClusterClient_ {

	private RedisClusterClient client;

	public RedisClusterClientLettuce(Iterable<RedisURI> redisURIs){
		this.client = RedisClusterClient.create(redisURIs);
	}

	public RedisClusterClientLettuce(Iterable<RedisURI> redisURIs, int timeout, int poolNum){
		//TODO
		this.client = RedisClusterClient.create(redisURIs);
	}


	@Override
	public void close() {
		client.shutdown();
	}

	@Override
	public void set(long hash, String key, String value) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.sync().set(RedisKeyUtil.generateKey(hash, key), value);
	}

	public void setAsync(long hash, String key, String value) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.async().set(RedisKeyUtil.generateKey(hash, key), value);
	}

	@Override
	public void setex(long hash, String key, String value, int expireTimeSec) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.sync().setex(RedisKeyUtil.generateKey(hash, key), expireTimeSec, value);
	}

	public void setexAsync(long hash, String key, String value, int expireTimeSec) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.async().setex(RedisKeyUtil.generateKey(hash, key), expireTimeSec, value);
	}

	@Override
	public void delete(long hash, String key) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		con.sync().del(RedisKeyUtil.generateKey(hash, key));
	}

	@Override
	public String get(long hash, String key) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		return con.sync().get(RedisKeyUtil.generateKey(hash, key));
	}

	@Override
	public Map<String, String> getMulti(long hash, String[] keys) throws IOException {
		StatefulRedisClusterConnection<String, String> con = client.connect();
		List<String> values =  con.sync().mget(RedisKeyUtil.generateKeys(hash, keys));
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++){
			map.put(keys[i], values.get(i));
		}
		return map;
	}
}
