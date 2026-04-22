package ui;

import domain.EnemyCharacter;
import domain.HeroCharacter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * 战斗游戏主类
 * 处理游戏的全部核心逻辑，包括：
 * - 角色创建和属性分配
 * - 回合制战斗系统
 * - 敌人AI行为
 * - 胜负判定和奖励机制
 */
public class FightingGame {

    /**
     * 游戏主入口
     * @param username 玩家用户名
     */
    public void gameStart(String username) {
        System.out.println("—————————————————————————————————");
        System.out.println("|        🎮 游戏页面 🎮         |");
        System.out.println("—————————————————————————————————");
        System.out.println("|        🎮 游戏开始 🎮         |");
        System.out.println("—————————————————————————————————");
        System.out.println("\t\t 用户：" + username);

        // 创建玩家角色
        HeroCharacter player = createPlayerCharacter(username);
        System.out.println("—————————————————————————————————");
        System.out.println("|     🎮 角色创建成功！ 🎮      |");
        System.out.println("—————————————————————————————————");
        System.out.println("\uD83C\uDF1F初始属性为：" + player.show() + "\uD83C\uDF1F");
        System.out.println("\uD83C\uDF1F拥有的技能为：" + player.showSkills() + "\uD83C\uDF1F");

        // 初始化敌人列表
        ArrayList<EnemyCharacter> enemies = new ArrayList<>();
        enemies.add(new EnemyCharacter("初级战士", 80, 15, 10, "猛击"));
        enemies.add(new EnemyCharacter("敏捷刺客", 60, 20, 5, "快速攻击"));
        enemies.add(new EnemyCharacter("重装坦克", 120, 10, 20, "防御姿态"));
        enemies.add(new EnemyCharacter("神秘法师", 70, 25, 8, "火球术"));

        int count = 1;   // 战斗场次计数器
        int wins = 0;    // 胜利场次计数器

        // 主游戏循环：只要玩家存活就继续
        while (player.isAlive()) {
            // 从第2场战斗开始，每场敌人属性增强
            if (count > 1) {
                for (int i = 0; i < enemies.size(); i++) {
                    EnemyCharacter enemy = enemies.get(i);
                    enemy.maxHP += 10;
                    enemy.HP = enemy.maxHP;
                    enemy.attack += 3;
                    enemy.defense += 2;
                    enemy.defending = false;
                }
            }

            // 随机选择一场战斗的敌人
            Random r = new Random();
            int index = r.nextInt(enemies.size());
            EnemyCharacter enemy = enemies.get(index);

            // 显示战斗开始信息
            System.out.println("=================================");
            System.out.println("⚔\uFE0F 第" + count + "场战斗开始！！！对手：" + enemy.name);
            System.out.println("=================================");
            System.out.println("\uD83C\uDF1F初始化属性成功！！！" + enemy.show() + "\uD83C\uDF1F");

            int round = 1;
            // 回合循环：直到一方死亡
            while (player.isAlive()) {
                System.out.println("=================================");
                System.out.println("|      ⚔\uFE0F 第" + round + "回合开始！！！      |");
                System.out.println("=================================");

                // 显示双方血条
                System.out.println(getHeathBar(player.name, player.HP, player.maxHP));
                System.out.println(getHeathBar(enemy.name, enemy.HP, enemy.maxHP));

                // 玩家回合
                playerTurn(player, enemy);
                // 检查敌人是否死亡
                if(!enemy.isAlive()){
                    System.out.println("\uD83C\uDF89 恭喜你你击败了："+enemy.name+"！！！");
                    wins ++;
                    break;
                }

                // 敌人回合
                enemyTurn(enemy, player);
                // 检查玩家是否死亡
                if(!player.isAlive()){
                    System.out.println("☠ 你被"+enemy.name+"击败了！！！游戏结束！！！");
                    break;
                }
                round ++;
            }

            // 战斗结束后玩家恢复生命值
            if(player.isAlive()){
                int healHP = r.nextInt(21)+20;
                player.heal(healHP);
                System.out.println("\uD83D\uDC96 战斗结束！！！你恢复了" + healHP + "点生命值！");
                System.out.println("\uD83C\uDFC6 当前胜场： "+ wins);
            }

            // 连胜3场触发福利：属性提升
            if(player.isAlive()&&wins>0 &&wins %3 ==0){
                System.out.println("\uD83C\uDF1F 恭喜！连胜三场，解锁隐藏福利————属性提升！！！ \uD83C\uDF1F");
                player.maxHP += 30;
                player.attack += 5;
                player.defense += 3;
                System.out.println("\uD83C\uDF1F " + player.show() + " \uD83C\uDF1F");
            }

            // 询问是否继续战斗
            if(player.isAlive()){
                System.out.println("是否继续战斗？（y/n）");
                System.out.print("请选择：");
                Scanner sc = new Scanner(System.in);
                String choice = sc.next();
                if ("y".equalsIgnoreCase(choice)) {
                    count ++;
                }else if ("n".equalsIgnoreCase(choice)){
                    break;
                }else{
                    System.out.println("输入错误，默认游戏继续！！！");
                    count++;
                }
            }
        }

        // 游戏结束
        System.out.println("=================================");
        System.out.println("|   游戏结束！！！\uD83C\uDFC6总胜场："+wins+"    |");
        System.out.println("=====感谢您游玩文字版格斗游戏=====");
        System.exit(0);
    }


    /**
     * 生成血条可视化显示
     * @param name 角色名称
     * @param HP 当前生命值
     * @param maxHP 最大生命值
     * @return 血条字符串
     */
    public String getHeathBar(String name, int HP, int maxHP) {
        int barLength = 20;
        int filled = (int) ((HP * 1.0 / maxHP) * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("：[");
        for (int i = 0; i < 20; i++) {
            if (i < filled) {
                sb.append("■");  // 填充部分
            } else {
                sb.append(" ");  // 空白部分
            }
        }
        sb.append("] ").append(HP).append("/").append(maxHP).append(" HP");

        return sb.toString();
    }

    /**
     * 创建玩家角色并分配属性点
     * @param username 用户名（作为角色名）
     * @return 创建的玩家角色对象
     */
    public HeroCharacter createPlayerCharacter(String username) {
        System.out.println("—————————————————————————————————");
        System.out.println("|     🎮 正在创建角色... 🎮     |");
        System.out.println("—————————————————————————————————");
        System.out.println("\t  您的角色名为：" + username);

        // 可分配的属性点总数
        int points = 20;

        System.out.println("—————————————————————————————————");
        System.out.println("|  🎮 请分配属性点（共20点） 🎮  |");
        System.out.println("—————————————————————————————————");
        System.out.println("|  🎮 生命值（每点 + 10HP） 🎮  |");
        System.out.println("—————————————————————————————————");
        System.out.println("|  🎮 攻击力（每点 + 2ATK） 🎮  |");
        System.out.println("—————————————————————————————————");
        System.out.println("|  🎮 防御力（每点 + 1DEF） 🎮  |");
        System.out.println("—————————————————————————————————");
        Scanner sc = new Scanner(System.in);

        String[] attributes = {"生命值", "攻击力", "防御力"};
        int[] values = new int[3];

        // 分配3种属性
        for (int i = 0; i < attributes.length; i++) {
            System.out.print("分配点数到" + attributes[i] + "：");
            int input = sc.nextInt();
            if (input < 0) {
                System.out.println("无效输入！！！默认分配0点！！！");
                input = 0;
            } else if (input > points) {
                System.out.println("属性点不足！！！剩余属性点全部分配到：" + attributes[i]);
                input = points;
            }
            points -= input;
            values[i] = input;
            // 如果是最后一项且还有剩余点数，自动分配
            if (i == 2 && points > 0) {
                System.out.println("属性点还剩：" + points + "，已分配到：" + attributes[2]);
                values[2] += points;
            }
        }

        // 创建玩家角色，基础属性 + 分配的属性
        HeroCharacter player = new HeroCharacter(
                username,
                100 + values[0] * 10,   // 基础100HP + 分配值
                10 + values[1] * 2,     // 基础10攻击 + 分配值
                values[2]               // 基础0防御 + 分配值
        );

        // 添加初始技能
        player.skillList.add("普通攻击");
        player.skillList.add("强力一击");
        player.skillList.add("生命汲取");

        return player;
    }

    /**
     * 玩家回合操作
     * 让玩家选择技能并执行相应效果
     * @param player 玩家角色
     * @param enemy 敌人角色
     */
    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("=========== 玩家回合 ============");
        System.out.println("1.普通攻击");
        System.out.println("2.强力一击");
        System.out.println("3.生命汲取");
        System.out.print("请选择选择技能：");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();

        switch (choice) {
            case 1 :
                // 普通攻击：造成基础伤害
                int damage1 = calculateDamage(player.attack, enemy.defense);
                System.out.println("⚔\uFE0F你对" +enemy.name+"使用了  普通攻击  ，造成" + damage1 + "点伤害！！！");
                enemy.takeDamage(damage1);
                break;
            case 2:
                // 强力一击：消耗10HP，造成1.8倍攻击力的伤害
                if (player.HP > 10) {
                    player.takeDamage(10);
                    int damage2 = calculateDamage((int)(player.attack * 1.8), enemy.defense);
                    System.out.println("\uD83D\uDCA5消耗10HP，你对" +enemy.name+"使用了  强力一击  ，造成" + damage2 + "点伤害！！！");
                    enemy.takeDamage(damage2);
                }else{
                    System.out.println("你的生命值不足10点，无法使用  强力一击  ！！！");
                }
                break;
            case 3 :
                // 生命汲取：消耗10HP，恢复0-20点生命值
                if(player.HP > 10){
                    player.takeDamage(10);
                    Random r = new Random();
                    int heal = r.nextInt(21);
                    player.heal(heal);
                    System.out.println("\uD83D\uDC96 消耗10HP，你使用了  生命汲取  ，恢复了 " +heal+ " 点生命值！！！");
                }else{
                    System.out.println("你的生命值不足10点，无法使用  生命汲取  ！！！");
                }
                break;
            default :
                System.out.println("无效选择！！！");
        }
    }

    /**
     * 计算伤害值
     * 伤害 = 攻击力 - 防御力，最小为1
     * @param attack 攻击方攻击力
     * @param defense 防御方防御力
     * @return 实际伤害值
     */
    public int calculateDamage(int attack, int defense) {
        if (attack <= defense) {
            return 1;
        } else {
            return attack - defense;
        }
    }

    /**
     * 敌人AI回合
     * 敌人随机选择普通攻击或使用特有技能
     * @param enemy 敌人角色
     * @param player 玩家角色
     */
    private void enemyTurn(EnemyCharacter enemy, HeroCharacter player) {
        System.out.println("=========== 电脑回合 ============");
        String action = "普通攻击";
        Random r = new Random();
        int num = r.nextInt(2);
        // 50%几率使用特有技能
        if (num == 1) {
            action = enemy.skill;
        }

        switch (action) {
            case "普通攻击" :
                // 普通攻击
                int damage1 = calculateDamage(enemy.attack, player.defense);
                player.takeDamage(damage1);
                System.out.println("⚔\uFE0F "+enemy.name+" 对你使用了  普通攻击  ，造成" + damage1 + "点伤害！！！");
                break;
            case "猛击":
                // 猛击：1.5倍攻击力
                int damage2 = calculateDamage((int)(enemy.attack * 1.5), player.defense);
                player.takeDamage(damage2);
                System.out.println("\uD83D\uDCA5 "+enemy.name+" 对你使用了  猛击  ，造成" + damage2 + "点伤害！！！");
                break;
            case "快速攻击":
                // 快速攻击：连续2次0.5倍攻击力的攻击
                int damage3 = 0;
                for (int i = 0; i < 2; i++) {
                    int temp = calculateDamage(enemy.attack/2, player.defense);
                    damage3 += temp;
                }
                player.takeDamage(damage3);
                System.out.println("\uD83D\uDCA5 "+enemy.name+" 对你使用了  快速攻击  ，造成" + damage3 + "点伤害！！！");
                break;
            case "防御姿态":
                // 防御姿态：进入防御状态，下一次受到伤害减半
                enemy.defending = true;
                System.out.println("\uD83D\uDCA5 "+enemy.name+" 使用了  防御姿态  ，已进入防御状态！！！");
                break;
            case "火球术":
                // 火球术：1.8倍攻击力
                int damage4 = calculateDamage((int) (enemy.attack * 1.8), player.defense);
                player.takeDamage(damage4);
                System.out.println("\uD83D\uDCA5 "+enemy.name+" 对你使用了  火球术  ，造成" + damage4 + "点伤害！！！");
                break;
        }
    }
}
