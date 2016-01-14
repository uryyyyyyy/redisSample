
## build image

sudo docker build -t uryyyyyyy/centosRedis:3.0 .

## run container(server)

sudo docker run -i --name redis -p 6379:6379 -t uryyyyyyy/centosRedis:3.0

## run container(client)

```
sudo docker run -i --name cauch -t uryyyyyyy/centos7_couchbase
$ /etc/init.d/couchbase-server start
```
