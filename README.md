Mantenha abaixo as tabelas necessárias para o funcionamento do seu código.

Tabelas do Banco


```
#!sql

CREATE DATABASE bancoCondominio;

USE bancoCondominio;

CREATE TABLE morador(
  id INT PRIMARY KEY AUTO_INCREMENT,
  nome CHAR(100) NOT NULL,
  telefone CHAR(11) UNIQUE
);

CREATE TABLE apartamento(
  id INT PRIMARY KEY AUTO_INCREMENT,
  numero INT(4) UNIQUE NOT NULL,
  quartos INT(1) NOT NULL,
  ocupacao CHAR(30) NOT NULL,
  id_morador INT NULL,
  FOREIGN KEY(id_morador) REFERENCES morador(id)
);

CREATE TABLE despesa_condominio(
  id INT PRIMARY KEY AUTO_INCREMENT,
  despesa CHAR(30) NOT NULL,
  valor FLOAT NOT NULL,
  dtVencimento DATE NOT NULL,
  dtCadastro DATE NOT NULL,
  dtAlterado DATE NOT NULL,
  id_condominio INT NULL,
  FOREIGN KEY(id_condominio) REFERENCES despesa_condominio(id)
);

```