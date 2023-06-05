# build springblog image
./docker-build-image.sh
# run postgresql (https://hanggi.me/post/kubernetes/k8s-postgresql/)
kubectl apply -f ./postgreSQL
# run springblog
kubectl apply -f ./springBlog-for-monitor.yml
