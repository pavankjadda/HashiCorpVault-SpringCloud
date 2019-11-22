# HashiCorpVault with Spring Cloud config
This repository demonstrates Hashi Corp Vault with Spring Cloud config

## Vault setup
1. Create a directory named HashiCorpVault and store all the setup files, config files there
2. Download vault library from [Vault](https://www.vaultproject.io/intro/getting-started/index.html) and add it to the `PATH`
3. Execute following command to enable vault commands auto complete
    ```
    $ vault -autocomplete-install
    ```
4. Create `config.hcl` file in HashiCorpVault directory
    ```
    storage "consul" {
      address = "127.0.0.1:8500"
      path    = "vault/"
    }
    
    ui = true
    
    listener "tcp" {
     address     = "127.0.0.1:8200"
     tls_disable = 1
    }
    ```
5. Download consul binary file from https://www.consul.io/downloads.html and add it to the `PATH`
6. Start consul with the following command
    ```
    $ consul agent -dev
    ``` 
7. Open new terminal and execute the following command to start vault
    ```
    $ vault server -config=<path_to_file>/config.hcl
    ``` 
8. Open one more terminal and execute the following command
    ```
    $ export VAULT_ADDR=http://127.0.0.1:8200 
    ```
9. Initialize the vault with the following command and ** copy the keys some where safe **
    ```
    $ vault operator init
    Unseal Key 1: E4GnjX+VP9G50uWQNcwpCflzGAMKGR38BbQywgq4I6L8
    Unseal Key 2: PYMxcCOswEYMNz7N6UW53Up6nu6y+SjAPwTJOTtkju3d
    Unseal Key 3: yuJ5cSxC7tSBR5mMVJ/WJ9bfhhfGb+uwWw9FQR0JKILh
    Unseal Key 4: 0vdvEFHM9PHEGMctJrl2ylHqoKQK8DLkfMU6ntmDz6jv
    Unseal Key 5: cI8yglWJX+jPf/yQG7Sg6SPWzy0WyrBPvaFTOAYkPJTx
    
    Initial Root Token: 62421926-81b9-b202-86f8-8850176c0cf3
    ```   
10. Begin unsealing the Vault with following command. Execute this 3 times, each time enter different keys from step 9
    ```
    $ vault operator unseal
    ```    
11. Go to http://localhost:8200/ui/ to see the vault UI

## Configure Spring boot project to use vault and get credentials from vault
1. First, create Key Value pair (KV) secret engine, which stores key value pairs
2. Go to [ACL policies](http://localhost:8200/ui/vault/policies/acl) and create the ACL policy named `vault-demo-policy` that controls access to our secret engine (will be created in later steps)
    ```
       path "vaultdemo/pres/dev/*" {
         capabilities = ["read","create","update"]
       }
   
    path "vaultdemo/pres/test/*" {
      capabilities = ["read","create","update"]
    }
    
    path "vaultdemo/pres/prod/*" {
      capabilities = ["read","create","update"]
    }
    ```
3. Use the following command to create a vault with secret engine `vaultdemo` and application name `pres` and profile `dev`. So, the following command creates 3 key value pairs 
    ```
    $ vault kv put vaultdemo/pres/dev username=root password=dev1234 url="jdbc:mysql://localhost:3306/bookstore_dev"
    ```  
4. Format of the vault should be `<secret_engine_name>/<application_name>/<profile>`. Following this format is necessary as Spring Cloud config depends on this format
5. Execute the following to create test, prod profile and key value pairs inside of it
    ```
    $ vault kv put vaultdemo/pres/test username=root password=test1234 url="jdbc:mysql://localhost:3306/bookstore_test"
    
    $ vault kv put vaultdemo/pres/prod username=root password=prod1234 url="jdbc:mysql://localhost:3306/bookstore_prod"
    ```  
6. Now go to spring boot project [src/main/resources](src/main/resources) directory and create `bootstrap.yml` file with the following content
    ```yaml
    spring:
      application:
        name: pres
      cloud:
        vault:
          authentication: TOKEN
          token: ${VAULT_TOKEN}
          scheme: http
          host: localhost
          port: 8200
          kv:
            enabled: true
            backend: vaultdemo
      profiles:
        include: dev,test
    ```
7. Use the following syntax to export token to Linux machine environmental variable
   ```
    $ export VAULT_TOKEN=<Your token>
   ```
7. Spring uses`bootstrap.yml` file to load vault config and key value pairs required by the spring profiles before initializing the context. The `${VAULT_TOKEN}` value will be taken from machine environmental variables. 
8. Make sure to include all the profiles in `include` attribute. This instructs Spring to load those profiles before initializing context
9. Now create `application-dev.yml` file with the following content. The keys from the vault should match here i.e ${username},${password},${url}
    ```
    ## Server Properties
    server:
      port: 8081
    
    spring:
      datasource:
        username: ${username}
        password: ${password}
        url: ${url}
    ```
10. Create `application-test.yml` and `application-prod.yml` config files for Test and Prod environments
11. Create `application.yml` file that selects actual spring profile based on maven command. Please [look at this gist](https://gist.github.com/pavankjadda/a9e684c7db699a050d87be4a8c391e4c) on "How to select Spring boot profile from maven"?
    ```yaml
    ## Select profile
    spring:
      profiles:
        active: @activatedProperties@
    
    ```

## Build and Run the project
1. Clone this project and build it maven. Make sure to pass `-Ddev` or `-Dtest` parameter to maven command, which selects spring profile id and passes to `application.yml` file 
    ```yaml
    $ mvn clean package -Dtest -DskipTests
    ```
2. Now run the project with the java command. This should start my project using the profile from previous step
    ```shell script
    java -jar target/vaultdemo-0.0.1.jar
    ``` 
