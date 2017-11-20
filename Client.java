public class Client{
    public static void main(String[] args){
	double[] d = {2,2} ;
	// System.out.println(Function.griewank(d) +":"+d[0]) ;
	// System.out.println(Function.ackley(d)) ;
	// System.out.println(Function.rosenbrock(d)) ;
	// System.out.println(Function.schaffer(d)) ;
	// System.exit(1) ;


	Foa foa = new Foa(30,150) ;
	double[] foaD = foa.execute() ;
	//	DDSCFOA ddscfoa = new DDSCFOA(30,150) ;
	//	double[] ddscD = ddscfoa.execute() ;
	MyFoa myFoa = new MyFoa(30,150) ;
	double[] bcD = myFoa.execute() ;
	MyFoaDB myfoadb = new MyFoaDB(30,150) ;
	double[] dbD = myfoadb.execute() ;
	IFoa iFoa = new IFoa(30,150) ;
	double iFoaD[] = iFoa.execute() ;
	for(int i = 0; i < foaD.length; i ++){
	    System.out.println(String.format("%1.2e",foaD[i]) + "~~~" + String.format("%1.2e",bcD[i]) + "~~~" + String.format("%1.2e",dbD[i]) + "~~~" + String.format("%1.2e",iFoaD[i])+ "----->"+i) ;
	}
	//	MyLine graph = new MyLine(myD) ;
    }
}
