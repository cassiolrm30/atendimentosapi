









insert into profissional(nome, telefone) values('Ana Paula', '(21) 99999-8888');
insert into profissional(nome, telefone) values('João Pedro', '(21) 98765-4321');
insert into profissional(nome, telefone) values('Carlos Silva', '(21) 78901-2345');
select * from profissional;

insert into servico(nome, preco) values('Corte de Cabelo', 50);
insert into servico(nome, preco) values('Barba', 25);
insert into servico(nome, preco) values('Alisamento', 200);
insert into servico(nome, preco) values('Manicure', 100);
insert into servico(nome, preco) values('Pedicure', 60);
select * from servico;

insert into servico_profissional(idservico, idprofissional) values(1, 1);
insert into servico_profissional(idservico, idprofissional) values(1, 2);
insert into servico_profissional(idservico, idprofissional) values(1, 3);
insert into servico_profissional(idservico, idprofissional) values(2, 1);
insert into servico_profissional(idservico, idprofissional) values(2, 2);
insert into servico_profissional(idservico, idprofissional) values(3, 2);
insert into servico_profissional(idservico, idprofissional) values(3, 3);
insert into servico_profissional(idservico, idprofissional) values(4, 1);
insert into servico_profissional(idservico, idprofissional) values(4, 2);
insert into servico_profissional(idservico, idprofissional) values(5, 1);
select * from servico_profissional;

select * from atendimento;
