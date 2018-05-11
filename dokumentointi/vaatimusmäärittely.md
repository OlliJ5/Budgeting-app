# Vaatimusmäärittely

## Sovelluksen tarkoitus
Sovellus on budjetointisovellus, jonka avulla käyttäjät voivat pitää kirjaa omista budjeteistaan. Sovelluksen käyttö
vaatii sisäänkirjautumisen ja jokaisella käyttäjällä on käytössä oma näkymä, jonka avulla he voivat seurata budjettejaan.

## Käyttäjät
Sovelluksessa on tällä hetkellä vain normaaleja käyttäjiä, joilla kaikilla on samat oikeudet. Myöhemmin saatetaan lisätä ylläpitäjiä, jotka voivat esimerkiksi poistaa normaaleja käyttäjiä.

## Sovelluksen toiminnallisuudet
### Ennen kirjautumista
* Käyttäjä voi luoda käyttäjätunnuksen sovellukseen
  * Käyttäjätunnuksen täytyy olla uniikki ja vähintään 2 merkkiä pitkä.
* Käyttäjä voi kirjautua sovellukseen
  * Kirjautuminen onnistuu, jos syötetään olemassaoleva käyttäjätunnus ja siihen kuuluva salasana. Virheellisestä käyttäjätunnuksesta tai salasanasta ilmoitetaan käyttäjälle

### Kirjautumisen jälkeen
* Käyttäjä voi kirjautua ulos
* Käyttäjä voi luoda uusia budjetteja, joille annetaan nimi ja määrä.
* Käyttäjä voi luoda kuluja budjetteihin. Kuluilla on nimi ja hinta
* Käyttäjä voi tarkastella yksittäistä budjettia ja näkee kuinka paljon siitä on vielä jäljellä tai kuinka paljon budjetin yli on menty
* Käyttäjä voi poistaa budjetteja
* Käyttäjä voi muokata budjetin nimeä ja määrää
* Käyttäjä voi poistaa kuluja budjeteista
* Käyttäjä voi poistaa oman käyttäjänsä, joka poistaa myös siihen liittyvät budjetit ja kulut

## Jatkokehitys
* Opintotuki-counter, joka kertoo milloin seuraava opintotuki on tulossa
* Ylläpitäjä, joka voi poistaa ja lisätä käyttäjiä
* Mahdollisuus kulujen kategorisointiin, esimerkiksi ruokaostokset.
* Mahdollisuus budjetin jakamiseen eri kategorioihin
* Enemmän statistiikka budjeteista ja niiden kuluista käyttäjälle

