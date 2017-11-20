public class Point{
    private Double x,y ;
    private double kDistance ;
    private int clusterID = 0;
    public Point(){} 
    public Point(double x,double y){
	this.x = x;
	this.y = y ;
    }
    public void setKDistance(double dis){this.kDistance = dis ;}
    public double getKDistance(){return kDistance ;}
    public double getX(){return x ;}
    public double getY(){return y ;}
    public void setClusterID(int k){clusterID = k ;}
    public int getClusterID(){return clusterID ;}
    public String toString(){
	return "(" + x + "," + y + ")" ;
    }
    public int hashCode(){
	return 31 * x.hashCode() + 31*y.hashCode() ;
    }
    public boolean equals(Object o){
	if(this == o)
	    return true ;
	if(!(o instanceof Point))
	    return false ;
	Point t = (Point)o ;
	if(this.getX() == t.getX() && this.getY() == t.getY())
	    return true ;
	else return false ;
    }
}