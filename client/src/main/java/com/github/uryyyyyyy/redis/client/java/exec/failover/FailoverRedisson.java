package com.github.uryyyyyyy.redis.client.java.exec.failover;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.client.lettuce.RedisClusterClientLettuce;
import com.github.uryyyyyyy.redis.client.java.client.reddison.RedisClusterClientReddison;
import com.lambdaworks.redis.RedisURI;

import java.util.Arrays;
import java.util.List;

public class FailoverRedisson {

	public static void main(String[] args) {
		List<String> redisUris = Arrays.asList("172.17.0.3:7000", "172.17.0.4:7000");
		RedisClusterClient_ client = new RedisClusterClientReddison(redisUris);
		Util.execute(client);
	}
}