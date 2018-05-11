# Käyttöohje

Lataa tiedosto [Budjetointisovellus.jar](https://github.com/OlliJ5/otm-harjoitustyo/releases)

## Ohjelman käynnistäminen 
Ohjelma käynnistetään komennolla
```
java -jar Budjetointisovellus.jar
```

## Sisään ja uloskirjautuminen
Sovellus käynnistyy kirjautumisnäkymään


<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/kirjautumisn%C3%A4kym%C3%A4.png" width="500">

Kirjautuminen onnistuu kirjoittamalla syötekenttiin olemassaoleva käyttäjätunnus sekä käyttäjätunnukseen liitetty salasana ja painamalla login. Kun olet sisäänkirjautunut paina 'Kirjaudu ulos' kirjautuaksesi ulos.

## Uuden käyttäjän luominen
Painamalla 'Luo uusi käyttäjä' siirrytään kirjautumisnäkymästä uuden käyttäjän luomiseen. Uusi käyttäjä luodaan syöttämällä uusi
käyttäjätunnus (täytyy olla uniikki), nimi sekä salasana ja painamalla 'Luo käyttäjä'


<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/userCreation.png" width="500">
Uuden käyttäjän onnistuneesta luomisesta ilmoitetaan käyttäjälle.

## Budjettien luominen
Käyttäjä voi luoda uuden budjetin kirjautumisen jälkeen painamalla 'Luo uusi budjetti' päänäkymästä, jolloin tullaan uuteen näkymään. Kirjoittamalla syötekenttiin
budjetin nimen, jonka täytyy olla uniikki (eri käyttäjät voivat kuitenkin luoda samannimisen budjetin) sekä budjetin määrän 
ja painamalla 'Luo uusi' saadaan uusi budjetti luotua.

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/uudenBudjetinLuonti.png" width="500">

## Kulujen lisääminen
Kulujen lisääminen onnistuu valitsemalla päänäkymän oikeasta reunasta muokattava budjetti olemassaolevien budjettien joukosta. Alhaalla oleviin
syötekenttiin syötetään kulun nimi sekä sen hinta ja sitten painetaan 'Lisää', jolloin uusi kulu ilmestyy taulukkoon.

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/uusiKulu.png" width="500">

## Budjetin poistaminen
Budjetin poistaminen onnistuu helposti painamalla taulukon alapuolelta 'Poista budjetti'.

## Kulujen poistaminen
Kulujen poistaminen onnistuu valitsemalla taulukosta rivin ja sen jälkeen painamalla alhaalla olevaa nappia 'Poista kulu'.

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/poistaKulu.png" width="500">

## Budjetin tietojen muokkaus
Budjetin tietojen muokkaus onnistuu painamalla päänäkymästä ylhäällä olevaa nappia 'Näytä lisätietoja', jolloin avautuu seuraava näkymä:

<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/budjetin%20tietojen%20muokkaus.png" width="500">

Budjetin nimeä voi muutta syöttämällä uuden nimen tekstikenttään ja painamalla 'Muuta nimi'. Budjetin määrää voi muuttaa syöttämällä budjetin uuden määrän tekstikenttään ja painamalla 'Muuta määrää'.

## Käyttäjän poistaminen
Jos haluat poistaa käyttäjäsi. Paina päänäkymästä vasemmalta reunalta 'Käyttäjän tiedot', jolloin avautuu uusi näkymä ja paina sitten 'Poista käyttäjä' ja valitse sitten vielä ok.
<img src="https://github.com/OlliJ5/otm-harjoitustyo/blob/master/dokumentointi/kuvat/k%C3%A4ytt%C3%A4j%C3%A4n%20poisto.png" width="500">
