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

import alexporechny.partOfSpeech.*;
import alexporechny.outInfo.*;
import MorfSdkJ.*;
import java.util.*;

/**
 * Класс содержит в себе графы опорных оборотов
 *
 * @author Alex Porechny alex.porechny@mail
 */
public final class SentenceGraph {

    private final ArrayList<Word> arrWord;
    private final String strSentence;
    private final ArrayList<Word> wordUnion;
    private final ArrayList<Integer> commas;
    private final ArrayList<ReferenceTurnover> sentenceGraphs;
    private static boolean isFirstWriteNotFindWord = true;

    public SentenceGraph(String sentenceInput) {
        this.strSentence = sentenceInput;
        this.commas = new ArrayList<>();
        this.sentenceGraphs = new ArrayList<>();
        this.arrWord = new ArrayList<>();
        this.wordUnion = new ArrayList<>();
        initialization();
    }

    private void initialization() {
        long start = System.currentTimeMillis();
        findWords();
        long finish = System.currentTimeMillis();
        ProgramAnalysis.addMorf(finish - start);

        ArrayList<ReferenceTurnover> arrReferenceTurnover = findReferenceTurnover();
        for (ReferenceTurnover referenceTurnover : arrReferenceTurnover) {
            if ((arrReferenceTurnover.size() - 1 != arrReferenceTurnover.indexOf(referenceTurnover))) {
                SystemInOut.printInfoln();
            }
        }
        sentenceGraphs.removeAll(sentenceGraphs);
        sentenceGraphs.addAll(bindingReferenceTurnover(arrReferenceTurnover));
        int couter = 0;
        for (ReferenceTurnover rt : sentenceGraphs) {
            if (rt.getHeadWord().isEmptyArrControlling()) {
                couter++;
            }
        }
        ProgramAnalysis.addCountReferenceTurnoverFinish(couter);
    }

    //связывание опорных оборотов
    private ArrayList<ReferenceTurnover> bindingReferenceTurnover(final ArrayList<ReferenceTurnover> sentRefTur) {

        /**
         * делаем дупбликат массива, чтобы не портить исходный, причем включаем
         * только те предложения в которых не встретился подчинительный союз
         */
        ArrayList<ReferenceTurnover> unrelated = new ArrayList<>(sentRefTur.size());
        for (ReferenceTurnover temp : sentRefTur) {

            boolean isFlag = true;
            if (temp.getUnionWord() != null ? temp.getUnionWord().isUnionSubordinate() : false) {
                for (int c : commas) {
                    if (temp.getUnionWord().getMyIndex() - c < 4) {
                        isFlag = false;
                    }
                }
            }
            if (isFlag) {
                unrelated.add(temp);
            }
        }
        //если остался 1 или 0 обороторв выходим
        if (unrelated.size() < 2) {
            return sentRefTur;
        }

        for (int i = 0; i < unrelated.size() - 1; i++) {
            if (unrelated.get(i).getHeadWord().сommunicationEstablishment(unrelated.get(i + 1).getHeadWord())) {
                unrelated.remove(unrelated.get(i + 1));
                i--;
            } else if (unrelated.get(i + 1).getHeadWord().сommunicationEstablishment(unrelated.get(i).getHeadWord())) {
                unrelated.remove(unrelated.get(i));
                i--;
            }
        }

        //связывание главного опорнго слова со словами не опорными из соседних оборотов
        for (int i = 0; i < unrelated.size() - 1; i++) {
            boolean isConnected = false;
            for (Word word : unrelated.get(i).getArrWord()) {
                if (word.сommunicationEstablishment(unrelated.get(i + 1).getHeadWord())) {
                    unrelated.remove(unrelated.get(i + 1));
                    i--;
                    isConnected = true;
                    break;
                }
            }
            if (!isConnected && i > 0 && i < unrelated.size() - 2) {
                for (Word word : unrelated.get(i + 1).getArrWord()) {
                    if (!isConnected && i > 0 && i < unrelated.size()
                            && word.сommunicationEstablishment(unrelated.get(i).getHeadWord())) {
                        unrelated.remove(unrelated.get(i));
                        i--;
                    }
                }
            }
        }
        return sentRefTur;
    }

    //Нахождение слов в предложении и определение является-ли слово опорным
    private void findWords() {
        String tempSentence = strSentence.replaceAll("[^а-я^А-Я^,^ ^;^-^\\:{}*]+", " ").replaceAll("[,;\\:{}*]+", " ,");
        String[] arrWordStr = tempSentence.split(" +");

        MorfSdkJava sdk = MorfSdkJavaControl.getMorfSdkJava();

        for (int i = 0; i < arrWordStr.length; i++) {

            if (!arrWordStr[i].equals(",")) {
                int normolWordJ = sdk.NormolWordJ(arrWordStr[i]);
                if (normolWordJ != 0) {
                    Word word = new Word(arrWordStr[i], i);
                    for (int j = 0; j < normolWordJ; j++) {
                        WordForm prOfSpBuf;
                        int g = sdk.GetTypeJ(j);
                        switch (sdk.GetTypeJ(j)) {
                            //существительные
                            case 1:
                            //Аббривеатура, Имя, Отчество, Фамилия делаем существительными
                            case 2:
                            case 3:
                            case 4:
                            case 23:
                                prOfSpBuf = new SNoun(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j), sdk.GetChisloJ(j));
                                break;
                            //Местоимение
                            case 8:
                                prOfSpBuf = new SNoun(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j), sdk.GetChisloJ(j), true);
                                break;
                            //Прилогательные
                            case 5:
                                if (sdk.GetPrilJ(j) == 1) {
                                    prOfSpBuf = new GpkShortParticiple(i, sdk.GetRodJ(j), sdk.GetChisloJ(j));
                                } else {
                                    prOfSpBuf = new PAdjective(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j), sdk.GetChisloJ(j));
                                }
                                break;
                            //Прилогательное-метоимение
                            case 9:
                                prOfSpBuf = new PAdjective(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j), sdk.GetChisloJ(j), true);
                                break;
                            //Глаголы
                            case 6:
                            case 7:
                                switch (sdk.GetGlagJ(j)) {
                                    case 2:
                                        if (sdk.GetPrilJ(j) == 1) {
                                            prOfSpBuf = new GpkShortParticiple(i, sdk.GetRodJ(j), sdk.GetChisloJ(j));
                                        } else {
                                            prOfSpBuf = new PgParticiple(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j), sdk.GetChisloJ(j), sdk.GetVremJ(j), sdk.GetDeistJ(j) == 1);
                                        }
                                        break;
                                    case 3:
                                        prOfSpBuf = new NgAdverbialParticiple(i, sdk.GetRodJ(j), sdk.GetChisloJ(j), sdk.GetVremJ(j));
                                        break;
                                    default:
                                        if (sdk.GetTypeJ(j) == 7) {
                                            prOfSpBuf = new GlPersonalVerb(i, sdk.GetRodJ(j), sdk.GetChisloJ(j), sdk.GetVremJ(j), sdk.GetLicoJ(j));
                                        } else {
                                            switch (sdk.GetVremJ(i)) {
                                                case 1:
                                                    prOfSpBuf = new GiInfinitive(i);
                                                    break;
                                                case 3:
                                                    prOfSpBuf = new GppvPastVerb(i, sdk.GetRodJ(j), sdk.GetChisloJ(j), sdk.GetLicoJ(j));
                                                    break;
                                                default:
                                                    prOfSpBuf = new BezImpersonalVerb(i, sdk.GetRodJ(j), sdk.GetChisloJ(j), sdk.GetVremJ(j));
                                            }
                                        }
                                }
                                break;
                            //Меcтоимение
                            case 10:
                                prOfSpBuf = new HAdverb(i, true);
                                break;
                            //Наречие
                            case 13:
                            case 14:
                            case 15:
                                prOfSpBuf = new HAdverb(i);
                                break;
                            //Предлог
                            case 17:
                                prOfSpBuf = new PPreposition(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j));
                                break;
                            //союз
                            case 19:
                                commas.add(i);
                                prOfSpBuf = new Union(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j), sdk.GetChisloJ(j));
                                wordUnion.add(word);
                                break;
                            default:
                                prOfSpBuf = new Unknown(i, sdk.GetRodJ(j), sdk.GetPadezhJ(j), sdk.GetChisloJ(j));
                                break;
                        }
                        if(prOfSpBuf != null){
                            prOfSpBuf.setOrigAndRealForm(sdk.GetMainFormJ(j), arrWordStr[i]);
                            word.addWordForm(prOfSpBuf);
                        }
                    }
                    word.cheakMainOrOnaValueAndOnaPrOfSpWord();
                    if (!word.getArrWordForm().isEmpty()) {
                        ProgramAnalysis.addWordForm(word.getArrWordForm().size());
                        arrWord.add(word);
                    }
                } else {
                    if (arrWordStr[i].length() > 1) {
                        SystemInOut.printInterimResultln(arrWordStr[i] + " - это слово не найдено в словаре! Возможно допущена орфографическая ошибка!");
                        SystemInOut.writeFile(arrWordStr[i], "notFindWord.txt", isFirstWriteNotFindWord, true);
                        isFirstWriteNotFindWord = false;
                    }
                }
            } else {
                commas.add(i);
            }
        }
        ProgramAnalysis.addWord(arrWord.size());
    }

    //поиск простых предложений и простых оборотов
    private ArrayList<ReferenceTurnover> findReferenceTurnover() {

        ArrayList<ReferenceTurnover> arrSimpSentece = new ArrayList<>();
        ArrayList<Word> mainWords = new ArrayList<>();

        for (Word word : arrWord) {
            if (word.isMainWord()) {
                mainWords.add(word);
            }
        }

        int nextSenrence = 0;
        if (mainWords.size() > 1 && commas.size() > 0) {
            for (int i = 1; i < mainWords.size(); i++) {

                int indexWord = mainWords.get(i).getMyIndex();
                int indexWordBefore = mainWords.get(i - 1).getMyIndex();
                int indexCommas = -1;
                for (int j = 0; j <= commas.size() - 1 ? commas.get(j) < indexWord : false; j++) {
                    indexCommas = commas.get(j);
                }
                if (indexCommas != -1 && indexWord < indexCommas && indexWord > nextSenrence && indexWord > indexCommas
                        && indexWordBefore < nextSenrence ? false : indexWordBefore < indexCommas) {
                    ArrayList<Word> bufWord = new ArrayList<>();
                    Word union = null;
                    for (; nextSenrence < arrWord.size() && arrWord.get(nextSenrence).getMyIndex() < indexCommas; nextSenrence++) {
                        if (!arrWord.get(nextSenrence).isUnion()) {
                            bufWord.add(arrWord.get(nextSenrence));
                        } else if (union == null) {
                            union = arrWord.get(nextSenrence);
                        }
                    }
                    if (bufWord.size() > 0) {
                        arrSimpSentece.add(new ReferenceTurnover(bufWord, union));
                    }
                }
            }
        }
        Word union = null;
        ArrayList<Word> bufWord = new ArrayList<>();
        for (int i = nextSenrence; i < arrWord.size(); i++) {
            if (!arrWord.get(i).isUnion()) {
                bufWord.add(arrWord.get(i));
            } else if (union == null) {
                union = arrWord.get(nextSenrence);
            }
        }
        arrSimpSentece.add(new ReferenceTurnover(bufWord, union));
        return arrSimpSentece;
    }

    /**
     * Возвращение графов предложения
     *
     * @return
     */
    public ArrayList<ReferenceTurnover> getSenteceGraphs() {
        return sentenceGraphs;
    }

    /**
     * Возвращение предложение в строчном представлении
     *
     * @return
     */
    public String getSenteceString() {
        return strSentence;
    }

    /**
     * вывод на печать всех слов предложения с использование доп.символами: ! -
     * слово содержит словоформу принадлежащую к опорной части речи * - у слова
     * однозначно определена словоформа & - у слова однозначно определена часть
     * речи ^ - у слова есть управляющее слово _ - у слова есть управляемое
     * слово
     */
    public void printSentenceWithDesignation() {
        for (ReferenceTurnover sentenceGraph : sentenceGraphs) {
            sentenceGraph.printAllWordWithDesignation();
            SystemInOut.printInterimResultln();
        }
    }

    /**
     * вывод на печать всех слов предложения
     */
    public void printSentence() {
        for (ReferenceTurnover sentenceGraph : sentenceGraphs) {
            sentenceGraph.printAllWord();
            SystemInOut.printInterimResultln();
        }
    }
}
