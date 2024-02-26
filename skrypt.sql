-- Dodaj przykładowe dane do tabeli 'posilek'
INSERT INTO ccApp.posilek (nazwa) VALUES
('Sniadanie'),
('Obiad'),
('Kolacja');

-- Dodaj przykładowe dane do tabeli 'produkty'
INSERT INTO ccApp.produkty (nazwa, jednostka) VALUES
('Jajka', 'szt.'),
('Kurczak', 'g'),
('Pomidor', 'szt.'),
('Maka', 'g');

-- Dodaj przykładowe dane do tabeli 'przepisy'
INSERT INTO ccApp.przepisy (nazwa, sposob_przygotowania) VALUES
('Jajecznica', 'Rozbij jajka do miseczki, ubij widelcem, smaż na patelni.'),
('Kurczak pieczony', 'Posól kurczaka, umieść w piekarniku, piecz przez 1 godzinę.'),
('Sałatka pomidorowa', 'Pokrój pomidory, dodaj sól i pieprz, wymieszaj.');

-- Dodaj przykładowe dane do tabeli 'dzien'
INSERT INTO ccApp.dzien (numer_dnia, posilek_id_posilek, przepisy_id_przepisy) VALUES
(1, 1, 1),
(1, 2, 2),
(2, 3, 3);

-- Dodaj przykładowe dane do tabeli 'przepisy_produkty'
INSERT INTO ccApp.przepisy_produkty (przepisy_id_przepisy, produkty_id_produkty) VALUES
(1, 1),
(2, 2),
(3, 3);

-- Dodaj przykładowe dane do tabeli 'lista_zakupow'
INSERT INTO ccApp.lista_zakupow (ilosc, przepisy_produkty_id_przepisy_produkty) VALUES
(6, 1),
(1, 2),
(4, 3);