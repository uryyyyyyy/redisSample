package com.github.uryyyyyyy.redis.client.java.jedis;

import com.github.uryyyyyyy.redis.client.java.spec.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.spec.RedisKeyUtil;
import com.lambdaworks.redis.RedisConnectionPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisClusterClientJedis implements RedisClusterClient_ {

	private JedisCluster jc;

	public RedisClusterClientJedis(Set<HostAndPort> jedisClusterNodes){
		this.jc = new JedisCluster(jedisClusterNodes, new GenericObjectPoolConfig());
	}

	public RedisClusterClientJedis(Set<HostAndPort> jedisClusterNodes, int timeoutMillis, int poolNum){
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(poolNum);
		config.setMaxIdle(poolNum);
		this.jc = new JedisCluster(jedisClusterNodes, timeoutMillis, config);
	}

	@Override
	public void close() {
		try {
			jc.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public void set(long hash, String key, String value) throws IOException {
		jc.set(RedisKeyUtil.generateKey(hash, key), value);
	}

	@Override
	public void setex(long hash, String key, String value, int expireTimeSec) throws IOException {
		jc.setex(RedisKeyUtil.generateKey(hash, key), expireTimeSec, value);
	}

	@Override
	public void delete(long hash, String key) throws IOException {
		jc.del(key);
	}

	@Override
	public String get(long hash, String key) throws IOException {
		return jc.get(RedisKeyUtil.generateKey(hash, key));
	}

	@Override
	public Map<String, String> getMulti(long hash, String[] keys) throws IOException {
		List<String> values = jc.mget(RedisKeyUtil.generateKeys(hash, keys));
		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++){
			map.put(keys[i], values.get(i));
		}
		return map;
	}
}
