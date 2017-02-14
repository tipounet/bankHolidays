package com.github.tipounet;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class BankHolidayUtilsTest {

  private static final int YEAR = 2017;
  private static final int EXPECTED_DAY_OF_MONTH = 16;
  private LocalDate easterDay2016 = LocalDate.of(YEAR, Month.APRIL, EXPECTED_DAY_OF_MONTH);
  private BankHolidayUtils bankHolidayUtils;

  @Before
  public void setUp() throws Exception {
    bankHolidayUtils = new BankHolidayUtils();
  }

  @Test
  public void getEasterDay() throws Exception {
    LocalDate result = this.bankHolidayUtils.getEasterDay(YEAR);
    assertThat(result).isNotNull().isEqualTo(easterDay2016);
  }

  @Test
  public void getMondayOfEasterDay() throws Exception {
    LocalDate expected = easterDay2016.plus(1, ChronoUnit.DAYS);
    LocalDate result = this.bankHolidayUtils.getMondayOfEasterDay(easterDay2016);
    assertThat(result).isNotNull().isEqualTo(expected);
  }

  @Test
  public void getAscension() throws Exception {
    LocalDate expected = LocalDate.of(YEAR, Month.MAY, 25);
    LocalDate result = this.bankHolidayUtils.getAscension(easterDay2016);
    assertThat(result).isNotNull().isEqualTo(expected);
  }

  @Test
  public void getMondayOfPentecote() throws Exception {
    LocalDate expected = LocalDate.of(YEAR, Month.JUNE, 5);
    LocalDate result = this.bankHolidayUtils.getMondayOfPentecote(easterDay2016);
    assertThat(result).isNotNull().isEqualTo(expected);
  }

  @Test
  public void isBankHoliday() throws Exception {
    // test jour fixe
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.JANUARY, 1))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.MAY, 8))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.MAY, 1))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.JULY, 14))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.AUGUST, 15))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.NOVEMBER, 1))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.NOVEMBER, 11))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.DECEMBER, 25))).isTrue();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.DECEMBER, 31))).isTrue();

    // p�ques
    assertThat(bankHolidayUtils.isBankHoliday(easterDay2016)).isTrue();
    // ascension
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.MAY, 25))).isTrue();
    // Lundi de pentecote
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.JUNE, 5))).isTrue();

    // test jour non f�ri�
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.FEBRUARY, 14))).isFalse();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.APRIL, 29))).isFalse();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.AUGUST, 11))).isFalse();
    assertThat(bankHolidayUtils.isBankHoliday(LocalDate.of(2017, Month.SEPTEMBER, 24))).isFalse();
  }
}
