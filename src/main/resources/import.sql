insert into cozinha (id, nome) values (nextval('COZINHAS_ID'), 'Tailandesa');
insert into cozinha (id, nome) values (nextval('COZINHAS_ID'), 'Indiana');

insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1000, 'Thai Gourmet', 10, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1001, 'Thai Delivery', 9.50, 1);
insert into restaurante (id, nome, taxa_frete, cozinha_id) values (1002, 'Tuk Tuk Comida Indiana', 15, 2);

insert into estado (id, nome) values (1000, 'Minas Gerais');
insert into estado (id, nome) values (1001, 'São Paulo');
insert into estado (id, nome) values (1002, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1001);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1000);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 1001);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 1001);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 1002);

insert into forma_pagamento (id, descricao) values (1000, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (1001, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (1002, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');