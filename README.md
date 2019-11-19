# DynDnsNginx
Make your own DynDns, with hookers and blackjack ;) .. and an external server running nginx

## How it works

The software has 2 services to be deployed on your client and your server.
The client will simply send a POST request containing a secret.  
The server will save the data to a database (to install and setup separately), 
everytime an unknown IP posts the secret password to the server Nginx configuration scripts will be  
overwritten in /etc/nginx/conf.d/ and the server will be restarted.
multiple cli
**multiple clients are not supported yet, anyways feel free to use the code any way you want.**

## Server
simply add an application.properties file to the resources Path, containing the configuration you would like to run.
```
 server.port:9999
 
 server.servlet.session.persistent=false
 spring.datasource.url=jdbc:mysql://<SQLAddress>:3306/<DB_Name>
 spring.datasource.username=<USER>
 spring.datasource.password=<PASSWORD>
 spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
```
**THE SERVER NEEDS A DATABASE TO WORK**<br>
**Table cration:<br>**
 *feel free to rename the table just think about the @Entity name if you do.*
``` 
create table my_dashboard_ip_log
(
    id   int auto_increment
        primary key,
    ip   longtext    not null,
    time datetime(6) not null
);
```

**THE SERVER REQUIRES TEMPLATES TO WORK**<br>
Templates are read from the working directory. <br>
The marker for IP replacement is `[<NewIp>]`<br>
**Example:**
```
stream {
upstream ssh {
    server [<NewIp]:22;
}
server {
    listen        80;
    server_name ssh.cool.Dyn.Dns.com;
    
    ....

    proxy_pass    ssh;
} }

```
## Client
The client works Out-Of-The-Box, just follow the release instructions.

#### Release instructions
Each of the to projects contains a release-Directory, it contains a 
\*.service file to have the jar be deployed by systemd on boot.
**please pay attention to the contents, they ONLY work for my computers since they contain absolute paths.**<br>

#### New release:
After developing changes, **clean and build** both projects, move the new compiled jars to their release Path and restart all services on all machines!
I usually push the server jar to github to get it on server.

## Contribuitions 
Feel free to contribute any way you want. Open Issues, contact me by email
(a.colarietitosti@googlemail.com) or make a sacrifice to the Gods.. 
Any help is highly appreciated ;)

## Credits
colla69 (Andrea Colarieti Tosti)
