import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.border.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class Start extends JPanel implements ActionListener {

    private List<Bar> barsArray = new ArrayList<>();
    private JPanel graphBar;
    private JPanel labelPanel;
    JButton openMe = new JButton("OPEN");

    private Start(){
        setBorder( new EmptyBorder(10, 10, 10, 10) );
        setLayout( new BorderLayout() );
        int barGap = 5;
        graphBar = new JPanel( new GridLayout(1, 0, barGap, 0) );
        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border inner = new EmptyBorder(10, 10, 0, 10);
        Border compound = new CompoundBorder(outer, inner);
        graphBar.setBorder(compound);
        labelPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        labelPanel.setBorder( new EmptyBorder(5, 10, 50, 10) );
        add(graphBar, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }

    private void addHistogramColumn(String label, int value, Color color){
        Bar bar = new Bar(label, value, color);
        barsArray.add( bar );
    }
    private void layoutHistogram(){
        graphBar.removeAll();
        labelPanel.removeAll();
        int maxValue = 0;
        for (Bar bar: barsArray)
            maxValue = Math.max(maxValue, bar.getValue());
        for (Bar bar: barsArray)
        {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);
            int histogramHeight = 300;
            int barHeight = (bar.getValue() * histogramHeight) / maxValue;
            int barWidth = 20;
            Icon icon = new IconColor(bar.getColor(), barWidth, barHeight);
            label.setIcon( icon );
            graphBar.add( label );

            JLabel barLabel = new JLabel( bar.getLabel() );
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add( barLabel );
        }
    }
    private class Bar {
        private int value;
        private Color color;
        private String label;
        private Bar(String label, int value, Color color) {
            this.label = label;
            this.value = value;
            this.color = color;}
        private String getLabel() { return label; }
        private int getValue() { return value; }
        private Color getColor() {return color; }
    }

    private class IconColor implements Icon {
        private Color color;
        private int width;
        private int height;
        private int shadow = 3;

        private IconColor(Color color, int width, int height) {
            this.color = color;
            this.width = width;
            this.height = height;}

        public int getIconWidth(){ return width;}
        public int getIconHeight() { return height; }
        public void paintIcon(Component c, Graphics g, int x, int y)
        {
            g.setColor(color);
            g.fillRect(x, y, width - shadow, height);
            g.setColor(Color.GRAY);
            g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
        }
    }

    private void createAndShowGUI()
    {
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setCurrentDirectory(new File("/Users/Mariusz/IdeaProjects/Oramus11/src"));

        Map histogramMap = new LinkedHashMap<Integer,Integer>();
        int result = jFileChooser.showOpenDialog(new JFrame());
        FileReader fr = null;
        String linia = "";

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = jFileChooser.getSelectedFile();
            try {
                fr = new FileReader(selectedFile);
            } catch (FileNotFoundException e){System.exit(1);}
            BufferedReader bfr = new BufferedReader(fr);
            try {
                while((linia = bfr.readLine()) != null) {
                    if (!histogramMap.containsKey(linia)) {
                        histogramMap.put(linia, 0);
                    }
                    if (histogramMap.containsKey(linia)) {
                        int keyValue = (int) histogramMap.get(linia);
                        histogramMap.put(linia, keyValue + 1);
                    }
                }
            } catch (IOException e) { System.exit(2); }
            try {fr.close();} catch (IOException e) {System.exit(3); }
        }
        Start panel = new Start();
        int suma = 0;
        int biggestKey = 0;
        for(Object key : histogramMap.keySet()){
            int foo = Integer.parseInt((String) key);
            if(foo > biggestKey)
                biggestKey = foo;
        }
        for(int i = 0; i <= biggestKey; i ++){
            if(!histogramMap.containsKey(i)){
                histogramMap.put(i,0);
            }
        }
        System.out.println(histogramMap);
        panel.layoutHistogram();
        JFrame frame = new JFrame("Histogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        JLabel etykietka = new JLabel( "Liczba przedziałów: "+ histogramMap.size()+
                "   Liczba wszystkich zczytanych liczb: " +suma);
        JLabel yaxis = new JLabel("Powtórzenia");
        frame.add(yaxis, BorderLayout.WEST);
        frame.add(etykietka, BorderLayout.PAGE_END);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == openMe){
            createAndShowGUI();
        }
    }

    private void projectStarter(){
        JFrame open = new JFrame("Open file");
        open.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open.add(openMe);
        openMe.addActionListener(this);
        open.setLocationByPlatform(true);
        open.pack();
        open.setVisible(true);
    }

    public static void main(String[] args)
    {
        Start h = new Start();
        h.projectStarter();
    }
}