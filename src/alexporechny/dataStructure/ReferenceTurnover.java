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
 package alexporechny.dataStructure;

import alexporechny.outInfo.ProgramAnalysis;
import alexporechny.outInfo.SystemInOut;
import java.util.ArrayList;

/**
 * класс опорны оборот
 * @author Alex Porechny alex.porechny@mail.ru
 */
public class ReferenceTurnover {

    private final ArrayList<Word> arrWord;
    private Word headWord;
    private final Word unionWord;

    /**
     * @param arrWord
     * @param unionWord
     */
    public ReferenceTurnover(ArrayList<Word> arrWord, Word unionWord) {
        this.arrWord = arrWord;
        this.headWord = null;
        this.unionWord = unionWord;
        buildingGraph();
    }

    //применение фильра для устранения неоднозначности
    @SuppressWarnings("empty-statement")
    private void reducingAmbiguity() {

        ArrayList<Word> oneValueWord = new ArrayList<>();
        ArrayList<Word> onePrOfSp = new ArrayList<>();

        //поиск опорный слов и однозначных слов
        for (Word word : getArrWord()) {

            if (word.isOneValue()) {
                oneValueWord.add(0, word);
            } else if (word.isOnaPrOfSp() && !word.isOneValue()) {
                onePrOfSp.add(0, word);
            }
        }

        //поиск предлогов и поиск зависимых для них
        for (Word word : getArrWord()) {
            if (word.getPrOfSp() == 17 && word.isOneValue()) {
                for (int i = getArrWord().indexOf(word); i < getArrWord().size() && !word.сommunicationEstablishment(arrWord.get(i)); i++);
            }
        }

        //нахождение родителей и детей относительно однозначных слов между опорных слов
        for (Word word : oneValueWord) {
            int posOne = getArrWord().indexOf(word);
            int i;

            for (i = 1; i < 5 && getArrWord().size() < posOne + i && (getArrWord().get(posOne + i).isOneValue() ? !arrWord.get(posOne + i).isMainWord() : true) && !arrWord.get(posOne + i).сommunicationEstablishment(word); i++);

            for (i = 1; i < 5 && 0 <= posOne - i && (getArrWord().get(posOne).getFormMainPrOfSp().getType() == getArrWord().get(posOne - i).getPrOfSp() ? true : (getArrWord().get(posOne).isMainWord() ? !arrWord.get(posOne - i).isMainWord()? true : !arrWord.get(posOne - i).isMainWord() : true)); i++) {
                word.сommunicationEstablishment(getArrWord().get(posOne - i));
            }

            for (i = 1; i < 5 && 0 <= posOne - i && (getArrWord().get(posOne - i).isOneValue() ? !arrWord.get(posOne - i).isMainWord() : true) && !arrWord.get(posOne - i).сommunicationEstablishment(word); i++);

            for (i = 1; i < 5 && posOne + i < getArrWord().size() && (getArrWord().get(posOne).isMainWord() ? !arrWord.get(posOne + i).isMainWord() : true); i++) {
                word.сommunicationEstablishment(getArrWord().get(posOne + i));
            }

            if (i < 2 && posOne + i < getArrWord().size() && word.getPrOfSp() == 1 && getArrWord().get(posOne + i).getPrOfSp() == 1) {
                word.сommunicationEstablishment(getArrWord().get(posOne + i));
            }
        }

        //поиск предлогов и поиск зависимых для них
        for (Word word : getArrWord()) {
            if (word.getPrOfSp() == 17 && word.isOnaPrOfSp() && !word.isOneValue()) {
                for (int i = getArrWord().indexOf(word); i < getArrWord().size() && !word.сommunicationEstablishment(arrWord.get(i)); i++);
            }
        }

        //нахождение родителей относительно одноречевых слов между опорных слов
        for (Word word : onePrOfSp) {
            int posOne = getArrWord().indexOf(word);
            //родители могут быть главными словами
            for (int i = 1; i < 5 && 0 <= posOne - i && (getArrWord().get(posOne - i).isOnaPrOfSp() ? (!arrWord.get(posOne - i).isMainWord()) : true) && !arrWord.get(posOne - i).сommunicationEstablishment(word); i++);
            for (int i = 1; i < 5 && getArrWord().size() < posOne + i && (getArrWord().get(posOne + i).isOnaPrOfSp() ? !arrWord.get(posOne + i).isMainWord() : true) && !arrWord.get(posOne + i).сommunicationEstablishment(word); i++);
            /*for (i = 1; i < 5 && 0 <= posOne - i && (arrWord.get(posOne).getFormMainPrOfSp().getPrOfSp() == arrWord.get(posOne - i).getPrOfSp() ? true : (arrWord.get(posOne).isMainWord() ? !arrWord.get(posOne - i).isMainWord() : true)); i++) {
                word.сommunicationEstablishment(arrWord.get(posOne - i));
            }
            for (int i = 1; i < 5 && posOne + i < arrWord.size() && (arrWord.get(posOne).isMainWord() ? !arrWord.get(posOne + i).isMainWord() : true); i++) {
                word.сommunicationEstablishment(arrWord.get(posOne + i));
            }

            if (int i < 2 && posOne + i < arrWord.size() && word.getPrOfSp() == 1 && arrWord.get(posOne + i).getPrOfSp() == 1) {
                word.сommunicationEstablishment(arrWord.get(posOne + i));
            }*/
        }
    }

    //связывание не опоорноных слов в пределах пары опорных слов
    private void bindingNonMainWithMainWord() {

        //нахождение детей относительно опорных слов слов между опорными словами, причем между опорными словами связь не устанавливается
        Word word;
        for (int j = getArrWord().size() - 1; j > -1; j--) {
            word = getArrWord().get(j);
            if (word.isMainWord()) {
                int posMainW = getArrWord().indexOf(word);

                for (int i = 1; i < 5 && i <= posMainW && !getArrWord().get(posMainW - i).isMainWord(); i++) {
                    word.сommunicationEstablishment(getArrWord().get(posMainW - i));
                }
                for (int i = 1; i < 5 && posMainW + i < getArrWord().size() && !getArrWord().get(posMainW + i).isMainWord(); i++) {
                    word.сommunicationEstablishment(getArrWord().get(posMainW + i));
                }
                for (int i = 1; i < 5 && i <= posMainW && !getArrWord().get(posMainW - i).isMainWord(); i++) {
                    if (getArrWord().get(posMainW - i).isEmptyArrControlling()) {
                        word.addControlled(getArrWord().get(posMainW - i));
                    }
                }
            }
        }
    }

    //связывание опороных слов между собою
    private void bindingMainWord() {
        //нахождение всех опорных слов неимеющих родителей
        ArrayList<Word> mainWords = new ArrayList<>();
        for (Word word : getArrWord()) {
            if (word.isMainWord() && word.isEmptyArrControlling()) {
                mainWords.add(word);
            }
        }

        //связывание опороных слов между собою
        if (mainWords.size() > 1) {
            for (int i = 1; i < mainWords.size(); i++) {
                for (int j = 0; j < i; j++) {
                    if (mainWords.get(i).сommunicationEstablishment(mainWords.get(j))) {
                        //printRelations(mainWords.get(i + 1).getRealForm(), mainWords.get(i).getRealForm());
                        mainWords.remove(j);
                        j--;
                        i--;
                    } else if (mainWords.get(j).сommunicationEstablishment(mainWords.get(i))) {
                        //printRelations(mainWords.get(i).getRealForm(), mainWords.get(i + 1).getRealForm());
                        mainWords.remove(i);
                        j--;
                        i--;
                    }
                }
            }
        }
    }

    //поиск главное слова опорного оборота
    private void searchMainWordTurn() {
        //нахождение всех опорных слов неимеющих родителей.
        ArrayList<Word> mainWords = new ArrayList<>();
        for (Word word : getArrWord()) {
            if (word.isMainWord() && word.isEmptyArrControlling()) {
                mainWords.add(word);
            }
        }

        //поиск главное слова опорного оборота
        Word mainWordOne;
        if (mainWords.size() > 1) {
            ArrayList<Word> mainVerb = new ArrayList<>();
            ArrayList<Word> mainP = new ArrayList<>();
            for (Word word : mainWords) {
                if (word.getPrOfSp() == 7 || word.getPrOfSp() == 61 || word.getPrOfSp() == 7 || word.getPrOfSp() == 71 || word.getPrOfSp() == 72 || word.getPrOfSp() == 73) {
                    mainVerb.add(word);
                }
                if (word.getPrOfSp() == 251 || word.getPrOfSp() == 252) {
                    mainP.add(word);
                }
            }
            if (mainVerb.size() >= 1) {
                mainWordOne = mainVerb.get(0);
            } else if (mainP.size() >= 1) {
                mainWordOne = mainP.get(0);
            } else {
                SystemInOut.printInterimResult("Появилась неоднозначность в предложении: ");
                printAllWordWithDesignation();
                mainWordOne = mainWords.get(0);
                SystemInOut.printInterimResult("Главное слово опорного оборота стало первое опорное слово без главного слова.");
            }
        } else {
            if (mainWords.size() == 1) {
                mainWordOne = mainWords.get(0);
            } else {
                SystemInOut.printInterimResult("Появилась неоднозначность в предложении: ");
                printAllWordWithDesignation();
                mainWordOne = new Word("Главное слово оборота не удалось найти");
                SystemInOut.printInterimResult("Главное слово опорного оборота стало первое опорное слово.");
            }
        }
        headWord = mainWordOne;
    }

    //нахождение всех опорных слов неимеющих родителей.
    private void bindingOfUnrelatedWords() {
        //нахождение всех опорных слов неимеющих родителей.
        ArrayList<Word> mainWords = new ArrayList<>();
        for (Word word : getArrWord()) {
            if (word.isEmptyArrControlling()) {
                mainWords.add(word);
            }
        }
        //если остались слова без родителя все причисляются главному слову опорного оборота
        for (int i = 0; i < mainWords.size(); i++) {
            if (headWord != mainWords.get(i)) {
                headWord.addControlled(mainWords.get(i));
                mainWords.remove(mainWords.get(i));
                i--;
            }
        }
    }

    //построение графа зависимости
    private void buildingGraph() {

        //работа фильтра
        printAllWordWithDesignation();
        ProgramAnalysis.addSingleWordsBeforeFilter(getArrWord());
        long startTime = System.nanoTime();
        this.reducingAmbiguity();
        long finishTime = System.nanoTime();
        ProgramAnalysis.addTimeWorkFilterNano(finishTime - startTime);
        ProgramAnalysis.addSingleWordsAfterFilter(getArrWord());
        SystemInOut.printInfoln("Фильтр применен для опорного оборота!");
        printAllWordWithDesignation();

        //работа поиска связей
        startTime = System.nanoTime();
        this.bindingNonMainWithMainWord();
        this.bindingMainWord();
        this.searchMainWordTurn();
        this.bindingOfUnrelatedWords();
        finishTime = System.nanoTime();
        ProgramAnalysis.addTimeWordsTyingNano(finishTime - startTime);
        SystemInOut.printInfoln("Установка связи в пределах опорного завершена!");
        printAllWordWithDesignation();
    }

    /**
     * вывод на печать всех слов опорного оборота с использование доп.символами: ! - слово
     * содержит словоформу принадлежащую к опорной части речи * - у слова
     * однозначно определена словоформа & - у слова однозначно определена часть
     * речи ^ - у слова есть управляющее слово _ - у слова есть управляемое
     * слово
     */
    public void printAllWordWithDesignation() {
        for (Word word : getArrWord()) {
            if (word.isMainWord()) {
                SystemInOut.printInterimResult("!");
            }
            if (word.isOneValue()) {
                SystemInOut.printInterimResult("*");
            } else if (word.isOnaPrOfSp()) {
                SystemInOut.printInterimResult("&");
            }
            if (!word.isEmptyArrControlling()) {
                SystemInOut.printInterimResult("^");
            }
            if (word.isNotEmptyArrControlled()) {
                SystemInOut.printInterimResult("_");
            }
            word.printRealForm();
        }
        SystemInOut.printInterimResultln();
    }

    /**
     * вывод на печать всех слов опорного оборота
     */
    public void printAllWord() {
        for (Word word : getArrWord()) {
            word.printAllWordForm();
        }
        SystemInOut.println();
    }

    /**
     * вывод на печать всех слов опорного оборота с их зависимыми словами
     */
    public void printAllWordWithDependentWord() {
        for (Word word : getArrWord()) {
            word.printWordWithControlled();
            SystemInOut.println();
        }
    }

    /**
     * @return возвращает главное слова опорого оборота
     */
    public Word getHeadWord() {
        return headWord;
    }

    public boolean isEmptyArrWord() {
        return getArrWord().isEmpty();
    }

    /**
     * @return провека содержит ли опорный оборот союзное слово
     */
    public boolean haveUnionWord() {
        return unionWord.isUnion();
    }

    public Word getUnionWord() {
        return unionWord;
    }

    public ArrayList<Word> getArrWord() {
        return arrWord;
    }
}
