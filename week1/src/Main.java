import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String SAVE_FILE="words.txt";

    public static void main(String[] args) {
        WordBook wordbook=new WordBook();
        wordbook.loadFromFile(SAVE_FILE);
        Scanner sc=new Scanner(System.in);

        while(true){
            printMenu();
            String choice=sc.nextLine();

            switch(choice){
                case"1":
                    addWord(wordbook,sc);
                    break;
                case"2":
                    startQuiz(wordbook,sc);
                    break;
                case"3":
                    showAll(wordbook);
                    break;
                case"4":
                    wordbook.saveToFile(SAVE_FILE);
                    System.out.println("再见！");
                    return;
                default:
                    System.out.println("输入无效，请重新输入！");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n====背单词本====");
        System.out.println("1.添加单词");
        System.out.println("2开始抽查单词");
        System.out.println("3.查看所有单词");
        System.out.println("4.退出");
        System.out.println("请输入你的选择：");
    }

    static void addWord(WordBook wordbook, Scanner sc) {
        System.out.println("请输入英文单词");
        String englishWord=sc.nextLine().trim();

        if(englishWord.isEmpty()){
            System.out.println("英文单词不能为空！");
            return;
        }

        System.out.println("请翻译成中文：");
        String chineseWord=sc.nextLine().trim();
        if(chineseWord.isEmpty()){
            System.out.println("中文释义不能为空！");
            return;
        }

        Word newWord=new Word(englishWord,chineseWord);
        wordbook.addWord(newWord);
        System.out.println("✅单词添加成功！");
    }

    static void startQuiz(WordBook wordbook, Scanner sc){
        if(wordbook.isEmpty()){
            System.out.println("目前单词本里没有单词，请先添加需要抽查的单词！");
            return;
        }

        Word randomWord=wordbook.randomWord();
        System.out.println("请输入"+randomWord.getEnglish()+"的中文释义：");
        String anwerChinese=sc.nextLine().trim();

        if(!randomWord.getChinese().equals(anwerChinese)){
            System.out.println("❌中文释义回答错误！");
            randomWord.setWrongCount(randomWord.getWrongCount()+1);
            return;
        }
        System.out.println("✅恭喜你，答对了！");
        randomWord.setRightCount(randomWord.getRightCount()+1);
    }

    static void showAll(WordBook wordbook){
        List<Word> wordList=wordbook.getAllWords();

        if(wordList.isEmpty()){
            System.out.println("单词本为空！");
            return;
        }
        System.out.println("\n=====单词本=====");
        for(Word w:wordList){
            System.out.println(w);
        }
    }
}
