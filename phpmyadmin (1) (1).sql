-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1
-- Время создания: Ноя 27 2024 г., 17:12
-- Версия сервера: 10.4.32-MariaDB
-- Версия PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `phpmyadmin`
--

DELIMITER $$
--
-- Процедуры
--
CREATE DEFINER=`user013_user1`@`localhost` PROCEDURE `GetCertificationTests` ()   BEGIN
    SELECT
        ct.TestID,
        oi.Name AS ObjectName,
        ct.TestName,
        ct.TestDate,
        ct.Status
    FROM
        Certification_Tests ct
    JOIN
        Object_Information oi ON ct.ObjectID = oi.ObjectID;
END$$

CREATE DEFINER=`user013_user1`@`localhost` PROCEDURE `GetSpecialResearch` ()   BEGIN
    SELECT
        sr.ResearchID,
        oi.Name AS ObjectName,
        sr.ResearchName,
        sr.ResearchDate,
        sr.Result
    FROM
        Special_Research sr
    JOIN
        Object_Information oi ON sr.ObjectID = oi.ObjectID;
END$$

DELIMITER ;


--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `balance` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Структура таблицы `игровые_сессии`
--

CREATE TABLE `игровые_сессии` (
  `ID_сессии` int(11) NOT NULL,
  `ID_пользователя` int(11) DEFAULT NULL,
  `ID_компьютера` int(11) DEFAULT NULL,
  `Время_начала` datetime NOT NULL,
  `Время_окончания` datetime DEFAULT NULL,
  `Стоимость` decimal(10,2) NOT NULL,
  `Длительность` time GENERATED ALWAYS AS (timediff(`Время_окончания`,`Время_начала`)) VIRTUAL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `игровые_сессии`
--

INSERT INTO `игровые_сессии` (`ID_сессии`, `ID_пользователя`, `ID_компьютера`, `Время_начала`, `Время_окончания`, `Стоимость`) VALUES
(2, 2, 4, '2024-11-21 21:40:58', '2024-11-26 01:40:58', 10000.00),
(3, 2, 4, '2024-11-21 21:41:02', '2024-11-26 01:41:02', 10000.00),
(4, 2, 4, '2024-11-21 21:41:02', '2024-11-26 01:41:02', 10000.00),
(5, 2, 4, '2024-11-21 21:41:02', '2024-11-26 01:41:02', 10000.00),
(6, 2, 4, '2024-11-21 21:41:02', '2024-11-26 01:41:02', 10000.00),
(7, 2, 4, '2024-11-21 21:41:03', '2024-11-26 01:41:03', 10000.00);

-- --------------------------------------------------------

--
-- Структура таблицы `компьютеры`
--

CREATE TABLE `компьютеры` (
  `ID` int(11) NOT NULL,
  `Характеристики` varchar(255) NOT NULL,
  `Статус` enum('Свободен','Занят','На техобслуживании') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `компьютеры`
--

INSERT INTO `компьютеры` (`ID`, `Характеристики`, `Статус`) VALUES
(1, 'ыаывавыа', 'Свободен'),
(2, 'ыаывавыа', 'Свободен'),
(3, 'ыаывавыа', 'Свободен'),
(4, 'ыаывавыа', 'Свободен'),
(5, 'ыаывавыа', 'Свободен');

-- --------------------------------------------------------

--
-- Структура таблицы `пользователи`
--

CREATE TABLE `пользователи` (
  `ID` int(11) NOT NULL,
  `Имя` varchar(100) NOT NULL,
  `Контактные_данные` varchar(100) DEFAULT NULL,
  `Статус` enum('VIP','Обычный','Администратор') NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `Дата_регистрации` datetime DEFAULT current_timestamp(),
  `Баланс` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Дамп данных таблицы `пользователи`
--

INSERT INTO `пользователи` (`ID`, `Имя`, `Контактные_данные`, `Статус`, `password`, `Дата_регистрации`, `Баланс`) VALUES
(1, 'admin', '1232141', 'Администратор', '1', '2024-11-08 12:01:34', 0),
(2, 'Tanakamarus', '123123', 'Обычный', '123', '2024-11-08 12:02:28', 120523);

-- --------------------------------------------------------

--
-- Структура таблицы `оплаты`
--

CREATE TABLE `оплаты` (
  `ID_оплаты` int(11) NOT NULL,
  `ID_пользователя` int(11) DEFAULT NULL,
  `Сумма` decimal(10,2) NOT NULL,
  `Дата_время` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `pma__central_columns`
--
ALTER TABLE `pma__central_columns`
  ADD PRIMARY KEY (`db_name`,`col_name`);

--
-- Индексы таблицы `pma__column_info`
--
ALTER TABLE `pma__column_info`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `db_name` (`db_name`,`table_name`,`column_name`);

--
-- Индексы таблицы `pma__designer_settings`
--
ALTER TABLE `pma__designer_settings`
  ADD PRIMARY KEY (`username`);

--
-- Индексы таблицы `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_user_type_template` (`username`,`export_type`,`template_name`);

--
-- Индексы таблицы `pma__favorite`
--
ALTER TABLE `pma__favorite`
  ADD PRIMARY KEY (`username`);

--
-- Индексы таблицы `pma__history`
--
ALTER TABLE `pma__history`
  ADD PRIMARY KEY (`id`),
  ADD KEY `username` (`username`,`db`,`table`,`timevalue`);

--
-- Индексы таблицы `pma__navigationhiding`
--
ALTER TABLE `pma__navigationhiding`
  ADD PRIMARY KEY (`username`,`item_name`,`item_type`,`db_name`,`table_name`);

--
-- Индексы таблицы `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  ADD PRIMARY KEY (`page_nr`),
  ADD KEY `db_name` (`db_name`);

--
-- Индексы таблицы `pma__recent`
--
ALTER TABLE `pma__recent`
  ADD PRIMARY KEY (`username`);

--
-- Индексы таблицы `pma__relation`
--
ALTER TABLE `pma__relation`
  ADD PRIMARY KEY (`master_db`,`master_table`,`master_field`),
  ADD KEY `foreign_field` (`foreign_db`,`foreign_table`);

--
-- Индексы таблицы `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `u_savedsearches_username_dbname` (`username`,`db_name`,`search_name`);

--
-- Индексы таблицы `pma__table_coords`
--
ALTER TABLE `pma__table_coords`
  ADD PRIMARY KEY (`db_name`,`table_name`,`pdf_page_number`);

--
-- Индексы таблицы `pma__table_info`
--
ALTER TABLE `pma__table_info`
  ADD PRIMARY KEY (`db_name`,`table_name`);

--
-- Индексы таблицы `pma__table_uiprefs`
--
ALTER TABLE `pma__table_uiprefs`
  ADD PRIMARY KEY (`username`,`db_name`,`table_name`);

--
-- Индексы таблицы `pma__tracking`
--
ALTER TABLE `pma__tracking`
  ADD PRIMARY KEY (`db_name`,`table_name`,`version`);

--
-- Индексы таблицы `pma__userconfig`
--
ALTER TABLE `pma__userconfig`
  ADD PRIMARY KEY (`username`);

--
-- Индексы таблицы `pma__usergroups`
--
ALTER TABLE `pma__usergroups`
  ADD PRIMARY KEY (`usergroup`,`tab`,`allowed`);

--
-- Индексы таблицы `pma__users`
--
ALTER TABLE `pma__users`
  ADD PRIMARY KEY (`username`,`usergroup`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `игровые_сессии`
--
ALTER TABLE `игровые_сессии`
  ADD PRIMARY KEY (`ID_сессии`),
  ADD KEY `ID_пользователя` (`ID_пользователя`),
  ADD KEY `ID_компьютера` (`ID_компьютера`);

--
-- Индексы таблицы `компьютеры`
--
ALTER TABLE `компьютеры`
  ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `пользователи`
--
ALTER TABLE `пользователи`
  ADD PRIMARY KEY (`ID`);

--
-- Индексы таблицы `оплаты`
--
ALTER TABLE `оплаты`
  ADD PRIMARY KEY (`ID_оплаты`),
  ADD KEY `ID_пользователя` (`ID_пользователя`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `pma__bookmark`
--
ALTER TABLE `pma__bookmark`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `pma__column_info`
--
ALTER TABLE `pma__column_info`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `pma__export_templates`
--
ALTER TABLE `pma__export_templates`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `pma__history`
--
ALTER TABLE `pma__history`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `pma__pdf_pages`
--
ALTER TABLE `pma__pdf_pages`
  MODIFY `page_nr` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `pma__savedsearches`
--
ALTER TABLE `pma__savedsearches`
  MODIFY `id` int(5) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `игровые_сессии`
--
ALTER TABLE `игровые_сессии`
  MODIFY `ID_сессии` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT для таблицы `компьютеры`
--
ALTER TABLE `компьютеры`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `пользователи`
--
ALTER TABLE `пользователи`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `оплаты`
--
ALTER TABLE `оплаты`
  MODIFY `ID_оплаты` int(11) NOT NULL AUTO_INCREMENT;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `игровые_сессии`
--
ALTER TABLE `игровые_сессии`
  ADD CONSTRAINT `Игровые_сессии_ibfk_1` FOREIGN KEY (`ID_пользователя`) REFERENCES `пользователи` (`ID`) ON DELETE CASCADE,
  ADD CONSTRAINT `Игровые_сессии_ibfk_2` FOREIGN KEY (`ID_компьютера`) REFERENCES `компьютеры` (`ID`) ON DELETE SET NULL;

--
-- Ограничения внешнего ключа таблицы `оплаты`
--
ALTER TABLE `оплаты`
  ADD CONSTRAINT `Оплаты_ibfk_1` FOREIGN KEY (`ID_пользователя`) REFERENCES `пользователи` (`ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
