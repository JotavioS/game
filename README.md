# Jogo de Adivinhação em Rede

## Discentes

- João Otávio Gurgel Souto
- Guilherme Bonifácio Feitosa


## Introdução

Este projeto foi desenvolvido para obtenção da nota final da disciplina de __Redes de Computadores__, ministrada pelo docente __David Alain do Nascimento__ ao curso de __Tecnólogo em Análise e Desenvolvimento de Sistemas__ na Instituição __IFPE - Campus Garanhuns__. O trabalho tem como objetivo implementar um jogo em rede usando a linguagem de programação Java, explorando conceitos como comunicação por sockets e multithreading.
## Descrição do Projeto
O projeto consiste em um jogo de adivinhação em rede, no qual os jogadores podem se conectar a um servidor para participar do jogo. O servidor gera um número aleatório entre 1 e 100, e os jogadores tentam adivinhar esse número. A comunicação entre o servidor e os clientes é realizada por meio de sockets em Java.
## Tecnologias Utilizadas

- __Java:__ A linguagem de programação utilizada para desenvolver tanto o servidor quanto o cliente.
- __Sockets:__ A comunicação entre o servidor e os clientes é implementada por meio de sockets, permitindo a troca de mensagens em tempo real.
- __Threads:__ O uso de threads possibilita que o servidor atenda a vários jogadores simultaneamente, garantindo uma experiência de jogo fluída.
- __IO Streams:__ Para lidar com entrada e saída de dados, são utilizadas classes como BufferedReader e PrintWriter.
- __Apache Maven:__ Utilizado para gerenciamento de dependências e construção do projeto.


## Estrutura do Código
O código está organizado em três arquivos principais: __GameServer__, __GameClient__, e __ClientHandler__. A estrutura do projeto é gerenciada pelo Maven.
### Cliente (GameClient)
O cliente é responsável por estabelecer a conexão com o servidor, enviar o nome do jogador e participar do jogo. O cliente pode enviar mensagens ao servidor e recebe mensagens em tempo real.

### Servidor (GameServer)
O servidor gerencia a lógica do jogo, mantendo a lista de jogadores conectados, gerando o número secreto e distribuindo mensagens entre os jogadores. A classe GameServer cria uma instância de ClientHandler para cada jogador conectado.

### Manipulação do Cliente (ClientHandler)
A classe ClientHandler é uma implementação de Runnable e lida com a interação entre o servidor e um cliente específico. Ela recebe mensagens do cliente, as processa de acordo com a lógica do jogo e as transmite para os demais jogadores.
## Configuração do Maven
O arquivo __pom.xml__ contém as configurações do projeto Maven, incluindo as versões do compilador Java, plugins necessários e a definição de scripts Batch para facilitar a execução do servidor e do cliente.
## Execução do Projeto

Compile e empacote os arquivos Java com Maven

```bash
  mvn clean package
```

Inicie o servidor

```bash
  java -jar target/server/game-server.jar
```

Execute o cliente

```bash
  java -jar target/client/game-client.jar
```

Observação: Modifique o endereço de rede do servidor no cliente caso tente executar em maquinas diferentes.