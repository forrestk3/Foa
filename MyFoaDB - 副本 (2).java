import java.util.ArrayList ;
import java.util.Scanner ;

public class MyFoaDB{
    private ArrayList<Fly> flyList ;
    private int sizePop ;                //种群个体数
    private int maxGen ;                 //果大迭代次数
    private double low = -5.12 ;    //搜索范围
    private double high = 5.12 ;
    private double a = low * 0.01 ;
    private double b = high * 0.01 ;
    private int stage = 1 ;
    // private Train train ;
    
    public MyFoaDB(int sizePop,int maxGen){
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }

    private void init(){       //生成果蝇群体并赋值
	//train = new Train() ;
	flyList = new ArrayList<>() ;
	for(int i = 0; i < sizePop; i ++){
	    flyList.add(generateFly()) ;
	}
    }

    //生成体个随机果蝇
    private Fly generateFly(){
	int dimension = 30 ;   //控制果蝇坐标维度
	Fly fly = new Fly() ;
	double[] d = new double[dimension] ;
	double[] w = new double[dimension] ;
	for(int i = 0; i < dimension; i ++){
	    w[i] = (high - low) * 0.01 ;
	    d[i] = Math.random() * (high - low) - high ;
	}
	fly.setCoordinate(d) ;
	fly.setWeight(w) ;
	return fly ;
    }
    //执行函数
    public double[] execute(){
	// //

	// double d = 1.5 ;
	// double t = 0 ;
	// while(Math.abs(d) > 10e-5){
	//     t += d ;
	//     System.out.println(t) ;
	//     if(false){
	// 	t -= d ;
	// 	break ;
	//     }
	//     t -= d ;

	//     d *= -1 ;
	//     t += d ;
	//     System.out.println(t) ;
	//     if(false){
	// 	t -= d ;
	// 	break ;
	//     }
	//     t -= d ;
	//     d /= 2 ;
	// }
	// System.out.println(t) ;
	// System.exit(1) ;

	// //
	init() ;
	Fly bestFly = flyList.get(0) ;                 //全局最优个体保存
	double bestSmell = Function.rastrigin(bestFly) ;  //全局最优浓度保存
	int gen = 0 ;                                  //迭代次数
	double[] bestS = new double[maxGen] ;          //记录每次迭代全局最优
	int noBetter = 0 ;
	while(gen < maxGen){
	    boolean getBetter = false ;
	    train(flyList,gen,stage) ;
	    for(Fly f : flyList){                      //寻找群体最优个体
		double smell = Function.rastrigin(f) ;
		if(smell < bestSmell){
		    getBetter = true ;
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		}
	    }
	    
	    // System.out.println(Function.rastrigin(flyList.get(0))) ;
	    //System.out.println(Function.rastrigin(bestFly)) ;

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
		if(b > 10e-3){
		    a = a/2 ;
		    b = b/2 ;
		}
	    }
	    Scanner s = new Scanner(System.in) ;	
	    // s.nextLine() ;
	    if(gen < 50){
		for(Fly f : flyList){
		    double[] coor = f.getCoordinate() ;
		    double[] weight = f.getWeight() ;
		    for(int i = 0; i < coor.length; i ++){
			coor[i] += weight[i] ;
		    }
		}
	    }
	    else{
		for(Fly f : flyList){                     //更新更新群体位置
		    double[] coor = f.getCoordinate() ;
		    double[] weight = f.getWeight() ;
		    double[] bestCoor = bestFly.getCoordinate() ;
		    double[] bestWeight = bestFly.getWeight() ;
		    for(int i = 0; i < coor.length; i ++){
			coor[i] = bestCoor[i] + Math.random() * (b - a) - a + weight[i];
		    }
		}
	    }
	    bestS[gen] = bestSmell ;
  	    gen ++ ;
	}

	double tmp = 0.1 ;
	double[] d = bestFly.getCoordinate() ;
	double[] w = bestFly.getWeight() ;

	double tmpSmell = Function.rastrigin(bestFly) ;
	for(int i = 0; i < d.length; i ++){
	    // d[i] += tmp ;
	    // if(Function.rastrigin(bestFly) < tmpSmell)
	    // 	w[i] += tmp ;
	    // else w[i] -= tmp ;
	    // d[i] -= tmp ;
	    w[i] = Math.random() * 2 - 1 ;
	    while(Math.abs(w[i]) > 10e-5){
		d[i] += w[i] ;
		if(Function.rastrigin(bestFly) < tmpSmell){
		    d[i] -= w[i] ;
		    break ;
		}
		d[i] -= w[i] ;

		w[i] *= -1 ;
		d[i] += w[i] ;
		if(Function.rastrigin(bestFly) < tmpSmell){
		    d[i] -= w[i] ;
		    break ;
		}
		d[i] -= w[i] ;

		w[i] /= 2 ;
	    }
	}

	for(int i = 0; i < d.length; i ++){
	    //System.out.println(d[i] + "~~~~~~~~" + w[i]) ;
	    d[i] += w[i] + Math.random() * (b -a) + b ;
	}
	System.out.println(tmpSmell + "~~~~~" + Function.rastrigin(bestFly)) ;
	//System.exit(1) ;
	// double[] d = new double[30] ;
	// for(int i = 0; i < 30; i ++)
	//     d[i] = 5 ;
	// Fly fly = generateFly() ;
	// fly.setCoordinate(d) ;
	// System.out.println(Function.rastrigin(fly)) ;

	// System.out.println(stage) ;
	// System.out.println(a + ":" + b) ;
	return bestS ;
    }
    //训练函数
    private void train(ArrayList<Fly> flyList,int gen,int stage){
	double[] coor;
	double[] weight ;
	double tmp = (150 - gen) * 0.01 / (0.1 * stage + 0.9);//将stage转变为一个较平稳的递增直线以降低tmp的值

	double smell ;
	outer:
	for(Fly f : flyList){           //对每一个果蝇的每一维进行试探训练
	    smell = Function.rastrigin(f) ;
	    coor = f.getCoordinate() ;
	    weight = f.getWeight() ;
	    for(int i = 0; i < coor.length; i ++){
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
		coor[i] += tmp ;
		if(Function.rastrigin(f) < smell)
		    if(weight[i] > 0)
			weight[i] += tmp ;
		    else weight[i] = tmp ;
		else 
		    if(weight[i] < 0)
			weight[i] -= tmp ;
		    else weight[i] = -tmp ;
		coor[i] -= tmp ;
	    }
	}
    }
}