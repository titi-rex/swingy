package fr.ft.app;

public class Creature extends Entity {

  private Artifact artifact;
  private int level = 1;
  private int xp;
  private int attack;
  private int defense;
  private int hitPoint;
  private int xpGrowth;
  private int attackGrowth;
  private int defenseGrowth;
  private int hitPointGrowth;

  public int getLevel() {
    return level;
  }

  public void setLevel(int value) {
    level = value;
  }

  public void increaseLevel(int value) {
    setLevel(level + value);
  }

  public int getXp() {
    return xp;
  }

  public void setXp(int value) {
    xp = value;
  }

  public void increaseXp(int value) {
    setXp(xp + value);
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int value) {
    attack = value;
  }

  public void increaseAttack(int value) {
    setAttack(attack + value);
  }

  public int getDefense() {
    return defense;
  }

  public void setDefense(int value) {
    defense = value;
  }

  public void increaseDefense(int value) {
    setDefense(defense + value);
  }

  public int getHitPoint() {
    return hitPoint;
  }

  public void setHitPoint(int value) {
    hitPoint = value;
  }

  public void increaseHitPoint(int value) {
    setHitPoint(hitPoint + value);
  }

  public int getXpGrowth() {
    return xp;
  }

  public void setXpGrowth(int value) {
    xp = value;
  }

  public void increaseXpGrowth(int value) {
    setXpGrowth(xp + value);
  }

  public int getAttackGrowth() {
    return attack;
  }

  public void setAttackGrowth(int value) {
    attack = value;
  }

  public void increaseAttackGrowth(int value) {
    setAttackGrowth(attack + value);
  }

  public int getDefenseGrowth() {
    return defense;
  }

  public void setDefenseGrowth(int value) {
    defense = value;
  }

  public void increaseDefenseGrowth(int value) {
    setDefenseGrowth(defense + value);
  }

  public int getHitPointGrowth() {
    return hitPoint;
  }

  public void setHitPointGrowth(int value) {
    hitPoint = value;
  }

  public void increaseHitPointGrowth(int value) {
    setHitPointGrowth(hitPoint + value);
  }

}
