import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    static int inputFromFirstPage;
    final static Random random = new Random();
    static int[] arrayOfButton;
    static JPanel jPanel;
    static JPanel sortAndReset;
    static JButton buttonSort;
    static JButton buttonReset;
    static boolean isDescSort;
    static JFrame jFrame;

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
            if (validating(textField))
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
        JButton buttonLessThan30 = getButtonLessThan30(jFrame);
        jPanel.add(buttonLessThan30);

        jFrame.add(sortAndReset);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        arrayOfButton = new int[inputFromFirstPage];

        for (int i = 0; i < inputFromFirstPage - 1; i++) {
            JButton buttonRandom = getRandomButton(jFrame);
            int number = Integer.parseInt(buttonRandom.getText());
            arrayOfButton[i] = number;
            jPanel.add(buttonRandom);
        }
    }

    public static void getSortingPage() {
        jFrame = getFrame("Sort screen");
        jPanel = new JPanel();
        jPanel.setLayout(new MyLayout());
        sortAndReset = getPanelSecondPage(jFrame);

        jFrame.add(sortAndReset);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        if (!isDescSort) {
            sorting(jFrame);
            isDescSort = true;
        } else {
            sorting(jFrame);
            isDescSort = false;
        }
    }

    public static JPanel getPanelSecondPage(JFrame jFrame) {
        sortAndReset = new JPanel();
        sortAndReset.setLayout(new FlowLayout());
        sortAndReset.setBounds(530, 0, 100, 100);

        buttonSort = getSortButton(jFrame);
        buttonReset = getResetButton(jFrame);
        sortAndReset.add(buttonSort);
        sortAndReset.add(buttonReset);
        return sortAndReset;
    }

    private static boolean validating(JTextField textField) {
        inputFromFirstPage = Integer.parseInt(textField.getText());
        return inputFromFirstPage >= 0 & inputFromFirstPage <= 1000;
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

    public static JFrame getFrame(String title) {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle(title);
        jFrame.setSize(700, 700);
        return jFrame;
    }

    public static JButton getButtonLessThan30(JFrame jFrame) {
        JButton reSort = new JButton();
        reSort.addActionListener(e -> {
            jFrame.dispose();
            getSecondPage();
        });
        reSort.setBackground(Color.blue);
        reSort.setText("" + 0);
        reSort.setFocusable(false);
        reSort.setForeground(Color.WHITE);
        reSort.setBorder(BorderFactory.createEtchedBorder());
        return reSort;
    }

    public static JButton getRandomButton(JFrame jFrame) {
        JButton jButton = getButton();
        int randomNumber = random.nextInt(100);
        jButton.setText("" + randomNumber);
        isSmallerThan30(jButton, jFrame);
        return jButton;
    }

    public static JButton getButton() {
        JButton jButton = new JButton();
        jButton.setFocusable(false);
        jButton.setForeground(Color.WHITE);
        jButton.setBackground(Color.blue);
        jButton.setBorder(BorderFactory.createEtchedBorder());
        return jButton;
    }

    private static void isSmallerThan30(JButton jButton, JFrame jFrame) {
        if (Integer.parseInt(jButton.getText()) > 30) {
            jButton.addActionListener(e -> JOptionPane.showMessageDialog
                    (jPanel, "Please select a value smaller or equals to 30"));
        } else {
            jButton.addActionListener(e -> {
                jFrame.dispose();
                isDescSort = false;
                getSecondPage();
            });
        }
    }

    public static JButton getSortButton(JFrame jFrame) {
        JButton jButton = new JButton();
        jButton.setBackground(Color.BLACK);
        jButton.setText("Sort");
        jButton.setFocusable(false);
        jButton.setForeground(Color.WHITE);
        jButton.setBorder(BorderFactory.createEtchedBorder());
        jButton.addActionListener(e -> {
            jFrame.dispose();
            getSortingPage();
        });
        return jButton;
    }

    public static JButton getResetButton(JFrame jFrame) {
        JButton jButton = new JButton();
        jButton.setBackground(Color.GREEN);
        jButton.setText("Reset");
        jButton.setFocusable(false);
        jButton.setForeground(Color.WHITE);
        jButton.setBorder(BorderFactory.createEtchedBorder());
        jButton.addActionListener(e -> {
            jFrame.dispose();
            getFirstPage();
        });
        return jButton;
    }

    public static void sorting(JFrame jFrame) {
        int high = arrayOfButton.length - 1;
        SortClass.quickSort(arrayOfButton, 0, high);
        for (int j : arrayOfButton) {
            JButton temp = getButton();
            temp.setText("" + j);
            isSmallerThan30(temp, jFrame);
            jPanel.add(temp);
        }
    }

    static class SortClass {
        static int partition(int[] array, int low, int high) {
            int pivot = array[high];
            int i = (low - 1);

            if (isDescSort) {
                for (int j = low; j < high; j++) {
                    if (array[j] <= pivot) {
                        i++;

                        int temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
            } else {
                for (int j = low; j < high; j++) {
                    if (array[j] >= pivot) {
                        i++;

                        int temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                    }
                }
            }
            int temp = array[i + 1];
            array[i + 1] = array[high];
            array[high] = temp;
            return (i + 1);
        }

        public static void quickSort(int[] array, int low, int high) {
            if (low < high) {
                int pi = partition(array, low, high);
                quickSort(array, low, pi - 1);
                quickSort(array, pi + 1, high);
            }
        }
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


