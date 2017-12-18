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

/**
 * Причастие - ПГ - 251
 *
 * @author Alex Porechny alex.porechny@mail.ru
 */
public final class PgParticiple extends WordForm {

    private final int indexVrem;
    private final boolean intDeist;

    public PgParticiple(int myIndex, int intRod, int intPad, int intChis, int indexVrem, boolean intDeist) {
        super(myIndex, intRod, intPad, intChis);
        this.intDeist = intDeist;
        this.indexVrem = indexVrem;
        initialization();
    }

    @Override
    protected void initialization() {
        namePtOfSp = "Причастие";
        indexPtOfSp = 251;
    }

    @Override
    public boolean isRelationsSwitch(WordForm child) {
        int buf = child.myIndex - myIndex;
        switch (child.indexPtOfSp) {
            //+сущ
            case 1:
                if (isIntDeist()) {
                    return buf > 0 && buf <= 2 && (intChis != child.intChis || intRod != child.intRod);
                } else {
                    return (child.intPad != 1 && child.intPad != 4 && buf > 0 && buf <= 2)// && (intChis != child.intChis || intRod != child.intRod))
                            || (Math.abs(buf) <= 4 && (child.intPad == 1 || child.intPad == 4) && (!((SNoun) child).havePreposition()));
                }
            case 17:
                return buf > 0 && buf < -2;
            //+наречие
            case 13:
            case 14:
            case 15:

            default:
                return false;
        }
    }

    @Override
    public boolean isMainPS() {
        return true;
    }
    
    /**
     * @return Возвращает время глагола: 0 - неопределено 1 - действие 2 -
     * причастие 3 - деепричастие
     */
    public int getIndexVrem() {
        return indexVrem;
    }

    /**
     * @return возвращает true - действительное false - страдательное
     */
    public boolean isIntDeist() {
        return intDeist;
    }
}
