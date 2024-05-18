package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

class TeknonymyService implements ITeknonymyService {
  // this class save the deepestDescendent and his level of three
  private static class Result {
    Person person;
    int level;

    Result(Person person, int level) {
        this.person = person;
        this.level = level;
    }
  }

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    if (person == null) {
      return "";
    }
    Result deepestDescendant = findDeepestDescendant(person, 0);
    return buildTeknonymyName(person, deepestDescendant);
  }

  /**
     * Method to find the deepest descendant in the family tree using DFS algorithm
     * 
     * @param current Current person in the recursion
     * @param level Current level of depth in the tree
     * @return Result object containing the deepest person and their level
     */

  private Result findDeepestDescendant(Person current, int level) {
  if (current.children() == null || current.children().length == 0) {
    return new Result(current, level);
  }

  Result deepest = new Result(current, level);
  for (Person child : current.children()) {
    Result childResult = findDeepestDescendant(child, level + 1);
    if (childResult.level > deepest.level) {
      deepest = childResult;
    } else if (childResult.level == deepest.level) {
        if (childResult.person.dateOfBirth().isBefore(deepest.person.dateOfBirth())) {
          deepest = childResult;
        }
    }
  }
  return deepest;
  }

  /**
   * Method to build the Teknonymy name based on the deepest descendant
   * 
   * @param ancestor The initial person
   * @param deepestDescendant The deepest descendant found
   * @return The Teknonymy name
   */
  private String buildTeknonymyName(Person ancestor, Result deepestDescendant) {
  StringBuilder teknonymyName = new StringBuilder();
  Character sex = ancestor.sex();

  if (deepestDescendant.level == 0) {
    return "";
  }

  if (sex.equals('M')) {
    teknonymyName.insert(0, "father of " + deepestDescendant.person.name());
  } else if (sex.equals('F')) {
    teknonymyName.insert(0, "mother of " + deepestDescendant.person.name());
  }

  for (int i = 1; i < deepestDescendant.level; i++) {
    if (i == 1) {
      teknonymyName.insert(0, "grand");
    } else {
      teknonymyName.insert(0, "great-");
    }
  }
  return teknonymyName.toString();
  }
}