package jiaxing.tian.bu;

import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class CacheSim extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	
	int addr = 0;		//Request address
	int cacheAddr = 0;	//cache address
	int length = 0;		//length of the stream
	int Scheme = 0;		//Enter the scheme wanted to simulate.
	int noCache = 0; 	//recode the number of request delivered from cache
	float cache_frac = 0;
	int numCache = 0;	//The number of data block in the cache
	int maxCache = 0;	//recode the maximum cache number
	float [] avg_cachef = new float[51];
	float [] avg_MaxCache = new float[51];
	float [] avg_MaxAux = new float[51];
	
	ArrayList<Integer> request = new ArrayList<Integer>();	//Array list to store request value
	ArrayList<Integer> cache = new ArrayList<Integer>();	//array list to store cache value 
	
	JComboBox<Integer> t1 = new JComboBox<Integer>();
	JLabel t2 = new JLabel();
	JLabel t3 = new JLabel();
	JLabel t4 = new JLabel();
	JLabel t5 = new JLabel();
	JLabel t6 = new JLabel();
	JTextField t7 = new JTextField("20");
	JButton t8 = new JButton("Plot Simulation");
	JTextArea t9 = new JTextArea("Jiaxing TIAN (U98335741)---HW0 \n"
			+ "Please use the combobox meun to select\n"
			+ "different simulation scheme. Without click\n"
			+ "the simulation button, it use the defualt\n"
			+ "stream loaded from text file stream.txt.\n"
			+ "After plot simulation is clicked, 100 streams\n"
			+ "with the specified single stream length is\n"
			+ "generated for simulation and the graph is\n"
			+ "shown in separate windows");
	
	//Constructor for GUI output
	public CacheSim(){
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		p1.setLayout(new GridLayout(6,2,3,3));
		p2.setLayout(new BorderLayout());
		
		t1.addItem(1);t1.addItem(2);t1.addItem(3);t1.addItem(4);t1.addItem(5);
		t1.setSelectedIndex(0);
		t1.addActionListener(this);
		t1.setActionCommand("selectScheme");
		t8.addActionListener(this);
		
		p1.add(new JLabel("Simulation Scheme:"));
		p1.add(t1);
		p1.add(new JLabel("Elements in Cache:"));
		p1.add(t2);
		p1.add(new JLabel("Maximum elements in cache:"));
		p1.add(t3);
		p1.add(new JLabel("Maximum elements in Queue:"));
		p1.add(t4);
		p1.add(new JLabel("Request delivered from cache:"));
		p1.add(t5);
		p1.add(new JLabel("Cache-frac:"));
		p1.add(t6);
		p2.add(p1, BorderLayout.NORTH);
		p2.add(new JLabel("Enter single stream length:"),BorderLayout.WEST);
		p2.add(t7,BorderLayout.CENTER);
		p2.add(t8, BorderLayout.EAST);
		
		add(p2,BorderLayout.WEST);
		add(t9,BorderLayout.CENTER);
		//add(new JLabel(new ImageIcon("Graph.jpg")),BorderLayout.EAST);
		
	}
	
	

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		CacheSim sim = new CacheSim();
		sim.setTitle("Cache Simulation");
		//sim.setSize(500,220);
		sim.pack();
		sim.setLocationRelativeTo(null);
		sim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sim.setVisible(true);
		
		int[] values =readfile(); 			//read the stream and return value as integer array
		sim.simulate(values);

	}
	public void simulate(int[] values){
				
		Random random = new Random();							//generate random number for scheme 5
		
		
		for(addr=0; addr<values.length;addr++){
			
			if(values[addr]>0){
				request.add(values[addr]);	//request x[addr]=k is made
				length++;					//Record the total length of the request stream
				
				//Scheme 1
				if(Scheme == 0){
					//Find if the request is cached before
					if(cache.size()!=0){
						cacheAddr = cache.lastIndexOf(values[addr]);	
						if(cacheAddr!=-1){
							cache.remove(cacheAddr);
							numCache--;
							noCache++;
						//if cached before, delivered the content and evict from cache
						}
					}
					cache.add(values[addr]+1);	//cache k+1
					numCache++;
				}
				
				//Scheme 2
				else if (Scheme == 1){
					//check if cached previously
					cacheAddr = cache.lastIndexOf(values[addr]);	
					if(cacheAddr == -1){	
						//not cached before
						//check if k-1 is requested previously
						if(request.contains(values[addr]-1)){
							cache.add(values[addr]+1);
							numCache++;
						}
					}
					else{
						//if cached before, delivered the content and evict from cache
						//cache k+1
						cache.remove(cacheAddr);						
						cache.add(values[addr]+1);	//cache k+1	
						noCache++;
					}
											
				}
				
				//Scheme 3
				else if (Scheme == 2){
					
					//check if cached previously
					cacheAddr = cache.lastIndexOf(values[addr]);	
					if(cacheAddr == -1){	
						//not cached before
						//check if k-1 and k-2 is requested previously
						if(request.contains(values[addr]-1)&&request.contains(values[addr]-2)){
							cache.add(values[addr]+1);
							numCache++;
						}
					}
					else{
						//if cached before, delivered the content and evict from cache
						//cache k+1
						cache.remove(cacheAddr);						
						cache.add(values[addr]+1);	//cache k+1
						noCache++;
					}
				}
				
				//Scheme 4
				else if (Scheme == 3){
					
					//check if cached previously
					cacheAddr = cache.lastIndexOf(values[addr]);	
					if(cacheAddr == -1){	
						//not cached before
						//check cache k+1						
						cache.add(values[addr]+1);
						numCache++;
						
					}
					else{
						//if cached before, delivered the content and evict from cache
						cache.remove(cacheAddr);	
						numCache--;
						noCache++;
					}
				}
				
				//Scheme 5
				else if (Scheme == 4){
					
					//check if cached previously
					cacheAddr = cache.lastIndexOf(values[addr]);	
					if(cacheAddr != -1){	
						//Cached previously						
						cache.remove(cacheAddr);	
						noCache++;
						numCache--;
						
					}
					int rand = random.nextInt(10000);
					cache.add(rand);
					numCache++;
				}
				
				
				//End of simulation schemes
				if(numCache>maxCache){
					maxCache = numCache;
				}
			}
		}
		
		cache_frac = (float)noCache/(float)length;
		
		t2.setText(""+ numCache);
		t3.setText(""+ maxCache);
		t4.setText(""+ length);
		t5.setText(""+ noCache);
		t6.setText(""+ cache_frac);
		
		
		System.out.println("Simulation scheme: " + (Scheme+1));
		System.out.println("Maximum number of elements in the auxiliury data structure: " + length);
		System.out.println("Number of request delivered from cache: " + noCache);
		System.out.println("Cache number: " + numCache);
		System.out.println("Maximum elemsnts in Cache: " + maxCache);		
		System.out.println("Cache-frac=" + cache_frac);
		
		for(int i=0;i<cache.size();i++){
			System.out.println(cache.get(i));
		}
		
		
	}
	
	
	//Generate 100 stream and return an interleaving stream as integer array
	public int[] getStream(int len,int p){
		
		int[][] stream = new int[100][len];		//100 stream with predicability of p
		int[] interStream = new int[100*len];
		Random ran = new Random();
		
		
		//loop for generate the 100 stream
		for(int i=0;i<100;i++){
			
			stream[i][0] = ran.nextInt(10000);	//initial all the request
			for(int j=1;j<len;j++){
				//Generate the next integer r[i+1]=ri + 1
				
				int prob = ran.nextInt(100);
				if(prob<=p){
					stream[i][j] = stream[i][j-1]+1;
				}
				else{
					stream[i][j] = ran.nextInt(10000);
				}				
			}
		}
		
		for(int i=0;i<100;i++){			
			for(int j=0;j<len;j++){
				interStream[i*len + j] = stream[i][j];
			}
		}		
		return interStream;
	
	}
	
	//method to read the stream file
	public static  int[] readfile()
	{
	    int[] num =new int[1000];
	            
	    int i=0;

	    Scanner s = null;
	    try {   
	         s = new Scanner(new BufferedReader(new FileReader("stream.txt")));   	            
	         
	         while (s.hasNext()) {
	        	 
	            if (s.hasNextInt()) {
	            	num[i]=s.nextInt();   
	            	i++;
	            }
	         }
	 
	     } 
	     catch (FileNotFoundException e) {   
	         // TODO Auto-generated catch block   
	         e.printStackTrace();   
	     }    
	         	     
	     finally {   
	         if (s != null) {   
	             s.close();   
	         }   
	     }   
	     
	    return num;
	             
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int[] values = null;
		String cmd = e.getActionCommand();
		if(cmd.equals("selectScheme")){
			
			Scheme = t1.getSelectedIndex();
			values =readfile(); 			//read the stream and return value as integer array
			simulate(values);
			length = 0;
			noCache = 0;
			numCache = 0;
			maxCache = 0;
			cache.clear();
			request.clear();
			
		}
		else if(cmd.equals("Plot Simulation")){
			Scheme = t1.getSelectedIndex();
			int len = Integer.parseInt(t7.getText());
			//plot the graph
			for(int k=0;k<=50;k++){
				//Get the generated stream with predictability 0:0.2:1
				values = getStream(len,2*k);
				simulate(values);
				avg_cachef[k] = cache_frac;
				avg_MaxAux[k] = values.length;
				avg_MaxCache[k] = numCache;
				length = 0;
				noCache = 0;
				numCache = 0;
				maxCache = 0;
				cache.clear();
				request.clear();
			}
			
			DrawLine draw = new DrawLine(avg_cachef, Scheme);			
			JFrame f = new JFrame();
	        f.setTitle("Cache_frac Simulation Scheme " + (Scheme+1));
	        f.add(draw);
	        f.setSize(500,500);
	        f.setLocation(200,200);
	        f.setVisible(true);
	        DrawLine draw1 = new DrawLine(avg_MaxCache,Scheme);			
			JFrame c = new JFrame();
	        c.setTitle("Average Max Cache Simulation Scheme " + (Scheme+1));
	        c.add(draw1);
	        c.setSize(500,500);
	        c.setLocation(700,200);
	        c.setVisible(true);
	        DrawLine draw2 = new DrawLine(avg_MaxAux,Scheme);			
			JFrame a = new JFrame();
	        a.setTitle("Average Max Auxiliary Simulation Scheme " + (Scheme+1));
	        a.add(draw2);
	        a.setSize(500,500);
	        a.setLocation(1200,200);
	        a.setVisible(true);
	        
		}
		
	}
	
}
