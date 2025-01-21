package fr.ft.app.Entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString public class Creature extends Entity {

  private int level = 1;
  private int xp = 0;
  @NonNull private Role role;
  @NonNull private Statistic attack;
  @NonNull private Statistic defense;
  @NonNull private Statistic hitPoint;
  private Artifact artifact;

  Creature(String p_name, Role p_role, Statistic p_attack, Statistic p_defense, Statistic p_hitPoint) {
    super(p_name);
    role = p_role;
    attack = p_attack;
    defense = p_defense;
    hitPoint = p_hitPoint;
  }

  public void gainXp(int value) {
    setXp(xp + value);
    checkLevel();
  } 

  private void checkLevel() {
    int nextLevel = level * 1000 + (level - 1) * (level - 1) * 450;
    if (xp >= nextLevel) {
      levelUp();
        xp -= nextLevel;
        checkLevel();
    }
  }

  public void levelUp() {
      setLevel(level + 1);
      attack.growth();
      defense.growth();
      hitPoint.growth();

  }

  public void levelUp(int n) {
    for (int i = 0; i < n; i++) {
      setLevel(level + 1);
      attack.growth();
      defense.growth();
      hitPoint.growth();
    }
  }

  static public Creature invoke(String p_name, Role p_role) {
    return new Creature(p_name, p_role, 
      new Statistic(p_role.attack, p_role.aGrowth), 
      new Statistic(p_role.defense, p_role.dGrowth), 
      new Statistic(p_role.hitPoint, p_role.hGrowth));
  }

}
