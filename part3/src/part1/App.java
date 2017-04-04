package part1;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

class UserInterface {

   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   
   public static int enterkey = 0;
   public static JButton one = new JButton("1");
   public static JButton two = new JButton("2");
   public static JButton three = new JButton("3");
   public static JButton four = new JButton("4");
   public static JButton five = new JButton("5");
   public static JButton six = new JButton("6");
   public static JButton seven = new JButton("7");
   public static JButton eight = new JButton("8");
   public static JButton nine = new JButton("9");
   public static JButton zero = new JButton("0");
   public static JButton add = new JButton("+");
   public static JButton sub = new JButton("-");
   public static JButton mul = new JButton("*");
   public static JButton div = new JButton("\\");
   public static JTextField output = new JTextField();

   public UserInterface(){
      prepareGUI();
   }

   public void main(String[] args){
	  UserInterface userinterface = new UserInterface();  
	  userinterface.showEventDemo();       
   }
      
   private void prepareGUI(){
      mainFrame = new JFrame("Java SWING Examples");
      mainFrame.setSize(400,650);
      mainFrame.setLayout(new GridLayout(3, 1));
      //mainFrame.requestFocus();

      headerLabel = new JLabel("",JLabel.CENTER );
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
	        System.exit(0);
         }        
      });    
      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.setVisible(true);  
   }

   private void showEventDemo(){
      headerLabel.setText("Calculator"); 
      
      JPanel panel0 = new JPanel();
      JPanel panel1 = new JPanel();
      JPanel panel2 = new JPanel();
      JPanel panel3 = new JPanel();
      JPanel function = new JPanel();
      
      output.setText("0");
 
      one.setActionCommand("one");
      two.setActionCommand("two");
      three.setActionCommand("three");
      four.setActionCommand("four");
      five.setActionCommand("five");
      six.setActionCommand("six");
      seven.setActionCommand("seven");
      eight.setActionCommand("eight");
      nine.setActionCommand("nine");
      zero.setActionCommand("zero");
      add.setActionCommand("add");
      sub.setActionCommand("sub");
      mul.setActionCommand("mul");
      div.setActionCommand("div");
      output.setActionCommand("output");

      one.addActionListener(new ButtonClickListener()); 
      two.addActionListener(new ButtonClickListener()); 
      three.addActionListener(new ButtonClickListener()); 
      four.addActionListener(new ButtonClickListener()); 
      five.addActionListener(new ButtonClickListener()); 
      six.addActionListener(new ButtonClickListener()); 
      seven.addActionListener(new ButtonClickListener()); 
      eight.addActionListener(new ButtonClickListener()); 
      nine.addActionListener(new ButtonClickListener()); 
      zero.addActionListener(new ButtonClickListener());
      add.addActionListener(new ButtonClickListener());
      sub.addActionListener(new ButtonClickListener());
      mul.addActionListener(new ButtonClickListener());
      div.addActionListener(new ButtonClickListener());

      panel0.add(one);
      panel0.add(two);
      panel0.add(three);
      panel1.add(four);
      panel1.add(five);
      panel1.add(six);
      panel2.add(seven);
      panel2.add(eight);
      panel2.add(nine);
      panel3.add(zero);
      function.add(add);
      function.add(sub);
      function.add(mul);
      function.add(div);
      
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
      
      panel.add(output);
      panel.add(panel0);
      panel.add(panel1);
      panel.add(panel2);
      panel.add(panel3);
      panel.add(function);
      
      controlPanel.add(panel); 

      mainFrame.setVisible(true); 
      mainFrame.requestFocus();
      mainFrame.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
        	  int keyCode = e.getKeyCode();
              if (keyCode == KeyEvent.VK_ENTER){
            	  enterkey = 1;
              }
          }});
   }

   private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         if( command.equals( "OK" ))  {
            statusLabel.setText("Ok Button clicked.");
         }
         else if( command.equals( "Submit" ) )  {
            statusLabel.setText("Submit Button clicked."); 
         }
         else  {
            statusLabel.setText("Cancel Button clicked.");
         }  	
      }		
   }
}

class highlight implements Runnable{
	public void run(){
		while(true){
			UserInterface.output.setText(String.valueOf(App.getAcc()));
			if(App.isFlag() == false){
				if(App.getI()==0){
					UserInterface.one.setBackground(Color.red);
					UserInterface.zero.setBackground(null);
				}
				else if(App.getI()==1){
					UserInterface.two.setBackground(Color.red);
					UserInterface.one.setBackground(null);
				}
				else if(App.getI()==2){
					UserInterface.three.setBackground(Color.red);
					UserInterface.two.setBackground(null);
				}
				else if(App.getI()==3){
					UserInterface.four.setBackground(Color.red);
					UserInterface.three.setBackground(null);
				}
				else if(App.getI()==4){
					UserInterface.five.setBackground(Color.red);
					UserInterface.four.setBackground(null);
				}
				else if(App.getI()==5){
					UserInterface.six.setBackground(Color.red);
					UserInterface.five.setBackground(null);
				}
				else if(App.getI()==6){
					UserInterface.seven.setBackground(Color.red);
					UserInterface.six.setBackground(null);
				}
				else if(App.getI()==7){
					UserInterface.eight.setBackground(Color.red);
					UserInterface.seven.setBackground(null);
				}
				else if(App.getI()==8){
					UserInterface.nine.setBackground(Color.red);
					UserInterface.eight.setBackground(null);
				}
				else if(App.getI()==9){
					UserInterface.zero.setBackground(Color.red);
					UserInterface.nine.setBackground(null);
					App.setI(-1);
				}
				App.setI(App.getI()+1);
			}
			else{
				if(App.getI() == 0){
					UserInterface.add.setBackground(Color.red);
					UserInterface.div.setBackground(null);
				}
				else if(App.getI() == 1){
					UserInterface.sub.setBackground(Color.red);
					UserInterface.add.setBackground(null);
				}
				else if(App.getI() == 2){
					UserInterface.mul.setBackground(Color.red);
					UserInterface.sub.setBackground(null);
				}
				else if(App.getI() == 3){
					UserInterface.div.setBackground(Color.red);
					UserInterface.mul.setBackground(null);
					App.setI(-1);
				}
				App.setI(App.getI() + 1);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class changefunction implements Runnable{
	public void run(){
		while(true){
			if(UserInterface.enterkey == 1){
				UserInterface.enterkey = 0;
				if(App.isFlag() == true){
					App.setPrev(App.getI());
					App.setI(0);
					App.setFlag(false);
					UserInterface.add.setBackground(null);
					UserInterface.sub.setBackground(null);
					UserInterface.mul.setBackground(null);
					UserInterface.div.setBackground(null);
				}
				else{
					if(App.getPrev() == 0)
						App.setAcc(App.getAcc() / App.getI());
					else if(App.getPrev() == 1)
						App.setAcc(App.getAcc() + App.getI());
					else if(App.getPrev() == 2)
						App.setAcc(App.getAcc() - App.getI());
					else
						App.setAcc(App.getAcc() * App.getI());
					App.setI(0);
					App.setFlag(true);
					UserInterface.one.setBackground(null);
					UserInterface.two.setBackground(null);
					UserInterface.three.setBackground(null);
					UserInterface.four.setBackground(null);
					UserInterface.five.setBackground(null);
					UserInterface.six.setBackground(null);
					UserInterface.seven.setBackground(null);
					UserInterface.eight.setBackground(null);
					UserInterface.nine.setBackground(null);
					UserInterface.zero.setBackground(null);
				}
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class App {
	private static int i;
	private static int acc;
	private static boolean flag;
	private static int prev;
	public static void main(String[] args){
		App app = new App();
		App.setI(0);
		App.setAcc(0);
		App.setFlag(false);
		App.setPrev(1);
		app.program();
	}
	public void program(){
	    UserInterface ui = new UserInterface();
		ui.main(null);
		
		Thread t1 = new Thread(new highlight());
		Thread t2 = new Thread(new changefunction());
		t1.start();
		t2.start();
		
	}
	public static int getI() {
		return i;
	}
	public static void setI(int i) {
		App.i = i;
	}
	public static int getAcc() {
		return acc;
	}
	public static void setAcc(int acc) {
		App.acc = acc;
	}
	public static boolean isFlag() {
		return flag;
	}
	public static void setFlag(boolean flag) {
		App.flag = flag;
	}
	public static int getPrev() {
		return prev;
	}
	public static void setPrev(int prev) {
		App.prev = prev;
	}
}
