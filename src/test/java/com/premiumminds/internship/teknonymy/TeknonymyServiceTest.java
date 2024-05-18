package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonTwoChildrenTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ 
          new Person("Holy", 'F', null, LocalDateTime.of(2005, 1, 1, 0, 0)),
          new Person("Erik", 'M', null, LocalDateTime.of(2003, 1, 1, 0, 0)) 
        },
        LocalDateTime.of(1980, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Erik";
    assertEquals(expected, result);
  }

  @Test
  public void PersonTwoChildrenTest2() {
    Person person = new Person(
        "Maria",
        'F',
        new Person[]{ 
          new Person("Chappie", 'M', null, LocalDateTime.of(2002, 2, 1, 0, 0)),
          new Person("Erik", 'M', null, LocalDateTime.of(2002, 1, 1, 0, 0)) 
        },
        LocalDateTime.of(1982, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "mother of Erik";
    assertEquals(expected, result);
  }

  @Test
    public void GrandParentTest() {
      Person person = new Person(
          "Marta",
          'F',
          new Person[]{ 
            new Person("Erik", 'M', new Person[]{ 
              new Person("Jade", 'F', null, LocalDateTime.of(2000, 1, 1, 0, 0)) 
            }, LocalDateTime.of(2024, 1, 1, 0, 0)) 
          },
          LocalDateTime.of(1964, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "grandmother of Jade";
      assertEquals(expected, result);
    }

    @Test
    public void GreatGrandParentTest() {
      Person person = new Person(
          "Mary",
          'F',
          new Person[]{ 
            new Person("Erik", 'M', new Person[]{ 
              new Person("Peter", 'M', new Person[]{ 
                new Person("Holy", 'F', null, LocalDateTime.of(2019, 1, 1, 0, 0)),
                new Person("Mike", 'M', null, LocalDateTime.of(2019, 1, 1, 0, 5)) 
              }, LocalDateTime.of(1999, 1, 1, 0, 0)) 
            }, LocalDateTime.of(1979, 1, 1, 0, 0)) 
          },
          LocalDateTime.of(1961, 1, 1, 0, 0));
      String result = new TeknonymyService().getTeknonymy(person);
      String expected = "great-grandmother of Holy";
      assertEquals(expected, result);
    }

  @Test
  public void ManyGenerationsTest() {
    Person person = new Person(
        "Steven",
        'M',
        new Person[]{ 
          new Person("Tyson", 'M', new Person[]{ 
            new Person("Telma", 'F', new Person[]{ 
              new Person("Paul", 'M', new Person[]{ 
                new Person("Erik", 'M', null, LocalDateTime.of(2015, 1, 1, 0, 0)) 
              }, LocalDateTime.of(1993, 1, 1, 0, 0)) 
            }, LocalDateTime.of(1970, 1, 1, 0, 0)) 
          }, LocalDateTime.of(1950, 1, 1, 0, 0)) 
        },
        LocalDateTime.of(1910, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-grandfather of Erik";
    assertEquals(expected, result);
  }
}