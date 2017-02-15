# bankHollidays
Permet de vérifier si une date est un jour férié en france. 


##Calcul des jours férié "flottant"
- Jour de pâques
- Le lundi de pâques (déduit du jour de pâques)
- Le lundi de pentecôte (déduit du jour de pâques)
- L'ascencion (déduit du jour de pâques)

Ces dates sont communes a tout les pays qui utilise le calendrier grégorien. 

##Exemple d'utilisation
```
  @Test
  public void test() {
    BankHolidayUtils bankHolidayUtils = new BankHolidayUtils();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.DECEMBER, 31))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.FEBRUARY, 14))).isFalse();
  }
```

Nécéssite assertJ pour fonctionner


## Méthode de calcule
###Utilisation de la méthode de calcul de Meeus
[source](https://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques_selon_la_m%C3%A9thode_de_Meeus#Calcul_de_la_date_de_P.C3.A2ques_gr.C3.A9gorienne)

### Utilisation de la méthode de Conway
[source](https://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques_selon_la_m%C3%A9thode_de_Conway)
