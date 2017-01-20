CREATE TRIGGER `before_insert_inscription` BEFORE INSERT ON `inscrire`
 FOR EACH ROW BEGIN
	DECLARE P1 int;
    DECLARE P2 int;

	SELECT competition.estEnEquipe INTO P1 FROM competition 
    WHERE competition.id_competition=NEW.id_competition;
    SELECT COUNT(personne.id_candidat) INTO P2 FROM personne 
    WHERE personne.id_candidat=NEW.id_candidat;
    
    IF P1 = P2 THEN 
    	SIGNAL SQLSTATE '45000';
    END IF;
END

CREATE TRIGGER `before_update_candidat` BEFORE INSERT ON `candidat`
 FOR EACH ROW BEGIN
   IF NEW.nom_candidat = ""
   THEN 
   SET NEW.nom_candidat = NULL;
   END IF;
END