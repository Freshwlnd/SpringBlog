docker rmi registry.cn-hangzhou.aliyuncs.com/microservice-extraction/springblog:springblog-for-monitor
docker build --no-cache -t registry.cn-hangzhou.aliyuncs.com/microservice-extraction/springblog:springblog-for-monitor .
docker push registry.cn-hangzhou.aliyuncs.com/microservice-extraction/springblog:springblog-for-monitor