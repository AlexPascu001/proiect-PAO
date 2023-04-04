# proiect-PAO

## Etapa I
### 1) Definirea sistemului
Să se creeze o lista pe baza temei alese cu cel puțin 10 acțiuni/interogări care se pot face în cadrul sistemului și o lista cu cel puțin 8 tipuri de obiecte.

### Tipuri de obiecte:
- `Customer`
- `Address`
- `Account`
- `SavingsAccount`
- `Transaction`
- `Card`
- `MasterCard`
- `Visa`
- `BankService`

### Acțiuni
- crearea unui client (`createCustomer`)
- crearea unui cont bancar (`createAccount`)
- crearea unui card (`createCard`)
- crearea unei tranzacții (`createTransaction`)
- crearea de carduri de tipuri diferite (`MasterCard`/`Visa`)
- obținerea soldului bancar (`getBalance`)
- adaugarea/retragerea unei sume de bani (`deposit`/`withdraw`)
- transferul unei sume de bani (`transfer`)
- obținerea cardurilor asociate unui client (`getCards`)
- obținerea adresei unui client (`getAddress`) - pentru a trimite recuperatorii :)
- obținerea ratei dobânzii și a dobânzii anuale pentru un cont de economii (`getInterestRate`/`getInterestPerYear`)
- afișarea detaliilor unui client, card, cont (`override toString()` în fiecare clasă) 


### 2) Implementare
Sa se implementeze în limbajul Java o aplicație pe baza celor definite la primul punct.
Aplicația va conține:
* clase simple cu atribute private / protected și metode de acces
* cel puțin 2 colecții diferite capabile să gestioneze obiectele definiteanterior (eg: List, Set, Map, etc.) dintre care cel puțin una sa fie sortata – se vor folosi array-uri uni- /bidimensionale în cazul în care nu se parcurg colectiile pana la data checkpoint-ului.
* utilizare moștenire pentru crearea de clase adiționale și utilizarea lor încadrul colecțiilor;
* cel puțin o clasă serviciu care sa expună operațiile sistemului
* o clasa Main din care sunt făcute apeluri către servicii