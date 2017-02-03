CREATE TRIGGER `before_insert_appartenir` BEFORE INSERT ON `appartenir`
 FOR EACH ROW BEGIN
	DECLARE P1 boolean;

    SELECT COUNT(personne.id_candidat) INTO P1 FROM personne 
    WHERE personne.id_candidat=NEW.id_personne;
    
    IF (P1 <> 1) THEN 
    	SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Vous ne pouvez pas ajouter une equipe en tant que membre';
    END IF;
END

CREATE TRIGGER `before_insert_inscrire` BEFORE INSERT ON `inscrire`
 FOR EACH ROW BEGIN
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

CREATE TRIGGER `before_update_candidat` BEFORE INSERT ON `candidat`
 FOR EACH ROW BEGIN
   IF NEW.nom_candidat = ""
   THEN 
   SET NEW.nom_candidat = NULL;
   END IF;
END
