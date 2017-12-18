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

import alexporechny.collocation.CombinationWords;
import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 *
 */
public class SystemInOut {

    private static String folderPath = "";
    private static final boolean IS_PRINT_PROSESS_SENT = true;
    //Контроль применение Алгоритмов
    private static boolean isPrintInfo = false;
    //различные сообщения
    private static boolean isPrintMasseg = true;
    //Контрльная печать промежуточных результаторв предложений предложений
    private static boolean isPrintInterimResult = false;
    //Время затраты работы программы
    private static boolean isPrintProgrammEstimates = true;

    private static String addPathFile(String pathFile){
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        return folderPath + pathFile;
    }

    public static void printInfoAndContrln() {
        if (isPrintInterimResult()) {
            printInfo("\n");
        }
    }

    public static void printInfoln() {
        printInfo("\n");
    }

    public static void printInfoln(String str) {
        printInfo(str + "\n");
    }

    public static void printInfo(String str) {
        if (isPrintInfo() ? isPrintInfo() : isPrintInterimResult()) {
            System.out.print(str);
        }
    }

    public static void setIsPrintInfo(boolean isPrintInf) {
        isPrintInfo = isPrintInf;
    }

    public static void printInterimResultln() {
        printInterimResult("\n");
    }

    public static void printInterimResultln(String str) {
        printInterimResult(str + "\n");
    }

    public static void printInterimResult(String str) {
        if (isPrintInterimResult()) {
            System.out.print(str);
        }
    }

    public static void println() {
        print("\n");
    }

    public static void println(String str) {
        print(str + "\n");
    }

    public static void print(String str) {
        if (isPrintMasseg()) {
            System.out.print(str);
        }
    }

    public static void setIsPrintMasseg(boolean isPrintMass) {
        isPrintMasseg = isPrintMass;
    }

    public static String readFile(String pathFile, boolean isNewLine) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder("");
        String newLine;
        if (isNewLine) {
            newLine = "\r\n";
        } else {
            newLine = " ";
        }
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), "UTF-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            return "Ошибка в чтение файла, проверьте наличие файла и его кодировку (\"UTF-8\")!";
        }
        try {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                sb.append(buffer).append(newLine);
            }
        } catch (IOException ex) {
            return "Ошибка в чтение файла, проверьте наличие файла и его кодировку (\"UTF-8\")!";
        }
        try {
            reader.close();
        } catch (IOException ex) {
        }
        if (sb.length() > 1) {
            return sb.substring(0);
        } else {
            return "Ошибка в чтение файла, проверьте наличие файла и его кодировку (\"UTF-8\")!";
        }
    }

    public static void writeFile(String strOut, String pathFile, boolean isOverwrite, boolean isNewLine) {

        pathFile = addPathFile(pathFile);

        if (!isOverwrite) {
            strOut = readFile(pathFile, isNewLine) + strOut;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathFile), "UTF-8"));
            writer.write(strOut + "\r\n");
            try {
                writer.close();
            } catch (IOException ex) {
            }
        } catch (IOException ex) {
        }
    }

    public static void writeCollToFile(Collection<CombinationWords> s, String pathFile, boolean isFull) {

        pathFile = addPathFile(pathFile);

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathFile), "UTF-8"));
            int i = 1;
            //writer/
            for (CombinationWords cw : s) {
                if (isFull ? true : (cw.getIsPossibleNotion() && cw.getCounter() > 1)) {
                    writer.write(new String(new StringBuilder().append("№").append(i++)
                            .append(" кол. повторов: ").append(cw.getCounter()).append(" ")
                            .append(cw.getCombWordsOrig()).append("\r\n")));
                }
            }
            writer.write("Конец множества словосочетаний!");
            try {
                writer.close();
            } catch (IOException ex) {
            }
        } catch (IOException ex) {
        }
    }

    public static void printProsessSent(String str) {
        if (IS_PRINT_PROSESS_SENT) {
            System.out.print(str);
        } else {
            printInfoln(str);
        }
    }

    public static void printProsessSentln(String str) {
        printProsessSent(str + "\n");
    }

    public static void printProgrammEstimates(String str) {
        if (isPrintProgrammEstimates()) {
            System.out.print(str);
        }
    }

    public static void setIsPrintInterimResult(boolean aIsPrintInterimResult) {
        isPrintInterimResult = aIsPrintInterimResult;
    }

    public static void setIsPrintProgrammEstimates(boolean aIsPrintProgrammEstimates) {
        isPrintProgrammEstimates = aIsPrintProgrammEstimates;
    }

    public static boolean isPrintInfo() {
        return isPrintInfo;
    }

    public static boolean isPrintMasseg() {
        return isPrintMasseg;
    }

    public static boolean isPrintInterimResult() {
        return isPrintInterimResult;
    }

    public static boolean isPrintProgrammEstimates() {
        return isPrintProgrammEstimates;
    }

    public static void setFolderPath (String folderPath){
        SystemInOut.folderPath = folderPath + "/";
        copyFile(folderPath + ".txt");
    }
    public static void copyFile(String path){
        try{
            Files.copy(new File(path).toPath(), new File(addPathFile(path)).toPath());
        }catch (IOException e){
        }
    }
}
