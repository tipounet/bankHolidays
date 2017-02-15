package com.github.tipounet;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Gestions des jours feries Francais
 */
public class BankHolidayUtils {

  private List<LocalDate> fixedDay;

  private void initFixedDay(final int year) {
    LocalDate easterDay = getEasterDay(year);
    fixedDay = Lists.newArrayList(LocalDate.of(year, Month.JANUARY, 1),
        LocalDate.of(year, Month.MAY, 1),
        LocalDate.of(year, Month.MAY, 8),
        LocalDate.of(year, Month.JULY, 14),
        LocalDate.of(year, Month.AUGUST, 15),
        LocalDate.of(year, Month.NOVEMBER, 1),
        LocalDate.of(year, Month.NOVEMBER, 11),
        LocalDate.of(year, Month.DECEMBER, 25),
        LocalDate.of(year, Month.DECEMBER, 31),
        easterDay,
        getAscension(easterDay),
        getMondayOfEasterDay(easterDay),
        getMondayOfPentecote(easterDay)
    );
  }

  /**
   * Calcul le jour de paques pour l'annee
   * <p>
   * Algorithme de Butcher, méthode de Meeus
   * https://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques_selon_la_m%C3%A9thode_de_Meeus#Calcul_de_la_date_de_P.C3.A2ques_gr.C3.A9gorienne
   *
   * @param year
   * @return
   */
  LocalDate getEasterDay(final int year) {

    final int cycleMeton = year % 19;
    final int centaine = year / 100;
    final int rang = year % 100;

    final int siecleBissextile = centaine / 4;
    final int siecleBissextileReste = centaine % 4;
    final int cycleProemptose = (centaine + 8) / 25;

    final int proemptose = (centaine - cycleProemptose + 1) / 3;
    final int epacte = (19 * cycleMeton + centaine - siecleBissextile - proemptose + 15) % 30;
    final int anneeBissextile = rang / 4;
    final int anneeBissextileReste = rang % 4;
    final int lettreDominicale = (32 + 2 * siecleBissextileReste + 2 * anneeBissextile - epacte - anneeBissextileReste) % 7;
    final int correction = (cycleMeton + 11 * epacte + 22 * lettreDominicale) / 451;
    final int tmp = epacte + lettreDominicale - 7 * correction + 114;
    final int m = tmp / 31;
    final int day = (tmp % 31) + 1;

    Month month;
    if (m == 3) {
      month = Month.MARCH;
    } else {
      month = Month.APRIL;
    }
    return LocalDate.of(year, month, day);
  }

  /**
   * Calcul le jour de pâque pour l'année en paramètre avec la méthode de Conway
   * https://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques_selon_la_m%C3%A9thode_de_Conway
   *
   * @param year
   * @return
   */
  LocalDate getEasterDayConwayMethod(final int year) {
    final int s = year / 100;//annnée séculaire
    final int t = year % 100; // millésime
    final int a = t / 4;// terme bissextil
    final int p = s % 4;
    final int jps = (9 - 2 * p) % 7; // jour pivot séculaire
    final int jp = (jps + t + a) % 7; // jout-pivot de l'année courante
    final int g = year % 19;
    final int G = g + 1; // Cycle de Méton
    final int b = s / 4; // Métemptose
    final int r = (8 * (s + 11)) / 25; //Proemptose

    final int C = -s + b + r;//Correction séculaire

    final int d = (((11 * G + C) % 30) + 30) % 30;//Pleine lune pascale

    final int h = (551 - 19 * d + G) / 544;// Correction des exceptions à l'épacte

    final int e = (50 - d - h) % 7;// écart de la pleine lune pascale au jour-pivot
    final int f = (e + jp) % 7; // jour de la plene lune pascale

    final int R = 57 - d - f - h; // Dimanche de paques
    int day;
    Month month;
    if (R <= 31) {
      day = R;
      month = Month.MARCH;
    } else {
      month = Month.APRIL;
      day = R - 31;
    }

    return LocalDate.of(year, month, day);
  }

  /**
   * lundi de paques
   *
   * @param easterDay
   * @return
   */
  LocalDate getMondayOfEasterDay(LocalDate easterDay) {
    assert easterDay != null;
    return easterDay.plus(1, ChronoUnit.DAYS);
  }

  /**
   * jeudi de l'ascencion
   *
   * @param easterDay
   * @return
   */
  LocalDate getAscension(LocalDate easterDay) {
    assert easterDay != null;
    return easterDay.plus(39, ChronoUnit.DAYS);
  }

  /**
   * Lundi de pentecote
   *
   * @param easterDay
   * @return
   */
  LocalDate getMondayOfPentecote(LocalDate easterDay) {
    assert easterDay != null;
    return easterDay.plus(50, ChronoUnit.DAYS);
  }

  /**
   * Verifie si la date ets un jour ferie.
   *
   * @param date
   * @return
   */
  public boolean isBankHoliday(LocalDate date) {
    assert date != null;
    initFixedDay(date.getYear());
    boolean retour = false;
    for (LocalDate l : fixedDay) {
      if (l.isEqual(date)) {
        retour = true;
        break;
      }
    }
    return retour;
  }
}
