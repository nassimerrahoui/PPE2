DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createCompetition`(IN `Competition` VARCHAR(100), IN `Cloture` DATE, IN `Ouverture` DATE, IN `EnEquipe` BOOLEAN)
INSERT INTO competition (nom_competition, dateCloture, dateOuverture, estEnEquipe) VALUES (Competition, Cloture, Ouverture, EnEquipe)$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `addMembreEquipe`(IN `idPersonne` INT, IN `idEquipe` INT)
BEGIN
    INSERT INTO appartenir (id_personne, id_equipe) VALUES (idPersonne, idEquipe);
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createEquipe`(IN `pCandidat` VARCHAR(50))
BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 

END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `createPersonne`(IN `pCandidat` VARCHAR(50), IN `pPrenom` VARCHAR(50), IN `pMail` VARCHAR(50))
BEGIN

    INSERT INTO candidat (nom_candidat) VALUES (pCandidat); 
    SET @last_id_in_candidat = LAST_INSERT_ID();
    INSERT INTO personne (prenom_personne,mail,id_candidat) VALUES (pPrenom, pMail, @last_id_in_candidat);
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCandidat`(IN `idCandidat` INT)
BEGIN	
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
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCompetition`(IN `pID` INT)
BEGIN
	DELETE FROM competition WHERE competition.id_competition = pID;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidatCarac`(IN `pID` INT)
    READS SQL DATA
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
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidatCompetition`(IN `idCompetition` INT)
BEGIN

	DECLARE P1 boolean;
    
    SELECT COUNT(competition.id_competition) INTO P1
    FROM competition
    WHERE competition.id_competition = idCompetition
    AND competition.estEnEquipe = 1;
    
    IF P1 = 0 THEN
        SELECT candidat.nom_candidat, personne.prenom_personne, 			personne.mail
        FROM inscrire, candidat, personne
        WHERE inscrire.id_candidat = candidat.id_candidat
        AND inscrire.id_competition = idCompetition
        AND personne.id_candidat = inscrire.id_candidat;
    
    ELSE
    	SELECT candidat.nom_candidat
        FROM candidat, inscrire
        WHERE inscrire.id_candidat = candidat.id_candidat
        AND inscrire.id_competition = idCompetition;
    END IF;
    
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCandidats`()
    NO SQL
BEGIN
    SELECT nom_candidat, "Equipe" as prenom_personne,
    "Contacter un des membres" as mail
    FROM candidat, personne
    WHERE candidat.id_candidat NOT IN (
        SELECT personne.id_candidat
        FROM personne )
    GROUP BY candidat.id_candidat
    
    UNION
    
    SELECT nom_candidat, personne.prenom_personne,
    personne.mail
    FROM candidat, personne
    WHERE candidat.id_candidat = personne.id_candidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getCompetitionCarac`(IN `idCompetition` INT)
BEGIN

	DECLARE result VARCHAR(50);
	DECLARE isOnTeam boolean;

	SELECT competition.nom_competition, competition.dateCloture, competition.dateOuverture
    FROM competition
    WHERE competition.id_competition = idCompetition;
    
    SELECT competition.estEnEquipe INTO isOnTeam
    FROM competition
    WHERE competition.id_competition = idCompetition;
    
    IF isOnTeam = 1 THEN
    	SET result = 'Compétition en equipe';
    ELSE
    	SET result = 'Compétition individuelle';
    END IF;
	
	SELECT result;
	
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getEquipeOfPersonne`(IN `idPersonne` INT)
BEGIN

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
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getEquipes`()
BEGIN
    SELECT nom_candidat
    FROM candidat
    WHERE candidat.id_candidat NOT IN (
        SELECT personne.id_candidat
        FROM personne )
    GROUP BY candidat.id_candidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getInscriptionCandidat`(IN `idCandidat` INT)
BEGIN
    SELECT nom_competition, dateOuverture, dateCloture
    FROM competition, candidat, inscrire
    WHERE competition.id_competition = inscrire.id_competition
    AND inscrire.id_candidat = candidat.id_candidat
    AND candidat.id_candidat = idCandidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getMembreEquipe`(IN `idEquipe` INT)
BEGIN

	DECLARE nameTeam VARCHAR(50);
	
    SELECT candidat.nom_candidat INTO nameTeam
    FROM candidat,appartenir
    WHERE candidat.id_candidat = appartenir.id_equipe
    AND appartenir.id_equipe = idEquipe
    GROUP BY candidat.id_candidat;

    SELECT
    nameTeam, candidat.nom_candidat, personne.prenom_personne,
    personne.mail
    FROM candidat, personne, appartenir 
    WHERE personne.id_candidat = candidat.id_candidat
    AND candidat.id_candidat = appartenir.id_personne
    AND appartenir.id_equipe = idEquipe;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `getPersonnes`()
BEGIN
    SELECT nom_candidat, personne.prenom_personne, 
    personne.mail
    FROM candidat, personne
    WHERE candidat.id_candidat = personne.id_candidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inscriptionCandidat`(IN `idCandidat` INT, IN `idCompetition` INT)
BEGIN

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
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `removeCandidatCompetition`(IN `idCompetition` INT, IN `idCandidat` INT)
BEGIN
    DELETE
    FROM inscrire
    WHERE id_competition=idCompetition
    AND id_candidat=idCandidat;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `removePersonneEquipe`(IN `idEquipe` INT, IN `idPersonne` INT)
    MODIFIES SQL DATA
BEGIN
    DELETE 
    FROM appartenir
    WHERE id_equipe=idEquipe
    AND id_personne=idPersonne;
END$$
DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `setCandidatCarac`(IN `pID` INT, IN `nomCandidat` VARCHAR(50), IN `prenomPersonne` VARCHAR(50), IN `email` VARCHAR(30))
BEGIN	
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

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `setCompetitionCarac`(IN `Competition` VARCHAR(50), IN `Ouverture` DATE, IN `Cloture` DATE, IN `EnEquipe` BOOLEAN, IN `pID` INT)
BEGIN

UPDATE competition SET nom_competition = @Competition, dateOuverture = @Ouverture, dateCloture = @Cloture, estEnEquipe = @EnEquipe WHERE id_competition = @pID;

END$$
DELIMITER ;
