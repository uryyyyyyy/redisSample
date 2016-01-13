FROM centos:7
MAINTAINER uryyyyyyy <koki305@gmail.com>
RUN yum update -y
RUN yum clean all
RUN yum install tcl gcc wget make vim net-tools -y

RUN wget http://download.redis.io/releases/redis-3.0.6.tar.gz
RUN tar xzf redis-3.0.6.tar.gz
RUN mv redis-3.0.6 /etc/redis
RUN cd /etc/redis && make
ADD redis.conf /etc/redis/

RUN yum install -y ruby
RUN gem install redis

EXPOSE 6379
CMD [ "/etc/redis/src/redis-server" ]
