package com.github.uryyyyyyy.redis.client.java.exec.asyncRequest;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.client.lettuce.RedisClusterClientLettuce;
import com.lambdaworks.redis.RedisURI;

import java.util.Arrays;
import java.util.List;

public class AsyncLettuce {

	public static void main(String[] args) {
		RedisURI uri1 = RedisURI.Builder.redis("172.17.0.3").withPort(7000).build();
		RedisURI uri2 = RedisURI.Builder.redis("172.17.0.4").withPort(7000).build();
		List<RedisURI> redisUri = Arrays.asList(uri1, uri2);

		RedisClusterClient_ client = new RedisClusterClientLettuce(redisUri);
		Util.execute(client);
	}
}