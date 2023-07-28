
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class OutputDataSort implements TestData1 {
    private Map<Point,ToStation> map;
    private final File file;

    public OutputDataSort(Map<Point,ToStation> map,File file){
        this.map=map;
        this.file=file;
    }
    @Deprecated
    public void getTestData1(){
        Map<Point,ToStation> temp=new HashMap<>();
        temp.put(p1,t1);
        temp.put(p4,t4);
        temp.put(p3,t3);
        temp.put(p2,t2);
        this.map=temp;
    }
    public void getOutputSortedData(){
        Set<Point> set=this.map.keySet();
        Iterator<Point> i=set.iterator();
        List<Point> pointList=new ArrayList<>();
        while (i.hasNext()){
            Point p= i.next();
            pointList.add(p);
        }
        pointList.sort(Point::compareTo);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            String lines = "地点ID,地点名字,地点经度,地点纬度,最近的基站ID,最近的基站名字,最近的基站经度,最近的基站纬度,最近基站距离\n";
            fileOutputStream.write(lines.getBytes(StandardCharsets.UTF_8));
            for (Point p : pointList) {
                String s = p.getPointID() + "," + p.getPointName() + "," + p.getLon() + "," + p.getLat() + ","
                        + map.get(p).getS().getCellID() + "," + map.get(p).getS().getCellName() + ","
                        + map.get(p).getS().getLon() + "," + map.get(p).getS().getLat() +
                        "," + map.get(p).getDistance() + "\n";
                //System.out.println("Point:" + p + "," + "ToStation:" + map.get(p));
                fileOutputStream.write(s.getBytes(StandardCharsets.UTF_8));
            }
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
