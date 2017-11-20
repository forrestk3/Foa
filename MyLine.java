import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
import java.util.ArrayList ;
public class MyLine extends JFrame{
    private double[] data ;
    public MyLine(double[] data){
	super("MyLine") ;
	this.data = data ;
	setSize(550,500) ;
	show() ;
    }
    public void paint(Graphics g){
       	g.setColor(Color.black) ;
	//	g.drawLine(30,0,30,480) ;
	//	g.drawLine(30,480,470,480) ;
	for(int i = 0; i < data.length - 1; i ++){
	    int a1 = i * 3 + 30 ;
	    int a2 = a1 + 3 ;
	    int b1 = -(int)sigmoid(data[i]) * 480 + 480 + 0 ;
	    int b2 = -(int)sigmoid(data[i+1])*480 +480 + 0;
	    //  System.out.println(a1 + ":" + a2 + ":" + b1 + ":" + b2 + ":" + data[i] + ":" + sigmoid(data[i+1])) ;
	    g.drawLine(a1,b1,a2,b2) ;
	}
	g.drawLine(0,200,100,200) ;
    }
    public double sigmoid(double t){
	return 1.0 / (1 + Math.exp(-t)) ;
    }
}