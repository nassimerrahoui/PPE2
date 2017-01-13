-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Ven 16 Décembre 2016 à 15:50
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `nerrahoui`
--

-- --------------------------------------------------------

--
-- Structure de la table `appartenir`
--

CREATE TABLE `appartenir` (
  `id_equipe` int(11) NOT NULL,
  `id_personne` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `candidat`
--

CREATE TABLE `candidat` (
  `id_candidat` int(11) NOT NULL,
  `nom_candidat` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `candidat`
--

INSERT INTO `candidat` (`id_candidat`, `nom_candidat`) VALUES
(5, 'TeamRed'),
(6, 'TeamBlue'),
(8, 'Joueur 1'),
(9, 'TeamWhite'),
(10, 'Joueur'),
(11, 'TestParam'),
(12, ''),
(13, ''),
(14, NULL);

--
-- Déclencheurs `candidat`
--
DELIMITER $$
CREATE TRIGGER `before_update_candidat` BEFORE INSERT ON `candidat` FOR EACH ROW BEGIN
   IF NEW.nom_candidat = ""
   THEN 
   SET NEW.nom_candidat = NULL;
   END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `competition`
--

CREATE TABLE `competition` (
  `id_competition` int(11) NOT NULL,
  `nom_competition` varchar(50) NOT NULL,
  `dateCloture` date DEFAULT NULL,
  `dateOuverture` date DEFAULT NULL,
  `estEnEquipe` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `competition`
--

INSERT INTO `competition` (`id_competition`, `nom_competition`, `dateCloture`, `dateOuverture`, `estEnEquipe`) VALUES
(1, 'Test', '2017-01-20', '2017-01-10', 1),
(2, 'Solo', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `inscrire`
--

CREATE TABLE `inscrire` (
  `id_candidat` int(11) NOT NULL,
  `id_competition` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déclencheurs `inscrire`
--
DELIMITER $$
CREATE TRIGGER `before_insert_inscription` BEFORE INSERT ON `inscrire` FOR EACH ROW BEGIN
	DECLARE P1 int;
    DECLARE P2 int;

	SELECT competition.estEnEquipe INTO P1 FROM competition 
    WHERE competition.id_competition=NEW.id_competition;
    SELECT COUNT(personne.id_candidat) INTO P2 FROM personne 
    WHERE personne.id_candidat=NEW.id_candidat;
    
    IF P1 != P2 THEN 
    	SIGNAL SQLSTATE '45000';
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `personne`
--

CREATE TABLE `personne` (
  `prenom_personne` varchar(50) DEFAULT NULL,
  `mail` varchar(30) DEFAULT NULL,
  `id_candidat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `personne`
--

INSERT INTO `personne` (`prenom_personne`, `mail`, `id_candidat`) VALUES
('Test', 'joueur1@test.fr', 8),
('Joueur 2', 'joueur2@joueur.fr', 10),
('', '', 11),
('', '', 12),
('', '', 13),
('', '', 14);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `appartenir`
--
ALTER TABLE `appartenir`
  ADD PRIMARY KEY (`id_equipe`,`id_personne`),
  ADD KEY `FK_appartenir_id_personne` (`id_personne`);

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
-- Index pour la table `inscrire`
--
ALTER TABLE `inscrire`
  ADD PRIMARY KEY (`id_candidat`,`id_competition`),
  ADD KEY `FK_inscrire_id_competition` (`id_competition`);

--
-- Index pour la table `personne`
--
ALTER TABLE `personne`
  ADD PRIMARY KEY (`id_candidat`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `candidat`
--
ALTER TABLE `candidat`
  MODIFY `id_candidat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT pour la table `competition`
--
ALTER TABLE `competition`
  MODIFY `id_competition` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `appartenir`
--
ALTER TABLE `appartenir`
  ADD CONSTRAINT `FK_appartenir_id_equipe` FOREIGN KEY (`id_equipe`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `FK_appartenir_id_personne` FOREIGN KEY (`id_personne`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `appartenir_ibfk_1` FOREIGN KEY (`id_equipe`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `appartenir_ibfk_2` FOREIGN KEY (`id_personne`) REFERENCES `candidat` (`id_candidat`);

--
-- Contraintes pour la table `inscrire`
--
ALTER TABLE `inscrire`
  ADD CONSTRAINT `FK_inscrire_id_candidat` FOREIGN KEY (`id_candidat`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `FK_inscrire_id_competition` FOREIGN KEY (`id_competition`) REFERENCES `competition` (`id_competition`),
  ADD CONSTRAINT `inscrire_ibfk_1` FOREIGN KEY (`id_competition`) REFERENCES `competition` (`id_competition`);

--
-- Contraintes pour la table `personne`
--
ALTER TABLE `personne`
  ADD CONSTRAINT `FK_personne_id_candidat` FOREIGN KEY (`id_candidat`) REFERENCES `candidat` (`id_candidat`),
  ADD CONSTRAINT `personne_ibfk_1` FOREIGN KEY (`id_candidat`) REFERENCES `candidat` (`id_candidat`);
  

DELIMITER $$
--
-- Procédures
--
CREATE PROCEDURE `addCompetition` (IN `Competition` VARCHAR(100), IN `Cloture` DATE, IN `Ouverture` DATE, IN `EnEquipe` BOOLEAN)  INSERT INTO competition (nom_competition, dateCloture, dateOuverture, estEnEquipe) VALUES (@Competition, @Cloture, @Ouverture, @EnEquipe)$$

CREATE PROCEDURE `addEquipe` (IN `pCandidat` VARCHAR(50))  BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 

END$$

CREATE PROCEDURE `addMembre` (IN `idPersonne` INT, IN `idEquipe` INT)  BEGIN
    INSERT INTO appartenir (id_personne, id_equipe) VALUES (idPersonne, idEquipe);
END$$

CREATE  PROCEDURE `addPersonne` (IN `pCandidat` VARCHAR(50), IN `pPrenom` VARCHAR(50), IN `pMail` VARCHAR(50))  BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 
    SET @last_id_in_candidat = LAST_INSERT_ID();
    INSERT INTO personne (prenom_personne,mail,id_candidat) VALUES (pPrenom, pMail, @last_id_in_candidat);
    
END$$

CREATE  PROCEDURE `getCandidats` ()  BEGIN
    SELECT * FROM candidat;
END$$

CREATE  PROCEDURE `getCompetitions` (IN `idCandidat` INT)  BEGIN
    SELECT nom_competition, dateOuverture, dateCloture
    FROM competition, candidat, inscrire
    WHERE competition.id_competition = inscrire.id_competition
    AND inscrire.id_candidat = candidat.id_candidat
    AND candidat.id_candidat = idCandidat;
END$$

CREATE  PROCEDURE `getEquipes` ()  BEGIN
    SELECT nom_candidat
    FROM candidat, personne
    WHERE candidat.id_candidat <> personne.id_candidat;
END$$

CREATE  PROCEDURE `getInscriptions` ()  BEGIN
    SELECT * FROM inscrire;
END$$

CREATE  PROCEDURE `getMembres` (IN `idEquipe` INT)  BEGIN
    SELECT id_personne
    FROM appartenir WHERE id_equipe = idEquipe;
END$$

CREATE  PROCEDURE `getPersonnes` ()  BEGIN
    SELECT nom_candidat
    FROM candidat, personne
    WHERE candidat.id_candidat = personne.id_candidat;
END$$

CREATE  PROCEDURE `inscriptionEquipe` (IN `idEquipe` INT, IN `idCompetition` INT)  BEGIN
    INSERT INTO inscrire (id_candidat, id_competition) VALUES (idEquipe, idCompetition);
END$$

CREATE  PROCEDURE `inscriptionPersonne` (IN `idPersonne` INT, IN `idCompetition` INT)  BEGIN
    INSERT INTO inscrire (id_candidat, id_competition) VALUES (idPersonne, idCompetition);
END$$

CREATE  PROCEDURE `reinitialiseInscription` ()  BEGIN
    DELETE FROM inscrire;
END$$

CREATE  PROCEDURE `removeCandidat` (IN `idCandidat` INT, IN `idCompetition` INT)  BEGIN
    DELETE FROM inscrire WHERE id_candidat=idCandidat AND id_competition=idCompetition;
END$$

CREATE  PROCEDURE `removeMembre` (IN `idEquipe` INT, IN `idPersonne` INT)  BEGIN
    DELETE FROM appartenir WHERE id_equipe=idEquipe AND id_personne=idPersonne;
END$$

CREATE  PROCEDURE `setCompetition` (IN `Competition` VARCHAR(50), IN `Ouverture` DATE, IN `Cloture` DATE, IN `EnEquipe` BOOLEAN, IN `pID` INT)  BEGIN

UPDATE competition SET nom_competition = @Competition, dateOuverture = @Ouverture, dateCloture = @Cloture, estEnEquipe = @EnEquipe WHERE id_competition = @pID;

END$$

CREATE  PROCEDURE `suppCandidat` (IN `pID` INT)  BEGIN
    DELETE FROM candidat WHERE candidat.id_candidat = pID ;
END$$

CREATE  PROCEDURE `suppCompetition` (`pID` INT)  BEGIN
    DELETE FROM competition WHERE competition.id_competition = pID;                            
END$$

DELIMITER ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
