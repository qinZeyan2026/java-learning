import java.util.Scanner;

public class MultiplicationTable {
    private static final int MAX_INVALID_COUNT = 5;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("小朋友，今天我们来学习乘法表吧!");
        int size=readSize(sc);
        if(size==-1){
            System.out.println("警告！警告！警告！学习结束！");
        }else{
            printTable(size);
        }
    }

    private static void printTable(int size) {
        for (int row = 1; row <=size ; row++) {
            for (int col =1; col <=row; col++) {
                System.out.print(col+"*"+row+"="+(row*col)+"\t");
            }
            System.out.println();
        }
    }

    private static int readSize(Scanner sc) {
        int invalidCount=0;
        System.out.println("请输入你想学习的乘法表阶数（1~9噢）：");
        while (true) {

            if (!sc.hasNextInt()) {
                invalidCount++;

                //System.out.println(invalidCount);
                sc.next();
                if (isLimitExceeded(invalidCount)) {
                    return -1;
                }
                System.out.println("小朋友你输入的不是整数，请重新输入一个合法的整数吧。");
                continue;
            }
            int size = sc.nextInt();
            if(size<1||size>9){
                invalidCount++;

                if (isLimitExceeded(invalidCount)) {
                    return -1;
                }
                System.out.println("小朋友数字越界了，请重新输入");
                continue;
            }
            return size;
        }
    }
    private static boolean isLimitExceeded(int invalidCount){
        if (invalidCount >= MAX_INVALID_COUNT) {
            System.out.println("抱歉小朋友，因为你不听话次数太多，所以现在要你反思五分钟才能继续今天的乘法表学习");
            return true;
        }
        return false;
    }

}
