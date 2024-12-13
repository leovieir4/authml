#!/bin/bash

# Define o nome da imagem Docker
ImageName="authml"

# Define o nome do arquivo Dockerfile
Dockerfile="Dockerfile"

# Verifica se o Minikube está em execução
if [[ $(minikube status) == *"Running"* ]]; then
  echo "Minikube já está em execução."
else
  echo "Iniciando o Minikube..."
  minikube start
  echo "Minikube iniciado."
fi

# Executa o Gradle clean build
./gradlew clean build

# Constrói a imagem Docker
docker build -t "$ImageName" -f "$Dockerfile" .

# Aplica os arquivos do Kubernetes
kubectl apply -f k8s/secret-jwt.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

echo "Aguardando iniciar o port-forward..."
for i in {90..1}; do
  echo -n "."
  sleep 1
done
echo "" # Pula uma linha após o término do carregamento

# Abre a aplicação no navegador (requer 'kubectl proxy' em outro terminal)
kubectl port-forward service/authentication-service 8081:80 &

echo "Aplicação disponível em http://localhost:8081/"

# Para o Minikube (opcional)
# Se você quiser parar o Minikube após a implantação, descomente a linha abaixo:
# minikube stop