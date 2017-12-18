package alexporechny.dataStructure;

import alexporechny.outInfo.SystemInOut;
import alexporechny.partOfSpeech.Union;
import alexporechny.partOfSpeech.WordForm;
import java.util.*;

/**
 * класс слова
 *
 * @author Alex Porechny alex.porechny@mail
 */
public class Word {

    private boolean isMainWord = false;
    private boolean isOnaPrOfSp = false;
    private final int myIndex;
    private int prOfSpWord;
    private final String realForm;
    private ArrayList<WordForm> arrWordForm;
    private final ArrayList<Word> arrControlling;
    private final ArrayList<Word> arrControlled;
    private WordForm formMainPrOfSp;

    public Word(String realForm) {
        this(realForm, -1);
    }

    public Word(String realForm, int myIndex) {
        this.myIndex = myIndex;
        this.arrWordForm = new ArrayList<>();
        this.arrControlling = new ArrayList<>();
        this.arrControlled = new ArrayList<>();
        this.formMainPrOfSp = null;
        this.realForm = realForm;
        this.prOfSpWord = -1;
    }

    public void addWordForm(WordForm wordForm) {
        arrWordForm.add(wordForm);
    }

    public boolean isEmptyArrControlling() {
        return getArrControlling().isEmpty();
    }

    /**
     * вывод на печать исходной формы слова
     */
    public void printRealForm() {
        SystemInOut.printInterimResult(realForm + " ");
    }

    public boolean isMainWord() {
        return isMainWord;
    }

    /**
     * @return провека содерждит ли слово словоформу
     */
    public boolean isUnion() {
        for (WordForm form : arrWordForm) {
            if (form.getType() == 19) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnionSubordinate() {
        for (WordForm form : arrWordForm) {
            if (form.getType() == 19) {
                return ((Union) form).isSubordinate();
            }
        }
        return false;
    }

    public boolean isOneValue() {
        return arrWordForm.size() == 1;
    }

    public boolean isOnaPrOfSp() {
        return isOnaPrOfSp;
    }

    /**
     * @return получение индекса положения слова в предложении
     */
    public int getMyIndex() {
        return myIndex;
    }

    public int getPrOfSp() {
        return prOfSpWord;
    }

    /**
     * @return возвращает исходную форму слова
     */
    public String getRealForm() {
        return realForm;
    }

    public ArrayList<Word> getArrControlled() {
        return arrControlled;
    }

    public ArrayList<Word> getArrControlling() {
        return arrControlling;
    }

    /**
     * @return возвращает главную форму данного слова или же первую словоформу
     * данного слова, если их несколько
     */
    public WordForm getFormMainPrOfSp() {
        if (isOnaPrOfSp) {
            return formMainPrOfSp;
        } else {
            return arrWordForm.get(0);
        }
    }

    /**
     * @param word
     * @return возвращает true - если слова совпадают, false - если слова не
     * совпадают
     */
    public boolean isEqualsWord(Word word) {
        if (isOnaPrOfSp && word.isOnaPrOfSp) {
            return formMainPrOfSp.getType() == word.formMainPrOfSp.getType()
                    && formMainPrOfSp.getChis() == word.formMainPrOfSp.getChis()
                    && formMainPrOfSp.getPad() == word.formMainPrOfSp.getPad()
                    && formMainPrOfSp.getRod() == word.formMainPrOfSp.getRod();
        } else {
            return false;
        }
    }

    /**
     * проверка есть ли форма опорного слова и имеет однозначность
     */
    public void cheakMainOrOnaValueAndOnaPrOfSpWord() {
        isOnaPrOfSp = true;
        for (WordForm prOfSp : arrWordForm) {
            if (prOfSp.isMainPS()) {
                isMainWord = true;
            }
            if (arrWordForm.get(0).getType() != prOfSp.getType()) {
                //isMainWord = false;
                isOnaPrOfSp = false;
                prOfSpWord = -1;
            }
            if (prOfSp.getType() == 17) {
                isMainWord = false;
                isOnaPrOfSp = true;
                prOfSpWord = 17;
                ArrayList<WordForm> al = (ArrayList<WordForm>) arrWordForm.clone();
                for (WordForm prOfSp1 : al) {
                    if (prOfSp1.getType() != 17) {
                        arrWordForm.remove(prOfSp1);
                    }
                }
                break;
            }
        }
        if (arrWordForm.size() == 1) {
            formMainPrOfSp = arrWordForm.get(0);
        }
        if (isOnaPrOfSp) {
            prOfSpWord = arrWordForm.get(0).getType();
        }
    }
    /**
     * установление связи, входное слово является подчинительным или нет.
     * обратной проверки не происходит
     *
     * @param chWord
     * @return
     */
    public boolean сommunicationEstablishment(Word chWord) {
        //добави проверку на наличие родителя возможно стоит удалить
        if (chWord.isEmptyArrControlling() && !chWord.isWordInMyArrControlling(this) && !isWordInMyArrControlling(chWord) && this != chWord) {
            ArrayList<WordForm> bufSuitable = new ArrayList<>();
            ArrayList<WordForm> bufArrWordForm = new ArrayList<>();

            if (formMainPrOfSp != null) {
                if (chWord.isOneValue()) {
                    if (formMainPrOfSp.isRelationsSwitch(chWord.formMainPrOfSp)) {
                        bufSuitable.add(chWord.formMainPrOfSp);
                    }
                } else {
                    for (WordForm prOfSp : chWord.arrWordForm) {
                        if (formMainPrOfSp.isRelationsSwitch(prOfSp)) {
                            bufSuitable.add(prOfSp);
                        }
                    }
                }
            } else {
                for (WordForm prOfSpW : arrWordForm) {
                    if (chWord.isOneValue()) {
                        if (prOfSpW.isRelationsSwitch(chWord.formMainPrOfSp)) {
                            bufSuitable.add(chWord.formMainPrOfSp);
                            bufArrWordForm.add(prOfSpW);
                        }
                    } else {
                        boolean isFirst = true;
                        for (WordForm prOfSp : chWord.arrWordForm) {
                            if (prOfSpW.isRelationsSwitch(prOfSp)) {
                                bufSuitable.add(prOfSp);
                                if(isFirst){
                                    bufArrWordForm.add(prOfSpW);
                                    isFirst = false;
                                }
                            }
                        }
                    }
                }
            }

            switch (bufSuitable.size()) {
                case 0:
                    return false;
                case 1:
                    chWord.arrWordForm = bufSuitable;
                    chWord.formMainPrOfSp = bufSuitable.get(0);
                    chWord.isOnaPrOfSp = true;
                    chWord.prOfSpWord = bufSuitable.get(0).getType();
                    chWord.cheakMainOrOnaValueAndOnaPrOfSpWord();
                    this.addControlled(chWord);
                    if(!bufArrWordForm.isEmpty()){
                        arrWordForm = bufArrWordForm;
                        cheakMainOrOnaValueAndOnaPrOfSpWord();
                    }
                    printRelations(getRealForm(), chWord.getRealForm());
                    return true;
                default:
                    int typeFirst = bufSuitable.get(0).getType();
                    for (WordForm pos : bufSuitable) {
                        if (typeFirst != pos.getType()) {
                            return false;
                        }
                    }
                    chWord.arrWordForm = bufSuitable;
                    chWord.isOnaPrOfSp = true;
                    chWord.prOfSpWord = bufSuitable.get(0).getType();
                    this.addControlled(chWord);
                    if(!bufArrWordForm.isEmpty()){
                        arrWordForm = bufArrWordForm;
                        cheakMainOrOnaValueAndOnaPrOfSpWord();
                    }
                    printRelations(getRealForm(), chWord.getRealForm());
                    return true;
            }
        }
        return false;
    }

    public boolean isWordInMyArrControlling(Word word) {
        for (Word w : getArrControlling()) {
            if (w.equals(word)) {
                return true;
            }
        }
        return false;
    }

    public boolean isWordInMyArrControlled(Word word) {
        for (Word w : getArrControlled()) {
            if (w == word) {
                return true;
            }
        }
        return false;
    }

    public void addControlled(Word child) {
        if (this != child && !isWordInMyArrControlling(child) && !isWordInMyArrControlled(child)
                && !child.isWordInMyArrControlling(this) && !child.isWordInMyArrControlled(this)) {
            if (child.arrControlling.add(this)) {
                this.arrControlled.add(child);
            }
        }
    }

    public void printWordWithControlled() {
        SystemInOut.print(" " + realForm + (formMainPrOfSp != null ? " " + formMainPrOfSp.getType() + " " + formMainPrOfSp.getPad() : "") + (arrControlled.isEmpty() ? ";" : " ->"));
        if (!getArrControlled().isEmpty()) {
            for (Word wordChild : getArrControlled()) {
                wordChild.printWordWithControlled();
            }
        }
    }

    public void printAllWordForm() {
        for (WordForm prOfSp : arrWordForm) {
            SystemInOut.print(realForm + " ");
            prOfSp.printAllInfo();
        }
        SystemInOut.println();
    }

    public ArrayList<WordForm> getArrWordForm() {
        return arrWordForm;
    }

    private void printRelations(String parent, String child) {
        SystemInOut.printInterimResultln(new String(new StringBuilder("%\"")
                .append(parent).append("\" стало главным к слову \"")
                .append(child).append("\"")));
    }

    public boolean isNotEmptyArrControlled() {
        return !getArrControlled().isEmpty();
    }

    //поиск родителя, родителm засчитывается только если подошла одна часть речи (не подходящие формы отбрасываются)
    /*public boolean isMyParent(Word prWord) {
        if (!isWordInMyArrControlling(prWord) && this != prWord) {
            ArrayList<WordForm> bufSuitable = new ArrayList<>();

            if (formMainPrOfSp != null) {
                for (WordForm prOfSp : prWord.arrWordForm) {
                    if (prOfSp.isRelationsSwitch(formMainPrOfSp)) {
                        bufSuitable.add(prOfSp);
                    }
                }
            } else {
                ArrayList<WordForm> bufThisSuit = new ArrayList<>();
                for (WordForm thisPrOfSp : arrWordForm) {
                    for (WordForm prOfSp : prWord.arrWordForm) {
                        if (prOfSp.isRelationsSwitch(thisPrOfSp)) {
                            bufSuitable.add(prOfSp);
                            bufThisSuit.add(thisPrOfSp);
                        }
                    }
                }
                if (!bufSuitable.isEmpty()) {
                    int typeFirst = bufSuitable.get(0).getType();
                    for (WordForm pos : bufSuitable) {
                        if (typeFirst != pos.getType()) {
                            return false;
                        }
                    }
                    arrWordForm = bufThisSuit;
                    cheakMainOrOnaValueAndOnaPrOfSpWord();
                }
            }
            switch (bufSuitable.size()) {
                case 0:
                    return false;
                case 1:
                    prWord.arrWordForm = bufSuitable;
                    prWord.formMainPrOfSp = bufSuitable.get(0);
                    prWord.isOnaPrOfSp = true;
                    prWord.prOfSpWord = bufSuitable.get(0).getType();
                    prWord.cheakMainOrOnaValueAndOnaPrOfSpWord();
                    prWord.addControlled(this);
                    printRelations(prWord.getRealForm(), getRealForm());
                    return true;
                default:
                    int typeFirst = bufSuitable.get(0).getType();
                    for (WordForm pos : bufSuitable) {
                        if (typeFirst != pos.getType()) {
                            return false;
                        }
                    }
                    prWord.arrWordForm = bufSuitable;
                    prWord.isOnaPrOfSp = true;
                    prWord.prOfSpWord = bufSuitable.get(0).getType();
                    prWord.addControlled(this);
                    printRelations(prWord.getRealForm(), getRealForm());
                    return true;
            }
        }
        return false;
    }*/
}
