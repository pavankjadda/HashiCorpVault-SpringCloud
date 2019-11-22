# HashiCorpVault-SpringCloud
This repository demonstrates Hashi Corp Vault with Spring Cloud Vault

## Vault setup
1. Create a directory named HashiCorpVault and store all the setup files, config files there
2. Download vault library from [Vault](https://www.vaultproject.io/intro/getting-started/index.html) and add it to the `PATH`
3. Execute following command to enable vault commands auto complete
    ```
    vault -autocomplete-install
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
    consul agent -dev
    ``` 
7. Open new terminal and execute the following command to start vault
    ```
    vault server -config=<path_to_file>/config.hcl
    ``` 
8. Open one more terminal and execute the following command
    ```
    export VAULT_ADDR=http://127.0.0.1:8200 
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

