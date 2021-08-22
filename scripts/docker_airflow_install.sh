#!/bin/bash

echo "============================================="
echo "Instalando Docker"
curl -fsSL https://get.docker.com -o get-docker.sh
DRY_RUN=1 sh ./get-docker.sh
groupadd docker
usermod -aG docker $USER
newgrp docker
docker run hello-world
echo "Fim da instalação do Docker"

echo "============================================="
echo "Instalando Docker Compose"
curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
echo "Fim da Instalação do Docker Compose"

echo "============================================="
echo "Subindo Airflow"
curl -LfO 'https://airflow.apache.org/docs/apache-airflow/2.1.2/docker-compose.yaml'
mkdir ./dags ./logs ./plugins
echo -e "AIRFLOW_UID=$(id -u)\nAIRFLOW_GID=0" > .env
docker-compose up -d airflow-init
sleep 120
docker-compose up -d
echo "Airflow up and running"