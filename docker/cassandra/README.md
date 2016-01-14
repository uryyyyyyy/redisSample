
## build image

sudo docker build -t uryyyyyyy/centos7_kyototycoon ./

## run container(server)

sudo docker run -i --name redis  -t uryyyyyyy/centos7_kyototycoon lab/ktservctl start
