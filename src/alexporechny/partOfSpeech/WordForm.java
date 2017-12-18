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
 package alexporechny.partOfSpeech;

import alexporechny.outInfo.SystemInOut;

/**
 * Абстрактный класс словоформы
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public abstract class WordForm {

    protected String namePtOfSp;
    protected int indexPtOfSp;
    protected int myIndex;
    protected int intRod;
    protected int intPad;
    protected int intChis;
    protected String originalForm;
    protected String realForm;

    public WordForm(int myIndex, int intRod, int intChis) {
        this(myIndex, intRod, 0, intChis);
    }

    public WordForm(int myIndex, int intRod, int intPad, int intChis) {
        this.intRod = intRod;
        this.intPad = intPad;
        this.intChis = intChis;
    }

    protected abstract void initialization();

    /**
     * задаются правила по котором ищется связь и если она была найдена
     * возвращает true
     *
     * @param child - проверка установления связи
     * @return возвращает true - если связь установлена, false - если связь не
     * установлена
     */
    public abstract boolean isRelationsSwitch(WordForm child);

    /**
     * @return возвращает true - если словоформа принадлежит к множеству опорных
     * класов, иначе false
     */
    public abstract boolean isMainPS();

    protected void printMessageln(String str) {
        printMessage(str + "\n");
    }

    protected void printMessage(String str) {
        SystemInOut.print(str);
    }

    /**
     * @return возвращает название части речи словоформы
     */
    public String getNamePtOfSp() {
        return namePtOfSp;
    }

    /**
     * @return возвращает название номер части речи: 0 - неопределено, 1 -
     * существительное, 5 - прилагательное, 6 - Безличный глагол, 61 -
     * Инфинитив, 7 - личный глагол, 71 - Краткое прилагательное, 72 - Глагол
     * прошедшего времени, 73 - Краткое причастие, 15 - наречия, 17 - предлог.
     * 251 - причастие, 26 - деепричастие.
     */
    public int getType() {
        return indexPtOfSp;
    }

    /**
     * @return возвращает: 0 - неопределено, 1 - именительный, 2 - родительный,
     * 3 - дательный, 4 - винительный. 5 - творительный. 6 - предложный. 6 -
     * звательный (боже).
     *
     */
    public int getPad() {
        return intPad;
    }

    /**
     * @return возвращает 0 - неопределено,1 - единственное,2 - множественное.
     */
    public int getChis() {
        return intChis;
    }

    /**
     * @return возвращает 0 - неопределено,1 - мужской, 2 - женский, 3 -
     * средний, 4 - мужской/женский, 5 - pluralia tantum.
     */
    public int getRod() {
        return intRod;
    }

    public void printAllInfo() {
        SystemInOut.println("namePtOfSp=" + namePtOfSp + " Rod=" + intRod + " Pad=" + intPad + " Chis=" + intChis);
    }

    /**
     * @param originalForm - слово в начальной форме,
     * @param realForm - слова такое как есть в тексте.
     */
    public void setOrigAndRealForm(String originalForm, String realForm) {
        this.realForm = realForm;
        this.originalForm = originalForm;
    }

    /**
     * @return - слово в начальной форме
     */
    public String getOriginalForm() {
        return originalForm;
    }

    /**
     * @return - слова такое как есть в тексте
     */
    public String getRealForm() {
        return realForm;
    }
}
