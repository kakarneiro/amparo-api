-- Carga inicial (so roda no perfil dev, ver application-dev.properties)
INSERT INTO tb_cidadao (nome, data_nascimento, telefone, endereco, condicao, ativo)
VALUES ('Maria das Dores', '1948-03-12', '11988880001', 'Rua das Flores, 120 - Sao Paulo', 'Idosa, mobilidade reduzida', true);
INSERT INTO tb_cidadao (nome, data_nascimento, telefone, endereco, condicao, ativo)
VALUES ('Jose Carlos Lima', '1952-07-30', '11988880002', 'Av. Brasil, 900 - Guarulhos', 'Idoso com Alzheimer inicial', true);

INSERT INTO tb_cuidador (nome, telefone, email, parentesco, prioridade, cidadao_id)
VALUES ('Ana Paula', '11977770001', 'ana@email.com', 'Filha', 1, 1);
INSERT INTO tb_cuidador (nome, telefone, email, parentesco, prioridade, cidadao_id)
VALUES ('Seu Roberto', '11977770002', 'roberto@email.com', 'Vizinho', 2, 1);
INSERT INTO tb_cuidador (nome, telefone, email, parentesco, prioridade, cidadao_id)
VALUES ('Carla Lima', '11977770003', 'carla@email.com', 'Esposa', 1, 2);

INSERT INTO tb_dispositivo (codigo_serial, modelo, ativo, cidadao_id)
VALUES ('ESP32-0001', 'Amparo v1 - ESP32 + MPU6050', true, 1);
INSERT INTO tb_dispositivo (codigo_serial, modelo, ativo, cidadao_id)
VALUES ('ESP32-0002', 'Amparo v1 - ESP32 + MPU6050', true, 2);

INSERT INTO tb_lembrete (titulo, descricao, horario, ativo, cidadao_id)
VALUES ('Remedio da pressao', 'Losartana 50mg', '08:00:00', true, 1);
INSERT INTO tb_lembrete (titulo, descricao, horario, ativo, cidadao_id)
VALUES ('Caminhada', 'Caminhar 15 minutos no quintal', '16:00:00', true, 2);
