CREATE DATABASE IF NOT EXISTS academico;

USE academico;

CREATE TABLE IF NOT EXISTS aluno (
	mat_Aluno integer UNSIGNED NOT NULL, 
	nome_Aluno varchar(255) NOT NULL,
	data_Nasc date NOT NULL,
	CR double NOT NULL,
	curso varchar(255) NOT NULL,
	PRIMARY KEY (mat_Aluno)
);

CREATE TABLE IF NOT EXISTS curso (
	nome_Curso varchar(255) NOT NULL,
	instituicao varchar(255) NOT NULL,
	PRIMARY KEY (nome_Curso, instituicao)
);