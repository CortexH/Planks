create database plank;

create table plank.equipe(
	id_equipe int not null auto_increment,
	nomeEquipe varchar(255),
	
	primary key (id_equipe)
);

create table plank.usuario(
	id int not null auto_increment,
	display_name varchar(255),
	senha varchar(30) not null,
	email varchar(255),
	cpf varchar(12),
	primary key (id)
);

alter table plank.usuario 
add column id_equipe int,
add foreign key (id_equipe) references plank.equipe(id_equipe);

alter table plank.usuario 
add column nivel_Permissao_Equipe int;

/* Sistema de tarefas */
create table plank.checkList(
	id_sprout int not null auto_increment,
	nome varchar(50),
	descricao text,
	user_id int,

	foreign key(user_id) references plank.usuario(id) on delete cascade,
	primary key (id_sprout)
);

create table plank.tarefa_CheckList(
	id_tarefa_Check int not null auto_increment,
	nome_Tarefa varchar(50),
	descricao_Da_Tarefa text,
	Checked bool default false,
	tarefa_id int,
	Tempo_Reinicia time default null,
	

	
	foreign key(tarefa_id) references plank.checkList(id_sprout) on delete cascade,
	primary key (id_tarefa_Check)
);


/* Sistema de ponto 
create table plank.Ponto(
	
);*/

select * from plank.tarefa_CheckList
select * from plank.usuario u 
select * from plank.checklist c 
select * from plank.equipe e 