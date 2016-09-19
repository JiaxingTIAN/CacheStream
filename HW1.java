import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//The constructor
//should set all three coefficients to zero, and
//another method should allow you to change these
//coefficients. There should be accessor methods to
//retrieve the current values of the coefficients. There
//should also be a method to allow you to “evaluate”
//the quadratic expression at a particular value of x
public class Quadratic {
	//Three Coefficients
	double a, b, c;
	//Constructor asked by text book to set all coefficients to zero
	public Quadratic(){
		this.a = 0;
		this.b = 0;
		this.c = 0;
	}
	//Constructor to set three coefficients
	public Quadratic(double a, double b, double c){
		this.a = a;
		this.b = b;
		this.c = c;
	}
	//Three methods to retrieve and change each coefficient
	public double getA() {
		return a;
	}
	public void setA(double a) {
		this.a = a;
	}
	public double getB() {
		return b;
	}
	public void setB(double b) {
		this.b = b;
	}
	public double getC() {
		return c;
	}
	public void setC(double c) {
		this.c = c;
	}
	//Evaluate the quadratic expression
	public double evaluate(double x){
		return a*x*x + b*x + c;
	}
	//Sum method to return a new Quadratic
	public static Quadratic sum(Quadratic q1, Quadratic q2){
		Quadratic q = new Quadratic(q1.a+q2.a, q1.b+q2.b, q1.c+q2.c);
		return q;
	}
	//Scale method to return the new Quadratic
	public static Quadratic scale(double r, Quadratic q){
		Quadratic quad = new Quadratic(q.a*r, q.b*r, q.c*r);
		return quad;
	}
	//Display method for display quadratic expression
	public void displayQuadratic(){
		StringBuilder sb = new StringBuilder();
		if(a != 0){
			sb.append(a).append("x^2");
		}
		if(this.b != 0){
			if(b > 0 && a != 0)
				sb.append(" + ");
			else if(a != 0 && b < 0)
				sb.append(" - ");
			else if( a == 0 && b < 0)
				sb.append("-");
				
			sb.append(Math.abs(this.b)).append("x");
		}
		if(this.c != 0){
			if(this.c > 0)
				sb.append(" + ");
			else
				sb.append(" - ");
			sb.append(Math.abs(c));
		}
		System.out.println(sb.toString());
	}
	
	public static void main(String[] args) throws IOException {
		double a, b, c;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		//Create p1, p2 and p3
		System.out.println("Please enter three coefficient a, b, and c separated by a space: ");
		String input1 = br.readLine();
		String[] coe1 = input1.split(" ");
		a = Double.valueOf(coe1[0]);
		b = Double.valueOf(coe1[1]);
		c = Double.valueOf(coe1[2]);
		Quadratic q1 = new Quadratic(a, b, c);
		q1.displayQuadratic();
		
		System.out.println("Please enter three coefficient a, b, and c separated by a space: ");
		String input2 = br.readLine();
		String[] coe2 = input2.split(" ");
		a = Double.valueOf(coe2[0]);
		b = Double.valueOf(coe2[1]);
		c = Double.valueOf(coe2[2]);
		Quadratic q2 = new Quadratic(a, b, c);
		q2.displayQuadratic();
		
		System.out.println("Please enter three coefficient a, b, and c separated by a space: ");
		String input3 = br.readLine();
		String[] coe3 = input3.split(" ");
		a = Double.valueOf(coe3[0]);
		b = Double.valueOf(coe3[1]);
		c = Double.valueOf(coe3[2]);
		Quadratic q3 = new Quadratic(a, b, c);
		q3.displayQuadratic();
		//sum of p1 and p2
		Quadratic s = sum(q1, q2);
		s.displayQuadratic();
		
		System.out.println("Please input the scale factor: ");
		String in = br.readLine();
		double r = Double.valueOf(in.trim());
		Quadratic fac = scale(r, q3);
		fac.displayQuadratic();
	}
}
