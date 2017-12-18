package alexporechny.dataStructure;

import alexporechny.outInfo.ProgramAnalysis;
import alexporechny.outInfo.SystemInOut;
import java.util.*;

/**
 * Обработка исходного текста, составление графов предложений выделенных из исходного текста
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class ManagingSentenceGraphs {

    private final ArrayList<SentenceGraph> arrSentence;

    public ManagingSentenceGraphs(String inputStr) {
        long startTime = System.currentTimeMillis();
        ProgramAnalysis.setTimeStart(startTime);
        this.arrSentence = separation(inputStr);
        long finishTime = System.currentTimeMillis();
        ProgramAnalysis.addTimeSentence(finishTime - startTime);
        ProgramAnalysis.setCounterSentence(this.arrSentence.size());
    }

    private static ArrayList<SentenceGraph> separation(String inputStr) {

        if (inputStr == null || inputStr.isEmpty() || "".equals(inputStr.replaceAll(" ", ""))) {
            inputStr = "Введена пустая строка!";
        }
        //разбиение на предложения
        ArrayList<SentenceGraph> arrSent = new ArrayList<>();
        //String[] arrInputSentence = inputStr.split("[.!?;\"]+");
        inputStr = inputStr.replaceAll("[{}*]+", ",");
        ArrayList<String> arrInputSentence = new ArrayList<>(Arrays.asList(inputStr.split("[.!?;\"]+")));

        //обработка предложения (убираем лишнии пробелы, склеиваем некоторые случайно разбитые предложения
        for (int i = 0; i < arrInputSentence.size() - 1; i++) {

            String str = arrInputSentence.get(i);
            str = str.replaceAll("[ ]+", " ");
            if (str.charAt(0) == ' ') {
                str = str.substring(1);
            }
            if (str.length() > 1) {
                if (!(str.charAt(0) >= 'А' && str.charAt(0) <= 'Я') && i > 0) {
                    arrInputSentence.set(i - 1, arrInputSentence.get(i - 1) + " " + str);
                    arrInputSentence.remove(i);
                    i--;
                } else {
                    arrInputSentence.set(i, str);
                }
            } else {
                arrInputSentence.remove(i);
                i--;
            }
        }

        int delte = 10;
        if(arrInputSentence.size() > 10000){
            delte = 500;
        }else if(arrInputSentence.size() > 5000){
            delte = 100;
        }
        //получаем графы предложений
        int i = 1;
        for (String bufSentence : arrInputSentence) {
            if (i > 20000) {
                break;
            }
            if (i > 0) {
                if (!SystemInOut.isPrintInterimResult()) {
                    if ((i - 1) % delte == 0) {
                        SystemInOut.printProsessSentln("Запущено построение графов опорных оборотов в диапазоне №" + i + "-" + ((i - 1) + delte));
                    }
                } else {
                    SystemInOut.printProsessSentln("Запущено построение графа/графов предложения №" + i);
                }

                //SystemInOut.printInterimResultln(bufSentence);
                SystemInOut.printInterimResultln(bufSentence);
                arrSent.add(new SentenceGraph(bufSentence));

                if (!SystemInOut.isPrintInterimResult()) {
                    if ((i - 1) % delte == delte - 1) {
                        SystemInOut.printProsessSentln("Построение графов предложений выполнена!\n");
                    }
                } else {
                    SystemInOut.printProsessSentln("Построение графа/графов предложения выполнена!\n");
                }
            }
            i++;
        }
        SystemInOut.printProsessSentln("Построение графов предложений выполнена!\n");
        return arrSent;
    }

    public ArrayList<SentenceGraph> getArrSentence() {
        return arrSentence;
    }

    public SentenceGraph get(int index) {
        return arrSentence.get(index);
    }

    public int size() {
        return arrSentence.size();
    }
}
