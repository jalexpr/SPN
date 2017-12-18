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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MorfSdkJava {
	
    static int counter = 0;
    private CustomClassLoader cl;
    Class ca;
    Object o;
    Method m;
	
    private void restartSdk() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        m = null;
        ca = null;
        o = null;
        cl = null;
        System.gc();
        System.gc();
        cl = new CustomClassLoader();
        ca = cl.findClass("MorfSdkJ.MorfSdk");
        o = ca.newInstance();
        counter = 0;
    }
    private void startSdk() {
        try {
            cl = new CustomClassLoader();
            ca = cl.findClass("MorfSdkJ.MorfSdk");
            o = ca.newInstance();
            counter = 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void incrementUsageCounter()  {
        if (++counter > 1000) {
            try {
                restartSdk();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public MorfSdkJava() {
        MorfSdkJava.counter = 0;
        startSdk();
    }
    @Override
    protected void finalize() throws Throwable{
        m = null;
        ca = null;
        o = null;
        cl = null;
    }
    
    public void StartJ(String msg1,String msg2,String msg3) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("StartJ", String.class, String.class, String.class);
            m.invoke(o, msg1, msg2, msg3); 
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int GrafAnalysJ(String textStrForAnalisys) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GrafAnalysJ", String.class);
            return (Integer)m.invoke(o, textStrForAnalisys); 
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int GrafAnalysFileJ(String filename) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GrafAnalysFileJ", String.class);
            return (Integer)m.invoke(o, filename); 
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
        
    public int NormolWordByNomJ(int wordNumber,int numberOfForm) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("NormolWordByNomJ",int.class,int.class);
            int k = (Integer)m.invoke(o, wordNumber, numberOfForm); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
      
    public int NormolWordJ(String word) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("NormolWordJ",String.class);
            int k = (Integer)m.invoke(o,word); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public String DivWordJ(String word) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("DivWordJ",String.class);
            return (String)m.invoke(o,word);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public int GetColSentsJ() {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetColSentsJ");
            int k = (Integer)m.invoke(o); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public String GetWordJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("DivWordJ",int.class);
            return (String)m.invoke(o,i);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public int GetIDJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetIDJ", int.class);
            int k = (Integer)m.invoke(o, i); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public String GetMainFormJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetMainFormJ",int.class);
            return (String)m.invoke(o,i);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public int GetTypeJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetTypeJ", int.class);
            int k = (Integer)m.invoke(o, i); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public String GetSentTextJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetSentTextJ",int.class);
            return (String)m.invoke(o,i);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private int runIntToInt (String funcName, int val) {
        try {
            incrementUsageCounter();
            m = ca.getMethod(funcName,int.class);
            return (int)m.invoke(o,val);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;    
    }
            

    public int GetRodJ(int i) {
        return runIntToInt ("GetRodJ", i);
    }
    public int GetChisloJ(int i) {
        return runIntToInt ("GetChisloJ", i);
    }
    public int GetPadezhJ(int i) {
        return runIntToInt ("GetPadezhJ", i);
    }
    public int GetOdushJ(int i) {
        return runIntToInt ("GetOdushJ", i);
    }
    public int GetPrilJ(int i) {
        return runIntToInt ("GetPrilJ", i);
    }
    public int GetVremJ(int i) {
        return runIntToInt ("GetVremJ", i);
    }
    public int GetGlagJ(int i) {
        return runIntToInt ("GetGlagJ", i);
    }
    public int GetLicoJ(int i) {
        return runIntToInt ("GetLicoJ", i);
    }
    public int GetDeistJ(int i) {
        return runIntToInt ("GetDeistJ", i);
    }
    public int GetVidJ(int i) {
        return runIntToInt ("GetVidJ", i);
    }
    public int GetPerehJ(int i) {
        return runIntToInt ("GetPerehJ", i);
    }
    public int GetProchJ(int i) {
        return runIntToInt ("GetProchJ", i);
    }
    public int GetProch2J(int i) {
        return runIntToInt ("GetProch2J", i);
    }

    /*public static void main(String[] args) throws IOException, Throwable {
        MorfSdkJava morf = MorfSdkJavaControl.getMorfSdkJava();

  	for(int i = 0; i < 2000000; i++){
            morf.NormolWordJ("Арбуз");
            System.out.println(morf.NormolWordJ("Арбуз") + " " + (i+1));
        }
    }*/
}