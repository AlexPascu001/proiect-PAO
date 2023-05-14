# proiect-PAO

## Etapa I
### 1) Definirea sistemului
Să se creeze o lista pe baza temei alese cu cel puțin 10 acțiuni/interogări care se pot face în cadrul sistemului și o lista cu cel puțin 8 tipuri de obiecte.

### Tipuri de obiecte:
- `model.customer`
- `Address`
- `Account`
- `SavingsAccount`
- `Transaction`
- `model.card`
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
- adăugarea unui nou card la un cont (`setCard`)
- obținerea cardului asociat unui cont (`getCard`)
- adăugarea unui nou cont asociat unui client (`addAccount`)
- ștergerea unui cont asociat unui client (`removeAccount`)
- obținerea conturilor asociate unui client (`getAccounts`)
- obținerea adresei unui client (`getAddress`) - pentru a trimite recuperatorii :)
- obținerea ratei dobânzii și a dobânzii anuale pentru un cont de economii (`getInterestRate`/`getInterestPerYear`)
- afișarea detaliilor unui client, card, cont (`override toString()` în fiecare clasă) 
- afișarea tranzacțiilor unui client (`filterTransactions`)


### 2) Implementare
Sa se implementeze în limbajul Java o aplicație pe baza celor definite la primul punct.
Aplicația va conține:
* clase simple cu atribute private / protected și metode de acces
* cel puțin 2 colecții diferite capabile să gestioneze obiectele definiteanterior (eg: List, Set, Map, etc.) dintre care cel puțin una sa fie sortata – se vor folosi array-uri uni- /bidimensionale în cazul în care nu se parcurg colectiile pana la data checkpoint-ului.
* utilizare moștenire pentru crearea de clase adiționale și utilizarea lor încadrul colecțiilor;
* cel puțin o clasă serviciu care sa expună operațiile sistemului
* o clasa Main din care sunt făcute apeluri către servicii


## Etapa II
### 1) Extindeți proiectul din prima etapă prin realizarea persistenței utilizând o bază de date relațională și JDBC.
Să se realizeze servicii care sa expună operații de tip `create`, `read`, `update` și `delete` pentru cel puțin 4
dintre clasele definite. Se vor realiza servicii singleton generice pentru scrierea și citirea din baza de
date.

#### Clasele pentru care se realizează serviciile
- `Customer`
- `Account`
- `Card`
- `Transaction`

### 2) Realizarea unui serviciu de audit
Se va realiza un serviciu care sa scrie într-un fișier de tip CSV de fiecare data când este executată una
dintre acțiunile descrise în prima etapa. Structura fișierului: `nume_actiune, timestamp`
