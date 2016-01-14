FROM centos:7
MAINTAINER uryyyyyyy <koki305@gmail.com>
RUN yum update -y
RUN yum clean all
RUN yum install tcl gcc wget make vim net-tools -y
RUN yum install zlib-devel gcc gcc-c++ -y

RUN wget http://fallabs.com/kyotocabinet/pkg/kyotocabinet-1.2.76.tar.gz
RUN tar zxf kyotocabinet-1.2.76.tar.gz
WORKDIR kyotocabinet-1.2.76
RUN ./configure
RUN make && make install

WORKDIR ../
RUN wget http://fallabs.com/kyototycoon/pkg/kyototycoon-0.9.56.tar.gz
RUN tar zxf kyototycoon-0.9.56.tar.gz
WORKDIR kyototycoon-0.9.56
RUN ./configure
RUN sed -i '19a\#include <unistd.h>' ktdbext.h
RUN make && make install


CMD [ "/bin/bash" ]
