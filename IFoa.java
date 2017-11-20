import java.util.ArrayList ;
public class IFoa{
    ArrayList<Fly> flyList ;
    private int sizePop ;
    private int maxGen ;
    private double increase = 1.1 ;
    private double decrease = 0.9 ;
    public IFoa(int sizePop,int maxGen) {
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }
    private void init(){
	flyList = new ArrayList<>() ;
	for(int i = 0; i < sizePop; i ++){
	    flyList.add(generateFly()) ;
	}
    }
    public double[] execute(){
	init() ;
	double[] bestS = new double[maxGen] ;
	int k = 0;
	double bestSmell  ;
	Fly bestFly = flyList.get(0) ;
	bestSmell = Function.sphere(bestFly) ;
	int gen = 0 ;
	double t = 1 ;
	while(gen++ < maxGen){
	    if(gen < maxGen/2){
	    	if(gen == 1)
	    	    t = increase ;
	    	else t = t * increase ;
	    }
	    else{
	    	if(gen == maxGen / 2)
	    	    t = decrease ;
	    	else t = t * decrease ;
	    }
	    //  t = t * decrease ;
	    for(Fly f : flyList){     //寻找最优位置果蝇
		double smell = Function.sphere(f) ;
		if(smell < bestSmell){
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		}
	    }	   
	    for(Fly f : flyList){
		double[] d = bestFly.getCoordinate() ;
		double[] coor = f.getCoordinate() ;
		for(int i = 0; i < coor.length; i ++){
		    coor[i] = d[i] + (Math.random() * 2 - 1) * t ;
		}
	    }
	    bestS[k++] = bestSmell + 1 ;
	}
	// for(int i = 0; i < 30; i ++){
	//     System.out.println(bestFly.getCoordinate()[i]) ;
	// }
	// System.out.println(t) ;
	// System.exit(1) ;
	System.out.println("t=" + t) ;
	return bestS ;//刚刚放了一个极响的屁，旁边的人说了一句“我去~”2017-10-16 20:15
    }
    private Fly generateFly(){
	Fly f = new Fly() ;
	int dimension = 30 ;
	double[] coor = new double[dimension] ;
	for(int i = 0; i < coor.length; i ++){
	    coor[i] = Math.random() * 200 - 100 ;
	}
	f.setCoordinate(coor) ;
	f.setWeight(new double[dimension]) ;
	return f ;
    }
    public void test(){
	for(int i = 0; i < flyList.size(); i += 3){
	    double[] d = flyList.get(i).getCoordinate() ;
	    for(int j = 0; j < d.length; j ++){
		System.out.print(d[j] + " " ) ;
	    }
	}
    }
}