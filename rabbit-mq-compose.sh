docker-compose -f rabbit-mq-compose.yml down || true && \
docker-compose -f rabbit-mq-compose.yml up -d  && \
docker rmi $(docker images -f "dangling=true" -q) || true