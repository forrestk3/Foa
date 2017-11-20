public class MyPoint{
    private double[] coor ;
    private boolean processed = false ;
    public MyPoint(){}
    public MyPoint(double[] coor){
	setCoor(coor) ;
    }
    public boolean isProcess(){return processed ;}
    public void process(){processed = true ;}
    public void unProcess(){processed = false ;}
    public void setCoor(double[] coor){
	this.coor = coor ;
    }
    public double[] getCoordinate(){return coor ;}
    public int hashCode(){
	Double d ;
	int sum = 0 ;
	for(int i = 0; i < coor.length; i ++){
	    d = coor[i] ;
	    sum += 31 * d.hashCode() ;
	}
	return sum ;
    }
    public boolean equals(Object o){
	if(this == o)
	    return true ;
	if(!( o instanceof MyPoint)){
	    return false ;
	}
	MyPoint p = (MyPoint)o ;
	double[] d = p.getCoordinate() ;
	for(int i = 0; i < d.length; i ++){
	    if(d[i] != this.coor[i])
		return false ;
	}
	return true ;
    }
    public String toString(){
	String str = "" ;
	for(double d : coor){
	    str = str + String.format("%.2f ",d) ;
	}
	return str ;
    }
}