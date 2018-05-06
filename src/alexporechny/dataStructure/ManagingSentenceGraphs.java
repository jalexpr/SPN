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
import java.util.Arrays;

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
        int indexSentence = 1;
        for (String bufSentence : arrInputSentence) {
            if (indexSentence > 20000) {
                break;
            }
            if (indexSentence > 0) {
                if (!SystemInOut.isPrintInterimResult()) {
                    if ((indexSentence - 1) % delte == 0) {
                        SystemInOut.printProsessSentln("Запущено построение графов опорных оборотов в диапазоне №" + indexSentence + "-" + ((indexSentence - 1) + delte));
                    }
                } else {
                    SystemInOut.printProsessSentln("Запущено построение графа/графов предложения №" + indexSentence);
                }

                //SystemInOut.printInterimResultln(bufSentence);
                SystemInOut.printInterimResultln(bufSentence);
                arrSent.add(new SentenceGraph(bufSentence, indexSentence));

                if (!SystemInOut.isPrintInterimResult()) {
                    if ((indexSentence - 1) % delte == delte - 1) {
                        SystemInOut.printProsessSentln("Построение графов предложений выполнена!\n");
                    }
                } else {
                    SystemInOut.printProsessSentln("Построение графа/графов предложения выполнена!\n");
                }
            }
            indexSentence++;
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
