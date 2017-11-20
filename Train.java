import java.util.ArrayList ;
public class Train{
    public  void train(ArrayList<Fly> flyList,int gen){
	double[] coor;
	double[] weight ;
	double tmp = (150 - gen) * 0.01 ;


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