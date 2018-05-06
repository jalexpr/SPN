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
 package alexporechny.outInfo;

import alexporechny.dataStructure.Word;

import java.util.ArrayList;

/**
 *
 */
public final class ProgramAnalysis {

    private static long counterFilter = 0;
    private static long timeWorkFilter = 0;

    private static long counterReferenceTurnover = 0;
    private static long counterReferenceTurnoverFinish = 0;
    private static long timeWordsTying = 0;

    private static long counterSentence = 0;
    private static long timeSentence = 0;

    private static long counterMorf = 0;
    private static long timeMorf = 0;

    private static long counterCombWords = 0;
    private static long timeCombWords = 0;

    private static long counterWord = 0;
    private static long counterWordForm = 0;

    private static long timeStart = 0;
    private static long timeFinish = 0;

    private static long singleWordsBeforeFilter = 0;
    private static long singleWordsAfterFilter = 0;

    private static String nameIputeFile;

    private static boolean isFirstWrite = true;

    private static int sizeSingleWords(ArrayList<Word> arrWord) {
        int value = 0;
        for (Word word : arrWord) {
            if (!word.isOneValue() && !word.isOnaPrOfSp()) {
                value++;
            }
        }
        return value;
    }

    public static void setNameIputeFile(String nameIputeFile){
        ProgramAnalysis.nameIputeFile = nameIputeFile;
    }

    public static void addTimeWorkFilterNano(long time) {
        counterFilter++;
        timeWorkFilter += time;
    }

    public static void addTimeWordsTyingNano(long time) {
        counterReferenceTurnover++;
        timeWordsTying += time;
    }

    public static void addTimeSentence(long time) {
        timeSentence += time;
    }

    public static void setCounterSentence(long counter) {
        counterSentence = counter;
    }

    public static void addMorf(long time) {
        counterMorf++;
        timeMorf += time;
    }

    public static void addTimeCombWordsNano(long time) {
        counterCombWords++;
        timeCombWords += time;
    }

    public static void addWord(long counter) {
        counterWord += counter;
    }

    public static void addWordForm(long counter) {
        counterWordForm += counter;
    }

    public static void setTimeStart(long time) {
        timeStart += time;
    }

    public static void setTimeFinish(long time) {
        timeFinish += time;
    }

    public static void addSingleWordsBeforeFilter(ArrayList<Word> arrWord) {
        singleWordsBeforeFilter += sizeSingleWords(arrWord);
    }

    public static void addSingleWordsAfterFilter(ArrayList<Word> arrWord) {
        singleWordsAfterFilter += sizeSingleWords(arrWord);
    }

    public static void printFilterEstimates() {
        String strInfo = new String(new StringBuffer("\r\n|Количество неоднозначных слов до применения фильтра: ")
                .append(singleWordsBeforeFilter)
                .append("\r\n|Количество неоднозначных слов после применения фильтра: ")
                .append(singleWordsAfterFilter));
        SystemInOut.printProgrammEstimates(strInfo);

        SystemInOut.writeFile(strInfo, "workEstimate.txt", isFirstWrite, true);
        isFirstWrite = false;
    }

    public static void addCountReferenceTurnoverFinish(int count) {
        counterReferenceTurnoverFinish += count;
    }

    public static void printBildRTEstimates() {
        String strInfo = new String(new StringBuffer("\r\n| Количество опорных оборотов до связывания: ")
                .append(counterReferenceTurnover)
                .append("\r\n| Количество опорных оборотов после связывания: ")
                .append(counterReferenceTurnoverFinish)
                .append("\r\n"));

        SystemInOut.printProgrammEstimates(strInfo);
        SystemInOut.writeFile(strInfo, "workEstimate.txt", isFirstWrite, true);
        isFirstWrite = false;
    }

    public static void printTimeResultln() {
        StringBuffer str = new StringBuffer("Имя файла: ").append(nameIputeFile)
                .append("\r\nИнфомация о работе инструмента:");
        if (counterSentence < 10) {
            str.append("!!!Из-за особенностей работы JAVA-машины и маленько выборки (меньше 10 предложений), возможны не точности в расчете времени.\n");
        }
        String strTimeInfo = new String(str.append("\r\n| Количество предложений: ").append(counterSentence)
                .append("\r\n| Количество опорных оборотов: ").append(counterReferenceTurnover)
                .append("\r\n| Количество слов: ").append(counterWord)
                .append("\r\n| Количество словоформ: ").append(counterWordForm)
                .append("\r\n| Среднее время морфологической обработки предложения: ").append(timeMorf / counterMorf).append(" мс")
                .append("\r\n| Среднее время работы фильтра: ").append(timeWorkFilter / counterFilter).append(" нс")
                .append("\r\n| Среднее время нахождения связей в опорном обороте: ").append(timeWordsTying / counterReferenceTurnover).append(" нс")
                .append("\r\n| Среднее время обработки предложения: ").append(timeSentence / counterSentence).append(" мс")
                .append("\r\n| Среднее время построения всех возможных комбинаций словосочетаий в предложении: ")
                .append(timeCombWords / counterCombWords).append(" нс")
                .append("\r\n")
                .append("\r\n| Время обработки предложений: ").append(timeSentence).append(" мс")
                .append("\r\n| Dремя морфологической обработки предложений: ").append(timeMorf).append(" мс")
                .append("\r\n| Среднее длинна предложений в словах: ")
                .append(counterWord / counterSentence)
                .append("\r\n| Среднее время получение графов зависимости для каждого предложения с момента загрузки текста в память: ")
                .append((timeFinish - timeStart) / counterSentence).append(" мс")
                .append("\r\n| Полное время работы программы с момента загрузки текста в память: ")
                .append(System.currentTimeMillis() - timeStart).append(" мс\r\n")
                );

        SystemInOut.printProgrammEstimates(strTimeInfo);

        SystemInOut.writeFile(strTimeInfo, "workEstimate.txt", isFirstWrite, true);
        isFirstWrite = false;
    }

    public static void printResultln() {
        printTimeResultln();
        printFilterEstimates();
        printBildRTEstimates();
    }
}
