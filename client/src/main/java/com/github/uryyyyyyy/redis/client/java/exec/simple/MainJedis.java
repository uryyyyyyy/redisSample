package com.github.uryyyyyyy.redis.client.java.exec.simple;

import com.github.uryyyyyyy.redis.client.java.client.jedis.RedisClusterClientJedis;
import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;
import redis.clients.jedis.HostAndPort;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MainJedis {

	public static void main(String[] args) {
		Set<HostAndPort> jedisClusterNodes = new HashSet<>();
//Jedis Cluster will attempt to discover cluster nodes automatically
		jedisClusterNodes.add(new HostAndPort("192.168.0.1", 6380));
		jedisClusterNodes.add(new HostAndPort("192.168.0.1", 6379));
		RedisClusterClient_ client = new RedisClusterClientJedis(jedisClusterNodes);
		Util.execute(client);
	}
}