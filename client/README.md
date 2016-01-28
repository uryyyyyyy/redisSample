
## client

there are 3 major java client for redis.

- Jedis
- Lettuce
- Redisson

### Jedis

most popular redis client.
it support most base commands.

not support non-blocking query.
=> https://github.com/xetorthio/jedis/issues/241

### Lettuce

it has async method.
and connection seems thread safe..
=>  https://github.com/mp911de/lettuce/issues/157

current stable version(4.0.2) seems not support some option,
so I used snapshot version.

### Redisson

current stable version(2.2.5) don't support mget/mset in cluster mode.
=> https://github.com/mrniko/redisson/issues/378

so I used snapshot version.

```
git clone ~~~
maven install
set local m2 directory to build.sbt
```

## check/test

### simple

you can use MONITOR command for checking it.
=> http://redis.io/commands/MONITOR

all client support set/get/mget/mset/expire.

we should use hash tags for mget/mset, like `{hash}-key`

### failover

all client support online fail-over.

you can check it with redis cluster.

my environment is,,

```
client: local-machine
cluster: local-docker-machine(3master + 3 slave)
```

usage:

1. check all node(3master + 3slave)
1. run failover *Main.java
1. stop one of redis-master-node
1. check client try retry connection.
1. see client recover and do the rest tasks.

(your cluster need to be fail-over quickly than retry finish)

### async/sync

in my environment.

client: local-machine

cluster: local-docker-machine(3master + 3 slave)

(most case, there is more network latency, so async will be better score I think)

#### Jedis

async: 5502
sync: 4116

#### Lettuce

async: 4105
sync: 8986

#### Redisson

async: 4897
sync: 12705

