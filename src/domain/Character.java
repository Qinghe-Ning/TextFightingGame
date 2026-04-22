package domain;

/**
 * 角色基类
 * 定义角色的基本属性和行为，所有角色（玩家和敌人）都继承此类
 */
public class Character {
    public String name;     // 角色名称
    public int HP;          // 当前生命值
    public int maxHP;       // 最大生命值
    public int attack;      // 攻击力
    public int defense;     // 防御力

    /**
     * 默认构造函数
     */
    public Character(){
    }

    /**
     * 带参构造函数
     * @param name 角色名称
     * @param HP 生命值
     * @param attack 攻击力
     * @param defense 防御力
     */
    public Character(String name, int HP, int attack, int defense){
        this.name = name;
        this.HP = HP;
        this.maxHP = HP;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * 判断角色是否存活
     * @return 生命值大于0返回true，否则返回false
     */
    public boolean isAlive(){
        return HP > 0;
    }

    /**
     * 治疗角色
     * @param amount 恢复的生命值数量
     */
    public void heal(int amount){
        HP += amount;
        // 生命值不能超过最大值
        if(HP > maxHP){
            HP = maxHP;
        }
    }

    /**
     * 角色受到伤害
     * @param amount 受到的伤害值
     */
    public void takeDamage(int amount){
        HP -= amount;
        // 生命值不能低于0
        if(HP < 0){
            HP = 0;
        }
    }

    /**
     * 显示角色属性信息
     * @return 包含角色名称、当前生命、攻击力和防御力的字符串
     */
    public String show(){
        return name + "[当前生命：" + HP + "，攻击：" + attack + "，防御：" + defense + "]";
    }
}
