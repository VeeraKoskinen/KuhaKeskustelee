PRAGMA foreign_keys=OFF;
BEGIN TRANSACTION;
CREATE TABLE Keskustelupalsta (
PalstaId integer PRIMARY KEY,
Keskustelupalstan_Nimi varchar(100) NOT NULL
);
INSERT INTO "Keskustelupalsta" VALUES(1,'Fitter');
CREATE TABLE Keskustelualue (
KeskustelualueId integer PRIMARY KEY,
Otsikko varchar(100) NOT NULL,
Palsta integer NOT NULL,
FOREIGN KEY (Palsta) REFERENCES Keskustelupalsta(PalstaId)
);
INSERT INTO "Keskustelualue" VALUES(1,'Lemmikit',1);
INSERT INTO "Keskustelualue" VALUES(2,'Lautapelit',1);
INSERT INTO "Keskustelualue" VALUES(3,'Matematiikka',1);
INSERT INTO "Keskustelualue" VALUES(4,'Tietokone',1);
CREATE TABLE Keskustelu (
KeskusteluId integer PRIMARY KEY,
Otsikko varchar(1000) NOT NULL,
Keskustelualue integer NOT NULL,
FOREIGN KEY (Keskustelualue) REFERENCES Keskustelualue(KeskustelualueId)
);
INSERT INTO "Keskustelu" VALUES(1,'Koirat on kivoja!! XD',1);
INSERT INTO "Keskustelu" VALUES(2,'Lähteekö marsuiltanne karvat?',1);
INSERT INTO "Keskustelu" VALUES(3,'Arvosteluja pelille Coup',2);
INSERT INTO "Keskustelu" VALUES(4,'Miten vaihdan käyttöjärjestelmän?',4);
CREATE TABLE Viesti (
Id integer PRIMARY KEY,
Keskustelu integer NOT NULL,
Nimimerkki varchar(20) NOT NULL,
Saapumishetki timestamp NOT NULL,
Viestisisältö varchar(100000) NOT NULL DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (Keskustelu) REFERENCES Keskustelu(KeskusteluId)
);
INSERT INTO "Viesti" VALUES(1,2,'Marsuloverrr',1455879300,'En ole huomannut moista. Viekää marsunne lääkäriin!!');
INSERT INTO "Viesti" VALUES(2,2,'Marsudoctor',1455879320,'Joopajoo..');
INSERT INTO "Viesti" VALUES(3,2,'Hibbe',1455879340,'Kyllä meijänki marsut välillä luo nahkansa.');
INSERT INTO "Viesti" VALUES(4,1,'Hibbe',1455879200,'Ei terve.');
COMMIT;
