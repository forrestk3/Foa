import java.util.ArrayList ;

public class MyFoa{
    private ArrayList<Fly> flyList ;
    private int sizePop ;                //种群个体数
    private int maxGen ;                 //果大迭代次数
    private double high = 100 ;
    private double low = -100 ;
    private double a = low * 0.01 ;
    private double b = high * 0.01 ;
    private int stage = 1 ;
    private int dimension = 30 ;   //控制果蝇坐标维度

    // private Train train ;

    public MyFoa(int sizePop,int maxGen){
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }

    private void init(){       //生成果蝇群体并赋值
	flyList = new ArrayList<>() ;
	for(int i = 0; i < sizePop; i ++){
	    flyList.add(generateFly()) ;
	}
	//train = new Train() ;
    }

    //生成体个随机果蝇
    private Fly generateFly(){
	Fly fly = new Fly() ;
	double[] d = new double[dimension] ;
	double[] w = new double[dimension] ;
	for(int i = 0; i < dimension; i ++){
	    w[i] = maxGen * 0.01 ;
	    d[i] = Math.random() * (high - low) - high ;
	}
	fly.setCoordinate(d) ;
	fly.setWeight(w) ;
	return fly ;
    }
    //执行函数
    public double[] execute(){
	init() ;
	Fly bestFly = flyList.get(0) ;                 //全局最优个体保存
	double bestSmell = Function.sphere(bestFly) ;  //全局最优浓度保存
	System.out.println(bestSmell) ;
	int gen = 0 ;                                  //迭代次数
	int noBetter = 0 ;
	double[] bestS = new double[maxGen] ;          //记录每次迭代全局最优
	
	while(gen < maxGen){
	    boolean getBetter = false ;
	    // train(flyList,gen) ;
	    train(flyList,gen,stage) ;
	    for(Fly f : flyList){                      //寻找群体最优个体
		double smell = Function.sphere(f) ;
		if(smell < bestSmell){
		    getBetter = true ;
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		}
	    }
	    // System.out.println(bestSmell) ;
	    if(!getBetter){
		noBetter ++ ;
	    }
	    else{
		noBetter = 0 ;
	    }
	    
	    if(noBetter >= 3){
		stage ++ ;
		noBetter = 0 ;
	    }
	    if(stage % 3 == 0){
		if(b > 10e-4){
		    a = a/2 ;
		    b = b/2 ;
		}
	    }
	    // for(Fly f : flyList){
	    // 	double[] coor = f.getCoordinate() ;
	    // 	double[] weight = f.getWeight() ;
	    // 	for(int i = 0; i < coor.length; i ++){
	    // 	    coor[i] += Math.random() * 2 - 1 + weight[i] ;
	    // 	}
	    // }
	    for(Fly f : flyList){                     //更新更新群体位置
	    	double[] coor = f.getCoordinate() ;
	    	double[] weight = f.getWeight() ;
	    	double[] bestCoor = bestFly.getCoordinate() ;
	    	double[] bestWeight = bestFly.getWeight() ;
	    	for(int i = 0; i < coor.length; i ++){
	    	    coor[i] = bestCoor[i] + Math.random() * (b - a) - b + weight[i];
	    	}
	    }

	    bestS[gen] = bestSmell ;
	    gen ++ ;
	}
	// System.out.println("MyFoa") ;
	// double[] d = flyList.get(0).getWeight() ;
	// for(int i = 0; i < d.length; i ++){
	//     System.out.println(d[i]) ;
	// }
	System.out.println(a + ":" + b) ;
	return bestS ;
    }
    //训练函数
    private void train(ArrayList<Fly> flyList,int gen,int stage){
	double[] coor;
	double[] weight ;
	double tmp = (150 - gen) * 0.01 / (0.1 * stage + 0.9);
	double smell ;

	for(Fly f : flyList){           //对每一个果蝇的每一维进行试探训练
	    smell = Function.sphere(f) ;
	    coor = f.getCoordinate() ;
	    weight = f.getWeight() ;
	    for(int i = 0; i < coor.length; i ++){
		coor[i] += tmp ;
		if(Function.sphere(f) < smell)
		    if(weight[i] > 0)
			weight[i] += tmp ;
		    else weight[i] = tmp ;
		else 
		    if(weight[i] < 0)
			weight[i] -= tmp ;
		    else weight[i] = -tmp ;
		coor[i] -= tmp ;

		// while(Math.abs(weight[i]) > 10e-5){
		//     coor[i] += weight[i] ;
		//     if(Function.rastrigin(f) < smell){
		// 	coor[i] -= weight[i] ;

		// 	break ;
		//     }
		//     coor[i] -= weight[i] ;

		//     weight[i] *= -1 ;
		//     coor[i] += weight[i] ;
		//     if(Function.rastrigin(f) < smell){
		// 	coor[i] -= weight[i] ;
	
		// 	break ;
		//     }
		//     coor[i] -= weight[i] ;
		//     weight[i] /= 2 ;
		// }
	    }
	}
    }
}