import java.io.File ;
import java.io.BufferedReader ;
import java.io.FileReader ;
import java.util.ArrayList ;
import java.util.Set ;
import java.util.HashSet ;
import java.util.Comparator ;
import java.util.* ;
public class GDBSCAN{
    //    private ArrayList<MyPoint> datas ;
    private ArrayList<Fly> datas ;
    private int minPts ;
    private String filePath ;
    public GDBSCAN(int minPts,String filePath){
	this.minPts = minPts ;
	this.filePath = filePath ;
	//	test() ;
    }
    public GDBSCAN(int minPts){
	this.minPts = minPts ;
    }
    private void init(){
	//	datas = getDatas(filePath) ;
    }
    public void setDatas(ArrayList<Fly> flyList){
	datas = flyList ;
    }
    private ArrayList<MyPoint> getDatas(String filePath){
	File file = new File(filePath) ;
	ArrayList<MyPoint> tmpDatas = new ArrayList<>() ;
	try{
	    BufferedReader in = new BufferedReader(new FileReader(file)) ;
	    String str = null ;
	    String[] strData = null ;
	    double[] tmpData = null ;
	    while((str = in.readLine()) != null){
		strData = str.split(" ") ;
		tmpData = new double[strData.length] ;
		for(int i = 0; i < strData.length; i ++){
		    tmpData[i] = Double.parseDouble(strData[i]) ;
		}
		tmpDatas.add(new MyPoint(tmpData)) ;
	    }
	}catch(Exception e){}
	return tmpDatas ;
    }
    public ArrayList<Set<Fly>> execute(){
	int classify = 0 ;
	ArrayList<Set<Fly>> clusters = new ArrayList<>() ;
	for(Fly p : datas){
	    if(!p.isProcess()){
		Set<Fly> tmpNeighbor = new HashSet<>();
		double tmpEps = getNeighbor(p,datas,tmpNeighbor) ;
		if(isNoise(tmpEps,tmpNeighbor)){
		    System.out.println("Noise:" + p) ;   
		}
		else{		 
		    tmpNeighbor.add(p) ;
		    markProcessed(tmpNeighbor) ;
		    clusters.add(tmpNeighbor) ;
		    classify ++ ;
		}
	    }
	}
	//System.out.println(clusters.size()) ;
	// for(Set<Fly> mp : clusters)
	//     System.out.println(mp) ;
	mergeClusters(clusters) ;
	System.out.println(clusters.size()) ;
	for(Set<Fly> flyList:clusters){
	    System.out.println(flyList.size() + "~") ;
	}
	System.exit(1) ;
	// for(Set<Fly> mp : clusters){
	//     System.out.println(mp) ;
	// }
	
	return clusters ;
    }
    //获得一个元素的K个近邻，并将其放入tmpNeighbor集合中
    private double getNeighbor(Fly p,ArrayList<Fly> datas,Set<Fly> tmpNeighbor){
	int k = minPts - 1 ;
	TreeSet<Double> tmpEps = new TreeSet<>(new Comparator<Double>(){
		public int compare(Double o1,Double o2){
		    double diff = o1 -  o2 ;
		    if(diff > 0)
			return -1 ;
		    if(diff < 0)
			return 1 ;
		    return 0 ;
		}
	    }) ;            //降序
	TreeMap<Double,Fly> neighbor = new TreeMap<>() ; //TreeMap元素自动升序排序
	double tmpDis = 0 ;
	double maxDis = 0 ;
	for(Fly mp : datas){
	    if(mp.equals(p))
		continue ;
	    tmpDis = distance(mp,p) ;
	    neighbor.put(tmpDis,mp) ;
	    tmpEps.add(tmpDis) ;
	    if(tmpEps.size() > k)
		tmpEps.pollFirst() ;
	}
	for(Map.Entry entry : neighbor.entrySet()){
	    if(k -- > 0)
		tmpNeighbor.add((Fly)entry.getValue()) ;
	    else break ;
	}
	return tmpEps.first() ;
    }
    //用EPS是否远大于簇内其他元素的平均距离判断是否是游离点,是则返回TRUE
    private boolean isNoise(double tmpEps,Set<Fly> tmpNeighbor){
	double sum = 0 ;
	for(Fly mp : tmpNeighbor){
	    sum += avgDistance(mp,datas) ;
	}
	sum = sum / (minPts - 1) ;
	if(sum * 2 < tmpEps)
	    return true ;
	return false ;
    }
    //标记为已处理
    private void markProcessed(Set<Fly> tmpCluster){
	for(Fly p : tmpCluster){
	    p.process() ;
	}
    }
    //将有共同元素的聚类进行合并
    private void mergeClusters(ArrayList<Set<Fly>> clusters){
	Set<Fly> tmp1 = null,tmp2 = null ;
	HashSet<Fly> result = new HashSet<>() ;
	for(int i = 0; i < clusters.size(); i ++){
	    for(int j = i + 1; j < clusters.size(); j ++ ){
		result.clear() ;
		tmp1 = clusters.get(i) ;
		result.addAll(tmp1) ;

		tmp2 = clusters.get(j) ;
		result.retainAll(tmp2) ;
		if(result.size() != 0){
		    tmp1.addAll(tmp2) ;
		    clusters.remove(tmp2) ;
		    j -- ;
		}
	    }
	}
    }
    //计算该点与其K近邻的平均距离
    private double avgDistance(Fly p,ArrayList<Fly> datas){
	TreeSet<Double> dis = new TreeSet<>(new Comparator<Double>(){
		public int compare(Double o1,Double o2){
		    double diff = o1 - o2 ;
		    if(diff > 0)
			return -1 ;
		    if(diff < 0)
			return 1 ;
		    return 0 ;
		}
	    }) ;//降序
	double sum = 0 ;
	for(Fly mp : datas){
	    if(mp.equals(p))
		continue ;
	    dis.add(distance(mp,p)) ;
	    if(dis.size() > minPts - 1)
		dis.pollFirst() ;
	}
	for(double d : dis){
	    sum += d ;
	}
	return sum / (minPts - 1) ;
    }
    //计算两点距离
    private double distance(Fly p1,Fly p2){
	double[] d1 = p1.getCoordinate() ;
	double[] d2 = p2.getCoordinate() ;
	double sum = 0 ;
	for(int i = 0; i < d1.length; i ++){
	    sum += (d1[i] - d2[i])*(d1[i] - d2[i]) ;
	}
	return Math.sqrt(sum) ;
    }
    public void test(){	
	//	init() ;
	System.out.println(datas.size()) ;
	//	System.exit(1) ;
       	execute() ;
    }
   
}