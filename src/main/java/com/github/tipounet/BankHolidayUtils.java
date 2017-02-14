package com.github.tipounet;

import com.google.common.collect.Lists;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Gestions des jours fériés Français
 * <p>
 * Algorithme de Butcher
 * https://fr.wikipedia.org/wiki/Calcul_de_la_date_de_P%C3%A2ques_selon_la_m%C3%A9thode_de_Meeus#Calcul_de_la_date_de_P.C3.A2ques_gr.C3.A9gorienne
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
   * Calcul le jour de pâques pour l'année
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
   * lundi de pâques
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
   * Vérifie si la date ets un jour férié.
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
