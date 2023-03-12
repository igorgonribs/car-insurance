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

## Autenticação

### Signup
Rota para realizar cadastro na plataforma:
```
endpoint:  /api/v1/signup
{
  "email": "teste@email.com",
  "senha": "password",
  "confirmacaoSenha": "password",
  "cpf": "512.595.690-20",
  "dataNascimento": "10/03/1980",
  "name": "Nome teste 2"
}
```

### Login
Rota para realizar login na aplicação:
```
Request:
  POST /api/v1/login
  {
    "email": "employee@email.com",
    "senha": "password"
  }

Response:
  Status-Code: 200
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
  }
```

### Logoff
Rota para realizar logoff:
```
endpoint: /api/v1/logoff
Authorization: token_jwt
```
