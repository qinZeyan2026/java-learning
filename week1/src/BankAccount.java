public class BankAccount {
    private  String  accountld;
    private double balance;

    public BankAccount(){

    }

    public BankAccount(String accountld,double balance){
        this.accountld=accountld;
        this.balance=balance;
    }

    //存款
    public void deposit(double money){
        if(money<=0){
            System.out.println("存款金额必须大于零哦，存款失败！！！");
            return;
        }
        balance+=money;
    }

    //取款
    public boolean withdraw(double money){
        if(money<=0){
            System.out.println("取款金额必须大于零哦，取款失败！！！");
            return false;
        }else if(money>balance){
            System.out.println("余额不足，取款失败！！！");
            return false;
        }
        balance-=money;
        return true;
    }

    //给他人转账
    public void transfer(BankAccount other,double money){
        if(withdraw(money)){
            other.deposit(money);
        }
    }

    public String getAccountld() {
        return accountld;
    }

    public double getBalance() {
        return balance;
    }


}
