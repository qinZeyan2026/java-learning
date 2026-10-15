import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordBook {
    private List<Word> words=new ArrayList<>();

    //添加单词
    public void addWord(Word word){
        words.add(word);
    }

    //获取所有单词
    public List<Word> getAllWords(){
        return words;
    }

    //判断是否为空
    public boolean isEmpty(){
        return words.isEmpty();
    }

    //随机抽查一个单词
    public Word randomWord(){
        Random random=new Random();
        int index=random.nextInt(words.size());
        Word w = words.get(index);
        return w;
    }

    //保存到文件里
    public void saveToFile(String path){
        try(BufferedWriter bw=new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8))){
            for(Word w:words){
                String line=w.getEnglish()+","+w.getChinese()+","+
                        w.getRightCount()+","+w.getWrongCount();
                bw.write(line);
                bw.newLine();
            }
            System.out.println("✅已保存"+words.size()+"个单词。");
        }catch (IOException e){
            System.out.println("❌保存失败："+e.getMessage());
        }
    }

    //读取文件
    public void loadFromFile(String path){
        File file=new File(path);
        if(!file.exists()){
            System.out.println("没有找到存档文件！");
            return;
        }
        try(BufferedReader br=new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))){
            String line;
            while((line=br.readLine())!=null){
                String[] part=line.split(",");
                String englishWord=part[0];
                String chineseWord=part[1];
                int rightCount=Integer.parseInt(part[2]);
                int wrongCount=Integer.parseInt(part[3]);
                Word word=new Word(englishWord,chineseWord,rightCount,wrongCount);
                words.add(word);
            }
        }catch(IOException e){
            System.out.println("❌读取失败："+e.getMessage());
        }catch(NumberFormatException|ArrayIndexOutOfBoundsException e){
            System.out.println("⚠文件格式错误！");
        }
    }


    /*
    //测试
    public static void main(String[] args) {
        WordBook wordBook=new WordBook();
        Word w1=new Word("apple","苹果");
        Word w2=new Word("banana","香蕉");
        Word w3=new Word("orange","橙子");
        wordBook.addWord(w1);
        wordBook.addWord(w2);
        wordBook.addWord(w3);

        if (!wordBook.isEmpty()) {
            System.out.println(wordBook.getAllWords());
        }

        System.out.println(wordBook.randomWord());
        System.out.println(wordBook.randomWord());
        System.out.println(wordBook.randomWord());
        System.out.println(wordBook.randomWord());
        System.out.println(wordBook.randomWord());
        System.out.println(wordBook.randomWord());

    }*/
}
