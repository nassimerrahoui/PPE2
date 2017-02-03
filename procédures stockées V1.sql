DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addCompetition`(IN `Competition` VARCHAR(100), IN `Cloture` DATE, IN `Ouverture` DATE, IN `EnEquipe` BOOLEAN)
INSERT INTO competition (nom_competition, dateCloture, dateOuverture, estEnEquipe) VALUES (@Competition, @Cloture, @Ouverture, @EnEquipe)$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidats`()
BEGIN
    SELECT * FROM candidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addPersonne`(IN `pCandidat` VARCHAR(50), IN `pPrenom` VARCHAR(50), IN `pMail` VARCHAR(50))
BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 
    SET @last_id_in_candidat = LAST_INSERT_ID();
    INSERT INTO personne (prenom_personne,mail,id_candidat) VALUES (pPrenom, pMail, @last_id_in_candidat);
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCompetitions`(IN `idCandidat` INT)
BEGIN
    SELECT nom_competition, dateOuverture, dateCloture
    FROM competition, candidat, inscrire
    WHERE competition.id_competition = inscrire.id_competition
    AND inscrire.id_candidat = candidat.id_candidat
    AND candidat.id_candidat = idCandidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addEquipe`(IN `pCandidat` VARCHAR(50))
BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addMembre`(IN idPersonne INT, IN idEquipe INT)
BEGIN
    INSERT INTO appartenir (id_personne, id_equipe) VALUES (idPersonne, idEquipe);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getEquipes`()
BEGIN
    SELECT nom_candidat
    FROM candidat, personne
    WHERE candidat.id_candidat <> personne.id_candidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getInscriptions`()
BEGIN
    SELECT * FROM inscrire;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getMembres`(IN idEquipe INT)
BEGIN
    SELECT id_personne
    FROM appartenir WHERE id_equipe = idEquipe;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPersonnes`()
BEGIN
    SELECT nom_candidat
    FROM candidat, personne
    WHERE candidat.id_candidat = personne.id_candidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inscriptionEquipe`(IN `idEquipe` INT, IN `idCompetition` INT)
    MODIFIES SQL DATA
BEGIN
    INSERT INTO inscrire (id_candidat, id_competition) VALUES (idEquipe, idCompetition);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inscriptionPersonne`(IN idPersonne INT, IN idCompetition INT)
BEGIN
    INSERT INTO inscrire (id_candidat, id_competition) VALUES (idPersonne, idCompetition);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `reinitialiseInscription`()
BEGIN
    DELETE FROM inscrire;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeCandidat`(IN `idCandidat` INT, IN `idCompetition` INT)
BEGIN
    DELETE FROM inscrire WHERE id_candidat=idCandidat AND id_competition=idCompetition;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeMembre`(IN idEquipe INT, IN idPersonne INT)
BEGIN
    DELETE FROM appartenir WHERE id_equipe=idEquipe AND id_personne=idPersonne;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `setCompetition`(IN `Competition` VARCHAR(50), IN `Ouverture` DATE, IN `Cloture` DATE, IN `EnEquipe` BOOLEAN, IN `pID` INT)
BEGIN

UPDATE competition SET nom_competition = @Competition, dateOuverture = @Ouverture, dateCloture = @Cloture, estEnEquipe = @EnEquipe WHERE id_competition = @pID;

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `suppCandidat`(IN pID INT)
BEGIN
    DELETE FROM candidat WHERE candidat.id_candidat = pID ;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `suppCompetition`(pID INT)
BEGIN
    DELETE FROM competition WHERE competition.id_competition = pID;                            
END$$
DELIMITER ;
