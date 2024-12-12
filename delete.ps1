# Para o Minikube
minikube stop

# Remove a imagem Docker
docker rmi authml

# Remove o deployment do Kubernetes
kubectl delete -f k8s/secret-jwt.yaml
kubectl delete -f k8s/deployment.yaml
kubectl delete -f k8s/service.yaml

minikube delete