package alexporechny.collocation;

import alexporechny.outInfo.SystemInOut;
import java.util.*;
import alexporechny.partOfSpeech.WordForm;
import alexporechny.partOfSpeech.SNoun;
import alexporechny.partOfSpeech.PAdjective;
import alexporechny.partOfSpeech.HAdverb;

/**
 * @author Alex Porechny alex.porechny@mail.ru
 */
public class CombinationWords {

    private final ArrayList<WordForm> arrWords = new ArrayList<>();
    private final String combWordsOrig;
    private final String combWordsReal;
    private int counter = 1;
    private boolean isPossibleConcept = false;

    public CombinationWords(WordForm prOfSp) {
        this.arrWords.add(prOfSp);
        this.combWordsOrig = prOfSp.getOriginalForm();
        this.combWordsReal = prOfSp.getRealForm();
    }

    public CombinationWords(CombinationWords collocation) {
        for (WordForm prOfSp : collocation.arrWords) {
            arrWords.add(prOfSp);
        }
        this.combWordsOrig = collocation.combWordsOrig;
        this.combWordsReal = collocation.combWordsReal;
    }

    public CombinationWords(CombinationWords collocation, WordForm prOfSp) {
        for (WordForm bufPrOfSp : collocation.arrWords) {
            arrWords.add(bufPrOfSp);
        }
        this.arrWords.add(prOfSp);
        this.combWordsOrig = prOfSp.getOriginalForm() + " " + collocation.combWordsOrig;
        this.combWordsReal = prOfSp.getRealForm() + " " + collocation.combWordsReal;
    }
//провка является ли словосочетание возможным понятием

    private static boolean isPossibleNotion(CombinationWords cw) {
        boolean isFirst;
        boolean haveS_Noun = false;
        for (WordForm prOfSp : cw.arrWords) {
            isFirst = true;
            switch (prOfSp.getType()) {
                case 1:
                    if (!((SNoun) prOfSp).getIsPronoun()) {
                        haveS_Noun = true;
                        break;
                    }
                    isFirst = false;
                case 5:
                    if (isFirst && !((PAdjective) prOfSp).getIsPronoun()) {
                        break;
                    }
                    isFirst = false;
                case 15:
                    if (isFirst && !((HAdverb) prOfSp).getIsPronoun()) {
                        break;
                    }
                case 6:
                case 61:
                case 7:
                case 72:
                    return false;
            }

        }
        if (cw.arrWords.get(cw.arrWords.size() - 1).getType() == 17 || cw.arrWords.get(0).getType() == 17) {
            return false;
        }

        if (haveS_Noun) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        CombinationWords ue = (CombinationWords) obj;
        return this.hashCode() == ue.hashCode() || combWordsOrig.equals(ue.combWordsOrig);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.combWordsOrig);
        return hash;
    }

    public void print() {
        SystemInOut.println(new String(new StringBuilder().append(" кол. повторов: ").append(counter).append(" : ").append(combWordsOrig).append(" :: ").append(combWordsReal)));
    }

    public int sizeArrWord() {
        return arrWords.size();
    }

    public String getCombWordsOrig() {
        return combWordsOrig;
    }

    /**
     * увеличение счестчика
     */
    public void upCounter() {
        counter++;
    }

    /**
     * @return значение счестчика
     */
    public int getCounter() {
        return counter;
    }

    /**
     * @return если true - это словосочетание возможно понятие иначе false
     */
    public boolean getIsPossibleNotion() {
        if (!isPossibleConcept) {
            isPossibleConcept = isPossibleNotion(this);
        }
        return isPossibleConcept;
    }
}
