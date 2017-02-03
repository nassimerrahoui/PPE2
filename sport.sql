-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Ven 03 Février 2017 à 11:44
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `sport`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `addCompetition` (IN `Competition` VARCHAR(100), IN `Cloture` DATE, IN `Ouverture` DATE, IN `EnEquipe` BOOLEAN)  INSERT INTO competition (nom_competition, dateCloture, dateOuverture, estEnEquipe) VALUES (@Competition, @Cloture, @Ouverture, @EnEquipe)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addEquipe` (IN `pCandidat` VARCHAR(50))  BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addMembre` (IN `idPersonne` INT, IN `idEquipe` INT)  BEGIN
    INSERT INTO appartenir (id_personne, id_equipe) VALUES (idPersonne, idEquipe);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `addPersonne` (IN `pCandidat` VARCHAR(50), IN `pPrenom` VARCHAR(50), IN `pMail` VARCHAR(50))  BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 
    SET @last_id_in_candidat = LAST_INSERT_ID();
    INSERT INTO personne (prenom_personne,mail,id_candidat) VALUES (pPrenom, pMail, @last_id_in_candidat);
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidats` ()  BEGIN
    SELECT * FROM candidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCompetitions` (IN `idCandidat` INT)  BEGIN
    SELECT nom_competition, dateOuverture, dateCloture
    FROM competition, candidat, inscrire
    WHERE competition.id_competition = inscrire.id_competition
    AND inscrire.id_candidat = candidat.id_candidat
    AND candidat.id_candidat = idCandidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getEquipes` ()  BEGIN
    SELECT nom_candidat
    FROM candidat, personne
    WHERE candidat.id_candidat <> personne.id_candidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInscriptions` ()  BEGIN
    SELECT * FROM inscrire;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getMembres` (IN `idEquipe` INT)  BEGIN
    SELECT id_personne
    FROM appartenir WHERE id_equipe = idEquipe;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getPersonnes` ()  BEGIN
    SELECT nom_candidat
    FROM candidat, personne
    WHERE candidat.id_candidat = personne.id_candidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `inscriptionEquipe` (IN `idEquipe` INT, IN `idCompetition` INT)  MODIFIES SQL DATA
BEGIN
    INSERT INTO inscrire (id_candidat, id_competition) VALUES (idEquipe, idCompetition);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `inscriptionPersonne` (IN `idPersonne` INT, IN `idCompetition` INT)  BEGIN
    INSERT INTO inscrire (id_candidat, id_competition) VALUES (idPersonne, idCompetition);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `reinitialiseInscription` ()  BEGIN
    DELETE FROM inscrire;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `removeCandidat` (IN `idCandidat` INT, IN `idCompetition` INT)  BEGIN
    DELETE FROM inscrire WHERE id_candidat=idCandidat AND id_competition=idCompetition;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `removeMembre` (IN `idEquipe` INT, IN `idPersonne` INT)  BEGIN
    DELETE FROM appartenir WHERE id_equipe=idEquipe AND id_personne=idPersonne;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `setCompetition` (IN `Competition` VARCHAR(50), IN `Ouverture` DATE, IN `Cloture` DATE, IN `EnEquipe` BOOLEAN, IN `pID` INT)  BEGIN

UPDATE competition SET nom_competition = @Competition, dateOuverture = @Ouverture, dateCloture = @Cloture, estEnEquipe = @EnEquipe WHERE id_competition = @pID;

END$$

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
  `id_personne` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déclencheurs `appartenir`
--
DELIMITER $$
CREATE TRIGGER `before_Insert_appartenir` BEFORE INSERT ON `appartenir` FOR EACH ROW BEGIN
	DECLARE P1 boolean;

    SELECT COUNT(personne.id_candidat) INTO P1 FROM personne 
    WHERE personne.id_candidat=NEW.id_personne;
    
    IF (P1 <> 1) THEN 
    	SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Vous ne pouvez pas ajouter une equipe en tant que membre';
    END IF;
END
$$
DELIMITER ;

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
CREATE TRIGGER `before_Insert_candidat` BEFORE INSERT ON `candidat` FOR EACH ROW BEGIN
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
  `dateCloture` date NOT NULL,
  `dateOuverture` date DEFAULT NULL,
  `estEnEquipe` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contenu de la table `competition`
--

INSERT INTO `competition` (`id_competition`, `nom_competition`, `dateCloture`, `dateOuverture`, `estEnEquipe`) VALUES
(1, 'Test', '2017-01-29', '2017-01-10', 1),
(2, 'Solo', '2017-01-29', '2017-01-02', 0);

--
-- Déclencheurs `competition`
--
DELIMITER $$
CREATE TRIGGER `before_Insert_competition` BEFORE INSERT ON `competition` FOR EACH ROW BEGIN
   IF (NEW.dateOuverture = '')
   THEN 
   SET NEW.dateOuverture = CURRENT_DATE;
   END IF;
   
   IF (NEW.dateOuverture > NEW.dateCloture)
   THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'Date Cloture doit être après la Date Ouverture';
   END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_Update_competition` BEFORE UPDATE ON `competition` FOR EACH ROW BEGIN
	DECLARE P1 boolean;

    SELECT COUNT(competition.id_competition) INTO P1 FROM competition 
    WHERE OLD.dateCloture < NEW.dateCloture;
    
    IF (P1 <> 1) THEN 
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Date Cloture incorrect';
    END IF;
    
    IF (NEW.dateOuverture = '')
	THEN 
	SET NEW.dateOuverture = OLD.dateOuverture;
 	END IF;
    
    IF (NEW.dateOuverture > NEW.dateCloture)
	THEN 
 	SIGNAL SQLSTATE '45000'
 	SET MESSAGE_TEXT = 'Date Cloture doit être après la Date Ouverture';
 	END IF;
END
$$
DELIMITER ;

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
CREATE TRIGGER `before_Insert_inscrire` BEFORE INSERT ON `inscrire` FOR EACH ROW BEGIN
	DECLARE P1 boolean;
    DECLARE P2 int;
    DECLARE P3 date;

	SELECT competition.estEnEquipe INTO P1 FROM competition 
    WHERE competition.id_competition=NEW.id_competition;
    SELECT COUNT(personne.id_candidat) INTO P2 FROM personne 
    WHERE personne.id_candidat=NEW.id_candidat;
    SELECT competition.dateCloture INTO P3 
    FROM competition
    WHERE competition.id_competition=NEW.id_competition;
    
    IF (P3 < CURRENT_DATE) THEN
    	SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Date de clôture dépassé';
    ELSE IF (P1 = P2) THEN 
    	SIGNAL SQLSTATE '45001'
        SET MESSAGE_TEXT = 'Inscription impossible';
    END IF;
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
