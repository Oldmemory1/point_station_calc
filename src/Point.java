import static java.lang.Integer.getInteger;

public class Point implements constant1,Comparable<Point>,Cloneable{
    private double lon;
    private double lat;
    private String PointName;
    private String PointID;
    @Override
    public String toString(){
        return  "PointID:"+PointID+","+"PointName:"+PointName+","+"LON:"+lon+","+"LAT:"+lat;
    }
    public double DistanceCalculation(Station s){
        double StationLon=s.getLon();
        double PointLon=this.getLon();
        double StationLat=s.getLat();
        double PointLat=this.getLat();
        double deltaLat=(StationLat-PointLat)*Pi/180.0;
        double deltaLon=(StationLon-PointLon)*Pi/180.0;
        StationLat=StationLat*Pi/180.0;
        PointLat=PointLat*Pi/180.0;
        double a=(Math.pow(Math.sin(deltaLat/2),2)+Math.pow(Math.sin(deltaLon/2),2)*Math.cos(StationLat)*Math.cos(PointLat));
        return 2*R*Math.asin(Math.sqrt(a));
    }
    public double getLon() {
        return this.lon;
    }
    public double getLat() {
        return this.lat;
    }
    public String getPointName() {
        return this.PointName;
    }
    public String getPointID() {
        return this.PointID;
    }
    public void setLon(double lon) {
        this.lon = lon;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public void setPointName(String PointName) {
        this.PointName = PointName;
    }
    public void setPointID(String PointID) {
        this.PointID = PointID;
    }
    public Point(double lon, double lat, String PointName, String PointID) {
        this.lon = lon;
        this.lat = lat;
        this.PointName = PointName;
        this.PointID = PointID;
    }
    @Override
    public int compareTo(Point o) {
        return (this.PointID).compareTo(o.PointID);
    }
    @Override
    public Point clone() throws CloneNotSupportedException {
        Point clone = (Point) super.clone();
        return new Point(this.lon,this.lat,this.PointName,this.PointID);
    }
}
