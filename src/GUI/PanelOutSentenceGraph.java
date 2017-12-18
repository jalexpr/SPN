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
 package GUI;

import alexporechny.dataStructure.Word;
import alexporechny.dataStructure.ReferenceTurnover;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 */
public final class PanelOutSentenceGraph extends JPanel {

    private final ReferenceTurnover sentGraph;

    public PanelOutSentenceGraph(ReferenceTurnover sentGraph) {
        this.sentGraph = sentGraph;
        initialization();
    }

    private void initialization() {
        drawAllSentence();
        setVisible(true);
    }

    private JPanel newGraf(Word headWord) {
        JPanel panel = new JPanel();
        
        panel.setLayout(new BorderLayout());
        panel.add(newButton(headWord), BorderLayout.NORTH);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        for (Word wordChild : headWord.getArrControlled()) {
            p.add(newGraf(wordChild), BorderLayout.CENTER);
        }
        panel.add(p);
        return panel;
    }

    private void drawAllSentence() {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        if(!sentGraph.isEmptyArrWord()){
            jp.add(newGraf(sentGraph.getHeadWord()));
        }else{
            jp.add(new JButton("Не найдено ни одно слово с морфологическим разбором!"));
        }
        add(jp);
    }

    private static JButton newButton(Word word) {
        return newButton(word, 0);
    }

    private static JButton newButton(Word word, int index) {
        JButton button = new JButton(
                (word.isMainWord() ? "!" : "")
                + (word.isOneValue() ? "*"
                : (word.isOnaPrOfSp() ? "&" : ""))
                + word.getRealForm());

        // System.out.println(setn.getClass());
        switch (index) {
            case 1:
                //button.setBackground(Color.YELLOW);
                break;
            case 2:
                //button.setBackground(Color.ORANGE);
                break;
            default:
                switch (word.getPrOfSp()) {
                    case 1:
                        button.setBackground(Color.GREEN);
                        break;
                    case 6:
                    case 61:
                    case 7:
                    case 71:
                    case 72: 
                    case 73:
                        button.setBackground(Color.BLUE);
                        break;
                    case 251:
                    case 252:
                    case 26:
                        button.setBackground(Color.YELLOW);
                        break;
                    case 17:
                        button.setBackground(Color.CYAN);
                        break;
                    case 5:
                    case 15:
                        button.setBackground(Color.LIGHT_GRAY);
                        break;
                    default:
                        button.setBackground(Color.WHITE);
                        break;
                }
        }
        if (word.isEmptyArrControlling()) {
            button.setForeground(Color.RED);
        }
        button.setPreferredSize(new Dimension(150, 20));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFrame jf = new JFrame();
                String str = "<html>"
                        + "Слово: " + word.getRealForm() + "<br>"
                        + "Часть: " + word.getPrOfSp() + "<br>"
                        /*+ "Число: " + word.getChis() + "<br>"
                            + "Падеж: " + word.getPad() + "<br>"
                            + "Род: " + word.getRod()*/ + "</html>";
                JLabel jLabel = new JLabel(str);
                jf.add(jLabel);
                jf.setLocation(MouseInfo.getPointerInfo().getLocation().x + 10, MouseInfo.getPointerInfo().getLocation().y + 10);
                jf.setVisible(true);
                jf.pack();
            }
        });
        return button;
    }
}
