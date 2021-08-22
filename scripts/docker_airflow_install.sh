#!/bin/bash


# Pra executar esse script, logue na instância EC2 e execute os comandos: 
#   $ curl -Lf0 https://raw.githubusercontent.com/titowoche30/eng-dados-cloud-igti/main/scripts/docker_airflow_install.sh > docker_airflow_install.sh
#   $ bash docker_airflow_install.sh

# Por conta do comando newgrp docker, pouco tempo depois que você executar o script, aperte CTRL+D


echo "============================================="
echo "Instalando Docker"
sudo apt-get update -y
sudo apt-get remove docker docker-engine docker.io containerd runc -y
sudo apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=amd64 signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update -y
sudo apt-get install -y docker-ce docker-ce-cli containerd.io
sudo usermod -aG docker $USER
newgrp docker
docker run hello-world
echo "Fim da instalação do Docker"

echo "============================================="
echo "Instalando Docker Compose"
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
echo "Fim da Instalação do Docker Compose"

echo "============================================="
echo "Subindo Airflow"
curl -LfO 'https://airflow.apache.org/docs/apache-airflow/2.1.2/docker-compose.yaml'
mkdir ./dags ./logs ./plugins
echo -e "AIRFLOW_UID=$(id -u)\nAIRFLOW_GID=0" > .env
docker-compose up -d airflow-init
sleep 60
docker-compose up -d
echo "Airflow up and running"