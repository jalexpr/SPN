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

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//import java.lang.reflect.*;
    
/**
 * 
 * Simple custom class loader implementation
 * 
 */
public class CustomClassLoader extends ClassLoader {

    /**
     * The HashMap where the classes will be cached
    */
    private Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

    @Override
    public String toString() {
        return CustomClassLoader.class.getName();
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        if (classes.containsKey(name)) {
            return classes.get(name);
        }

        byte[] classData;

        try {
            classData = loadClassData(name);
        } catch (IOException e) {
            throw new ClassNotFoundException("Class [" + name
                    + "] could not be found", e);
        }

        Class<?> c = defineClass(name, classData, 0, classData.length);
        resolveClass(c);
        classes.put(name, c);

        return c;
    }

    /**
     * Load the class file into byte array
     * 
     * @param name
     *            The name of the class e.g. com.codeslices.test.TestClass}
     * @return The class file as byte array
     * @throws IOException
     */
    private byte[] loadClassData(String name) throws IOException {
        BufferedInputStream in = new BufferedInputStream(
                ClassLoader.getSystemResourceAsStream(name.replace(".", "/")
                        + ".class"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int i;

        while ((i = in.read()) != -1) {
            out.write(i);
        }

        in.close();
        byte[] classData = out.toByteArray();
        out.close();

        return classData;
    }

    /**
     * Simple usage of the CustomClassLoader implementation
     * 
     * @param args
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     */
    /*public static void main(String[] args) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            NoSuchMethodException, SecurityException, IllegalArgumentException,
            InvocationTargetException
    {
        CustomClassLoader loader = new CustomClassLoader();
        // This class should be in your application class path
        Class<?> c = loader.findClass("MorfSdkJ.MorfSdkJava");
        Object o = new Object();
        Method m = c.getMethod("toString");
        System.out.println(m.invoke(o));
    }*/
}