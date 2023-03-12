# Car Insurance API
Api de orçamentos de seguros automobilísticos

## Como executar
Primeiro, clone o projeto em sua máquina.

Para executar o projeto execute o comando
```
docker-compose up
```

Para testar a aplicação, importe no postman o arquivo `CarInsuranceAPI.postman_collection.json` e execute as chamadas. 

É necessário efetuar login através do endpoint ```/api/v1/login``` antes de executar as chamadas referentes ao orçamento para as validações de segurança.

O modelo de dados final está apresentado no arquivo ```modelo_dados.drawio```, na raiz do projeto.
O desenho da arquitetura está apresentado no arquivo ```arquitetura.drawio```, na raiz do projeto.