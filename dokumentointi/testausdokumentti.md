# Testausdokumentti

Ohjelmaan on tehty yksikkö- ja integraatiotestejä sekä järjestelmätason testejä. Yksikkö -ja integraatiotestit on
testattu automaatisoiduilla JUnit-testeillä ja järjestelmätason testejä on tehty manuaalisesti.

## Yksikkö- ja integraatiotestaus
### Sovelluslogiikka
Sovelluslogiikkaa eli pakkauksessa budjetointisovellus.domain olevia luokkia on testattu automaattisilla JUnit-testeillä. 
Sovelluslogiikkaa testaa domainTest-pakkauksessaolevat testit. 

Yksikkötesteillä testataan luokkien User, Budget ja
Expense toimivuutta. Luokissa ei kuitenkaan ole paljoa metodeja, joten niitä testaavien luokkien UserTest, BudgetTest sekä
ExpenseTest ainoat testit testaavat kostruktorien toimivuutta. Integraatiotestit kattavat myös näiden luokkien toimivuutta
paljon.

Integraatiotestejä sovelluslogiikkaan tekee luokka BudgetServiceTest, jonka testit simuloivat käyttöliittymän
BudgetService-olion avulla suorittamia toiminnallisuuksia. Integraatiotesteissä dataa tallennetaan luokkien 
FakeUserDao, FakeBudgetDao ja FakeExpenseDao, jotka toteuttavat sovelluksen budjetointisovellus.dao-pakkauksen
rajapinnat.

### DAO-luokat
Sovelluksen DAO-luokkia eli pakkauksessa budjetointisovellus.dao olevia luokkia SqlUserDao, SqlBudgetDao sekä
SqlExpenseDao on testattu JUnit-testeillä. Testit löytyvät pakkauksesta daoTest. Testit luovat uuden tiedoston
test.db(jos sitä ei vielä ole olemassa), alustaa sinne aina jotain tietoa ja sitten muokkaa tietokannan tietoja ja
tarkistaa, että tietokannan tiedot ovat muuttuneet halutulla tavalla

### Testauskattavuus

## Järjestelmätestaus

Sovelluksen järjestelmätestaus on suoritettu manuaalisesti.
## Asennus ja konfigurointi

## Toiminnallisuudet

Kaikki määrittelydokumentin listaamant toiminnallisuudet on käyty läpi ja yritetty rikkoa. Toiminnallisuutta on
yritetty rikkoa antamalla mm. syötekenttään merkkijono kun se haluaa luvun.
