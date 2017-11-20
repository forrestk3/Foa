import java.util.ArrayList ;
public class BCFoa{
    ArrayList<Fly> flyList ;
    private int sizePop ;
    private int maxGen ;
    private int dimension ;
    private double high ;
    private double low ;
    public BCFoa(int sizePop,int maxGen) {
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }
    //初始化维度，搜索空间，并形成果蝇
    private void init(){
	dimension = 30 ;
	high = 10 ;
	low = 0 ;
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
	double worstSmell ;
	Fly bestFly = flyList.get(0) ;
	Fly worstFly = flyList.get(0) ;
	bestSmell = Function.sphere(bestFly) ;
	worstSmell = Function.sphere(worstFly) ;
	double smellRecord[] = new double[dimension] ;
	int recordIndex = 0 ;
	while(maxGen-- > 0){
	    recordIndex = 0 ;          //此处下标若不处理第二次迭代便会下标溢出。
	    boolean sita = false ;
	    for(Fly f : flyList){     //寻找最优位置果蝇
		double smell = Function.sphere(f) ;
		smellRecord[recordIndex] = smell ;
		recordIndex ++ ;
		if(smell < bestSmell){
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		}
		if(smell > worstSmell){
		    worstSmell = smell ;
		    worstFly = f.clone() ;
		}
	    }	   
		// System.out.println(worstSmell + ":" + bestSmell ) ;
		// System.exit(1) ;
	    for(int i = 0; i < smellRecord.length - 1; i ++){
		if(smellRecord[i] != smellRecord[i + 1]){
		    sita = true ;           //表示适应度函数方差不为0
		    break ;
		}
	    }
	    for(Fly f : flyList){
		double[] bestCoor = bestFly.getCoordinate() ;
		double[] worstCoor = worstFly.getCoordinate() ;
		double[] coor = f.getCoordinate() ;
		    for(int i = 0; i < coor.length; i ++){
			if(sita){
			    coor[i] += bestCoor[i];
			}
			else{
			    coor[i] -= worstCoor[i] ;
			}
		    }
	    }
	    // for(Fly f : flyList){
	    // 	double[] d = bestFly.getCoordinate() ;
	    // 	double[] coor = f.getCoordinate() ;
	    // 	for(int i = 0; i < coor.length; i ++){
	    // 	    coor[i] = d[i] + Math.random() * 2 - 1 ;
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
	for(int i = 0; i < coor.length; i ++){
	    coor[i] = Math.random() * (high - low) + low ;
	}
	f.setCoordinate(coor) ;
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