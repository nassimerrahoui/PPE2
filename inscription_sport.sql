-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Ven 02 Décembre 2016 à 15:51
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `inscription_sport`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addPersonne` (IN `pCandidat` VARCHAR(50), IN `pPrenom` VARCHAR(50), IN `pMail` VARCHAR(50))  BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 
    SET @last_id_in_candidat = LAST_INSERT_ID();
    INSERT INTO personne (prenom_personne,mail,id_candidat) VALUES (pPrenom, pMail, @last_id_in_candidat);
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `ajoutCompetition` (IN `Competition` VARCHAR(100) CHARSET utf8, IN `Cloture` DATE, IN `EnEquipe` BOOLEAN)  MODIFIES SQL DATA
INSERT INTO competition (nom_competition, dateCloture, estEnEquipe) VALUES (@Competition, @Cloture, @EnEquipe)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `suppCandidat` (IN `pID` INT)  BEGIN
    DELETE FROM candidat WHERE candidat.id_candidat = pID ;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `suppCompetition` (`pID` INT)  BEGIN
    DELETE FROM competition WHERE competition.id_competition = pID;                            
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `appartenir`
--

CREATE TABLE `appartenir` (
  `id_equipe` int(11) NOT NULL,
  `id_candidat` int(11) NOT NULL,
  `id_personne` int(11) NOT NULL,
  `id_candidat_1` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `candidat`
--

CREATE TABLE `candidat` (
  `id_candidat` int(11) NOT NULL,
  `nom_candidat` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `candidat`
--

INSERT INTO `candidat` (`id_candidat`, `nom_candidat`) VALUES
(1, 'test1'),
(3, 'test3'),
(5, 'test4'),
(6, 'test5');

-- --------------------------------------------------------

--
-- Structure de la table `competition`
--

CREATE TABLE `competition` (
  `id_competition` int(11) NOT NULL,
  `nom_competition` varchar(100) NOT NULL,
  `dateCloture` date DEFAULT NULL,
  `dateOuverture` date DEFAULT NULL,
  `estEnEquipe` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `competition`
--

INSERT INTO `competition` (`id_competition`, `nom_competition`, `dateCloture`, `dateOuverture`, `estEnEquipe`) VALUES
(1, 'Test', '2016-12-24', '2016-11-25', 1);

-- --------------------------------------------------------

--
-- Structure de la table `equipe`
--

CREATE TABLE `equipe` (
  `id_equipe` int(11) NOT NULL,
  `id_candidat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `inscrire`
--

CREATE TABLE `inscrire` (
  `id_candidat` int(11) NOT NULL,
  `id_competition` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `id_personne` int(11) NOT NULL,
  `prenom_personne` varchar(25) DEFAULT NULL,
  `mail` varchar(25) DEFAULT NULL,
  `id_candidat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `personne`
--

INSERT INTO `personne` (`id_personne`, `prenom_personne`, `mail`, `id_candidat`) VALUES
(1, 'test4', 'test4', 5),
(2, 'test5', 'test5', 6);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `appartenir`
--
ALTER TABLE `appartenir`
  ADD PRIMARY KEY (`id_equipe`,`id_candidat`,`id_personne`,`id_candidat_1`),
  ADD KEY `FK_appartenir_id_candidat` (`id_candidat`),
  ADD KEY `FK_appartenir_id_personne` (`id_personne`),
  ADD KEY `FK_appartenir_id_candidat_1` (`id_candidat_1`);

--
-- Index pour la table `candidat`
--
ALTER TABLE `candidat`
  ADD PRIMARY KEY (`id_candidat`);

--
-- Index pour la table `competition`
--
ALTER TABLE `competition`
  ADD PRIMARY KEY (`id_competition`);

--
-- Index pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD PRIMARY KEY (`id_equipe`,`id_candidat`),
  ADD KEY `FK_equipe_id_candidat` (`id_candidat`);

--
-- Index pour la table `inscrire`
--
ALTER TABLE `inscrire`
  ADD PRIMARY KEY (`id_candidat`,`id_competition`),
  ADD KEY `FK_inscrire_id_competition` (`id_competition`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id_personne`,`id_candidat`),
  ADD KEY `FK_personne_id_candidat` (`id_candidat`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `candidat`
--
ALTER TABLE `candidat`
  MODIFY `id_candidat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT pour la table `competition`
--
ALTER TABLE `competition`
  MODIFY `id_competition` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT pour la table `equipe`
--
ALTER TABLE `equipe`
  MODIFY `id_equipe` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `personne`
--
ALTER TABLE `personne`
  MODIFY `id_personne` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `appartenir`
--
ALTER TABLE `appartenir`
  ADD CONSTRAINT `FK_appartenir_id_candidat` FOREIGN KEY (`id_candidat`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `FK_appartenir_id_candidat_1` FOREIGN KEY (`id_candidat_1`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `FK_appartenir_id_equipe` FOREIGN KEY (`id_equipe`) REFERENCES `equipe` (`id_equipe`),
  ADD CONSTRAINT `FK_appartenir_id_personne` FOREIGN KEY (`id_personne`) REFERENCES `personne` (`id_personne`);

--
-- Contraintes pour la table `equipe`
--
ALTER TABLE `equipe`
  ADD CONSTRAINT `FK_equipe_id_candidat` FOREIGN KEY (`id_candidat`) REFERENCES `candidat` (`id_candidat`);

--
-- Contraintes pour la table `inscrire`
--
ALTER TABLE `inscrire`
  ADD CONSTRAINT `FK_inscrire_id_candidat` FOREIGN KEY (`id_candidat`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `FK_inscrire_id_competition` FOREIGN KEY (`id_competition`) REFERENCES `competition` (`id_competition`);

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `FK_personne_id_candidat` FOREIGN KEY (`id_candidat`) REFERENCES `candidat` (`id_candidat`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
