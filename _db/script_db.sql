DROP DATABASE db_concessionaria;
CREATE DATABASE IF NOT EXISTS db_concessionaria;

-- Seleção do banco de dados
USE db_concessionaria;

create table tb_status (
	id 			TINYINT PRIMARY KEY,
    descricao 	VARCHAR(30) NOT NULL
);

-- Criação da tabela veiculos
CREATE TABLE IF NOT EXISTS tb_veiculos (
    id 		    INT AUTO_INCREMENT PRIMARY KEY,
    marca 	    VARCHAR(50) NOT NULL,
    modelo 	    VARCHAR(50) NOT NULL,
    cor		    VARCHAR(20) NOT NULL,
    ano 	    INT NOT NULL,
    preco 	    DECIMAL(10, 2) NOT NULL,
    id_status   TINYINT
);
CREATE INDEX idx_status ON tb_veiculos(id_status);

ALTER TABLE tb_veiculos ADD CONSTRAINT fk_status FOREIGN KEY (id_status) REFERENCES tb_status (id);

INSERT INTO tb_status(id, descricao)
VALUES (1, 'Em estoque'), (2, 'Vendido');

INSERT INTO tb_veiculos (marca, modelo, cor, ano, preco, id_status)
VALUES ('Renault', 'Captur', 'Branca', 2020, 90000, 1),
	   ('Chevrolet', 'Corsa', 'Vinho', 1998, 10000, 1);


SELECT
    ve.id,
    ve.marca,
    ve.modelo,
    ve.cor,
    ve.ano,
    ve.preco,
    ve.id_status,
    st.descricao as desc_status
FROM
    tb_veiculos             ve
    INNER JOIN tb_status    st ON ve.id_status = st.id