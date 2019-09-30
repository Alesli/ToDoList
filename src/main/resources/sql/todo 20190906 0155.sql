
-- Отключение внешних ключей
-- 
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;

-- 
-- Установить режим SQL (SQL mode)
-- 
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 
-- Установка кодировки, с использованием которой клиент будет посылать запросы на сервер
--
SET NAMES 'utf8';

DROP DATABASE IF EXISTS todo;

CREATE DATABASE IF NOT EXISTS todo
CHARACTER SET utf8
COLLATE utf8_general_ci;

USE todo;

CREATE TABLE IF NOT EXISTS task (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  description varchar(255) DEFAULT NULL,
  event_date date NOT NULL,
  deleted tinyint(1) NOT NULL,
  completed tinyint(1) NOT NULL,
  orig_filename varchar(50) DEFAULT NULL,
  gener_filepath varchar(255) DEFAULT NULL,
  gener_filename varchar(40) DEFAULT NULL,
  mime_type varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 6,
CHARACTER SET utf8,
COLLATE utf8_general_ci,
COMMENT = 'task to perform';

INSERT INTO task VALUES
(1, 'relax', 'sleep, read the book', '2019-09-09', 0, 0, 'IT.txt', 'f:/bin/downloads/upload_folder/p/k/', '6cf56988-b05a-452a-bd67-db216deb45d1', 'text/plain'),
(2, 'go to shop', 'buy milk, bread', '2019-09-06', 0, 0, 'black_cat.png', 'f:/bin/downloads/upload_folder/w/p/', 'e51ab2a7-ec10-4a1d-a602-57b0ff5fb1f8', 'image/png'),
(3, 'read', 'read magazine', '2019-09-12', 0, 0, NULL, NULL, NULL, NULL),
(4, 'learn', 'read documentation Gerrit, Git', '2019-09-10', 0, 0, NULL, NULL, NULL, NULL);

-- 
-- Восстановить предыдущий режим SQL (SQL mode)
-- 
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;

-- 
-- Включение внешних ключей
-- 
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;