-- =========================
-- BASE DE DATOS
-- =========================
create database if not exists sistema_licencias;
use sistema_licencias;

-- =========================
-- TABLA USUARIO
-- =========================
create table usuario (
                         id int auto_increment primary key,
                         nombre varchar(100) not null,
                         cedula varchar(10) unique not null,
                         username varchar(50) unique not null,
                         password_hash varchar(255) not null,
                         rol enum('admin', 'analista') not null,
                         activo tinyint(1) default 1
);

-- =========================
-- TABLA SOLICITANTE
-- =========================
create table solicitante (
                             id int auto_increment primary key,
                             cedula varchar(10) unique not null,
                             nombre varchar(100) not null,
                             fecha_nacimiento date not null,
                             tipo_licencia varchar(5) not null,
                             fecha_solicitud date not null
);

-- =========================
-- TABLA TRAMITE (CON AUDITORÍA)
-- =========================
create table tramite (
                         id int auto_increment primary key,
                         solicitante_id int not null,
                         estado varchar(30) not null,
                         fecha_creacion date not null,

                         created_at datetime not null,
                         created_by int not null,

                         constraint fk_tramite_solicitante
                             foreign key (solicitante_id)
                                 references solicitante(id)
                                 on delete cascade
                                 on update cascade,

                         constraint fk_tramite_usuario
                             foreign key (created_by)
                                 references usuario(id)
);

-- =========================
-- TABLA REQUISITOS
-- =========================
create table requisitos (
                            id int auto_increment primary key,
                            tramite_id int unique not null,
                            certificado_medico tinyint(1) default 0,
                            pago tinyint(1) default 0,
                            multas tinyint(1) default 0,
                            observaciones text,

                            constraint fk_requisitos_tramite
                                foreign key (tramite_id)
                                    references tramite(id)
                                    on delete cascade
                                    on update cascade
);

-- =========================
-- TABLA EXAMEN
-- =========================
create table examen (
                        id int auto_increment primary key,
                        tramite_id int unique not null,
                        nota_teorica decimal(4,2),
                        nota_practica decimal(4,2),
                        fecha_registro date not null,

                        constraint fk_examen_tramite
                            foreign key (tramite_id)
                                references tramite(id)
                                on delete cascade
                                on update cascade
);

-- =========================
-- TABLA LICENCIA (CON AUDITORÍA)
-- =========================
create table licencia (
                          id int auto_increment primary key,
                          tramite_id int unique not null,
                          numero_licencia varchar(20) unique not null,
                          fecha_emision date not null,
                          fecha_vencimiento date not null,

                          created_at datetime not null,
                          created_by int not null,

                          constraint fk_licencia_tramite
                              foreign key (tramite_id)
                                  references tramite(id)
                                  on delete cascade
                                  on update cascade,

                          constraint fk_licencia_usuario
                              foreign key (created_by)
                                  references usuario(id)
);

-- =========================
-- TRIGGERS DE AUDITORÍA
-- =========================
delimiter //

create trigger trg_tramite_created_at
    before insert on tramite
    for each row
begin
    if new.created_at is null then
        set new.created_at = now();
end if;
end//

create trigger trg_licencia_created_at
    before insert on licencia
    for each row
begin
    if new.created_at is null then
        set new.created_at = now();
end if;
end//

delimiter ;

-- =========================
-- PROCEDIMIENTO
-- =========================
delimiter //

create procedure total_licencias_por_fecha(in p_fecha date)
begin
select count(*) as total
from licencia
where fecha_emision = p_fecha;
end//

delimiter ;

-- =========================
-- DATOS INICIALES
-- =========================
insert into usuario (nombre, cedula, username, password_hash, rol)
values
    ('Administrador', '0102030405', 'admin', '1234', 'admin'),
    ('Analista Uno', '0102030406', 'analista', '1234', 'analista');
