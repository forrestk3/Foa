public class Function{
    static FunctionName function ;
    public static void setFunction(FunctionName f){function = f ;}
    public static void test(){
	switch(function){
	case Sphere:System.out.println("Sphere") ;break ;
	case Rastrigin:System.out.println("Ras") ;break ;
	}
    }
    public static double sphere(Fly f){        //-l00 ~ 100
	double[] d = f.getCoordinate() ;
	double sum = 0 ;
	for(int i = 0; i < d.length; i ++){
	    sum += d[i] * d[i] ;
	}
	return sum ;
    }
    public static double dist(Fly f1,Fly f2){
	double[] d1 = f1.getCoordinate() ;
	double[] d2 = f2.getCoordinate() ;
	double sum = 0 ;
	for(int i = 0; i < d1.length; i ++){
	    sum += (d1[i] - d2[i])*(d1[i] - d2[i]) ;
	}
	return Math.sqrt(sum) ;
    }
    public static double rastrigin(Fly f){
	double[] d = f.getCoordinate() ;
	return rastrigin(d) ;
    }
 //当自变量在-5.12~5.12之间时有全局最小值0,在0处取得

    public static double rastrigin(double[] d){//[-5.12,5.12]
	double sum = 0 ;
	for(int i = 0; i < d.length; i ++){
	    sum += d[i] * d[i] - 10 * Math.cos(2 * Math.PI * d[i]) + 10 ;
	}
	return sum ;
    }
    public static double griewank(Fly f){
	double[] d = f.getCoordinate() ;
	return griewank(d) ;
    }
    public static double griewank(double[] d){ //[[-600,600]
	double sum1 = 0 ;
	double sum2 = 1 ;
	for(int i = 0; i < d.length; i ++){
	    sum1 += d[i] * d[i] ;
	    sum2 *= Math.cos(d[i] / Math.sqrt(i + 1)) ;
	}
	return (1.0/4000*sum1 - sum2 + 1) ;
    }
    public static double ackley(Fly f){
	double[] d = f.getCoordinate() ;
	return ackley(d) ;
    }
    public static double ackley(double[] d){//[-32,32]
	double sum1 = 0;
	double sum2 = 0 ;
	for(int i = 0; i < d.length; i ++){
	    sum1 += d[i] * d[i] ;
	    sum2 += Math.cos(2 * Math.PI * d[i]) ;
	}
	sum1 = -0.2 * Math.sqrt(1.0/d.length * sum1) ;
	sum2 = 1.0/d.length * sum2 ;
	double result = -20 * Math.exp(sum1) - Math.exp(sum2) + Math.E + 20;
	return result ;
    }
    public static double rosenbrock(Fly f){
	double[] d = f.getCoordinate() ;
	return rosenbrock(d) ;
    }
    public static double rosenbrock(double[] d){//[-30,30]
	double sum = 0 ;
	for(int i = 0; i < d.length - 1; i ++){
	    sum += (d[i] - 1)*(d[i] - 1) + 100 * (d[i+1] - d[i]) * (d[i+1] - d[i]) ;
	}
	return sum ;
    }
    public static double schaffer(Fly f){
	double[] d = f.getCoordinate() ;
	return schaffer(d) ;
    }
    public static double schaffer(double[] d){//[-100,100]
	double tmp = d[0] * d[0] + d[1] * d[1] ;
	double tmp1 = d[0] * d[0] - d[1] * d[1] ;
	double result = (Math.sin(tmp1) * Math.sin(tmp1) - 0.5)/
	    Math.pow(1 + 0.001*tmp,2) - 0.5 ;
	return result ;
    }

}

enum FunctionName{Sphere,Rastrigin}