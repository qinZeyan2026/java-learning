public class Word {
    private String english;
    private String chinese;
    private int rightCount;
    private int wrongCount;

    public Word(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
        this.rightCount = 0;
        this.wrongCount = 0;
    }

    public Word(String englishWord, String chineseWord, int rightCount, int wrongCount) {
        this.english = englishWord;
        this.chinese = chineseWord;
        this.rightCount = rightCount;
        this.wrongCount = wrongCount;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public int getRightCount() {
        return rightCount;
    }

    public void setRightCount(int rightCount) {
        this.rightCount = rightCount;
    }

    public int getWrongCount() {
        return wrongCount;
    }

    public void setWrongCount(int wrongCount) {
        this.wrongCount = wrongCount;
    }

    public String toString(){
        return english+"-"+chinese+"(对"+rightCount+",错"+wrongCount+")";
    }
}
