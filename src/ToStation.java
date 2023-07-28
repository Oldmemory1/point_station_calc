
public class ToStation implements Comparable<ToStation>{
    private Station s;
    private double distance;

    public Station getS() {
        return s;
    }

    public double getDistance() {
        return distance;
    }

    public void setS(Station s) {
        this.s = s;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
    public ToStation(Station s,double d){
        this.s=s;
        this.distance=d;
    }
    @Override
    public String toString(){
        return "Station:"+s+","+"Distance:"+distance;
    }

    @Override
    public int compareTo(ToStation o) {
        return Double.compare(this.distance, o.distance);
    }


}
