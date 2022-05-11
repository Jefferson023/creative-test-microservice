# creative-test-microservice

## Microserviço para autenticação e gerenciamento de usuários feito para o teste técnico da Creative Drive.
### Credenciais
O usuário ADMIN que pode ser utilizado tem email admin@email.com e senha 123

### Endpoins
POST /auth -> autenticação/criação do token do usuário. Deve ser enviado um json no formato {email: "email", senha: "senha"}

POST /usuario -> criação de um usuário. Deve ser enviado um json no formato  {nome: "nome", "email": "email", senha: "senha", perfil: "perfil"}

DELETE /usuario/{usuarioId} -> remoção de um usuário cujo o ID foi fornecido.

PUT /usuario -> alteração dos dados de um usuário. Deve ser enviado um json no formato  {nome: "nome", "email": "email", senha: "senha", perfil: "perfil"}

GET /usuario/{usuarioId} -> retorna os dados do usuário cujo o ID foi fornecido. 

GET /buscar -> busca os usuários cadastrados. Os seguintes parametros de busca podem ser enviados: nome, email, telefone, endereco, page, size, sortBy e order

### Rodando local
Para rodar a aplicação local, você pode rodar pela IDE ou pelo Docker utilizando o Dockerfile desde que exista 
um banco de dados mongo configurado atendendo na porta 27017.

### Deploy no kubernetes
Para realizar o deploy no kubernetes, basta executar os comandos abaixo:
```
kubectl apply -f k8s
kubectl port-forward service/creative 28015:30081 (após o serviços estarem rodando)
```
A aplicação vai atender na porta local 28015. 
