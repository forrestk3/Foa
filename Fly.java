public class Fly{
    private double[] weight ;
    private double[] coordinate ;
    private int id ;
    private boolean processed = false ;
    private double[] bestCoordinate ;
    private double bestSmell ;
    public Fly(){
    }
    public Fly(double[] coor,double[] weight){
	setCoordinate(coor) ;
	setWeight(weight) ;
    }
    public void setCoordinate(double[] coor){
	coordinate = coor ;
    }
    public double[] getCoordinate(){return coordinate ;}
    public void setWeight(double[] weight){
	this.weight = weight ;
    }
    public double[] getWeight(){return weight ;}
    public void setId(int k){
	id = k ;
    }
    public double[] getBestCoordinate(){return bestCoordinate ;}
    public double getBestSmell(){return bestSmell ;}
    public void setBestCoordinate(double[] coor){
	bestCoordinate = coor ;
    }
    public void setBestSmell(double smell){
	bestSmell = smell ;
    }
    public int getId(){return id ;}
    public Fly clone(){
	double[] d = new double[coordinate.length] ;
	double[] w = new double[weight.length] ;
	Fly f = new Fly() ;
	for(int i = 0; i < d.length; i ++){
	    d[i] = coordinate[i] ;
	    w[i] = weight[i] ;
	}
	f.setCoordinate(d) ;
	f.setWeight(w) ;
	return f ;
    }
    public boolean isProcess(){return processed ;}
    public void process(){processed = true ;}
    public void unProcess(){processed = false ;}
    public int hashCode(){
	Double d ;
	Double w ;
	int sum = 0 ;
	for(int i = 0; i < coordinate.length; i ++){
	    d = coordinate[i] ;
	    w = weight[i] ;
	    sum += 31 * d.hashCode() * w.hashCode() ;
	}
	return sum ;
    }
    public boolean equals(Object o){
	if(this == o)
	    return true ;
	if(!(o instanceof Fly))
	    return false ;
	Fly f = (Fly)o ;
	double[] d = f.getCoordinate() ;
	double[] w = f.getWeight() ;
	for(int i = 0; i < d.length; i ++){
	    if((d[i] != this.coordinate[i]) || (w[i] != this.weight[i]))
		return false ;
	}
	return true ;
    }
}