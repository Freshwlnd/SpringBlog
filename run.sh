# build springblog image
./docker-build-image.sh
# run postgresql
docker run \
  -e POSTGRES_USER=blog \
  -e POSTGRES_PASSWORD=blogpass \
  -e POSTGRES_DATABASE=blog \
  --name postgresSpringBlog \
  -v /opt/postgres/data:/var/lib/postgresql/data \
  -p 5432:5432 \
  -d postgres:13
# run springblog
kubectl apply -f ./springBlog-for-monitor.yml
