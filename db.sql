-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 23-02-2022 a las 15:57:26
-- Versión del servidor: 10.4.17-MariaDB
-- Versión de PHP: 8.0.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `restaurante`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detallefactura`
--

CREATE TABLE `detallefactura` (
  `idfactura` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `preciototal` double NOT NULL,
  `fkidplato` int(11) NOT NULL,
  `fkidservicio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `detallefactura`
--

INSERT INTO `detallefactura` (`idfactura`, `cantidad`, `preciototal`, `fkidplato`, `fkidservicio`) VALUES
(7, 4, 8, 2, 1),
(8, 3, 21, 1, 1),
(11, 12, 84, 1, 2),
(23, 7, 49, 1, 30),
(24, 1, 2, 2, 30),
(25, 1, 29, 4, 30),
(26, 1, 24, 6, 2),
(27, 3, 12, 3, 2),
(28, 2, 4, 2, 2),
(29, 1, 23, 5, 2),
(30, 1, 29, 4, 2),
(31, 1, 4, 3, 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesas`
--

CREATE TABLE `mesas` (
  `nummesa` int(11) NOT NULL,
  `ocupantesmax` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `mesas`
--

INSERT INTO `mesas` (`nummesa`, `ocupantesmax`) VALUES
(1, 4),
(2, 4),
(3, 2),
(4, 2),
(5, 2),
(6, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operario`
--

CREATE TABLE `operario` (
  `idoperario` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `password` varchar(250) NOT NULL,
  `rol` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `operario`
--

INSERT INTO `operario` (`idoperario`, `nombre`, `password`, `rol`) VALUES
(3, 'juan', '$2a$10$NWtvSpco2NRbGt5do4BBX.uozD9rdUO/XbM8QHwYPo1/fuxklJBEK', 'ROLE_USER');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `platos`
--

CREATE TABLE `platos` (
  `idplato` int(11) NOT NULL,
  `preciounidad` double NOT NULL,
  `disponible` bit(1) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `descripcion` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `platos`
--

INSERT INTO `platos` (`idplato`, `preciounidad`, `disponible`, `nombre`, `descripcion`) VALUES
(1, 7, b'1', 'Pizza Margarita', 'Típica pizza napolitana elaborada con tomate, mozz'),
(2, 2, b'1', 'Arepa', 'Masa de maíz seco molido o de harina de maíz preco'),
(3, 4, b'1', 'Cachapa', 'Harina de maiz dulce, queso, mantequilla'),
(4, 29.6, b'1', 'Lambcasing', 'Removal of Ext Fix from L Tarsal Jt, Open Approach'),
(5, 23, b'1', 'Sea Bass - Fillets', 'Insert Endobronch Valve in R Low Lobe Bronc, Via O'),
(6, 24.9, b'1', 'Hot Choc Vending', 'Alteration of Right Upper Leg with Autol Sub, Open'),
(7, 4, b'1', 'Mahi Mahi', 'Bypass Cystic Duct to Stomach w Intralum Dev, Perc');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `idservicio` int(11) NOT NULL,
  `fechacomienzo` bigint(20) NOT NULL,
  `fechafin` bigint(20) NOT NULL,
  `reservada` varchar(50) NOT NULL,
  `pagada` bit(1) NOT NULL,
  `fkmesa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `servicio`
--

INSERT INTO `servicio` (`idservicio`, `fechacomienzo`, `fechafin`, `reservada`, `pagada`, `fkmesa`) VALUES
(1, 1645552528333, 1645559728333, 'Reserva para comer la familia perez', b'1', 1),
(2, 1644483300000, 1644490500000, 'Reserva para 4 amigos ', b'1', 2),
(30, 1645816740000, 1645823940000, 'asd', b'0', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  ADD PRIMARY KEY (`idfactura`),
  ADD KEY `fkidplato` (`fkidplato`),
  ADD KEY `fkidservicio` (`fkidservicio`);

--
-- Indices de la tabla `mesas`
--
ALTER TABLE `mesas`
  ADD PRIMARY KEY (`nummesa`);

--
-- Indices de la tabla `operario`
--
ALTER TABLE `operario`
  ADD PRIMARY KEY (`idoperario`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `platos`
--
ALTER TABLE `platos`
  ADD PRIMARY KEY (`idplato`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`idservicio`),
  ADD KEY `fkmesa_mesas` (`fkmesa`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  MODIFY `idfactura` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `operario`
--
ALTER TABLE `operario`
  MODIFY `idoperario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `platos`
--
ALTER TABLE `platos`
  MODIFY `idplato` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `idservicio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detallefactura`
--
ALTER TABLE `detallefactura`
  ADD CONSTRAINT `fkidplato` FOREIGN KEY (`fkidplato`) REFERENCES `platos` (`idplato`),
  ADD CONSTRAINT `fkidservicio` FOREIGN KEY (`fkidservicio`) REFERENCES `servicio` (`idservicio`);

--
-- Filtros para la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD CONSTRAINT `fkmesa_mesas` FOREIGN KEY (`fkmesa`) REFERENCES `mesas` (`nummesa`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
