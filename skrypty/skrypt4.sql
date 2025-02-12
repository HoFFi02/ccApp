-- Dodaj przykładowe dane do tabeli 'posilek'
INSERT INTO ccApp.posilek (nazwa) VALUES
('Sniadanie'),
('Obiad'),
('Kolacja');

-- Dodaj przykładowe dane do tabeli 'produkty'
INSERT INTO ccApp.produkty (id_produkty, nazwa, jednostka) VALUES
('1', 'Jajka', 'szt'),
('2', 'Kurczak', 'szt'),
('3', 'Pomidor', 'szt'),
('4', 'Maka', 'g'),
('5', 'Makaron', 'g'),
('6', 'Kostka rosolowa', 'szt'),
('7', 'Cebula', 'szt'),
('8', 'Papryka', 'szt'),
('9', 'Przecier pomidorowy', 'lyzka/i'),
('10', 'Zabek czosnku', 'szt'),
('11', 'Mozarella', 'g'),
('12', 'Olej', 'lyzka/i'),
('13', 'Woda', 'szkl'),
('14', 'Kurczak', 'g'),
('15', 'Przyprawa do gyrosa', 'lyzeczka/i'),
('16', 'Kapusta pekinska', 'g'),
('17', 'Czerwona cebula', 'szt'),
('18', 'Kukurydza z puszki', 'opak'),
('19', 'Ogorki konserwowe', 'szt'),
('20', 'Majonez', 'g'),
('21', 'Ketchup', 'g'),
('22', 'Przyprawa do kurczaka', 'lyzeczka/i'),
('23', 'Woda', 'ml'),
('24', 'Smietana 18%', 'ml'),
('25', 'Maka', 'lyzka/i'),
('26', 'Olej', 'ml'),
('27', 'Natka pietruszki', 'szt'),
('28', 'Ser', 'g'),
('29', 'Przyprawa uniwersalna', 'lyzeczka/i'),
('30', 'Szpinak mrozony', 'opak'),
('31', 'Warzywa na patelnie', 'g'),
('32', 'Ryz', 'g'),
('33', 'Maslo', 'g'),
('34', 'Smietana', 'lyzka/i'),
('35', 'Oliwa', 'lyzka/i');


-- Dodaj przykładowe dane do tabeli 'przepisy'
INSERT INTO ccApp.przepisy (id_przepisy, nazwa, sposob_przygotowania) VALUES
('1', 'Zapiekanka makaronowa', 'Cebule pokroj w piorka, czosnek przecisnij przez praske. Podsmaz je na oleju. Ugotuj makaron al dente. Warzywa pokroj w paski i wraz z kurczakiem dodaj do calosci. Dus okolo 15 minut. Nastepnie podlej szklanka wody i dodaj kostke rosolowa oraz przecier pomidorowy. Makaron wyloz do naczynia zaroodpornego, zalej sosem i posyp startym serem. Wloz do piekarnika nagrzanego do 180 stopni na 20 minut.'), 
('2', 'Salatka gyros', 'Mieso pokroj w paski i oprosz przyprawa do gyrosa. Usmaz na patelni i odstaw do ostygniecia. Przygotuj sos, mieszajac ketchup z majonezem i sosem czosnkowym. Kapuste pokroj w paski, pomidory, cebule i ogorki w kostke, a kukurydze odcedz. Skladniki salatki gyros ukladaj warstwowo w misce. Zacznij od kapusty, potem dodawaj kolejno pozostale warzywa i mieso. Warstwy przekladaj co jakis czas sosem.'),
('3', 'Makaron z kurczakiem i sosem smietanowym', 'Ugotuj makaron al dente. Piersi pokroj w kostke 1x1 cm, cebule w plastry. Mieso dopraw do smaku przyprawa warzywna oraz pieprzem, oprosz w mace, obsmaz na patelni na rozgrzanym tluszczu na zloty kolor. Dodaj cebule. Calosc smaz, az zupelnie odparuja soki z miesa. Dodaj smietane i 300 ml zimnej wody. Calosc gotuj okolo 5 minut, az sos zgestnieje, a nastepnie dopraw kostka rosolowa, dodaj ugotowany makaron i posiekana natke pietruszki. Calosc dokladnie wymieszaj. Makaron podawaj posypany startym serem.'),
('4', 'Kurczak ze szpinakiem', 'Piersi rozetnij wzdluz na cienkie platy, aby uzyskac 6 porcji. Jedna kostke rosolowa wymieszaj z olejem i natrzyj nia mieso. Na patelni zeszklij pokrojona w kostke cebule, dodaj odsaczony szpinak, smietane i druga kostke. Wszystko dokladnie wymieszaj i gotuj do momentu, az smietana zgestnieje. Do szpinaku mozna dodac odrobine czosnku, a jesli lubisz ostre smaki  szczypte pieprzu cayenne. Farsz naloz na piersi kurczaka i posyp wszystko zoltym serem. Piecz w piekarniku nagrzanym do 190 stopni C przez 15 minut.'),
('5', 'Kolorowe risotto z kurczakiem', 'Cebule drobno pokroj i podsmaz ja w szerokim rondlu na rozgrzanej oliwie wraz z surowym ryzem, przez 1 minute. Piers z kurczaka pokroj w kostke, oprosz przyprawa do kurczaka i obsmaz na patelni. Do podsmazonego ryzu wlej 300 mililitrow wody oraz jedna bulionetke. Dus calosc okolo 12 minut. Nastepnie dodaj mieszanke warzyw, kurczaka, ziola prowansalskie oraz 250 mililitrow wody i dus kolejne 6 minut. Pod koniec duszenia dodaj starty zolty ser, smietane oraz maslo. Energicznie mieszaj i odstaw z ognia na kilka minut. Natychmiast podawaj.');



-- Dodaj przykładowe dane do tabeli 'dzien'
INSERT INTO ccApp.dzien (numer_dnia) VALUES 
(1), (2), (3), (4), (5), (6), (7), (8), (9), (10),
(11), (12), (13), (14), (15), (16), (17), (18), (19), (20),
(21), (22), (23), (24), (25), (26), (27), (28), (29), (30), (31);

-- Dodaj przykładowe dane do tabeli 'przepisy_produkty'
INSERT INTO ccApp.przepisy_produkty (przepisy_id_przepisy, produkty_id_produkty, ilosc) VALUES
(1, 5, 1),
(1, 6, 2),
(1, 2, 2),
(1, 8, 1),
(1, 7, 3),
(1, 3, 8),
(1, 9, 2),
(1, 10, 1),
(1, 11, 5),
(1, 12, 3),
(1, 13, 1),
(2, 14, 2),
(2, 15, 3),
(2, 16, 3),
(2, 3, 4),
(2, 17, 5),
(2, 18, 2),
(2, 19, 3),
(2, 20, 5),
(2, 21, 5),
(2, 12, 6),
(3, 14, 2),
(3, 6, 1),
(3, 5, 2),
(3, 7, 3),
(3, 28, 9),
(3, 23, 33),
(3, 24, 2),
(3, 25, 3),
(3, 26, 4),
(3, 27, 55),
(3, 28, 2),
(4, 14, 1),
(4, 6, 2),
(4, 30, 3),
(4, 7, 4),
(4, 28, 2),
(4, 12, 4),
(5, 14, 2),
(5, 31, 1),
(5, 6, 2),
(5, 7, 3),
(5, 22, 5),
(5, 32, 2),
(5, 35, 11),
(5, 34, 2),
(5, 28, 33),
(5, 33, 2),
(5, 23, 1);

-- Dodaj przykładowe dane do tabeli 'lista_zakupow'
INSERT INTO ccApp.lista_zakupow (ilosc, przepisy_produkty_id_przepisy_produkty) VALUES
(6, 1),
(1, 2),
(4, 3);
