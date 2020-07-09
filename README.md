#### petz-server-http-rest

Projeto maven para uma Petz - Server Rest API.

```
$> sudo docker run -d 
    --name <nome_container>             \ 
    --network <nome_rede>               \
    -e "DB_DRIVER=<driver>"             \
    -e "DB_URL=<url>"                   \
    -e "DB_USERNAME=<user>"             \
    -e "DB_PASSWORD=<password>"         \
    -p 8000:8000 petz-server-http-rest
```
