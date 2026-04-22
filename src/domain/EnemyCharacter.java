package domain;

/**
 * 敌人类
 * 继承自Character类，表示游戏中的敌人角色
 * 包含敌人特有的技能和防御状态
 */
public class EnemyCharacter extends Character{
    public String skill;        // 敌人特有技能
    public boolean defending;   // 防御状态，true表示正在防御

    /**
     * 默认构造函数
     */
    public EnemyCharacter(){
    }

    /**
     * 带参构造函数
     * @param name 敌人名称
     * @param HP 生命值
     * @param attack 攻击力
     * @param defense 防御力
     * @param skill 特有技能
     */
    public EnemyCharacter(String name, int HP, int attack, int defense, String skill){
        super(name, HP, attack, defense);
        this.skill = skill;
    }

    /**
     * 重写受到伤害的方法
     * 如果敌人处于防御状态，伤害减半
     * @param damage 受到的伤害值
     */
    @Override
    public void takeDamage(int damage){
        // 如果正在防御，伤害减半（最低为1点）
        if(defending){
            damage = damage / 2 > 1 ? damage / 2 : 1;
            defending = false;  // 防御状态结束后重置
        }

        super.takeDamage(damage);
    }
}
