import java.util.ArrayList ;
import java.util.Scanner ;
import java.util.* ;

public class MyFoaDB{
    private ArrayList<Fly> flyList ;
    private ArrayList<Set<Fly>> groups ;
    private int sizePop ;                //��Ⱥ������
    private int maxGen ;                 //�����������
    private double low = -100 ;     //������Χ
    private double high = 100 ;
    private double a = low * 0.01 ;
    private double b = high * 0.01 ;
    private int stage = 1 ;
    private double delta = 0.95 ;    //�趨������������
    private int dimension = 30 ;    //���ƹ�Ӭ����ά��
    private GDBSCAN divide ;
    Scanner scan = new Scanner(System.in) ;
    // private Train train ;
    
    public MyFoaDB(int sizePop,int maxGen){
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;

	// Fly f1 = new Fly() ,f2 = new Fly() ;
	// double[] coor1 = new double[30] ,coor2 = new double[30] ;
	// double[] weight1 = new double[30] ,weight2 = new double[30] ;
	// for(int i =0; i < 30; i ++){
	//     coor1[i] = i ;coor2[i] = i ;
	//     weight1[i] = i;weight2[i] = i ;
	// }
	//*����Fly��equals����*//
	// f1 = new Fly(coor1,weight1) ;
	// f2 = new Fly(coor2,weight2) ;
	// ArrayList<Fly> tmpList = new ArrayList<>() ;
	// tmpList.add(f1) ;
	// System.out.println(tmpList.contains(f2)) ;
	// System.exit(1) ;
    }

    private void init(){       //���ɹ�ӬȺ�岢��ֵ
	//train = new Train() ;
	//divide = new GDBSCAN(4,"") ;
	divide = new GDBSCAN(4) ;
	flyList = new ArrayList<>() ;
	for(int i = 0; i < sizePop; i ++){
	    flyList.add(generateFly()) ;
	}
	//	divide.setDatas(flyList) ;
    }

    //������������Ӭ
    private Fly generateFly(){
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
    //ִ�к���
    public double[] execute(){
	init() ;
	Fly bestFly = flyList.get(0) ;                 //ȫ�����Ÿ��屣��
	double bestSmell = Function.schaffer(bestFly) ;  //ȫ������Ũ�ȱ���
	int gen = 0 ;                                  //��������
	double[] bestS = new double[maxGen] ;          //��¼ÿ�ε���ȫ������
	int noBetter = 0 ;
	double t = 1 ;
	while(gen < maxGen){
	    boolean getBetter = false ;

	    for(Fly f : flyList){                      //Ѱ��Ⱥ�����Ÿ���
		double smell = Function.schaffer(f) ;
		if(smell < bestSmell){
		    getBetter = true ;
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		}
	    }
	    
	    if(!getBetter){
		noBetter ++ ;
	    }
	    else{
		noBetter = 0 ;
	    }
	    
	    if((noBetter % 3 == 0) && noBetter != 0){
		stage ++ ;
		//noBetter = 0 ;
	    }
	    if(noBetter % 6 == 0){      //���������ˡ�����
	    	//  if(b > 10e-3){
	    	    a = a/2 ;
	    	    b = b/2 ;
	        //	}
	    }
	    //	    System.out.println(noBetter) ;
	    Scanner s = new Scanner(System.in) ;	
	    // s.nextLine() ;
	    // if(gen < maxGen/5){
	    // 	train(flyList,gen,stage) ;
	    // 	for(Fly f : flyList){
	    // 	    double[] coor = f.getCoordinate() ;
	    // 	    double[] weight = f.getWeight() ;
	    // 	    for(int i = 0; i < coor.length; i ++){
	    // 		coor[i] += weight[i] ;
	    // 	    }
	    // 	}
	    // }
	    //  else{
		//	divide.test() ;
		t = t * delta ;
		double[] bestCoor = bestFly.getCoordinate() ;
		Fly childFly = bestFly.clone() ;
		double[] childCoor = childFly.getCoordinate() ;
		for(Fly f : flyList){                     //���¸���Ⱥ��λ��
		    double[] coor = f.getCoordinate() ;
		    for(int i = 0; i < coor.length; i ++){
			childCoor[i] = bestCoor[i] + (Math.random() * (b - a) + a )*t ;
			coor[i] = Math.random() * (bestCoor[i] - coor[i]) + coor[i]; 
		    }
		    if(Function.schaffer(childFly) < bestSmell){
			bestSmell = Function.schaffer(childFly) ;
			bestFly = childFly.clone() ;
		    }
		}
		// }
	    bestS[gen] = bestSmell + 1;
	    //	    System.out.println(bestSmell) ;
  	    gen ++ ;
	}

	// double tmp = 0.1 ;
	// double[] d = bestFly.getCoordinate() ;
	// double[] w = bestFly.getWeight() ;

	// double tmpSmell = Function.schaffer(bestFly) ;
	// for(int i = 0; i < d.length; i ++){
	//     w[i] = Math.random() * 2 - 1 ;
	//     while(Math.abs(w[i]) > 10e-5){
	// 	d[i] += w[i] ;
	// 	if(Function.schaffer(bestFly) < tmpSmell){
	// 	    d[i] -= w[i] ;
	// 	    break ;
	// 	}
	// 	d[i] -= w[i] ;

	// 	w[i] *= -1 ;
	// 	d[i] += w[i] ;
	// 	if(Function.schaffer(bestFly) < tmpSmell){
	// 	    d[i] -= w[i] ;
	// 	    break ;
	// 	}
	// 	d[i] -= w[i] ;

	// 	w[i] /= 2 ;
	//     }
	// }

	// for(int i = 0; i < d.length; i ++){
	//     d[i] += w[i] + Math.random() * (b -a) + b ;
	// }
	//System.out.println("a:"+a+"\tb:"+b) ;
	return bestS ;
    }
    //ѵ������
    private void train(ArrayList<Fly> flyList,int gen,int stage){
	double[] coor;
	double[] weight ;
	//double tmp = (maxGen - gen) * 0.01 / (0.1 * stage + 0.9);//��stageת��Ϊһ����ƽ�ȵĵ���ֱ���Խ���tmp��ֵ
	double tmp ;
	double smell ;
	outer:
	for(Fly f : flyList){           //��ÿһ����Ӭ��ÿһά������̽ѵ��
	    smell = Function.schaffer(f) ;
	   
	    coor = f.getCoordinate() ;
	    weight = f.getWeight() ;
	    for(int i = 0; i < coor.length; i ++){
		tmp = maxGen * 0.01 ;    //&*%*&%^*%*&% 
		while(Math.abs(weight[i]) > 10e-5){
		    coor[i] += tmp ;
		    if(Function.schaffer(f) < smell){
			coor[i] -= tmp ;
			weight[i] = tmp ;
			break ;
		    }
		    coor[i] -= tmp ;

		    tmp *= -1 ;
		    coor[i] += tmp ;
		    if(Function.schaffer(f) < smell){
			coor[i] -= tmp ;
			weight[i] = tmp ;
			break ;
		    }
		    coor[i] -= tmp ;
		    tmp *= delta ;    //(*&^(*^*(&^(&*^*^%&^%$$#
		    
		}
		// coor[i] += weight[i] ;                       //.....
		// System.out.println(smell + ":" + Function.schaffer(f) + ":" + 
		// 		   weight[i] + ":" + coor[i] + "i" + i) ;
		// coor[i] -= weight[i] ;
		//	scan.nextLine() ;
		// coor[i] += tmp ;
		// if(Function.schaffer(f) < smell)
		//     if(weight[i] > 0)
		// 	weight[i] += tmp ;
		//     else weight[i] = tmp ;
		// else 
		//     if(weight[i] < 0)
		// 	weight[i] -= tmp ;
		//     else weight[i] = -tmp ;
		// coor[i] -= tmp ;
		
	    }
	    // for(int i = 0; i < coor.length; i ++){
	    // 	coor[i] += weight[i] ;
	    // }
	    // System.out.println(smell + ":" + Function.schaffer(f));//..... 
	    //System.exit(1) ;//......
	}
    }
}