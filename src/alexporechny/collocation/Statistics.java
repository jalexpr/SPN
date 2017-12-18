
package alexporechny.collocation;

import alexporechny.outInfo.ProgramAnalysis;
import alexporechny.outInfo.SystemInOut;
import alexporechny.dataStructure.ReferenceTurnover;
import alexporechny.dataStructure.SentenceGraph;
import alexporechny.dataStructure.Word;
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
    private static Set<CombinationWords> CollocationByWord(Word headWord, boolean isOneLaval) {
        Set<CombinationWords> set = new HashSet<>();
            if (headWord != null) {
                for (WordForm wordForm : headWord.getArrWordForm()) {

                    Set<CombinationWords> bufSet;
                    for (Word wordChild : headWord.getArrControlled()) {
                        bufSet = CollocationByWord(wordChild, false);
                        if (bufSet.isEmpty()) {
                            set.add(new CombinationWords(wordForm));
                        } else {
                            for (CombinationWords buf : bufSet) {
                                CombinationWords bufCW = new CombinationWords(buf, wordForm);
                                set.add(bufCW);
                                MAIN_SET.add(bufCW);
                            }
                        }
                    }
                    if (!isOneLaval) {
                        set.add(new CombinationWords(wordForm));
                    }
                }
            }
        return set;
    }

    //полечение всех словосочитаний вхождение в первый уровень
    private static void CollocationByWord(Word headWord) {
        MAIN_SET.clear();
        CollocationByWord(headWord, true);
    }

    //собираем статистику
    public static ArrayList<CombinationWords> compilationOfStatistics(ArrayList<SentenceGraph> arrSentence) {

        ArrayList<CombinationWords> combWord = new ArrayList<>();
        SystemInOut.printProsessSent("Процесс построения словосочетаний");
        for (SentenceGraph sentence : arrSentence) {
            for (ReferenceTurnover sentenceGraph : sentence.getSenteceGraphs()) {
                if((INDEX_SENTECE_GRAPH  - 1) % 500 == 0){
                    SystemInOut.printProsessSent(".");
                }
                INDEX_SENTECE_GRAPH++;
                long startTime = System.nanoTime();
                CollocationByWord(sentenceGraph.getHeadWord());
                long finishTime = System.nanoTime();
                ProgramAnalysis.addTimeCombWordsNano(finishTime - startTime);
                int buf;
                for (CombinationWords cw : MAIN_SET) {
                    buf = combWord.indexOf(cw);
                    if (buf == -1) {
                        combWord.add(cw);
                    } else {
                        combWord.get(buf).upCounter();
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