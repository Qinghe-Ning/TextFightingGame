package domain;

import java.util.ArrayList;

/**
 * 玩家角色类
 * 继承自Character类，表示游戏中的玩家角色
 * 包含玩家特有的技能列表
 */
public class HeroCharacter extends Character{
    public ArrayList<String> skillList;  // 技能列表

    /**
     * 默认构造函数
     */
    public HeroCharacter(){
    }

    /**
     * 带参构造函数
     * @param name 角色名称
     * @param HP 生命值
     * @param attack 攻击力
     * @param defense 防御力
     */
    public HeroCharacter(String name, int HP, int attack, int defense){
        super(name, HP, attack, defense);
        skillList = new ArrayList<>();
    }

    /**
     * 显示角色所有技能
     * @return 技能列表的字符串表示，技能之间用逗号分隔
     */
    public String showSkills(){
        StringBuilder sb = new StringBuilder();
        for (String skill : skillList) {
            sb.append(skill).append(", ");
        }
        return sb.toString();
    }
}
