import java.util.ArrayList ;
import java.util.Scanner ;
public class VFoa{
    ArrayList<Fly> flyList ;
    private int sizePop ;
    private int maxGen ;
    private int dimension ;
    private double high ;
    private double low ;
    private double w,c1,c2 ;
    Scanner scan = new Scanner(System.in) ;
    public VFoa(int sizePop,int maxGen) {
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }
    //初始化维度，搜索空间，并形成果蝇
    private void init(){
	dimension = 30 ;
	high = 100 ;
	low = -100 ;
	w = 0.76;
	c1 = 3 ;
	c2 = 3 ;
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
	boolean getBetter = false ;
	while(maxGen-- > 0){
	    getBetter = false ;
	    for(Fly f : flyList){     //寻找最优位置果蝇
		double smell = Function.sphere(f) ;
		if(smell < bestSmell){
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		    getBetter = true ;
		}
	    }	   
	    double[] weightBest = bestFly.getWeight() ;
	    double[] coorBest = bestFly.getCoordinate() ;
	    
	    if(getBetter){
		for(Fly f : flyList){
		    double[] coor = f.getCoordinate() ;
		    double[] bestCoor = f.getBestCoordinate() ;
		    for(int i = 0; i < coor.length; i ++){
			coor[i] = coorBest[i];
			bestCoor[i] = coorBest[i] ;
		    }
		    f.setBestSmell(bestSmell)  ;
		}
	    }
	    for(Fly f : flyList){
		double[] coor = f.getCoordinate() ;
		double[] v = f.getWeight() ;
		double[] p = f.getBestCoordinate() ;
		for(int i = 0; i < coor.length; i ++){
		    v[i] = w*v[i] + c1 * Math.random()*(p[i] - coor[i]) + 
			c2 * Math.random() * (coorBest[i] - coor[i]) ;
		    coor[i] += v[i] ;
		    // System.out.println(v[i]) ;
		}
	    }
	    // for(Fly f : flyList){
	    // 	System.out.println(Function.sphere(f)) ;
	    // }
	    // scan.nextLine() ;
	    // for(Fly f : flyList){
	    // 	double[] weight = f.getWeight() ;
	    // 	double[] coor = f.getCoordinate() ;
	    // 	double[] bestC = f.getBestCoordinate() ;
	    // 	for(int i = 0; i < coor.length; i ++){
	    // 	    weight[i]=w*weight[i]+c1*Math.random()*(bestC[i]-coor[i]) + 
	    // 		c2*Math.random()*(coorBest[i] - coor[i]) ;
	    // 	    coor[i] = coorBest[i] + weight[i] ;
	    // 	}
	    // 	if(Function.sphere(f) < f.getBestSmell()){
	    // 	    f.setBestSmell(Function.sphere(f)) ;
	    // 	    for(int i = 0; i < bestC.length; i ++){
	    // 		bestC[i] = coor[i] ;
	    // 	    }
	    // 	}
	    // }
	    bestS[k++] = bestSmell ;
	}
	return bestS ;
    }
    private Fly generateFly(){
	Fly f = new Fly() ;
	double[] coor = new double[dimension] ;
	double[] weight = new double[dimension] ;
	double[] bestCoor = new double[dimension] ;
	for(int i = 0; i < coor.length; i ++){
	    coor[i] = Math.random() * (high - low) + low ;
	    weight[i] = Math.random() ;
	    bestCoor[i] = coor[i] ;
	}
	f.setCoordinate(coor) ;
	f.setBestCoordinate(bestCoor) ;
	f.setBestSmell(Function.sphere(f)) ;
	f.setWeight(weight) ;
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