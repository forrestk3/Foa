import java.util.ArrayList ;
public class Foa{
    ArrayList<Fly> flyList ;
    private int sizePop ;
    private int maxGen ;
    private int dimension ;
    private double high ;
    private double low ;
    public Foa(int sizePop,int maxGen) {
	this.sizePop = sizePop ;
	this.maxGen = maxGen ;
    }
    //��ʼ��ά�ȣ������ռ䣬���γɹ�Ӭ
    private void init(){
	dimension = 30 ;
	high = 100 ;
	low = -100 ;
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
	Fly bestFly = flyList.get(0) ;
	bestSmell = Function.sphere(bestFly) ;
	while(maxGen-- > 0){
	    for(Fly f : flyList){     //Ѱ������λ�ù�Ӭ
		double smell = Function.sphere(f) ;
		if(smell < bestSmell){
		    bestSmell = smell ;
		    bestFly = f.clone() ;
		}
	    }	   
	    for(Fly f : flyList){
		double[] d = bestFly.getCoordinate() ;
		double[] coor = f.getCoordinate() ;
		for(int i = 0; i < coor.length; i ++){
		    coor[i] = d[i] + Math.random() * 2 - 1 ;
		}
	    }
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