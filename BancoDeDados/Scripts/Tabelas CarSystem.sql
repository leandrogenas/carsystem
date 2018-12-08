create database Carsystem;
use Carsystem;

create table cliente(
 cod_cliente int not null IDENTITY(1,1),
 cpf varchar(14) not null,
 nome varchar(100),
 email varchar(100),
 telefone varchar(13),
 endereco varchar(100),
 primary key(cod_cliente)
);

create table peca(
 cod_peca int not null IDENTITY(1,1),
 em_estoque int not null,
 nome varchar(20) not null,
 modelo varchar(30),
 valor_unit numeric(5,2),
 primary key(cod_peca)
);

create table fornecedor(
 cod_fornecedor int not null IDENTITY(1,1),
 cnpj varchar(20) not null,
 nome varchar(30) not null,
 telefone varchar(13) not null,
 endereco varchar(100),
 primary key(cod_fornecedor)
);

create table servico(
 cod_servico int not null IDENTITY(1,1),
 descricao varchar(100) not null,
 nome varchar(20) not null,
 primary key(cod_servico)
);
create table pagamento(
 cod_pagamento int not null IDENTITY(1,1),
 forma_pagamento varchar(20) not null,
 num_parcelas int not null,
 pago bit not null,
 primary key(cod_pagamento)
);
create table veiculo(
 cod_veiculo int not null IDENTITY(1,1),
 placa varchar(8) not null,
 cod_proprietario int null,
 cor varchar(20),
 marca varchar(50),
 modelo varchar(50),
 ano int,
 importado bit,
 kilometragem int,
 primary key(cod_veiculo),
 CONSTRAINT fk_ClienteVeiculo FOREIGN KEY (cod_proprietario) REFERENCES cliente
(cod_cliente),
);
create table fornecedor_peca(
 cod_peca int not null,
 cod_fornecedor int not null,
 quantidade int not null,
 primary key(cod_peca, cod_fornecedor),
 CONSTRAINT fk_FornecimentoPeca FOREIGN KEY (cod_peca) REFERENCES peca(cod_peca),
 CONSTRAINT fk_FornecimentoFornecedor FOREIGN KEY (cod_fornecedor) REFERENCES fornecedor (cod_fornecedor),
);
create table orcamento(
 cod_orcamento int not null IDENTITY(1,1),
 cod_veiculo int not null,
 cod_cliente int null,
 preco float,
 data_inicio DATE,
 termino_previsto DATE,
 seguradora varchar(100),
 iniciado bit,
 cod_pagamento int,
 primary key(cod_orcamento),
 CONSTRAINT fk_OrcamentoCliente FOREIGN KEY (cod_cliente) REFERENCES cliente(cod_cliente),
 CONSTRAINT fk_OrcamentoVeiculo FOREIGN KEY (cod_veiculo) REFERENCES veiculo(cod_veiculo),
 CONSTRAINT fk_OrcamentoPagamento FOREIGN KEY (cod_pagamento) REFERENCES pagamento (cod_pagamento),
);
create table atendimento(
 cod_atendimento int not null IDENTITY(1,1),
 cod_orcamento int not null,
 fase int not null,
 data_inicio DATE,
 data_termino DATE,
 primary key(cod_atendimento),
 CONSTRAINT fk_AtendimentoOrcamento FOREIGN KEY (cod_orcamento) REFERENCES
orcamento (cod_orcamento),
);
create table orcamento_peca(
 cod_orcamento int not null,
 cod_peca int not null,
 quantidade int not null,
 primary key(cod_orcamento, cod_peca),
 CONSTRAINT fk_OrcamentoPeca FOREIGN KEY (cod_peca) REFERENCES peca (cod_peca),
 CONSTRAINT fk_Orcamento FOREIGN KEY (cod_orcamento) REFERENCES orcamento (cod_orcamento),
);
create table orcamento_servico(
 cod_orcamento int not null,
 cod_servico int not null,
 valor_total numeric(10, 2) not null,
 iniciado bit not null,
 finalizado bit not null,
 primary key(cod_orcamento, cod_servico),
 CONSTRAINT fk_OrcamentoPS FOREIGN KEY (cod_orcamento) REFERENCES orcamento (cod_orcamento),
 CONSTRAINT fk_Servico FOREIGN KEY (cod_servico) REFERENCES servico (cod_servico),
);