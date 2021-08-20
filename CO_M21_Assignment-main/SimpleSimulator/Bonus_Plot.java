package simulator;

import java.awt.*;  
import javax.swing.*;  
import java.awt.geom.*;  

public class Bonus_Plot extends JPanel{
	
//	We tried making the plot using java
//	However due to the complicated nature of plotting in java, 
//	We have created a text file and used it to plot using python
	
	public static Simulator sim = new Simulator();
	public static int mar = 50;
	
//	Video
	private static final int WIDTH = 400;
	private static final int HEIGHT = 400;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Simulator.main(args);
		
//		Create a frame
		JFrame frame =new JFrame("Memory v/s Cycle");
		
//		Video
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(new Bonus_Plot());
		frame.setVisible(true);
		
//	    frame.add(new Bonus_Plot());
//	    frame.setSize(Simulator.PCList.size() + mar, Simulator.PCList.size() + mar);
//	    frame.setLocation(mar, mar);
//	    frame.setVisible(true);
	}
	
    protected void paintComponent(Graphics g){
    	
        super.paintComponent(g);
        
        Graphics2D g1 = (Graphics2D)g;
        setBackground(Color.WHITE);
        
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g1.setPaint(Color.BLACK);
        
        int width = getWidth();
        int height = getHeight();
        
//        x1, y1, x2, y2
        
//        Y axis
//        (50, 50) to (50, height - 50) 
        g1.draw(new Line2D.Double(mar, mar, mar, height - mar)); 
        
//        X axis
//        (50, height - 50) to (width - 50, height - 50) 
        g1.draw(new Line2D.Double(mar, height - mar, width - mar, height - mar)); 
        
        int x1 = 0;
        int y1 = 0;
        for (int cycle = 0; cycle < Simulator.PCList.size(); cycle++) {
        	int mem_addr = Simulator.PCList.get(cycle);
//        	g1.drawLine(mar + cycle, height - mar - mem_addr, mar + cycle, height - mar - mem_addr);
			g1.drawLine(mar + x1, height - mar - y1, mar + cycle, height - mar - mem_addr);
			x1 = cycle;
			y1 = mem_addr;
        }  
    }  
}
 