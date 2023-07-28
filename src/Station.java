public class Station {
    private double lon;
    private double lat;

    private String CellName;

    private String CellID;
    @Override
    public String toString(){
        return "CellID:"+CellID+","+"CellName:"+CellName+","+"LON:"+lon+","+"LAT:"+lat;
    }
    public double getLon() {
        return this.lon;
    }

    public double getLat() {
        return this.lat;
    }

    public String getCellName() {
        return this.CellName;
    }

    public String getCellID() {
        return this.CellID;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setCellName(String CellName) {
        this.CellName = CellName;
    }

    public void setCellID(String CellID) {
        this.CellID = CellID;
    }

    public Station(double lon, double lat, String CellName, String CellID) {
        this.lon = lon;
        this.lat = lat;
        this.CellName = CellName;
        this.CellID = CellID;
    }
}
