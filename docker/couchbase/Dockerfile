FROM centos:7
MAINTAINER uryyyyyyy <koki305@gmail.com>
RUN yum update -y
RUN yum clean all
RUN yum install tcl gcc wget make vim net-tools -y
RUN yum install -y openssl initscripts
RUN wget http://packages.couchbase.com/releases/4.1.0/couchbase-server-enterprise-4.1.0-centos7.x86_64.rpm
RUN rpm -ivh couchbase-server-enterprise-4.1.0-centos7.x86_64.rpm

CMD [ "/bin/bash" ]
