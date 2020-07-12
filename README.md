
# petz-server-http-rest

Test Petz - Server Rest API.

## Construindo este projeto

```
$> git clone https://github.com/salespaulo/petz-server-http-rest.git
$> cd petz-server-http-rest
$> ./mvnw -Dmaven.test.skip=false clean package
```

## Executando este projeto c/ Maven

```
$> git clone https://github.com/salespaulo/petz-server-http-rest.git
$> cd petz-server-http-rest
$> ./mvnw spring-boot:run
```


## Executando este projeto c/ Docker

```
$> git clone https://github.com/salespaulo/petz-server-http-rest.git
$> cd petz-server-http-rest
$> sudo docker build .
$> sudo docker run -d 
    --name <nome_container>             \ 
    --network <nome_rede>               \
    -p 8080:8080 petz-server-http-rest
```

## Análise

Desenvolver CRUD das entidades cliente e pet e disponibilizar acesso através de um servidor Http seguindo o formato Rest utilizando protocolo JSON.

P/ desenvolver esta solução os itens abaixo foram considerados:

* Necessário desenvolver uma autenticação de acesso p/ disponibilizar os dados de maneira segura, para este fim foi escolhida as seguintes soluções: SpringBoot Security e JJWT.
* Necessário desenvolver um controle de acesso aos dados por perfil do usuário logado, permitindo um controle melhor de como e por quem os dados podem ser acessados, para este fim foi criada uma modelagem de privilégios que são utilizados c/ o SpringBoot Security p/ controlar o acesso aos dados através do Token JWT de autenticação enviado em cada requisição.

### Modelagem

1. Usuário (User.java): Entidade responsável por conter os dados p/ acesso aos dados de cliente e pet.
2. Grupo de Usuário (Usergroup.java): Entidade responsável por estabelecer uma relação N p/ N com a entidade Usuário.
3. Regra de Acesso (Role.java): Entidade responsável por estabelecer uma relação N p/ N com usuário e grupo.
3. Privilégio de Acesso (Privilege.java): Entidade responsável por por estabelecer uma relação N p/ N com N p/ N com a entidade regra de acesso.

4. Cliente (Cliente.java): Entidade responsável por conter os dados p/ identificarmos um cliente Petz. O cliente possui uma relação 1 p/ 1 com a entidade usuário e uma relação 1 p/ N com a entidade pet.
5. Pet (Cliente.java): Entidade responsável por conter os dados p/ identificarmos um ou mais pets cadastrados. O pet tem uma relação N p/ 1 com a entidade cliente.

## Documentação

* http://localhost:8080/swagger-ui.html
* http://localhost:8080/v2/api-docs

### API /auth/login

Endpoint p/ realizar login de acesso p/ utilização desta API. Segue dados:

Path: `/auth/login`
Method: `POST`
Header: `username:<username>`
Header: `password:<password>`
Response:

```json
{"accessToken":{"token":"eyJhbGciOiJIUzUxMiJ9...},"refreshToken":{"token":"eyJhbGciOiJIUzUxMiJ9...}}
```

### API /auth/token

Path: `/auth/token`
Method: `POST`
Header: `token:<refresh-token-jwt>`
Response:

```json
{"accessToken":{"token":"eyJhbGciOiJIUzUxMiJ9...},"refreshToken":{"token":"eyJhbGciOiJIUzUxMiJ9...}}
```

### API /api/users
Path: `/api/users`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `USER_LIST`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." -X GET http://localhost:8080/api/users 
```

Response:

```json
{"content":[{"id":1,"name":"John Admin","username":"john.admin@ps.org","refreshToken":"eyJhbGciOiJIUzUxMiJ9..."},{"id":2,"name":"Ana Guest","username":"ana.guest@ps.org","refreshToken":null},{"id":3,"name":"Mike Operator","username":"mike.operator@ps.org","refreshToken":null},{"id":4,"name":"Custom User","username":"custom.user@ps.org","refreshToken":null},{"id":6,"name":"lalalele","username":"abc","refreshToken":null}],"pageable":{"sort":{"sorted":false,"unsorted":true,"empty":true},"offset":0,"pageNumber":0,"pageSize":20,"unpaged":false,"paged":true},"last":true,"totalPages":1,"totalElements":5,"size":20,"number":0,"sort":{"sorted":false,"unsorted":true,"empty":true},"numberOfElements":5,"first":true,"empty":false}
```

Path: `/api/users`
Method: `POST`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `USER_SAVE`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-H "Content-Type: application/json"                       \
	-d '{"name": "abc", "username":"abc","password":"abc"}'                  \
	-X POST http://localhost:8080/api/users 
```

Response:

```json
{"id":3, "name":"abc","username":"abc"}
```

Path: `/api/users/{id}`
Method: `PUT`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `USER_SAVE`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-H "Content-Type: application/json"                       \
	-d '{"name": "abc", "username":"abc","password":"abc"}'                  \
	-X PUT http://localhost:8080/api/users/${ID}
```

Response:

```json
{"id":3, "name":"abc","username":"abc"}
```

Path: `/api/users/{id}`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `USER_GET`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-X GET http://localhost:8080/api/users/${USER_ID}
```

Response:

```json
{"id":3, "name":"abc","username":"abc"}
```

Path: `/api/users/profile`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `USER_GET`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-X GET http://localhost:8080/api/users/profile
```

Response:

```json
{"username":"john.admin@ps.org","authorities":[{"authority":"CLIENTE_GET"},{"authority":"USER_DELETE"},{"authority":"USER_SAVE"},{"authority":"USER_LIST"},{"authority":"CLIENTE_LIST"},{"authority":"PET_GET"},{"authority":"CLIENTE_SAVE"},{"authority":"USER_GET"},{"authority":"PET_SAVE"},{"authority":"PET_DELETE"},{"authority":"CLIENTE_DELETE"},{"authority":"PROFILE_GET"},{"authority":"PET_LIST"}]}
```

Path: `/api/users/username/{username}`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `USER_GET`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-X GET http://localhost:8080/api/users/username/${USER_USERNAME}
```

Response:

```json
{"id":3, "name":"abc","username":"abc"}
```

### API /api/clientes
Path: `/api/clientes`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `CLIENTE_LIST`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." -X GET http://localhost:8080/api/clientes
```

Response:

```json
{"content":[{"id":1,"cpf":"669.482.770-98","logradouro":"Rua das Flores, 200","cep":"11111-111","pets":[{"id":2,"nome":"Zuzi","especie":"Gato","genero":"Femea","descricao":null},{"id":1,"nome":"Huxley","especie":"Cao","genero":"Macho","descricao":null}]},{"id":2,"cpf":"340.587.170-09","logradouro":"Avenida dos Estados, 1050","cep":"22222-222","pets":[]},{"id":3,"cpf":"224.678.370-41","logradouro":"Rua Bela Cintra, 544","cep":"33333-333","pets":[]},{"id":4,"cpf":"082.584.760-50","logradouro":"Estrada da Palestina, 500 - Juquitiba","cep":"44444-444","pets":[]}],"pageable":{"sort":{"sorted":false,"unsorted":true,"empty":true},"offset":0,"pageNumber":0,"pageSize":20,"paged":true,"unpaged":false},"totalElements":4,"totalPages":1,"last":true,"size":20,"number":0,"sort":{"sorted":false,"unsorted":true,"empty":true},"numberOfElements":4,"first":true,"empty":false}
```

Path: `/api/clientes`
Method: `POST`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `USER_SAVE`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-H "Content-Type: application/json"                       \
	-d '{"cpf":"abc","logradouro":"abc", "cep": "11111"}'     \
	-X POST http://localhost:8080/api/clientes
```

Response:

```json
{"id":1,"cpf":"669.482.770-98","logradouro":"Rua das Flores, 200","cep":"11111-111","pets":[{"id":2,"nome":"Zuzi","especie":"Gato","genero":"Femea"}
```

Path: `/api/clientes/{id}`
Method: `PUT`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `CLIENTE_SAVE`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-H "Content-Type: application/json"                       \
	-d '{"cpf":"abc","logradouro":"abc", "cep": "11111"}'     \
	-X PUT http://localhost:8080/api/clientes/${ID}
```

Response:

```json
{"id":1,"cpf":"669.482.770-98","logradouro":"Rua das Flores, 200","cep":"11111-111","pets":[{"id":2,"nome":"Zuzi","especie":"Gato","genero":"Femea"}
```

Path: `/api/clientes/{id}`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `CLIENTE_GET`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-X GET http://localhost:8080/api/clientes/${CLIENTE_ID}
```

Response:

```json
{"id":1,"cpf":"669.482.770-98","logradouro":"Rua das Flores, 200","cep":"11111-111","pets":[{"id":2,"nome":"Zuzi","especie":"Gato","genero":"Femea"}
```

Path: `/api/users/cpf/{cpf}`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `CLIENTE_GET`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-X GET http://localhost:8080/api/users/cpf/${CLIENTE_CPF}
```

Response:

```json
{"id":1,"cpf":"669.482.770-98","logradouro":"Rua das Flores, 200","cep":"11111-111","pets":[{"id":2,"nome":"Zuzi","especie":"Gato","genero":"Femea"}
```

### API /api/pets
Path: `/api/pets`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `PET_LIST`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." -X GET http://localhost:8080/api/clientes
```

Response:

```json
{"content":[{"id":1,"nome":"Huxley","especie":"Cao","genero":"Macho","descricao":null},{"id":2,"nome":"Zuzi","especie":"Gato","genero":"Femea","descricao":null}],"pageable":{"sort":{"sorted":false,"unsorted":true,"empty":true},"offset":0,"pageNumber":0,"pageSize":20,"paged":true,"unpaged":false},"totalElements":2,"totalPages":1,"last":true,"size":20,"number":0,"sort":{"sorted":false,"unsorted":true,"empty":true},"numberOfElements":2,"first":true,"empty":false}
```

Path: `/api/pets/{clienteId}`
Method: `POST`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `PET_SAVE`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-H "Content-Type: application/json"                       \
	-d '{"nome":"abc","especie":"Cao", "genero": "Macho"}'     \
	-X POST http://localhost:8080/api/pets/${CLIENTE_ID}
```

Response:

```json
{"id":1,"nome":"Huxley","especie":"Cao","genero":"Macho"}
```

Path: `/api/pets/{id}`
Method: `PUT`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `PET_SAVE`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-H "Content-Type: application/json"                       \
	-d '{"nome":"abc","especie":"Cao", "genero": "Macho"}'     \
	-X PUT http://localhost:8080/api/pets/${ID}
```

Response:

```json
{"id":1,"nome":"Huxley","especie":"Cao","genero":"Macho"}
```

Path: `/api/clientes/{id}`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `CLIENTE_GET`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-X GET http://localhost:8080/api/clientes/${CLIENTE_ID}
```

Response:

```json
{"id":1,"nome":"Huxley","especie":"Cao","genero":"Macho"}
```

Path: `/api/users/cpf/{cpf}`
Method: `GET`
Header: `X-Authorization:Bearer <token-jwt>`
Claim Privilege: `CLIENTE_GET`
Request curl:

```
$> curl -v -H "X-Authorization:Bearer eyJhbGciOiJIUzUxMiJ9..." \
	-X GET http://localhost:8080/api/users/cpf/${CLIENTE_CPF}
```

Response:

```json
{"id":1,"nome":"Huxley","especie":"Cao","genero":"Macho"}
```