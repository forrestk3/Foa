import java.util.ArrayList ;

public class DDSCFOA{
    private int maxGen,sizePop ;
    ArrayList<Fly> flyList ;
    public DDSCFOA(int sizePop,int maxGen){
	this.maxGen = maxGen ;
	this.sizePop = sizePop ;
    }
    private void init(){
	flyList = new ArrayList<>() ;
	for(int i = 0; i < sizePop; i ++){
	    flyList.add(generateFly()) ;
	}
    }
    private Fly generateFly(){
	Fly f = new Fly() ;
	double[] coor = new double[30] ;
	for(int i = 0; i < coor.length; i ++){
	    coor[i] = Math.random() * 200 - 100 ;
	}
	f.setCoordinate(coor) ;
	return f ;
    }
    public double[] execute(){
	init() ;
	double[] bestS = new double[maxGen] ;
	int k = 0 ;
	Fly bestFly = flyList.get(0) ;
	Fly worstFly = flyList.get(0) ;
	double bestSmell = Function.sphere(bestFly) ;
	double worstSmell = Function.sphere(worstFly) ;
	while(maxGen-- > 0){
	    double smell = 0 ;
	    worstSmell = 0 ;
	    for(Fly f : flyList){        //寻找最优和最差个体
		smell = Function.sphere(f) ;
		if(smell < bestSmell){
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		}
		if(smell > worstSmell){
		    worstSmell = smell ;
		    worstFly = f.clone() ;
		}
	    }
	    for(Fly f : flyList){//先进子群混沌转换，后进子群随机转换
		if(Function.dist(f,bestFly) < Function.dist(f,worstFly)){
		    chaoticTrans(f) ;
		    smell = Function.sphere(f) ;
		    if(smell < bestSmell)
			continue ;
		    if(smell > worstSmell)
			continue ;
		}
		else{
		    double[] d = f.getCoordinate() ;
		    double[] bestD = bestFly.getCoordinate() ;
		    for(int i = 0; i < d.length; i ++){
			d[i] = bestD[i] + Math.random() * 2 - 1 ;
		    }
		    smell = Function.sphere(f) ;
		    if(smell < bestSmell)
			continue ;
		    if(smell > worstSmell)
			continue ;
		}
	    }
	    bestS[k++] = bestSmell ;
	}
	

	return bestS ;
    }
    public void test(Fly f){

	double[] d = f.getCoordinate() ;
	for(double t : d){
	    System.out.print(String.format("%.2f",t) + " ") ;
	}

    }
    public void chaoticTrans(Fly f){
	double[] d = f.getCoordinate() ;
	double cx ;
	double a = -100,b = 100 ;
	for(int i = 0; i < d.length; i ++){
	    cx = (d[i] - a)/(b - a) ;
	    cx = 4 * cx * (1 - cx) ;
	    d[i] = cx * (b - a) + a ;
	}
    }
  
}