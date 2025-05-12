DROP DATABASE IF EXISTS sistema_testes;

CREATE DATABASE sistema_testes;

USE sistema_testes;

-- Tabelas
CREATE TABLE Usuario
(
    id    BIGINT       NOT NULL AUTO_INCREMENT,
    nome  VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(64)  NOT NULL,
    papel ENUM('ADMIN', 'TESTADOR') NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Projeto
(
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    nome         VARCHAR(200) NOT NULL,
    descricao    TEXT,
    data_criacao DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE MembroProjeto
(
    projeto_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (projeto_id, usuario_id),
    FOREIGN KEY (projeto_id) REFERENCES Projeto (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES Usuario (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Estrategia
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    nome       VARCHAR(200) NOT NULL,
    descricao  TEXT,
    exemplos   TEXT,
    dicas      TEXT,
    imagem_url VARCHAR(500),
    PRIMARY KEY (id)
);

CREATE TABLE SessaoTeste
(
    id            BIGINT   NOT NULL AUTO_INCREMENT,
    projeto_id    BIGINT   NOT NULL,
    testador_id   BIGINT   NOT NULL,
    estrategia_id BIGINT   NOT NULL,
    tempo_minutos INT      NOT NULL,
    descricao     TEXT,
    status        ENUM('CRIADO', 'EM_EXECUCAO', 'FINALIZADO') DEFAULT 'CRIADO',
    data_criacao  DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_inicio   DATETIME,
    data_fim      DATETIME,
    PRIMARY KEY (id),
    FOREIGN KEY (projeto_id) REFERENCES Projeto (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (testador_id) REFERENCES Usuario (id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (estrategia_id) REFERENCES Estrategia (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Bug
(
    id            BIGINT   NOT NULL AUTO_INCREMENT,
    sessao_id     BIGINT   NOT NULL,
    descricao     TEXT     NOT NULL,
    data_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (sessao_id) REFERENCES SessaoTeste (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Inserção de dados
INSERT INTO Usuario (nome, email, senha, papel)
VALUES ('Admin Principal', 'admin@teste.com', 'admin123', 'ADMIN'),
       ('Carlos Testador', 'carlos@teste.com', 'senha123', 'TESTADOR'),
       ('Ana Game', 'ana@teste.com', 'senha456', 'TESTADOR');

INSERT INTO Estrategia (nome, descricao, exemplos, dicas)
VALUES ('Exploração Cega', 'O testador joga livremente sem direções específicas.', 'Jogar sem seguir missões.',
        'Observe o ambiente ao redor.'),
       ('Mapeamento de Interface', 'Testa interações com menus e HUD.', 'Abrir menus repetidamente.',
        'Teste diferentes resoluções de tela.'),
       ('Testes de Limite', 'Verifica comportamentos em extremos do jogo.', 'Pular da borda do mapa.',
        'Tente romper limites de movimento.');

INSERT INTO Projeto (nome, descricao)
VALUES ('Projeto: Jogo de Corrida', 'Testes no game de corrida urbana.'),
       ('Projeto: Aventura 3D', 'Game estilo RPG com mapas abertos.');

-- Carlos e Admin no projeto 1; Carlos e Ana no projeto 2
INSERT INTO MembroProjeto (projeto_id, usuario_id)
VALUES (1, 2),
       (1, 1),
       (2, 2),
       (2, 3);

-- Sessões de teste
INSERT INTO SessaoTeste (projeto_id, testador_id, estrategia_id, tempo_minutos, descricao, status)
VALUES (1, 2, 1, 30, 'Primeira sessão no jogo de corrida', 'CRIADO'),
       (2, 3, 2, 45, 'Testando interfaces do RPG', 'CRIADO');