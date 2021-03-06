package com.github.uryyyyyyy.redis.client.java.client;

import java.io.IOException;
import java.util.Map;

public interface RedisClusterClient_ {

	void close();

	/**
	 * same as SET command in redis
	 *
	 * @param hash for decide hash slot(mget/transaction will use it).
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	void set(String hash, String key, String value) throws IOException;

	void setAsync(String hash, String key, String value) throws IOException;

	/**
	 * same as SETEX command in redis
	 *
	 * @param hash for decide hash slot(mget/transaction will use it).
	 * @param key
	 * @param value
	 * @param expireTimeSec expire time(sec)
	 * @throws IOException
	 */
	void setex(String hash, String key, String value, int expireTimeSec) throws IOException;

	void setexAsync(String hash, String key, String value, int expireTimeSec) throws IOException;

	/**
	 * same as DEL command in redis
	 *
	 * @param hash
	 * @param key
	 * @throws IOException
	 */
	void delete(String hash, String key) throws IOException;

	/**
	 * same as GET command in redis
	 *
	 * @param hash
	 * @param key
	 * @throws IOException
	 */
	String get(String hash, String key) throws IOException;

	/**
	 * same as MGET command in redis
	 *
	 * @param hash
	 * @param keys
	 * @throws IOException
	 */
	Map<String, String> getMulti(String hash, String[] keys) throws IOException;

}