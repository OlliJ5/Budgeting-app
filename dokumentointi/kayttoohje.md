# Käyttöohje

Lataa tiedosto [Budjetointisovellus.jar](https://github.com/OlliJ5/otm-harjoitustyo/releases)

## Ohjelman käynnistäminen 
Ohjelma käynnistetään komennolla
```
java -jar Budjetointisovellus.jar
```

## Kirjautuminen
Sovellus käynnistyy kirjautumisnäkymään


<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/kirjautumisn%C3%A4kym%C3%A4.png" width="500">

Kirjautuminen onnistuu kirjoittamalla syötekenttiin olemassaoleva käyttäjätunnus sekä käyttäjätunnukseen liitetty salasana ja painamalla login.

## Uuden käyttäjän luominen
Painamalla 'Luo uusi käyttäjä' siirrytään kirjautumisnäkymästä uuden käyttäjän luomiseen. Uusi käyttäjä luodaan syöttämällä uusi
käyttäjätunnus (täytyy olla uniikki), nimi sekä salasana ja painamalla 'Luo käyttäjä'


<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/userCreation.png" width="500">
Uuden käyttäjän onnistuneesta luomisesta ilmoitetaan käyttäjälle.

## Budjettien luominen
Käyttäjä voi luoda uuden budjetin painamalla 'Luo uusi budjetti', jolloin tullaan uuteen näkymään. Kirjoittamalla syötekenttiin
budjetin nimen, jonka täytyy olla uniikki (eri käyttäjät voivat kuitenkin luoda samannimisen budjetin) sekä budjetin määrän 
ja painamalla 'Luo uusi' saadaan uusi budjetti luotua.

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/uudenBudjetinLuonti.png" width="500">

## Kulujen lisääminen
Kulujen lisääminen onnistuu valitsemalla päänäkymän oikeasta reunasta muokattava budjetti olemassaolevien budjettien joukosta. Alhaalla oleviin
syötekenttiin syötetään kulun nimi sekä sen hinta ja sitten painetaan 'Lisää', jolloin uusi kulu ilmestyy taulukkoon.

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/uusiKulu.png" width="500">

## Budjetin poistaminen
Budjetin poistaminen onnistuu helposti valitsemalla oikealta muokattava budjetti eli se, joka haluttan poistaa
ja sitten painamalla 'Poista budjetti

## Kulujen poistaminen
Kulujen poistaminen onnistuu valitsemalla taulukosta rivin ja sen jälkeen painamalla alhaalla olevaa nappia 'Poista kulu'.

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/poistaKulu.png" width="500">
