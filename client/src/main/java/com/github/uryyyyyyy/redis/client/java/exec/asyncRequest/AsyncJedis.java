package com.github.uryyyyyyy.redis.client.java.exec.asyncRequest;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.client.jedis.RedisClusterClientJedis;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;

public class AsyncJedis {

	public static void main(String[] args) {
		Set<HostAndPort> jedisClusterNodes = new HashSet<>();
//Jedis Cluster will attempt to discover cluster nodes automatically
		jedisClusterNodes.add(new HostAndPort("172.17.0.5", 7000));
		jedisClusterNodes.add(new HostAndPort("172.17.0.4", 7000));
		jedisClusterNodes.add(new HostAndPort("172.17.0.3", 7000));
		RedisClusterClient_ client = new RedisClusterClientJedis(jedisClusterNodes);
		Util.execute(client);
	}
}