import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.DIRECTORIES_ONLY;

public class GUI extends JFrame {
    private File StationFile;
    private File PointFile;
    private final Map<Point,ToStation> map=new HashMap<>();
    private final java.util.List<Point> pointList=new ArrayList<>();
    private final java.util.List<Station> stationList=new ArrayList<>();
    private final ToStation example1=new ToStation(new Station(1,1,"test1","test1"), Integer.MAX_VALUE);
    //输出文件名
    private String OutputFileName="outputList.csv";
    //输出文件夹名字
    private String OutputFileFolder="";
    private final ThreadPoolExecutor poolExecutor1=new ThreadPoolExecutor(4, 4, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), r -> new Thread(r,"线程:"+Thread.currentThread().getName()));
    public GUI(){
        //StationFile= new File("data1.csv");
        //PointFile =new File("data2.csv");
        JFrame frame=new JFrame("计算程序");
        Container container=frame.getContentPane();
        JButton button1=new JButton("选择基站文件，要求.csv格式");
        button1.setBounds(20,20,200,50);
        JButton button2=new JButton("选择地点文件，要求.csv格式");
        button2.setBounds(20,90,200,50);
        JButton button3=new JButton("确定输入的文件");
        button3.setBounds(20,160,200,50);
        JButton button4=new JButton("计算最短距离并且输出");
        button4.setBounds(20,230,200,50);
        JTextArea t1=new JTextArea("""
                输入的第一个文件应该为以下用例格式!
                ID,机房名称,经度(WGS84),纬度(WGS84)
                GB01,长春市九台市沐石河局设备放置点04,125.7638199,43.3435107
                GB02,长春市九台市卢家局设备杨树放置点,126.1175509,44.24267495
                GB03,长春市德惠市大青嘴姜窝棚模块点,125.8402759,44.43232124
                输入的第二个文件应该为以下格式!
                CellID,基站中文名,lon,lat
                GX24748,46001_524843_1_0_0_175,125.276,43.8257
                GX24749,46001_543647_1_0_0_175,125.680928,44.516408
                GX24750,46001_531762_1_0_0_134,125.344864,43.869469
                GX24751,46001_534605_1_0_0_152,125.215167,43.885843
                GX25285,46001_531626_1_0_0_150,125.463995,43.864057""");
        t1.setBounds(20,300,500,200);
        JLabel label1 = new JLabel("你选择的基站文件地址为:");
        label1.setBounds(20,520,500,40);
        JLabel label2=new JLabel("你选择的地点文件地址为:");
        label2.setBounds(20,570,500,40);
        JLabel label3=new JLabel("输出文件地址为:");
        label3.setBounds(20,620,500,40);
        JProgressBar progressBar1=new JProgressBar();
        progressBar1.setBounds(550,520,300,40);
        progressBar1.setStringPainted(true);
        progressBar1.setIndeterminate(false);
        progressBar1.setValue(0);
        progressBar1.setString("等待导入基站文件...");
        JProgressBar progressBar2=new JProgressBar();
        progressBar2.setBounds(550,570,300,40);
        progressBar2.setStringPainted(true);
        progressBar2.setIndeterminate(false);
        progressBar2.setValue(0);
        progressBar2.setString("等待导入地点文件...");
        JProgressBar progressBar3=new JProgressBar();
        progressBar3.setBounds(250,20,300,50);
        progressBar3.setStringPainted(true);
        progressBar3.setIndeterminate(false);
        progressBar3.setValue(0);
        progressBar3.setString("等待中...");
        JButton button5=new JButton("设定导出路径");
        button5.setBounds(250,90,200,50);
        JTextField textField=new JTextField();
        textField.setBounds(250,160,400,50);
        JButton button6=new JButton("确定导出的文件名,上方文本框的输入例子:testPut1");
        button6.setBounds(250,230,400,50);
        container.add(button1);
        container.add(button2);
        container.add(button3);
        container.add(button4);
        container.add(button5);
        container.add(button6);
        container.add(t1);
        container.add(label1);
        container.add(label2);
        container.add(label3);
        container.add(progressBar1);
        container.add(progressBar2);
        container.add(progressBar3);
        container.add(textField);
        frame.setLayout(null);
        button1.addActionListener(e -> {
            JFileChooser chooser=new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("CSV-UTF8逗号分隔(.csv)","csv"));
            int i=chooser.showOpenDialog(getContentPane());
            if(i== APPROVE_OPTION){
                poolExecutor1.execute(() -> {
                    StationFile=chooser.getSelectedFile();
                    System.out.println(StationFile.getAbsolutePath());
                    label1.setText("你选择的基站文件地址为:"+StationFile.getAbsolutePath());
                    SwingUtilities.updateComponentTreeUI(label1);
                });

            }
            button1.setEnabled(false);
        });
        button2.addActionListener(e -> {
            JFileChooser chooser=new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setFileFilter(new FileNameExtensionFilter("CSV-UTF8逗号分隔(.csv)","csv"));
            int i=chooser.showOpenDialog(getContentPane());
            if(i== APPROVE_OPTION){
                poolExecutor1.execute(() -> {
                    PointFile=chooser.getSelectedFile();
                    System.out.println(PointFile.getAbsolutePath());
                    label2.setText("你选择的地点文件地址为:"+PointFile.getAbsolutePath());
                    SwingUtilities.updateComponentTreeUI(label2);
                });
            }
            button2.setEnabled(false);
        });
        button3.addActionListener(e -> {
            ThreadPoolExecutor executor=new ThreadPoolExecutor(4, 4, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(4), r -> new Thread(r,"线程:"+Thread.currentThread().getName()));
            executor.execute(() -> {
                progressBar1.setIndeterminate(true);
                progressBar1.setString("正在读取基站文件");
            });
            executor.execute(() -> {
                try (BufferedReader br = Files.newBufferedReader(Paths.get(StationFile.toURI()))) {
                    // CSV文件的分隔符
                    String DELIMITER = ",";

                    // 按行读取
                    String line;
                    String CellID;
                    String CellName;
                    double LON;
                    double LAT;
                    int LineNumber=1;
                    while ((line = br.readLine()) != null) {
                        // 分割
                        String[] columns = line.split(DELIMITER);
                        // 打印行
                        if(LineNumber!=1){
                            try{
                                CellID=columns[0];
                                CellName=columns[1];
                                LON= Double.parseDouble(columns[2]);
                                LAT= Double.parseDouble(columns[3]);
                                stationList.add(new Station(LON,LAT,CellName,CellID));
                            }
                            catch (Exception ex){
                                ex.printStackTrace();
                                throw new Exception(ex);

                            }

                        }
                        LineNumber++;
                    }
                    TimeUnit.SECONDS.sleep(3);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                progressBar1.setIndeterminate(false);
                progressBar1.setValue(100);
                progressBar1.setString("已完成读取基站文件");
            });
            executor.execute(() -> {
                progressBar2.setIndeterminate(true);
                progressBar2.setString("正在读取地点文件");
            });
            executor.execute(() -> {
                try (BufferedReader br = Files.newBufferedReader(Paths.get(PointFile.toURI()))) {
                    // CSV文件的分隔符
                    String DELIMITER = ",";
                    // 按行读取
                    String line;
                    String PointID;
                    String PointName;
                    double LON;
                    double LAT;
                    int LineNumber=1;
                    while ((line = br.readLine()) != null) {
                        // 分割
                        String[] columns = line.split(DELIMITER);
                        // 打印行
                        if(LineNumber!=1){
                            try{
                                PointID=columns[0];
                                PointName=columns[1];
                                LON= Double.parseDouble(columns[2]);
                                LAT= Double.parseDouble(columns[3]);
                                pointList.add(new Point(LON,LAT,PointName,PointID));
                            }catch (Exception ex){
                                ex.printStackTrace();
                                throw new Exception(ex);
                            }
                        }
                        LineNumber++;
                    }
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                progressBar2.setIndeterminate(false);
                progressBar2.setValue(100);
                progressBar2.setString("已完成读取地点文件");
            });
            button3.setEnabled(false);
        });
        button4.addActionListener(e -> {
            ThreadPoolExecutor executor=new ThreadPoolExecutor(4, 4, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), r -> new Thread(r,"线程"+Thread.currentThread().getName()));
            executor.execute(() -> {
                progressBar3.setIndeterminate(true);
                progressBar3.setString("正在计算中");
            });
            executor.execute(() -> {
                for (Point point : pointList)
                    map.put(point, example1);
                Set<Point> set = map.keySet();
                for (Point p : set) {
                    for (Station station : stationList) {
                        double calcDistance = p.DistanceCalculation(station);
                        double nowDistance = map.get(p).getDistance();
                        if (nowDistance > calcDistance){
                            map.replace(p, new ToStation(station, calcDistance));
                        }
                    }

                }
                for (Point p : set)
                    map.get(p).setDistance(map.get(p).getDistance() * 1000.0D);
                button3.setEnabled(true);
                File outputFile = new File(OutputFileName);
                OutputDataSort outputDataSort=new OutputDataSort(map,outputFile);
                outputDataSort.getOutputSortedData();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                progressBar3.setIndeterminate(false);
                progressBar3.setValue(100);
                progressBar3.setString("已计算完成");
            });

        });
        button5.addActionListener(e -> {
           JFileChooser fileChooser=new JFileChooser();
           fileChooser.setFileSelectionMode(DIRECTORIES_ONLY);
           int i=fileChooser.showOpenDialog(frame.getContentPane());

           if(i==APPROVE_OPTION){
               File selected=fileChooser.getSelectedFile();
               OutputFileFolder= selected.getAbsolutePath();
               System.out.println(selected.getAbsolutePath());
           }
           button5.setEnabled(false);

        });
        button6.addActionListener(e -> {
            OutputFileName= textField.getText();
            System.out.println(OutputFileName);
            //确定输出文件地址，如果textField没有内容，就创建一个默认名字为outputFile.csv的文件;
            if(OutputFileName.isEmpty()){
                OutputFileName=OutputFileFolder+ "\\"+"outputFile.csv";
            }else {
                OutputFileName=OutputFileFolder+ "\\"+OutputFileName+".csv";
            }
            //System.out.println(OutputFileName);
            //OutputTestClass1 outputTestClass1=new OutputTestClass1(OutputFileName);
            //outputTestClass1.output();
            button6.setEnabled(false);
            textField.setEnabled(false);
            textField.setText("");
            label3.setText("输出文件地址为:"+OutputFileName);
        });
        frame.setVisible(true);
        frame.setBackground(Color.WHITE);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(1000,800);
    }
}
