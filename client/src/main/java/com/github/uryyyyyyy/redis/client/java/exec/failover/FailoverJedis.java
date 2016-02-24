package com.github.uryyyyyyy.redis.client.java.exec.failover;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.client.jedis.RedisClusterClientJedis;
import redis.clients.jedis.HostAndPort;

import java.util.HashSet;
import java.util.Set;

public class FailoverJedis {

	public static void main(String[] args) {
		Set<HostAndPort> jedisClusterNodes = new HashSet<>();

		jedisClusterNodes.add(new HostAndPort("192.168.0.1", 6380));
		jedisClusterNodes.add(new HostAndPort("192.168.0.1", 6379));
		RedisClusterClient_ client = new RedisClusterClientJedis(jedisClusterNodes);

		Util.execute(client);
	}
}