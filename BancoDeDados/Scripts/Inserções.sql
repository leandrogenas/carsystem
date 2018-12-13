use Carsystem

insert into cliente ( cpf, nome, email, telefone, endereco) 
values ('852.698.620-18', 'Douglas Azevedo Rocha', 'DouglasAzevedoRocha@jourrapide.com', '(67)9185-7622', 'Rua Curiango, 1163');

insert into veiculo (placa, cod_proprietario, cor, modelo, ano, importado, kilometragem, marca) 
              values ('ABC-1234', 1, 'Preto', 'Onix', 2010, 0, 200.00, 'Chevrolet');

insert into pagamento (forma_pagamento, num_parcelas, pago) values('credito', 4, 0)

insert into orcamento (cod_veiculo, preco, data_inicio, termino_previsto, seguradora, iniciado, cod_pagamento)
       values (1, 250.00, Convert(Date, '20-11-2018', 105), Convert(Date, '30-11-2018', 105), 'sim', 1, 1)

insert into atendimento(cod_orcamento, fase, data_inicio, data_termino) values( 1, 1, Convert(Date, '22/11/2018', 105), Convert(Date, '29-11-2018', 105))

insert into cliente ( cpf, nome, email, telefone, endereco) 
values ('480.686.350-50', 'Martim Alves Cavalcanti', 'MartimAlvesCavalcanti@dayrep.com', '(61)5775-8015', 'Quadra SQS 416 Bloco F, 752');

insert into veiculo (placa, cod_proprietario, cor, modelo, ano, importado, kilometragem, marca) 
              values ('ABC-1222', 2, 'Branco', 'Civic', 2018, 0, 0, 'Honda');

insert into pagamento (forma_pagamento, num_parcelas, pago) values('debito', 1, 1)

insert into orcamento (cod_veiculo, preco, data_inicio, termino_previsto, seguradora, iniciado, cod_pagamento)
       values (2, 500.00, Convert(Date, '15-11-2018', 105), Convert(Date, '25-11-2018', 105), 'nao', 1, 2)

insert into atendimento(cod_orcamento, fase, data_inicio, data_termino) values (2, 2, Convert(Date, '17/11/2018', 105), Convert(Date, '27-11-2018', 105))

insert into cliente ( cpf, nome, email, telefone, endereco) 
values ('176.088.970-90', 'Clara Fernandes Alves', 'ClaraFernandesAlves@armyspy.com', '(11)6418-4519', 'Rua Edgar Codazzi, 1398');

insert into veiculo (placa, cod_proprietario, cor, modelo, ano, importado, kilometragem, marca) 
              values ('ABC-1333', 3, 'Preto', 'Monza', 1997, 0, 20.000, 'Chevrolet');

insert into pagamento (forma_pagamento, num_parcelas, pago) values('� vista', 1, 1)

insert into orcamento (cod_veiculo, preco, data_inicio, termino_previsto, seguradora, iniciado, cod_pagamento)
       values (3, 300.00, Convert(Date, '28-11-2018', 105), Convert(Date, '15-12-2018', 105), 'nao', 0, 3)

-- SEM CADASTRO EM ATENDIMENTO UMA VEZ QUE O SERVI�O AINDA N�O FOI INICIADO*/

insert into cliente ( cpf, nome, email, telefone, endereco) 
values ('080.502.890-01', 'Bianca Alves Sousa', 'BiancaAlvesSousa@rhyta.com', '(11)6864-8342', 'Avenida Barro Branco, 523');

insert into veiculo (placa, cod_proprietario, cor, modelo, ano, importado, kilometragem, marca) 
              values ('ABC-1777', 4, 'Prata', 'Audi A4', 2002, 1, 0, 'Audi');

insert into pagamento (forma_pagamento, num_parcelas, pago) values('credito', 5, 0)

insert into orcamento (cod_veiculo, preco, data_inicio, termino_previsto, seguradora, iniciado, cod_pagamento)
       values (4, 1000.00, Convert(Date, '29-11-2018', 105), Convert(Date, '23-12-2018', 105), 'sim', 1, 4)

insert into atendimento(cod_orcamento, fase, data_inicio, data_termino) values (4, 3, Convert(Date, '01/12/2018', 105), Convert(Date, '23-12-2018', 105))

insert into cliente ( cpf, nome, email, telefone, endereco) 
values ('370.986.790-89', 'Jo�o Goncalves Barbosa', 'JoaoGoncalvesBarbosa@teleworm.us', '(11)4407-6539', 'Rua Jacuipe, 1933');

insert into veiculo (placa, cod_proprietario, cor, modelo, ano, importado, kilometragem, marca) 
              values ('ABC-1887', 5, 'Azul', 'Cruze', 2016, 0, 180.000, 'Crevrolet');

insert into pagamento (forma_pagamento, num_parcelas, pago) values('credito', 1, 1)

insert into orcamento (cod_veiculo, preco, data_inicio, termino_previsto, seguradora, iniciado, cod_pagamento)
       values (5, 100.00, Convert(Date, '29-11-2018', 105), Convert(Date, '02-12-2018', 105), 'nao', 1, 5)

insert into atendimento(cod_orcamento, fase, data_inicio, data_termino) values (5, 3, Convert(Date, '30/11/2018', 105), Convert(Date, '02-12-2018', 105))
