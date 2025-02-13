package vttp.batch5.paf.movies.models;

public class DirectorResults {
    public String Dname;
    public int Count;
    public long totalRev;
    public long totalBud;
    public String getDname() {
        return Dname;
    }
    public void setDname(String dname) {
        Dname = dname;
    }
    public int getCount() {
        return Count;
    }
    public void setCount(int count) {
        Count = count;
    }
    public long getTotalRev() {
        return totalRev;
    }
    public void setTotalRev(long totalRev) {
        this.totalRev = totalRev;
    }
    public long getTotalBud() {
        return totalBud;
    }
    public void setTotalBud(long totalBud) {
        this.totalBud = totalBud;
    }
    public DirectorResults(String dname, int count, long totalRev, long totalBud) {
        Dname = dname;
        Count = count;
        this.totalRev = totalRev;
        this.totalBud = totalBud;
    }
    public DirectorResults() {
    }
    public DirectorResults(String directorName) {
        this.Dname = directorName;
        this.Count = 0;
        this.totalRev = 0;
        this.totalBud = 0;
    }

    
}
