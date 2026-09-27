import java.util.Random;
import java.util.Scanner;

public class GuessNumber {
    private static final int MAX_GUESS_TIMES = 7;
    private static final int MAX_INVALID_TIMES = 3;
    public static void main(String[] args) {
       Random random=new Random();
       int targetNumber=random.nextInt(100)+1;
       Scanner scanner=new Scanner(System.in);
       int guessTimes=0;

       int invalidTimes = 0;

       boolean won=false;
       System.out.println("Hello，朋友，我们来玩猜数游戏吧！");
       System.out.println("规则如下：");
       System.out.println("1. 系统会随机生成一个 1~100 之间的整数；");
       System.out.println("2. 你最多可以猜 " + MAX_GUESS_TIMES + " 次；");
       System.out.println("3. 如果输入的不是整数，或者不在 1~100 范围内，会计入错误次数，错误次数最多 " + MAX_INVALID_TIMES + " 次，超过就结束游戏,可以续费继续游戏噢；");
       System.out.println("4. 猜对就赢，猜错会提示大了或小了。");
       System.out.println("请猜一个 1~100 的数：");

        while (guessTimes<MAX_GUESS_TIMES&&invalidTimes<MAX_INVALID_TIMES) {
            if(scanner.hasNextInt()){
                int guess=scanner.nextInt();
                if(guess<1||guess>100){
                    System.out.println("朋友，只能猜 1~100 之间的数，请重新输入噢!");
                    invalidTimes++;
                    continue;
                }
                guessTimes++;
                if(guess==targetNumber){
                    System.out.println("恭喜你朋友，第"+guessTimes+"次就猜对了！奖励你一个大红花ᕕ( ᐛ )ᕗ");
                    won=true;
                    break;
                }else if(guess>targetNumber){
                    System.out.println("糟糕，大了！");
                }else if(guess<targetNumber){
                    System.out.println("糟糕，小了！");
                }
            }else{
                scanner.next();
                invalidTimes++;
                System.out.println("朋友，你猜的内容不是整数，请重新认真输入噢!");
            }
        }

        if (!won) {
            if (invalidTimes>=MAX_INVALID_TIMES) {
                System.out.println("朋友，由于您猜内容不符合要求次数太多，无法继续游戏了，请先续费加次数吧٩(ˊᗜˋ*)و");
            }else{
                System.out.println("真是抱歉朋友，您的免费猜测次数用完了，请先续费再继续猜吧٩(ˊᗜˋ*)و");
            }
        }
        scanner.close();
    }
}
