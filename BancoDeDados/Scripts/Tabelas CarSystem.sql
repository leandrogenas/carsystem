create database CarSystem;
use CarSystem;

 create table Cliente(
 cpf varchar(18) not null,
 nome varchar(100) not null,
 email varchar(40) not null,
 telefone varchar(12) not null,
 endereco varchar(100) not null,
 primary key(cpf),
 );

 create table Peca(
 codigo varchar(10) not null,
 modelo varchar(25) not null,
 estoque int not null,
 nome varchar(100) not null,
 descricao varchar(100) not null,
 primary key(codigo),
 );

 create table Fornecedor(
 cnpj varchar(18) not null,
 nome varchar(10) not null,
 telefone int not null,
 endereco varchar(100) not null,
 primary key(cnpj),
 );

 create table Servico(
 codigo varchar(5) not null,
 descricao varchar(10) not null,
 primary key(codigo),
 );

 create table Pagamento(
 codigo_pagamento varchar(5) not null,
 forma_pagamento varchar(20) not null,
 num_parcelas int not null,
 primary key(codigo_pagamento),
 );

 create table Veiculo(
 placa varchar(7) not null,
 cpf_proprietario varchar(18) not null,
 num_parcelas int not null,
 cor varchar(20) not null,
 modelo varchar(25) not null,
 marca varchar(25) not null,
 ano varchar(4) not null,
 importado char(1) not null,
 kilometragem varchar(10) not null,
 primary key(placa),
 CONSTRAINT fk_ClienteVeiculo FOREIGN KEY (cpf_proprietario) REFERENCES Cliente (cpf),
 );

 create table UtilizacaoPeca(
 cod_peca varchar(10) not null,
 cod_servico varchar(5) not null,
 quantidade int not null,
 primary key(cod_peca),
 CONSTRAINT fk_UtilizaPeca FOREIGN KEY (cod_peca) REFERENCES Peca (codigo),
 CONSTRAINT fk_UtilizaServico FOREIGN KEY (cod_servico) REFERENCES Servico (codigo),
 );

 create table Fornecimento(
 cod_peca varchar(10) not null,
 cnpj_fornecedor varchar(18) not null,
 quantidade int not null,
 primary key(cod_peca, cnpj_fornecedor),
 CONSTRAINT fk_FornecimentoPeca FOREIGN KEY (cod_peca) REFERENCES Peca (codigo),
 CONSTRAINT fk_FornecimentoFornecedor FOREIGN KEY (cnpj_fornecedor) REFERENCES Fornecedor (cnpj),
 );

  create table Orcamento(
 cod_orcamento varchar(15) not null,
 placa_veiculo varchar(7) not null,
 valor money not null,
 datainicio DATE not null,
 datatermino DATE not null,
 terminoprevisto DATE not null,
 seguradora varchar(25) null,
 executado char(1) not null,
 primary key(cod_orcamento),
 CONSTRAINT fk_OrçamentoVeiculo FOREIGN KEY (placa_veiculo) REFERENCES Veiculo (placa),
 );

  create table ExecucaoServico(
 cod_servico varchar(5) not null,
 cod_orcamento varchar(15) not null,
 datainicio DATE not null,
 datatermino DATE not null,
 primary key(cod_servico, cod_orcamento),
 CONSTRAINT fk_ExecucaoServico FOREIGN KEY (cod_servico) REFERENCES Servico (codigo),
 CONSTRAINT fk_ExecucaoOrcamento FOREIGN KEY (cod_orcamento) REFERENCES Orcamento (cod_orcamento),
 );

 create table Status(
 cod_servico varchar(5) not null,
 texto varchar(15) not null,
 primary key(cod_servico),
 CONSTRAINT fk_StatusServico FOREIGN KEY (cod_servico) REFERENCES Servico (codigo),
 );



