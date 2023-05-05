-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 25, 2023 at 09:57 AM
-- Server version: 8.0.33
-- PHP Version: 7.4.3-4ubuntu2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vodkadatabase`
--

-- --------------------------------------------------------

--
-- Table structure for table `Categories`
--

CREATE TABLE `Categories` (
  `CatId` varchar(255) NOT NULL,
  `CatName` longtext,
  `Descript` longtext,
  `IsActive` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `Categories`
--

INSERT INTO `Categories` (`CatId`, `CatName`, `Descript`, `IsActive`) VALUES
('C00001', 'Rượu vang đỏ', '', '1'),
('C00002', 'Rượu vang trắng', '', '1'),
('C00003', 'Vang bịch', '', '1'),
('C00004', 'Rượu vang nổ', '', '1'),
('C00005', 'Vang hồng', '', '1'),
('C00006', 'Vang ngọt', '', '1'),
('C00007', 'Vang Organic', '', '1'),
('C00008', 'Champagne', '', '1');

-- --------------------------------------------------------

--
-- Table structure for table `Paymentmethods`
--

CREATE TABLE `Paymentmethods` (
  `PaymentId` varchar(255) NOT NULL,
  `PaymentName` longtext,
  `Descript` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `Paymentmethods`
--

INSERT INTO `Paymentmethods` (`PaymentId`, `PaymentName`, `Descript`) VALUES
('P00001', 'TIỀN MẶT', ''),
('P00002', 'THẺ', ''),
('P00003', 'CÔNG NỢ', ''),
('P00004', 'MOMO', 'Thanh toán bằng ví điện tử momo');

-- --------------------------------------------------------

--
-- Table structure for table `Products`
--

CREATE TABLE `Products` (
  `ProductNum` varchar(255) NOT NULL,
  `ProductName` longtext,
  `Descript` longtext,
  `Price` longtext,
  `Tax1` longtext,
  `Tax2` longtext,
  `Tax3` longtext,
  `Quan` longtext,
  `IsActive` longtext,
  `ImageSource` longtext,
  `CatId` longtext,
  `CategoryCatId` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `Products`
--

INSERT INTO `Products` (`ProductNum`, `ProductName`, `Descript`, `Price`, `Tax1`, `Tax2`, `Tax3`, `Quan`, `IsActive`, `ImageSource`, `CatId`, `CategoryCatId`) VALUES
('P00085', 'Rượu Vang  Argiolas Serra Lori', '', '500000', '0', '0', '0', '99', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/7k.jpg', 'C00005', NULL),
('P00086', 'Rượu Vang Buglioni Rosato Trevenezie Rosacipolla', '', '600000', '0', '0', '0', '99', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/7p.jpg', 'C00005', NULL),
('P00087', 'Rượu Vang Carpineto Dogajolo Sangiovese', '', '510000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/4j.jpg', 'C00005', NULL),
('P00088', 'Rượu Vang Jansz Tasmania Premium Cuvée Rose', '', '980000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/ruou-vang-jansz-tasmania-premium-cuvee-rose.jpg', 'C00005', NULL),
('P00089', 'Rượu Vang Soupcon De Fruit Rose D’Anjou', '', '536000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/94.jpg', 'C00005', NULL),
('P00090', 'Rượu Vang Pháp Arrogant Sparkling Rose', '', '310000', '0', '0', '0', '94', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/07/aa1.jpg', 'C00005', NULL),
('P00100', 'Rượu Vang Pipoly Rosato Basilicata', '', '660000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/12/ruou-vang-pipoly-rosato-basilicata.jpg', 'C00006', NULL),
('P00102', 'Rượu Vang Sensuale Moscato Di Basilicata', '', '450000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/12/ruou-vang-sensuale-moscato-di-basilicata.jpg', 'C00006', NULL),
('P00103', 'Rượu Vang Rapel Carilisa Moscato VinoSpumate', '', '550000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/12/Ruou-vang-Rapel-Carilisa-Moscato-Vino-Spumate.png', 'C00006', NULL),
('P00104', 'Rượu Vang Donnabella Rosso Semidolce', '', '350000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/11/Untitled-1.jpg', 'C00006', NULL),
('P00105', 'Rượu Vang Bubbles Moscato D’asti', '', '630000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/08/ruou-vang-bubbles-moscato-dasti-2.jpg', 'C00006', NULL),
('P00106', 'Rượu Vang Moscato Spumante DolceTini', '', '280000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/04/ruou-vang-moscato-spumante-dolce-tini.jpg', 'C00006', NULL),
('P00107', 'Rượu Vang Queen Queen', '', '320000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/04/quen.jpg', 'C00006', NULL),
('P00108', 'Rượu Vang Moscato D’asti Bosio', '', '550000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2020/08/ruou-vang-bosio-moscato-dasti.jpg', 'C00006', NULL),
('P00109', 'Rượu Vang CF Sotto Sopra', '', '950000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2020/08/ruou-vang-cf-sotto-sopra.jpg', 'C00006', NULL),
('P00110', 'Rượu Vang Kardos Tunder Mese', '', '520000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/99s.jpg', 'C00006', NULL),
('P00111', 'Rượu Vang Tokaji Muscat Lunel', '', '490000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/99p.jpg', 'C00006', NULL),
('P00112', 'Rượu Vang Banrock Station Moscato', '', '480000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/ruou-vang-banrock-station-moscato.jpg', 'C00006', NULL),
('P00113', 'Rượu Vang Banrock Station Crimson Cabernet', '', '480000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/ruou-vang-banrock-station-crimson-cabernet.jpg', 'C00006', NULL),
('P00114', 'Rượu Vang Tosti 1820 Asti Sparkling Wine', '', '500000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/8n.jpg', 'C00006', NULL),
('P00115', 'Rượu Vang Tosti 1820 Pink Moscato', '', '500000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/8v.jpg', 'C00006', NULL),
('P00116', 'Rượu Vang Petit Guiraud Sauternes', '', '1750000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/ruou-vang-petit-guiraud-sauternes.jpg', 'C00006', NULL),
('P00117', 'Rượu Vang Château De Montgueret Côteaux DuLayon', '', '710000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/ruou-vang-chateau-de-montgueret-coteaux-du-layon.jpg', 'C00006', NULL),
('P00118', 'Rượu Vang Soleil Gascon Semi Sweet', '', '380000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/ruou-vang-soleil-gascon-semi-sweet.jpg', 'C00006', NULL),
('P00119', 'Rượu Vang ngọt Romania Aprenta', '', '986000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/8999.jpg', 'C00006', NULL),
('P00120', 'Rượu Vang ngọt Dulong Moelleux Bordeaux', '', '356000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2021/08/85.jpg', 'C00006', NULL),
('P00121', 'Rượu Vang Odfjell Armador Sauvignon Blanc', '', '585000', '0', '0', '0', '100', '1', 'https://vangchat.com.vn/wp-content/uploads/2022/12/ruou-vang-odfjell-armador-sauvignon-blanc-1.jpg', 'C00007', NULL),
('P00122', 'Rượu đế VN', 'Rất ngon', '12000000', '1', '2', '3', '15', '1', 'thanhlam.com', 'C00002', 'C00002');

-- --------------------------------------------------------

--
-- Table structure for table `Taxinfos`
--

CREATE TABLE `Taxinfos` (
  `TaxId` varchar(255) NOT NULL,
  `TaxDes` longtext,
  `TaxRate` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `Taxinfos`
--

INSERT INTO `Taxinfos` (`TaxId`, `TaxDes`, `TaxRate`) VALUES
('T01', 'VAT', '10'),
('T02', 'None', '20'),
('T03', 'None', '15');

-- --------------------------------------------------------

--
-- Table structure for table `Transactdetails`
--

CREATE TABLE `Transactdetails` (
  `TransactDetailId` varchar(255) NOT NULL,
  `CostEach` longtext,
  `Tax1` longtext,
  `Tax2` longtext,
  `Tax3` longtext,
  `Total` longtext,
  `Quan` int DEFAULT NULL,
  `Status` longtext,
  `TransactId` varchar(255) DEFAULT NULL,
  `ProductNum` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `Transactdetails`
--

INSERT INTO `Transactdetails` (`TransactDetailId`, `CostEach`, `Tax1`, `Tax2`, `Tax3`, `Total`, `Quan`, `Status`, `TransactId`, `ProductNum`) VALUES
('TD00001', '500000', '0', '0', '0', '500000', 1, '1', 'TS00001', 'P00085'),
('TD00002', '600000', '0', '0', '0', '600000', 1, '1', 'TS00002', 'P00086'),
('TD00003', '510000', '0', '0', '0', '1020000', 2, '1', 'TS00002', 'P00087'),
('TD00004', '980000', '0', '0', '0', '2940000', 3, '1', 'TS00002', 'P00088'),
('TD00005', '310000', '0', '0', '0', '310000', 1, '1', 'TS00003', 'P00090'),
('TD00006', '600000', '0', '0', '0', '600000', 1, '3', 'TS00001', 'P00086');

-- --------------------------------------------------------

--
-- Table structure for table `Transactheaders`
--

CREATE TABLE `Transactheaders` (
  `TransactId` varchar(255) NOT NULL,
  `Net` longtext,
  `Tax1` longtext,
  `Tax2` longtext,
  `Tax3` longtext,
  `Total` longtext,
  `TimePayment` datetime(6) DEFAULT NULL,
  `WhoPay` longtext,
  `Status` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `Transactheaders`
--

INSERT INTO `Transactheaders` (`TransactId`, `Net`, `Tax1`, `Tax2`, `Tax3`, `Total`, `TimePayment`, `WhoPay`, `Status`) VALUES
('TS00001', '500000', '1', '1', '1', '725000', '2023-03-15 12:11:00.000000', 'U00001', '3'),
('TS00002', '4560000', '0', '0', '0', '4560000', '2023-03-14 07:55:00.000000', 'U00002', '3'),
('TS00003', '810000', '0', '0', '0', '810000', '2023-04-20 09:04:14.322000', 'U00002', '3');

-- --------------------------------------------------------

--
-- Table structure for table `Useraccounts`
--

CREATE TABLE `Useraccounts` (
  `UserId` varchar(255) NOT NULL,
  `UserName` longtext,
  `UserPassword` longtext,
  `AccessLevel` int NOT NULL,
  `TotalCash` longtext,
  `IsActive` longtext,
  `Email` longtext,
  `Address` longtext
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `Useraccounts`
--

INSERT INTO `Useraccounts` (`UserId`, `UserName`, `UserPassword`, `AccessLevel`, `TotalCash`, `IsActive`, `Email`, `Address`) VALUES
('U00001', 'AdminTest', 'thongtrandp1', 1, '3440000', '1', 'thongtrandp1@gmail.com', '278 trih dih trog'),
('U00002', 'Admin', 'thongtrandp1', 100, '910000', '1', '', ''),
('U00003', 'Client', 'thongtrandp1', 50, '250000', '1', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `__EFMigrationsHistory`
--

CREATE TABLE `__EFMigrationsHistory` (
  `MigrationId` varchar(150) NOT NULL,
  `ProductVersion` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

--
-- Dumping data for table `__EFMigrationsHistory`
--

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`) VALUES
('20230413153411_Initial', '7.0.4');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Categories`
--
ALTER TABLE `Categories`
  ADD PRIMARY KEY (`CatId`);

--
-- Indexes for table `Paymentmethods`
--
ALTER TABLE `Paymentmethods`
  ADD PRIMARY KEY (`PaymentId`);

--
-- Indexes for table `Products`
--
ALTER TABLE `Products`
  ADD PRIMARY KEY (`ProductNum`),
  ADD KEY `IX_Products_CategoryCatId` (`CategoryCatId`);

--
-- Indexes for table `Taxinfos`
--
ALTER TABLE `Taxinfos`
  ADD PRIMARY KEY (`TaxId`);

--
-- Indexes for table `Transactdetails`
--
ALTER TABLE `Transactdetails`
  ADD PRIMARY KEY (`TransactDetailId`),
  ADD KEY `IX_Transactdetails_ProductNum` (`ProductNum`),
  ADD KEY `IX_Transactdetails_TransactId` (`TransactId`);

--
-- Indexes for table `Transactheaders`
--
ALTER TABLE `Transactheaders`
  ADD PRIMARY KEY (`TransactId`);

--
-- Indexes for table `Useraccounts`
--
ALTER TABLE `Useraccounts`
  ADD PRIMARY KEY (`UserId`);

--
-- Indexes for table `__EFMigrationsHistory`
--
ALTER TABLE `__EFMigrationsHistory`
  ADD PRIMARY KEY (`MigrationId`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Products`
--
ALTER TABLE `Products`
  ADD CONSTRAINT `FK_Products_Categories_CategoryCatId` FOREIGN KEY (`CategoryCatId`) REFERENCES `Categories` (`CatId`);

--
-- Constraints for table `Transactdetails`
--
ALTER TABLE `Transactdetails`
  ADD CONSTRAINT `FK_Transactdetails_Products_ProductNum` FOREIGN KEY (`ProductNum`) REFERENCES `Products` (`ProductNum`),
  ADD CONSTRAINT `FK_Transactdetails_Transactheaders_TransactId` FOREIGN KEY (`TransactId`) REFERENCES `Transactheaders` (`TransactId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
