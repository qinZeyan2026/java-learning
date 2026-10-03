public class BankAccountTest {
    public static void main(String[] args) {
        BankAccount Lili = new BankAccount("001", 1000);
        BankAccount Quinn = new BankAccount("002", 500);
        System.out.println("初始存款：lili="+Lili.getBalance()+"  leah="+Quinn.getBalance());

        Lili.deposit(500);
        System.out.println("Lili存款伍佰元后：Lili"+Lili.getBalance()+"  Quinn="+Quinn.getBalance());

        Lili.withdraw(300);
        System.out.println("Lili取叁佰元后：Lili=" + Lili.getBalance() + "  Quinn=" + Quinn.getBalance());

        Lili.withdraw(2000);
        System.out.println("Lili取两千元失败后：Lili=" + Lili.getBalance() + "  Quinn=" + Quinn.getBalance());

        Lili.transfer(Quinn, 400);
        System.out.println("Lili转Quinn肆佰元后：Lili=" + Lili.getBalance() + "  Quinn=" + Quinn.getBalance());

    }
}
