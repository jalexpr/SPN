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

import alexporechny.dataStructure.ReferenceTurnover;
import alexporechny.dataStructure.SentenceGraph;
import alexporechny.dataStructure.Word;
import alexporechny.outInfo.ProgramAnalysis;
import alexporechny.outInfo.SystemInOut;
import alexporechny.partOfSpeech.WordForm;

import java.util.*;

/**
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class Statistics {

    private static int INDEX_SENTECE_GRAPH = 1;
    private static final TreeSet<CombinationWords> MAIN_SET = new TreeSet<>((CombinationWords cw1, CombinationWords cw2)
            -> (cw1 != null && cw2 != null && cw1 != cw2)
                    ? cw1.getCombWordsOrig().compareTo(cw2.getCombWordsOrig()) : 0);

    //полечение всех словосочитаний
    private static Set<CombinationWords> collocationByWord(Word headWord, boolean isOneLaval, int indexNumberSentence) {
        Set<CombinationWords> set = new HashSet<>();
            if (headWord != null) {
                for (WordForm wordForm : headWord.getArrWordForm()) {

                    Set<CombinationWords> bufSet;
                    for (Word wordChild : headWord.getArrControlled()) {
                        bufSet = Statistics.collocationByWord(wordChild, false, indexNumberSentence);
                        if (bufSet.isEmpty()) {
                            set.add(new CombinationWords(wordForm, indexNumberSentence));
                        } else {
                            for (CombinationWords buf : bufSet) {
                                CombinationWords bufCW = new CombinationWords(buf, wordForm);
                                set.add(bufCW);
                                MAIN_SET.add(bufCW);
                            }
                        }
                    }
                    if (!isOneLaval) {
                        set.add(new CombinationWords(wordForm, indexNumberSentence));
                    }
                }
            }
        return set;
    }

    //полечение всех словосочитаний вхождение в первый уровень
    private static void collocationByWord(Word headWord, int indexNumberSentence) {
        MAIN_SET.clear();
        Statistics.collocationByWord(headWord, true, indexNumberSentence);
    }

    //собираем статистику
    public static ArrayList<CombinationWords> compilationOfStatistics(ArrayList<SentenceGraph> arrSentence) {

        ArrayList<CombinationWords> combWord = new ArrayList<>();
        SystemInOut.printProsessSent("Процесс построения словосочетаний");
        for (SentenceGraph sentence : arrSentence) {
            int indexNumberSentence = sentence.getIndexNumber();
            for (ReferenceTurnover sentenceGraph : sentence.getSenteceGraphs()) {
                if((INDEX_SENTECE_GRAPH  - 1) % 500 == 0){
                    SystemInOut.printProsessSent(".");
                }
                INDEX_SENTECE_GRAPH++;
                long startTime = System.nanoTime();
                collocationByWord(sentenceGraph.getHeadWord(), indexNumberSentence);
                long finishTime = System.nanoTime();
                ProgramAnalysis.addTimeCombWordsNano(finishTime - startTime);
                int buf;
                for (CombinationWords cw : MAIN_SET) {
                    buf = combWord.indexOf(cw);
                    if (buf == -1) {
                        cw.addNumbersOfSentence(sentence.getIndexNumber());
                        combWord.add(cw);
                    } else {
                        combWord.get(buf).upCounterAndAddNumberSentence(sentence.getIndexNumber());
                    }
                }
            }
        }

        Collections.sort(combWord, (CombinationWords cw1, CombinationWords cw2)
                -> (cw1 != null && cw2 != null && cw1 != cw2)
                        ? cw2.getCounter() - cw1.getCounter() : 0);

        //SystemPrint.println("_________________________________");
        //printCollection(combWord);
        //printSet(combWord);
        return combWord;
    }

    public static void printCollection(Collection<CombinationWords> s) {
        int i = 1;
        SystemInOut.println();
        for (CombinationWords cw : s) {
            if (cw.getIsPossibleNotion() && cw.getCounter() > 1) {
                SystemInOut.print("№" + i++ + " ");
                cw.print();
            }
        }
        SystemInOut.println("Конец множества словосочетаний!");

        ProgramAnalysis.printResultln();
    }

    public static void printCollectionToFile(Collection<CombinationWords> s) {
        SystemInOut.writeCollToFile(s, "outData.txt", false);
        SystemInOut.writeCollToFile(s, "outFullData.txt", true);
    }
}
