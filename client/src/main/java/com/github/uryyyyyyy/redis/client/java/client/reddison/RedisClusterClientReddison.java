package com.github.uryyyyyyy.redis.client.java.client.reddison;

import com.github.uryyyyyyy.redis.client.java.client.RedisClusterClient_;
import com.github.uryyyyyyy.redis.client.java.client.RedisKeyUtil;
import com.lambdaworks.redis.RedisException;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.RedissonClient;
import org.redisson.core.RBucket;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisClusterClientReddison implements RedisClusterClient_ {

	private RedissonClient client;

	public RedisClusterClientReddison(List<String> redisServerList){
		Config config = new Config();
		config.useClusterServers()
				.setScanInterval(2000) // sets cluster state scan interval
				.addNodeAddress(redisServerList.toArray(new String[redisServerList.size()]));

		this.client = Redisson.create(config);
	}

	public RedisClusterClientReddison(List<String> redisServerList, int timeoutMillis, int poolNum){
		Config config = new Config();
		config.useClusterServers()
				.setTimeout(timeoutMillis)
				.setMasterConnectionPoolSize(poolNum)
				.setScanInterval(2000) // sets cluster state scan interval
				.addNodeAddress(redisServerList.toArray(new String[redisServerList.size()]));

		this.client = Redisson.create(config);
	}

	@Override
	public void close() {
		client.shutdown();
	}

	@Override
	public void set(long hash, String key, String value) throws IOException {
		try {
			RBucket<String> bucket = client.getBucket(RedisKeyUtil.generateKey(hash, key));
			bucket.set(value);
		}catch (RedisException e){
			throw new IOException(e);
		}
	}

	@Override
	public void setAsync(long hash, String key, String value) throws IOException {
		RBucket<String> bucket = client.getBucket(RedisKeyUtil.generateKey(hash, key));
		bucket.setAsync(value);
	}

	@Override
	public void setex(long hash, String key, String value, int expireTimeSec) throws IOException {
		RBucket<String> bucket = client.getBucket(RedisKeyUtil.generateKey(hash, key));
		bucket.set(value, expireTimeSec, TimeUnit.SECONDS);
	}

	@Override
	public void setexAsync(long hash, String key, String value, int expireTimeSec) throws IOException {
		RBucket<String> bucket = client.getBucket(RedisKeyUtil.generateKey(hash, key));
		bucket.setAsync(value, expireTimeSec, TimeUnit.SECONDS);
	}

	@Override
	public void delete(long hash, String key) throws IOException {
		RBucket<String> bucket = client.getBucket(RedisKeyUtil.generateKey(hash, key));
		bucket.delete();
	}

	@Override
	public String get(long hash, String key) throws IOException {
		try{
			RBucket<String> bucket = client.getBucket(RedisKeyUtil.generateKey(hash, key));
			return bucket.get();
		}catch (RedisException e){
			throw new IOException(e);
		}
	}

	@Override
	public Map<String, String> getMulti(long hash, String[] keys) throws IOException {
		return client.loadBucketValues(RedisKeyUtil.generateKeys(hash, keys));
	}
}
