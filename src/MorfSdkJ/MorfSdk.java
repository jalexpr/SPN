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
 package MorfSdkJ;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MorfSdk {

    private native void Start(String msg1, String msg2, String msg3);
    private native int GrafAnalys(String textStrForAnalisys);
    private native int GrafAnalysFile(String Filename);
    private native int NormolWordByNom(int i, int i1);
    private native int NormolWord(String word);
    private native String DivWord(String word);
    private native int GetColSents();
    private native String GetWord(int i);
    private native int GetID(int i);
    private native String GetMainForm(int i);
    private native int GetType(int i);
    private native String GetSentText(int i);
    private native int GetRod(int i);
    private native int GetChislo(int i);
    private native int GetPadezh(int i);
    private native int GetOdush(int i);
    private native int GetPril(int i);
    private native int GetVrem(int i);
    private native int GetGlag(int i);
    private native int GetLico(int i);
    private native int GetDeist(int i);
    private native int GetVid(int i);
    private native int GetPereh(int i);
    private native int GetProch(int i);
    private native int GetProch2(int i);
    private native int Uninitialize();
    //private static final String ADDITIONALPATH = "MorfSdkJ_MorfSdk/";

    static {
        // Или .load c абсолютным путём или .loadLibrary c относительным (при этом поиск ведется по путям %PATH%
        System.loadLibrary("MorfSdkJ_MorfSdk/MorfSdkToJava");
        //System.load("E:\\MorfSdkForJava2016\\JavaSide\\MorfSdkToJava.dll");
        //System.load("C:\\01\\MorfSdkToJava.dll");
    }

    public void StartJ(String msg1, String msg2, String msg3) {
        Start(msg1, msg2, msg3);
    }

    public int GrafAnalysJ(String textStrForAnalisys) {
        return GrafAnalys(textStrForAnalisys);
    }

    public int GrafAnalysFileJ(String Filename) {
        return GrafAnalysFile(Filename);
    }

    public int NormolWordByNomJ(int i, int i1) {
        return NormolWordByNom(i, i1);
    }

    public int NormolWordJ(String word) {
        int k = NormolWord(word);
        return k;
    }

    public String DivWordJ(String word) {
        return DivWord(word);
    }

    public int GetColSentsJ() {
        return GetColSents();
    }

    public String GetWordJ(int i) {
        return GetWord(i);
    }

    public int GetIDJ(int i) {
        return GetID(i);
    }

    public String GetMainFormJ(int i) {
        return decode(GetMainForm(i));
    }

    public int GetTypeJ(int i) {
        return GetType(i);
    }

    public String GetSentTextJ(int i) {
        return GetSentText(i);
    }

    public int GetRodJ(int i) {
        return GetRod(i);
    }

    public int GetChisloJ(int i) {
        return GetChislo(i);
    }

    public int GetPadezhJ(int i) {
        return GetPadezh(i);
    }

    public int GetOdushJ(int i) {
        return GetOdush(i);
    }

    public int GetPrilJ(int i) {
        return GetPril(i);
    }

    public int GetVremJ(int i) {
        return GetVrem(i);
    }

    public int GetGlagJ(int i) {
        return GetGlag(i);
    }

    public int GetLicoJ(int i) {
        return GetLico(i);
    }

    public int GetDeistJ(int i) {
        return GetDeist(i);
    }

    public int GetVidJ(int i) {
        return GetVid(i);
    }

    public int GetPerehJ(int i) {
        return GetPereh(i);
    }

    public int GetProchJ(int i) {
        return GetProch(i);
    }

    public int GetProch2J(int i) {
        return GetProch2(i);
    }

    public int UninitializeJ() {
        return Uninitialize();
    }

    public String decode(String word) {
        byte[] input = null;
        try {
            input = word.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(MorfSdk.class.getName()).log(Level.SEVERE, null, ex);
        }
        char[] output = new char[input.length / 2];
        for (int i = 1; i < input.length; i += 2) {
            output[i / 2] = (char) ('а' + input[i] + 96);
        }
        String retVal = String.valueOf(output);
        return retVal;
    }
    /*
 	public static String decode(byte[] input){
		char[] output=new char[input.length/2];
		for (int i=1;i<input.length;i+=2)
			output[i/2]= (char) ('Р°'+input[i]+96);
		String retVal = String.valueOf(output);
		return retVal;
	}
     */

}
