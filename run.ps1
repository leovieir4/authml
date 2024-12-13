# Define o nome da imagem Docker
$ImageName = "authml"

# Define o nome do arquivo Dockerfile
$Dockerfile = "Dockerfile"

# Verifica se o Minikube está em execução
$MinikubeStatus = minikube status
if ($MinikubeStatus -match "Running") {
  Write-Host "Minikube já está em execução."
} else {
  Write-Host "Iniciando o Minikube..."
  minikube start
  Write-Host "Minikube iniciado."
}

# Executa o Gradle clean build
.\gradlew clean build

# Constrói a imagem Docker
docker build -t $ImageName -f $Dockerfile .

# Aplica os arquivos do Kubernetes
kubectl apply -f k8s/secret-jwt.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml

Write-Host "Aguardando iniciar o port-forward..."
for ($i = 90; $i -gt 0; $i--) {
  Write-Host -NoNewline "."
  Start-Sleep -Seconds 1
}
Write-Host "" # Pula uma linha após o término do carregamento

# Abre a aplicação no navegador
kubectl port-forward service/authentication-service 8081:80

# Para o Minikube (opcional)
# Se você quiser parar o Minikube após a implantação, descomente a linha abaixo:
# minikube stop