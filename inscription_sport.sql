create database competition;

use competition;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

-- Structure de la table 'candidat'

CREATE TABLE candidat (
  id_candidat int NOT NULL AUTO_INCREMENT,
  nom_candidat varchar(50) DEFAULT NULL,
  PRIMARY KEY (id_candidat)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- Structure de la table 'personne'

CREATE TABLE personne (
  prenom_personne varchar(50) DEFAULT NULL,
  mail varchar(30) DEFAULT NULL,
  id_candidat int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Structure de la table 'appartenir'

CREATE TABLE appartenir (
  id_equipe int NOT NULL,
  id_personne int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- Structure de la table 'competition'

CREATE TABLE competition (
  id_competition int NOT NULL AUTO_INCREMENT,
  nom_competition varchar(50) NOT NULL,
  dateCloture date DEFAULT NULL,
  dateOuverture date DEFAULT NULL,
  estEnEquipe boolean DEFAULT NULL,
  PRIMARY KEY (id_competition)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

-- Structure de la table 'inscrire'

CREATE TABLE inscrire (
  id_candidat int NOT NULL,
  id_competition int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Index pour les tables exportées

-- Index pour la table 'appartenir'

ALTER TABLE appartenir
  ADD PRIMARY KEY (id_equipe,id_personne),
  ADD FOREIGN KEY (id_equipe) REFERENCES candidat (id_candidat),
  ADD FOREIGN KEY (id_personne) REFERENCES candidat (id_candidat);

-- Index pour la table 'inscrire'

ALTER TABLE inscrire
  ADD PRIMARY KEY (id_candidat,id_competition),
  ADD FOREIGN KEY (id_competition) REFERENCES competition (id_competition);

-- Index pour la table 'personne'

ALTER TABLE personne
  ADD PRIMARY KEY (id_candidat),
  ADD FOREIGN KEY (id_candidat) REFERENCES candidat (id_candidat);


-- Contraintes pour les tables exportées

-- Contraintes pour la table 'appartenir'

ALTER TABLE appartenir
  ADD CONSTRAINT FK_appartenir_id_equipe FOREIGN KEY (id_equipe) REFERENCES candidat (id_candidat),
  ADD CONSTRAINT FK_appartenir_id_personne FOREIGN KEY (id_personne) REFERENCES candidat (id_candidat);


-- Contraintes pour la table 'inscrire'

ALTER TABLE inscrire
  ADD CONSTRAINT FK_inscrire_id_candidat FOREIGN KEY (id_candidat) REFERENCES candidat (id_candidat),
  ADD CONSTRAINT FK_inscrire_id_competition FOREIGN KEY (id_competition) REFERENCES competition (id_competition);


-- Contraintes pour la table 'personne'

ALTER TABLE personne
  ADD CONSTRAINT FK_personne_id_candidat FOREIGN KEY (id_candidat) REFERENCES candidat (id_candidat);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
