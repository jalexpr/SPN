/*
 * Copyright (C) 2017  Alexander Porechny alex.porechny@mail.ru
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (CC BY-SA 3.0) as published by the Creative Commons.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Attribution-NonCommercial-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * for more details.
 *
 * You should have received a copy of the Attribution-NonCommercial-ShareAlike
 * 3.0 Unported (CC BY-SA 3.0) along with this program.
 * If not, see <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>
 *
 * Thanks to Sergey Politsyn and Katherine Politsyn for their help in the development of the library.
 *
 *
 * Copyright (C) 2017 Александр Поречный alex.porechny@mail.ru
 *
 * Эта программа свободного ПО: Вы можете распространять и / или изменять ее
 * в соответствии с условиями Attribution-NonCommercial-ShareAlike 3.0 Unported
 * (CC BY-SA 3.0), опубликованными Creative Commons.
 *
 * Эта программа распространяется в надежде, что она будет полезна,
 * но БЕЗ КАКИХ-ЛИБО ГАРАНТИЙ; без подразумеваемой гарантии
 * КОММЕРЧЕСКАЯ ПРИГОДНОСТЬ ИЛИ ПРИГОДНОСТЬ ДЛЯ ОПРЕДЕЛЕННОЙ ЦЕЛИ.
 * См. Attribution-NonCommercial-ShareAlike 3.0 Unported (CC BY-SA 3.0)
 * для более подробной информации.
 *
 * Вы должны были получить копию Attribution-NonCommercial-ShareAlike 3.0
 * Unported (CC BY-SA 3.0) вместе с этой программой.
 * Если нет, см. <https://creativecommons.org/licenses/by-nc-sa/3.0/legalcode>
 *
 * Благодарим Сергея и Екатерину Полицыных за оказание помощи в разработке библиотеки.
 */

package alexporechny.collocation;

import alexporechny.outInfo.SystemInOut;
import alexporechny.partOfSpeech.HAdverb;
import alexporechny.partOfSpeech.PAdjective;
import alexporechny.partOfSpeech.SNoun;
import alexporechny.partOfSpeech.WordForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex Porechny alex.porechny@mail.ru
 */
public class CombinationWords {

    private final ArrayList<WordForm> arrWords = new ArrayList<>();
    private final String combWordsOrig;
    private final String combWordsReal;
    private int counter = 1;
    private boolean isPossibleConcept = false;
    private final List<Integer> numbersOfSentences = new ArrayList<>();

    public CombinationWords(WordForm prOfSp, int numbersOfSentences) {
        this.arrWords.add(prOfSp);
        this.combWordsOrig = prOfSp.getOriginalForm();
        this.combWordsReal = prOfSp.getRealForm();
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
        SystemInOut.println(this.toString());
    }

    @Override
    public String toString() {
        return new String(new StringBuilder().append(" кол. повторов: ").append(counter).append(" ").append(getNumbersOfSentences()).append(" : ").append(combWordsOrig).append(" :: ").append(combWordsReal));
    }

    public int sizeArrWord() {
        return arrWords.size();
    }

    public String getCombWordsOrig() {
        return combWordsOrig;
    }

    /**
     * увеличение счестчика
     * @param numbersOfSentence
     */
    public void upCounterAndAddNumberSentence(int numbersOfSentence) {
        counter++;
        this.numbersOfSentences.add(numbersOfSentence);
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

    public List<Integer> getNumbersOfSentences() {
        return numbersOfSentences;
    }

    public void addNumbersOfSentence(int numbersOfSentence) {
        this.numbersOfSentences.add(numbersOfSentence);
    }

}
