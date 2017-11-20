import java.util.ArrayList ;

public class MyFoa{
    private ArrayList<Fly> flyList ;
    private int sizePop ;                //��Ⱥ������
    private int maxGen ;                 //�����������

    public MyFoa(int sizePop,int maxGen){
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }

    private void init(){       //���ɹ�ӬȺ�岢��ֵ
	flyList = new ArrayList<>() ;
	for(int i = 0; i < sizePop; i ++){
	    flyList.add(generateFly()) ;
	}
    }

    //������������Ӭ
    private Fly generateFly(){
	int dimension = 30 ;   //���ƹ�Ӭ����ά��
	Fly fly = new Fly() ;
	double[] d = new double[dimension] ;
	double[] w = new double[dimension] ;
	for(int i = 0; i < dimension; i ++){
	    w[i] = 0 ;
	    d[i] = Math.random() * 200 - 100 ;
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
	int gen = 0 ;                                  //��������
	double[] bestS = new double[maxGen] ;          //��¼ÿ�ε���ȫ������

	while(gen < maxGen){
	    train(flyList,gen) ;
	    for(Fly f : flyList){                      //Ѱ��Ⱥ�����Ÿ���
		double smell = Function.sphere(f) ;
		if(smell < bestSmell){
		    bestSmell = smell ;
		    bestFly = f.clone() ;
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
	    	    coor[i] = bestCoor[i] + Math.random() * 2 - 1 + weight[i];
	    	}
	    }

	    bestS[gen] = bestSmell ;
	    gen ++ ;
	}
	return bestS ;
    }
    //ѵ������
    private void train(ArrayList<Fly> flyList,int gen){
	double[] coor;
	double[] weight ;
	double tmp = (150 - gen) * 0.01 ;
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
		// while(tmp < 10e-5){
		//     coor[i] += tmp ;
		//     if(Function.rastrigin(f) < smell){
		// 	coor[i] -= tmp ;

		// 	break ;
		//     }
		//     coor[i] -= tmp ;

		//     tmp *= -1 ;
		//     coor[i] += tmp ;
		//     if(Function.rastrigin(f) < smell){
		// 	coor[i] -= tmp ;

		// 	break ;
		//     }
		    
		//     tmp /= 2 ;
		// }
	    }
	}
    }
}