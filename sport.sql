-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Jeu 20 Avril 2017 à 12:16
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
CREATE DEFINER=`root`@`localhost` PROCEDURE `addMembreEquipe` (IN `idPersonne` INT, IN `idEquipe` INT)  BEGIN
    INSERT INTO appartenir (id_personne, id_equipe) VALUES (idPersonne, idEquipe);
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createCompetition` (IN `nomCompetition` VARCHAR(100), IN `Cloture` DATE, IN `EnEquipe` BOOLEAN)  INSERT INTO competition (competition.nom_competition, dateCloture, dateOuverture, estEnEquipe) VALUES (nomCompetition, Cloture, NOW(), EnEquipe)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createEquipe` (IN `pCandidat` VARCHAR(50))  BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `createPersonne` (IN `pCandidat` VARCHAR(50), IN `pPrenom` VARCHAR(50), IN `pMail` VARCHAR(50))  BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 
    SET @last_id_in_candidat = LAST_INSERT_ID();
    INSERT INTO personne (prenom_personne,mail,id_candidat) VALUES (pPrenom, pMail, @last_id_in_candidat);
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCandidat` (IN `idCandidat` INT)  BEGIN	
    DECLARE perso boolean;
    
    SELECT COUNT(personne.id_candidat) INTO perso 
    FROM personne, candidat
    WHERE candidat.id_candidat = personne.id_candidat
    AND personne.id_candidat = idCandidat;
    
    IF perso = 1 THEN
        DELETE
    	FROM personne
    	WHERE personne.id_candidat = idCandidat;
        
        DELETE
    	FROM candidat
    	WHERE candidat.id_candidat = idCandidat;
        
    ELSE
    	DELETE
    	FROM candidat
    	WHERE candidat.id_candidat = idCandidat;
        
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCompetition` (IN `pID` INT)  BEGIN
	DELETE FROM competition WHERE competition.id_competition = pID;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidatCarac` (IN `pID` INT)  READS SQL DATA
BEGIN	
    DECLARE perso boolean;
    
    SELECT COUNT(personne.id_candidat) INTO perso 
    FROM personne, candidat
    WHERE candidat.id_candidat = personne.id_candidat
    AND personne.id_candidat = pID;
    
    IF perso = 1 THEN
        SELECT nom_candidat, prenom_personne, mail 
        FROM candidat, personne
        WHERE personne.id_candidat = candidat.id_candidat
        AND candidat.id_candidat = pID;
    ELSE
    	SELECT nom_candidat
        FROM candidat
        WHERE candidat.id_candidat = pID; 
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidatCompetition` (IN `idCompetition` INT)  BEGIN

	DECLARE P1 boolean;
    
    SELECT COUNT(competition.id_competition) INTO P1
    FROM competition
    WHERE competition.id_competition = idCompetition
    AND competition.estEnEquipe = 1;
    
    IF P1 = 0 THEN
        SELECT candidat.id_candidat, candidat.nom_candidat, personne.prenom_personne, 			personne.mail
        FROM inscrire, candidat, personne
        WHERE inscrire.id_candidat = candidat.id_candidat
        AND inscrire.id_competition = idCompetition
        AND personne.id_candidat = inscrire.id_candidat;
    
    ELSE
    	SELECT candidat.id_candidat, candidat.nom_candidat
        FROM candidat, inscrire
        WHERE inscrire.id_candidat = candidat.id_candidat
        AND inscrire.id_competition = idCompetition;
    END IF;
    
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidats` ()  NO SQL
BEGIN
    SELECT candidat.id_candidat, nom_candidat, "Equipe" as prenom_personne,
    "Contacter un des membres" as mail
    FROM candidat, personne
    WHERE candidat.id_candidat NOT IN (
        SELECT personne.id_candidat
        FROM personne )
    GROUP BY candidat.id_candidat
    
    UNION
    
    SELECT candidat.id_candidat, nom_candidat, personne.prenom_personne,
    personne.mail
    FROM candidat, personne
    WHERE candidat.id_candidat = personne.id_candidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCompetitionCarac` (IN `idCompetition` INT)  BEGIN

	DECLARE result VARCHAR(50);
	DECLARE isOnTeam boolean;
    
    SELECT competition.estEnEquipe INTO isOnTeam
    FROM competition
    WHERE competition.id_competition = idCompetition;
    
    IF isOnTeam = 1 THEN
    	SET result = 'Compétition en equipe';
    ELSE
    	SET result = 'Compétition individuelle';
    END IF;

	SELECT competition.nom_competition, competition.dateCloture, competition.dateOuverture, result
    FROM competition
    WHERE competition.id_competition = idCompetition;
	
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getCompetitions` ()  READS SQL DATA
SELECT * FROM competition$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getEquipeOfPersonne` (IN `idPersonne` INT)  BEGIN

	DECLARE nomPersonne VARCHAR(50);
    DECLARE prenomPersonne VARCHAR(50);
	
    SELECT candidat.nom_candidat, personne.prenom_personne
    INTO nomPersonne, prenomPersonne
    FROM candidat, personne, appartenir
    WHERE personne.id_candidat = candidat.id_candidat
    AND candidat.id_candidat = appartenir.id_personne
    AND appartenir.id_personne = idPersonne
    GROUP BY candidat.id_candidat;

    SELECT
    nomPersonne, prenomPersonne, candidat.nom_candidat
    FROM candidat, appartenir 
    WHERE candidat.id_candidat = appartenir.id_equipe
    AND appartenir.id_personne = idPersonne;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getEquipes` ()  BEGIN
    SELECT candidat.id_candidat ,nom_candidat
    FROM candidat
    WHERE candidat.id_candidat NOT IN (
        SELECT personne.id_candidat
        FROM personne )
    GROUP BY candidat.id_candidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getInscriptionCandidat` (IN `idCandidat` INT)  BEGIN

	DECLARE P1 bool;
    
    SELECT COUNT(personne.id_candidat) INTO P1
    FROM personne
    WHERE personne.id_candidat = idCandidat;
    
    IF P1 = 0 THEN
    	SELECT candidat.nom_candidat, nom_competition, 
        dateOuverture, dateCloture
        FROM competition, candidat, inscrire
        WHERE 
        competition.id_competition = inscrire.id_competition
        AND inscrire.id_candidat = candidat.id_candidat
        AND candidat.id_candidat = idCandidat;
    ELSE
    	SELECT candidat.nom_candidat, personne.prenom_personne,
        personne.mail,
        competition.nom_competition, 
        competition.dateOuverture, competition.dateCloture
        FROM competition, candidat, inscrire, personne
        WHERE competition.id_competition = inscrire.id_competition
        AND inscrire.id_candidat = candidat.id_candidat
        AND personne.id_candidat = candidat.id_candidat
        AND candidat.id_candidat = idCandidat;
    END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getMembreEquipe` (IN `idEquipe` INT)  BEGIN

	DECLARE nameTeam VARCHAR(50);
	
    SELECT candidat.nom_candidat INTO nameTeam
    FROM candidat,appartenir
    WHERE candidat.id_candidat = appartenir.id_equipe
    AND appartenir.id_equipe = idEquipe
    GROUP BY candidat.id_candidat;

    SELECT
    candidat.id_candidat ,nameTeam, candidat.nom_candidat, personne.prenom_personne,
    personne.mail
    FROM candidat, personne, appartenir 
    WHERE personne.id_candidat = candidat.id_candidat
    AND candidat.id_candidat = appartenir.id_personne
    AND appartenir.id_equipe = idEquipe;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getPersonnes` ()  BEGIN
    SELECT candidat.id_candidat, nom_candidat, personne.prenom_personne, 
    personne.mail
    FROM candidat, personne
    WHERE candidat.id_candidat = personne.id_candidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `inscriptionCandidat` (IN `idCandidat` INT, IN `idCompetition` INT)  BEGIN

    DECLARE P2 BOOLEAN;
    DECLARE P3 BOOLEAN;
    
    SELECT COUNT(candidat.id_candidat) into P2
    FROM candidat
    WHERE candidat.id_candidat = idCandidat;
    
    SELECT COUNT(competition.id_competition) into P3
    FROM competition
    WHERE competition.id_competition = idCompetition;   

	IF (P2 = 1 AND P3 = 1) THEN

	INSERT INTO inscrire (id_candidat, id_competition) VALUES (idCandidat, idCompetition);
   
   	END IF;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `removeCandidatCompetition` (IN `idCompetition` INT, IN `idCandidat` INT)  BEGIN
    DELETE
    FROM inscrire
    WHERE id_competition=idCompetition
    AND id_candidat=idCandidat;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `removePersonneEquipe` (IN `idEquipe` INT, IN `idPersonne` INT)  MODIFIES SQL DATA
BEGIN
    DELETE 
    FROM appartenir
    WHERE id_equipe=idEquipe
    AND id_personne=idPersonne;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `setCandidatCarac` (IN `pID` INT, IN `nomCandidat` VARCHAR(50))  BEGIN	
    UPDATE candidat SET nom_candidat = nomCandidat WHERE id_candidat = pID; 
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `setCompetitionCarac` (IN `Competition` VARCHAR(50), IN `Cloture` DATE, IN `EnEquipe` BOOLEAN, IN `pID` INT)  MODIFIES SQL DATA
BEGIN

    UPDATE competition SET competition.nom_competition = @Competition,
    competition.dateCloture = @Cloture, 
    competition.estEnEquipe = @EnEquipe
    WHERE competition.id_competition = @pID;

END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `setPersonneCarac` (IN `pID` INT, IN `nomCandidat` VARCHAR(50), IN `prenomPersonne` VARCHAR(50), IN `email` VARCHAR(30))  BEGIN	
    DECLARE perso boolean;
    
    SELECT COUNT(personne.id_candidat) INTO perso 
    FROM personne, candidat
    WHERE candidat.id_candidat = personne.id_candidat
    AND personne.id_candidat = pID;
    
    IF perso = 1 THEN
        UPDATE candidat SET nom_candidat = nomCandidat
        WHERE id_candidat = pID;
        UPDATE personne SET prenom_personne = prenomPersonne
        WHERE id_candidat = pID;
        UPDATE personne SET mail = email
        WHERE id_candidat = pID;
    ELSE
    	UPDATE candidat SET nom_candidat = nomCandidat WHERE id_candidat = pID; 
    END IF;
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
-- Contenu de la table `appartenir`
--

INSERT INTO `appartenir` (`id_equipe`, `id_personne`) VALUES
(18, 15),
(19, 16),
(20, 16),
(18, 17);

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
        SET MESSAGE_TEXT = "Vous ne pouvez pas ajouter une equipe en tant que membre d'une autre equipe ou d'une personne";
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
(8, 'TeamRo'),
(9, 'TeamWhite'),
(13, 'TeamThorin'),
(15, 'Joueur2'),
(16, 'Shingeki'),
(17, 'Joueur4'),
(18, 'TeamExploration'),
(19, 'TeamEscadron'),
(20, 'TeamValor'),
(21, 'TeamAAA'),
(22, 'TeamDEF'),
(23, 'Tony'),
(24, 'Boris'),
(25, 'Les Manouches'),
(26, 'Tonis'),
(27, 'Boric'),
(28, 'Les Manchots'),
(309, 'Test0'),
(310, 'Test00'),
(311, 'equipe'),
(312, 'TestP'),
(313, 'TestPP'),
(314, 'EquipeT'),
(315, 'Doe');

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
DELIMITER $$
CREATE TRIGGER `before_Update_candidat` BEFORE UPDATE ON `candidat` FOR EACH ROW BEGIN	
    IF NEW.nom_candidat = "" THEN
   		SET NEW.nom_candidat = OLD.nom_candidat;
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
(3, 'Solo', '2017-06-29', '2017-02-17', 0),
(4, 'Solo2', '2017-07-14', '2017-02-13', 0),
(6, 'Team', '2017-10-30', '2017-02-23', 1),
(7, 'competL', '2017-11-30', '2017-03-29', 1),
(8, 'Parkour', '2017-12-31', '2017-04-20', 1),
(9, 'Bike', '2018-03-01', '2017-04-20', 0),
(10, 'Football', '2017-12-31', '2017-04-20', 1),
(11, 'FootballChamp', '2017-12-31', '2017-04-20', 1),
(12, 'BasketBall', '2017-12-31', '2017-04-20', 1),
(13, 'Hockey', '2017-12-31', '2017-04-20', 1);

--
-- Déclencheurs `competition`
--
DELIMITER $$
CREATE TRIGGER `before_Insert_competition` BEFORE INSERT ON `competition` FOR EACH ROW BEGIN
   IF NEW.dateOuverture > NEW.dateCloture
   THEN 
   SIGNAL SQLSTATE '45000'
   SET MESSAGE_TEXT = 'Date Cloture doit être après la Date Ouverture';
   END IF;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `before_Update_competition` BEFORE UPDATE ON `competition` FOR EACH ROW BEGIN
	IF NEW.dateOuverture > NEW.dateCloture
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
-- Contenu de la table `inscrire`
--

INSERT INTO `inscrire` (`id_candidat`, `id_competition`) VALUES
(15, 3),
(16, 3),
(17, 3),
(15, 4),
(8, 6),
(9, 6),
(13, 6);

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
('Eren', 'mail2@mail.fr', 15),
('Rivaille', 'rivaille@mail.com', 16),
('Erwin', 'mail4@mail.fr', 17),
('Dent de plomb', 'azerty', 23),
('le Hachoir', 'ytreza', 24),
('Dent de plonge', 'qwerty', 26),
('le couteau', 'qsdf', 27),
('Test0', 'Test0@mail.Fr', 309),
('Test00', 'Test00@mail.fr', 310),
('TestP', 'TestP@mail.fr', 312),
('TestPP', 'TestPP@mail.fr', 313),
('John', 'johndoe@mail.fr', 315);

--
-- Déclencheurs `personne`
--
DELIMITER $$
CREATE TRIGGER `before_Update_personne` BEFORE UPDATE ON `personne` FOR EACH ROW BEGIN	
    IF NEW.prenom_personne = "" THEN
   		SET NEW.prenom_personne = OLD.prenom_personne;
    END IF;
    
    IF NEW.mail = "" THEN
   		SET NEW.mail = OLD.mail;
    END IF;
END
$$
DELIMITER ;

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
  MODIFY `id_candidat` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=316;
--
-- AUTO_INCREMENT pour la table `competition`
--
ALTER TABLE `competition`
  MODIFY `id_competition` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;
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
