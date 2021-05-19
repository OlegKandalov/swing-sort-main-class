import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    final static Random random = new Random();
    static int createXButtons;
    static boolean flag;
    static long speed = 500;
    static boolean isDescSort;
    static JButton[] arrayOfButton;
    static JPanel jPanel;
    static JPanel sortAndReset;
    static JButton buttonSort;
    static JButton buttonReset;
    static JFrame jFrame;
    static JTextField textInput;

    public static void main(String[] args) {
        getFirstPage();
    }

    public static void getFirstPage() {
        jFrame = getFrame("Intro screen");
        jFrame.setLayout(new GridBagLayout());
        JButton buttonFirstPage = getButtonFirstPage();
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(60, 20));
        buttonFirstPage.addActionListener(e -> {
            if (validationInputFirstPage(textField))
                jFrame.dispose();
            getSecondPage();
        });

        jFrame.add(textField);
        jFrame.add(buttonFirstPage);
        jFrame.setVisible(true);
    }

    public static void getSecondPage() {
        jFrame = getFrame("Sort screen");
        jPanel = new JPanel();
        jPanel.setLayout(new MyLayout());
        sortAndReset = getPanelSecondPage(jFrame);

        jFrame.add(sortAndReset);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        arrayOfButton = new JButton[createXButtons];
        for (int i = 0; i < createXButtons; i++) {
            JButton buttonRandom = getRandomButton(jFrame);
            arrayOfButton[i] = buttonRandom;
            jPanel.add(buttonRandom);
        }
    }

    public static JButton getButtonFirstPage() {
        JButton button = new JButton();
        button.setBounds(200, 100, 100, 75);
        button.setBackground(Color.blue);
        button.setText("Enter");
        button.setFocusable(false);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEtchedBorder());
        return button;
    }

    public static JButton getRandomButton(JFrame jFrame) {
        JButton jButton = new JButton();
        jButton.setFocusable(false);
        jButton.setForeground(Color.WHITE);
        jButton.setBackground(Color.blue);
        jButton.setBorder(BorderFactory.createEtchedBorder());
        int randomNumber = !flag ? random.nextInt(30) + 1 : random.nextInt(100) + 1;
        jButton.setText("" + randomNumber);
        isSmallerThan30(jButton);
        flag = true;
        return jButton;
    }

    public static JButton getSortButton() {
        JButton jButton = new JButton();
        jButton.setBackground(Color.BLACK);
        jButton.setText(" Sort  ");
        jButton.setFocusable(false);
        jButton.setForeground(Color.WHITE);
        jButton.addActionListener(e -> {
            try {
                sorting();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            repaint(arrayOfButton);
            isDescSort = !isDescSort;
            speed = 500;
        });
        return jButton;
    }

    public static JButton getResetButton(JFrame jFrame) {
        JButton jButton = new JButton();
        jButton.setBackground(Color.GREEN);
        jButton.setText("Reset");
        jButton.setFocusable(false);
        jButton.setForeground(Color.WHITE);
        jButton.addActionListener(e -> {
            jFrame.dispose();
            flag = false;
            getFirstPage();
        });
        return jButton;
    }

    public static JPanel getPanelSecondPage(JFrame jFrame) {
        sortAndReset = new JPanel();
        sortAndReset.setLayout(new FlowLayout());
        sortAndReset.setBounds(530, 0, 100, 200);

        buttonSort = getSortButton();
        buttonReset = getResetButton(jFrame);
        textInput = new JTextField();
        textInput.setPreferredSize(new Dimension(70, 25));
        JTextArea jTextArea = new JTextArea("Enter speed show sort (default 0.5 s):"
                , 2, 5);
        jTextArea.setFont(new Font("Serif", Font.BOLD, 12));
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setOpaque(false);
        jTextArea.setEditable(false);

        sortAndReset.add(buttonSort);
        sortAndReset.add(buttonReset);
        sortAndReset.add(jTextArea);
        sortAndReset.add(textInput);
        return sortAndReset;
    }

    private static boolean validationInputFirstPage(JTextField textField) {
        createXButtons = Integer.parseInt(textField.getText());
        return createXButtons >= 0 & createXButtons <= 1000;
    }

    private static void isSmallerThan30(JButton jButton) {
        if (Integer.parseInt(jButton.getText()) > 30) {
            jButton.addActionListener(e -> JOptionPane.showMessageDialog
                    (jPanel, "Please select a value smaller or equals to 30"));
        } else {
            jButton.addActionListener(e -> {
                jFrame.dispose();
                isDescSort = false;
                flag = false;
                createXButtons = Integer.parseInt(jButton.getText());
                getSecondPage();
            });
        }
    }

    public static JFrame getFrame(String title) {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle(title);
        jFrame.setSize(700, 700);
        return jFrame;
    }

    public static void sorting() throws InterruptedException {
        int high = arrayOfButton.length - 1;
        SortClass.quickSort(arrayOfButton, 0, high);
    }

    static class SortClass {
        static int partition(JButton[] array, int low, int high) throws InterruptedException {
            if (!textInput.getText().isBlank()) {
                speed = (long) Double.parseDouble(textInput.getText()) * 1000;
            }

            int pivot = Integer.parseInt(array[high].getText());
            int i = (low - 1);

            if (isDescSort) {
                for (int j = low; j < high; j++) {
                    if (Integer.parseInt(array[j].getText()) <= pivot) {
                        i++;

                        JButton temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
            } else {
                for (int j = low; j < high; j++) {
                    if (Integer.parseInt(array[j].getText()) >= pivot) {
                        i++;

                        JButton temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
            }
            array[i + 1].setBackground(Color.PINK);
            array[high].setForeground(Color.RED);
            jPanel.repaint();
            jPanel.revalidate();
            JButton temp = array[i + 1];
            array[i + 1] = array[high];
            array[high] = temp;

            repaint(array);
            Thread.sleep(speed);

            array[high].setBackground(Color.BLUE);
            array[i + 1].setForeground(Color.WHITE);
            jPanel.repaint();
            jPanel.revalidate();
            return (i + 1);
        }

        public static void quickSort(JButton[] array, int low, int high) throws InterruptedException {
            if (low < high) {
                int pi = partition(array, low, high);
                quickSort(array, low, pi - 1);
                quickSort(array, pi + 1, high);
            }
        }
    }

    public static void repaint(JButton[] array) {
        jPanel.removeAll();
        for (JButton jButton : array) {
            jPanel.add(jButton);
        }
        jFrame.repaint();
        jFrame.revalidate();
        RepaintManager.currentManager(jFrame).paintDirtyRegions();
    }

    static class MyLayout implements LayoutManager {
        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

        @Override
        public void layoutContainer(Container parent) {
            int countOfComponent = 0;
            int x = 20;
            int y = 0;
            int column = 9;

            for (int i = 0; i < parent.getComponentCount(); i++) {
                Component component = parent.getComponent(i);
                component.setBounds(x, y * 30, 45, 20);
                countOfComponent++;
                y++;
                if (countOfComponent > column) {
                    x += 50;
                    column += 10;
                    y = 0;
                }
            }
        }
    }
}


