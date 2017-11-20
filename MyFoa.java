import java.util.ArrayList ;

public class MyFoa{
    private ArrayList<Fly> flyList ;
    private int sizePop ;                //��Ⱥ������
    private int maxGen ;                 //�����������
    private double high = 100 ;
    private double low = -100 ;
    private double a = low * 0.01 ;
    private double b = high * 0.01 ;
    private int stage = 1 ;
    private int dimension = 30 ;   //���ƹ�Ӭ����ά��

    // private Train train ;

    public MyFoa(int sizePop,int maxGen){
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }

    private void init(){       //���ɹ�ӬȺ�岢��ֵ
	flyList = new ArrayList<>() ;
	for(int i = 0; i < sizePop; i ++){
	    flyList.add(generateFly()) ;
	}
	//train = new Train() ;
    }

    //������������Ӭ
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
    //ִ�к���
    public double[] execute(){
	init() ;
	Fly bestFly = flyList.get(0) ;                 //ȫ�����Ÿ��屣��
	double bestSmell = Function.sphere(bestFly) ;  //ȫ������Ũ�ȱ���
	System.out.println(bestSmell) ;
	int gen = 0 ;                                  //��������
	int noBetter = 0 ;
	double[] bestS = new double[maxGen] ;          //��¼ÿ�ε���ȫ������
	
	while(gen < maxGen){
	    boolean getBetter = false ;
	    // train(flyList,gen) ;
	    train(flyList,gen,stage) ;
	    for(Fly f : flyList){                      //Ѱ��Ⱥ�����Ÿ���
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
	    for(Fly f : flyList){                     //���¸���Ⱥ��λ��
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
    //ѵ������
    private void train(ArrayList<Fly> flyList,int gen,int stage){
	double[] coor;
	double[] weight ;
	double tmp = (150 - gen) * 0.01 / (0.1 * stage + 0.9);
	double smell ;

	for(Fly f : flyList){           //��ÿһ����Ӭ��ÿһά������̽ѵ��
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