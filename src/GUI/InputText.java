package GUI;

import alexporechny.collocation.Statistics;
import alexporechny.dataStructure.ReferenceTurnover;
import alexporechny.collocation.CombinationWords;
import alexporechny.dataStructure.ManagingSentenceGraphs;
import alexporechny.outInfo.ProgramAnalysis;
import alexporechny.outInfo.SystemInOut;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class InputText extends JFrame {

    private boolean isLoadingFile = true;
    private int index = 0;
    private ManagingSentenceGraphs managing = null;
    private JScrollPane panelGraph;
    private JPanel panelControl;
    private JPanel panelReadFromFile;
    private JPanel panelReadFromField;
    private JPanel panelInput;
    private PanelSupporting panelTransition;
    private final JTextField textFieldSent = new JTextField();
    private final JCheckBox isPrintInterimResult        = new JCheckBox("Печатать промежуточный результат.                   ");
    private final JCheckBox isPrintMasseg               = new JCheckBox("Печатать пользовательские сообщения.               ");
    private final JCheckBox isPrintInfo                 = new JCheckBox("Печатать сообщение о применение алгоритмов.");
    private final JCheckBox isPrintProgrammEstimates    = new JCheckBox("Печатать информацию о работе инструмента.     ");

    public InputText() {
        initialization();
    }

    public static void main(String[] args) throws IOException {
        InputText gUISentence = new InputText();
    }

    //инициализация графа
    private void initializationGraf(String inputSent) {

        managing = new ManagingSentenceGraphs(inputSent);

        ArrayList<CombinationWords> combWord = Statistics.compilationOfStatistics(managing.getArrSentence());
        ProgramAnalysis.setTimeFinish(System.currentTimeMillis());

        Statistics.printCollectionToFile(combWord);
        Statistics.printCollection(combWord);
    }

    private void initialization() {

        panelInput = new JPanel(new BorderLayout());
        JPanel panelRead = new JPanel(new BorderLayout());
        panelReadFromFile = new JPanel(new BorderLayout());
        panelReadFromField = new JPanel(new BorderLayout());
        JPanel panelRadio = new JPanel(new BorderLayout());
        panelControl = new JPanel(new BorderLayout());

        panelRead.add(panelReadFromFile, BorderLayout.NORTH);
        panelRead.add(panelReadFromField, BorderLayout.SOUTH);
        panelInput.add(panelRadio, BorderLayout.WEST);
        panelInput.add(panelRead, BorderLayout.EAST);
        panelInput.add(getPanelSetting(), BorderLayout.SOUTH);
        panelReadFromField.setVisible(false);

        //Компоненты для панели чтения из файла
        JTextField inputPath = new JTextField("inText.txt                                                    ");
        Button buttonLoading = new Button("Загрузка");
        buttonLoading.addActionListener(new ActLisLoading(inputPath));

        panelReadFromFile.add(new JLabel("Путь к файлу (относительный):"), BorderLayout.WEST);
        panelReadFromFile.add(inputPath, BorderLayout.CENTER);
        panelReadFromFile.add(buttonLoading, BorderLayout.EAST);

        //Компоненты для панели чтения из поля
        JTextField inputText = new JTextField("Взъерошенная ребенком добрая мама мыла раму, опрокинула ведро.");
        Button buttonInput = new Button("Ввод");
        buttonInput.addActionListener(new ActLisLoading(inputText));

        panelReadFromField.add(inputText, BorderLayout.CENTER);
        panelReadFromField.add(buttonInput, BorderLayout.EAST);

        //Компонент переключения
        ButtonGroup group = new ButtonGroup();

        JRadioButton jrb1 = new JRadioButton("Загрузить из файла: ", true);
        JRadioButton jrb2 = new JRadioButton("Ввести текст:        ", false);
        jrb1.addActionListener(new ActLisRadio());
        jrb2.addActionListener(new ActLisRadio());
        group.add(jrb1);
        group.add(jrb2);
        panelRadio.add(jrb1, BorderLayout.NORTH);
        panelRadio.add(jrb2, BorderLayout.SOUTH);

        //Компоненты управления переключения
        Button right = new Button(">");
        right.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                index++;
                graphDrawing();
            }
        });

        Button left = new Button("<");
        left.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                index--;
                graphDrawing();
            }
        });

        panelControl.add(right, BorderLayout.EAST);
        panelControl.add(textFieldSent, BorderLayout.CENTER);
        panelControl.add(left, BorderLayout.WEST);


        setLayout(new BorderLayout());
        add(panelInput, BorderLayout.NORTH);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
    }

    public static String inputeFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                sb.append(buffer);
            }
            try {
                reader.close();
            } catch (IOException ex) {
            }
        } catch (IOException e) {
        }

        return sb.substring(0);
    }

    private class ActLisLoading implements ActionListener {

        private final JTextField inputPathOrText;

        public ActLisLoading(JTextField inputPathOrText) {
            this.inputPathOrText = inputPathOrText;
        }

        @Override
        public void actionPerformed(ActionEvent ae) {

            SystemInOut.setIsPrintInterimResult(isPrintInterimResult.isSelected());
            SystemInOut.setIsPrintMasseg(isPrintMasseg.isSelected());
            SystemInOut.setIsPrintInfo(isPrintInfo.isSelected());
            SystemInOut.setIsPrintProgrammEstimates(isPrintProgrammEstimates.isSelected());

            if(inputPathOrText.getText().contains(".txt")){
                SystemInOut.setFolderPath(inputPathOrText.getText().replace(".txt", ""));
                ProgramAnalysis.setNameIputeFile(inputPathOrText.getText());
            }

            remove(panelInput);

            if (isLoadingFile) {
                initializationGraf(SystemInOut.readFile(inputPathOrText.getText(), false));
            } else {
                initializationGraf(inputPathOrText.getText());
            }

            panelTransition = new PanelSupporting();
            graphDrawing();

            add(panelControl, BorderLayout.NORTH);
            add(panelGraph, BorderLayout.CENTER);
            add(panelTransition, BorderLayout.SOUTH);
            revalidate();
            setSize(1000, 500);
        }
    }

    private class ActLisRadio implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {

            isLoadingFile = ae.getActionCommand().equals("Загрузить из файла: ");
            if (isLoadingFile) {
                panelReadFromFile.setVisible(true);
                panelReadFromField.setVisible(false);
                panelInput.revalidate();
            } else {
                panelReadFromFile.setVisible(false);
                panelReadFromField.setVisible(true);
                panelInput.revalidate();
            }
        }
    }

    private class PanelSupporting extends JPanel {

        private JLabel indexSentence = new JLabel();

        public PanelSupporting() {
            setLayout(new BorderLayout());
            JSpinner nextIndexSent = new JSpinner (new SpinnerNumberModel(index + 1, 1, managing.size(), 1));
            Button bTransition = new Button("перейти");
            bTransition.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    index = (int)nextIndexSent.getValue() - 1;
                    indexSentence.setText(Integer.toString(index));
                    graphDrawing();
                }
            });

            JPanel panelTransition = new JPanel();
            panelTransition.add(indexSentence, BorderLayout.CENTER);
            panelTransition.add(nextIndexSent, BorderLayout.WEST);
            panelTransition.add(bTransition, BorderLayout.EAST);
            add(panelTransition, BorderLayout.CENTER);
            add(getLabelLegend(), BorderLayout.SOUTH);
        }

        public void upIndexSentence() {
            indexSentence.setText("Предложение №" + (index + 1) + " из " + managing.size() + " | перейти на ");
        }
    }

    private void graphDrawing() {

        if(panelGraph != null){
            remove(panelGraph);
            panelGraph.removeAll();
        }

        if (index == managing.size()) {
            index = 0;
        } else {
            if (index < 0) {
                index = managing.size() - 1;
            }
        }

        panelTransition.upIndexSentence();
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        for (ReferenceTurnover sentenceGraph : managing.get(index).getSenteceGraphs()) {
            jPanel.add(new PanelOutSentenceGraph(sentenceGraph));
        }
        panelGraph = new JScrollPane(jPanel);
        panelGraph.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelGraph.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        textFieldSent.setText(managing.get(index).getSenteceString());
        panelGraph.revalidate();
        add(panelGraph);
        revalidate();
        setSize(1000, 500);
    }

    private JPanel getPanelSetting(){
        JPanel panelSetting = new JPanel();
        isPrintInterimResult.setSelected(SystemInOut.isPrintInterimResult());
        isPrintMasseg.setSelected(SystemInOut.isPrintMasseg());
        isPrintInfo.setSelected(SystemInOut.isPrintInfo());
        isPrintProgrammEstimates.setSelected(SystemInOut.isPrintProgrammEstimates());
        panelSetting.setLayout(new BoxLayout(panelSetting, BoxLayout.PAGE_AXIS));
        panelSetting.add(isPrintInterimResult);
        panelSetting.add(isPrintMasseg);
        panelSetting.add(isPrintInfo);
        panelSetting.add(isPrintProgrammEstimates);
        return panelSetting;
    }

    private static JLabel getLabelLegend() {
        String str = "<html><pre>       Легенда обозначений:<br> Если часть речи определена точно:<br>"
                + " Существительные:            зеленный<br>"
                + " Глаголы и отглагольные:     синий<br>"
                + " Причастия и деепричастия:   желый<br>"
                + " Предлоги:                   голубой<br>"
                + " Прилагательные и наречия:   серые<br>"
                + " Остальные:                  белый<br>"
                + " Подпись слова красным цветом, если главное слово не найдено, иначе черным<br>"
                + " Перед словами: !-опорное слово, *-однозначное слово, &-часть речи определена</pre></html>";
        return new JLabel(str);
    }
}
