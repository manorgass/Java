import java.awt.*; //for GUI
import javax.swing.JOptionPane; //for Message Box
import java.awt.event.*; //for Button action
import java.io.*;
import java.sql.*;

public class frame extends Frame implements ActionListener {
	public static frame f;
	public static int i;
	//Panel
	public static Panel p[] = new Panel[7];
	//Menu
	//new MenuShortcut 으로 단축키 생성 가능
	public static MenuBar mb = new MenuBar();
	public static Menu menu[] = new Menu[4];
	public static MenuItem menu0_item[] = new MenuItem[4];
	public static MenuItem menu1_item[] = new MenuItem[2];
	public static MenuItem menu2_item[] = new MenuItem[4];
	public static MenuItem menu3_item[] = new MenuItem[4];
	//PopupMenu
	public static PopupMenu pm = new PopupMenu();
	public static MenuItem pmItem[] = new MenuItem[3];
	//for p[0]
	public static Label p0_l[] = new Label[5];
	public static TextField p0_tf[] = new TextField[5];
	//for p[1]
	public static Label p1_l;
	public static Checkbox p1_cb[] = new Checkbox[4];
	public static TextField p1_tf;
	//for p[2]
	public static Label p2_l;
	public static Checkbox p2_cb[] = new Checkbox[4];
	public static CheckboxGroup p2_cbg;
	public static TextField p2_tf;
	//for p[3]
	public static Label p3_l;
	public static List p3_list;
	//for p[4]
	public static Label p4_l;
	public static Choice p4_choi;
	//for p[5]
	public static Font font;
	public static Label p5_l;
	public static TextArea p5_ta;
	public static Button p5_b[] = new Button[3];
	//for p[6]
	public static Button p6_b[] = new Button[10];
	//for Array logic
	public Students student[] = new Students[100];
	public int stuIndex=0; //for students count
	//생성자
	public frame() {
		super("KJW 성적관리 프로그램");
		this.setLayout(null);
		this.setSize(505, 655);
		this.setLocation(40,40);
		this.addWindowListener(new EventHandler()); //for Exit
		this.setResizable(false); //for fixing size
		
		makeComponents();
		settingComponents();
		addComponents();
		
		this.setMenuBar(mb);
		this.setVisible(true);
	}
	/*
	 * Main Function
	 */
	public static void main(String[] args) {
		f = new frame();
	}
	/*
	 * for ActionEvents
	 */
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		
		switch(event) {
		
		case "About this program...":
			popupMsg(
					"information", 
					"Producer : 배재대학교 정보통신공학과 09학번 김중원\n" +
					"Purpose : Java programming 13년 2학년 1학기 10주차 과제\n" +
					"Email : manorgass@gmail.com\n" +
					"Facebook : facebook.com/manorgass"
					);
			break;
		//p5 buttons
		case "Output":
			if(stuIndex == 0) //check array value
				popupMsg("Error!!", "Please enter the information to text field at left panel.");
			if(checkEmpty())//Check Empty field
				break;
			
			else {
				p5_ta.setText("");
				print_to_textarea(student[stuIndex-1]);
			}
			
			break;
		case "Clear":
			p5_ta.setText("");
			break;
		case "Exit":
			this.setVisible(false);
			this.dispose();
			System.exit(0);
			break;
		//p6 buttons	
		case "Array Save":
			//check empty field
			if(checkEmpty()) break;
			
			student[stuIndex] = new Students(get_Name(), get_Num(), getKor(),
					getMat(), getEng(), getFavoriteFood(), getFavoriteMusic(),
					getFavoriteMovie(), getFavoritProfessor(), stuIndex);
			p5_ta.setText("");
			print_to_textarea(student[stuIndex]);
			stuIndex++;
			break;
			
		case "Array Output":
			if(stuIndex == 0) { //check array value
				popupMsg("Error!!", "Please save the information of students.");
				break;
			}
				
			p5_ta.setText("");
			for(i=0; i<stuIndex; i++)
				print_to_textarea(student[i]);
			break;
			
		case "File Save":
			try {
				FileWriter fw = new FileWriter("d:/dAtA.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(p5_ta.getText());
				bw.close();
			} catch (IOException IOe) {
				IOe.printStackTrace();
			}
			break;
		case "File Load":
			String buffer = "";
			try
			{
				FileReader fr = new FileReader("d:/dAtA.txt");
				BufferedReader br = new BufferedReader(fr);
				
				String line = null;
				while ((line = br.readLine()) != null) {
					buffer += line + "\r\n";
				}
				br.close();
			} 
			catch (IOException IOe) { IOe.printStackTrace(); }
			p5_ta.setText(buffer);
			break;
		case "RAF save":
			break;
		case "RAF load":
			break;
		case "DB SAVE":
			break;
		case "DB LOAD":
			break;
		case "DB Search":
			break;
		case "DB Delete":
			break;
		case "Open":
			break;
		case "Save":
			break;
		case "Edit":
			break;
		case "Sum of score":
			for(i=0; i<stuIndex; i++)
				student[i].calculateSum();
			break;
		case "Ave of score":
			for(i=0; i<stuIndex; i++)
				student[i].calculateAve();
			break;
		}		
	}
	
	private boolean checkEmpty() {
		//check empty field
		boolean isEmpty = false;
		String atEmpty = "";
		//p0
		for(i=0; i<p0_tf.length; i++) {
			if(p0_tf[i].getText().equals("")) {
				isEmpty = true;
				atEmpty = p0_l[i].getText();
				break;
			}
		}
		if(isEmpty) {
			popupMsg("Error!!", atEmpty+"을 입력해주세요.");
			return true;
		}
		//p1
		int p1_count = 0;
		for(i=0; i<p1_cb.length; i++) {
			if(!p1_cb[i].getState())
				p1_count++;
		}
		if(p1_count == p1_cb.length) {
			popupMsg("Error!!", "음식을 선택해주세요.");
			return true;
		}
		if(p1_cb[3].getState() && p1_tf.getText().equals("")) {
			popupMsg("Error!!", "기타를 입력해주세요.");
			return true;
		}
		//p2
		if(p2_cbg.getSelectedCheckbox().getLabel().equals("기타") && p2_tf.getText().equals("")) {
			popupMsg("Error!!", "기타를 입력해주세요.");
			return true;
		}
		//p3
		if(p3_list.getSelectedIndex() == -1) {
			popupMsg("Error!!", "좋아하는 영화를 선택해주세요.");
			return true;
		}
		return false;
	}
	private String get_Name() 			{return p0_tf[0].getText();	}
	private String get_Num()			{return p0_tf[1].getText();	}
	private int getKor() {
		try {
			return Integer.parseInt(p0_tf[2].getText());
		} catch(NumberFormatException error) {
			popupMsg("Error!!", "성적에 숫자를 입력해주세요!");
			return -1;
		}
	}
	private int getMat() {
		try {
			return Integer.parseInt(p0_tf[3].getText());
		} catch(NumberFormatException error) {
			popupMsg("Error!!", "성적에 숫자를 입력해주세요!");
			return -1;
		}
	}
	private int getEng() {
		try {
			return Integer.parseInt(p0_tf[4].getText());
		} catch(NumberFormatException error) {
			popupMsg("Error!!", "성적에 숫자를 입력해주세요!");
			return -1;
		}
	}
	private String getFavoriteFood() {
		String sumString = "";
		int count = 0;
		for(i=0; i<p1_cb.length; i++) {
			if(p1_cb[i].getState() && i<p1_cb.length-1) {
				if(count>1)
					sumString += ", ";
				sumString += p1_cb[i].getLabel();
				count++;
			}
			else if(p1_cb[i].getState() && i == p1_cb.length-1) {
				if(count>1)
					sumString += ", ";
				sumString += p1_tf.getText();
				count++;
			}
		}
		return sumString;
	}
	private String getFavoriteMusic()	{
		if(p2_cbg.getSelectedCheckbox().getLabel() != "기타") 
			return p2_cbg.getSelectedCheckbox().getLabel();
		else
			return p2_tf.getText();
	}
	private String getFavoriteMovie()	{	return p3_list.getSelectedItem();	}
	private String getFavoritProfessor(){	return p4_choi.getSelectedItem();	}
	private void print_to_textarea(Students pst) {
		p5_ta.append("******* student " + pst.count +" *******\n\n");
		//p0
		p5_ta.append("이름 : " + pst.name + "\n\n" + 
					 "학번 : " + pst.num  + "\n\n" + 
					 "국어 : " + pst.kor  + "점\n\n" +
					 "영어 : " + pst.eng  + "점\n\n" +
					 "수학 : " + pst.mat  + "점\n\n");
		if(pst.sum != -1) { 
			p5_ta.append("합계 : " + pst.sum  + "점\n\n");
			if(pst.ave != -1)
				p5_ta.append("평균 : " + pst.ave  + "점\n\n");
		}
		p5_ta.append("좋아하는 음식 : " + pst.favorite_food + "\n\n");
		//p2
		p5_ta.append("좋아하는 음악 : " + pst.favorite_music + "\n\n");
		//p3
		p5_ta.append("좋아하는 영화 : " + pst.favorite_movie + "\n\n");
		//p4
		p5_ta.append("좋아하는 교수님 : " + pst.favorite_professor + "\n\n\n");
	}
	
	private void makeComponents() {
		/*
		 * Create Components
		 */
		//Panel
		for(i=0; i<p.length; i++) p[i] = new Panel();
		//Menu
		menu[0] = new Menu("File");
		menu0_item[0] = new MenuItem("Open");
		menu0_item[1] = new MenuItem("Save");
		menu0_item[2] = new MenuItem("Edit");
		menu0_item[3] = new MenuItem("Exit");
		for(i=0; i<menu0_item.length; i++)
			menu[0].add(menu0_item[i]);
		menu[1] = new Menu("Calculator");
		menu1_item[0] = new MenuItem("Sum of score");
		menu1_item[1] = new MenuItem("Ave of score");
		for(i=0; i<menu1_item.length; i++)
			menu[1].add(menu1_item[i]);
		menu[2] = new Menu("DataBase");
		menu[3] = new Menu("Help");
		menu3_item[0] = new MenuItem("About this program...");
		menu[3].add(menu3_item[0]);
		for(i=0; i<menu.length; i++)
			mb.add(menu[i]);
		//Popup menu
		pmItem[0] = new MenuItem("Edit");
		pmItem[1] = new MenuItem("Copy");
		pmItem[2] = new MenuItem("Paste");
		for(i=0; i<pmItem.length; i++)
			pm.add(pmItem[i]);
		//Panel 0
		p0_l[0] = new Label("이름: ", Label.CENTER);
		p0_l[1] = new Label("학번: ", Label.CENTER);
		p0_l[2] = new Label("국어: ", Label.CENTER);
		p0_l[3] = new Label("수학: ", Label.CENTER);
		p0_l[4] = new Label("영어: ", Label.CENTER);
		for(i=0; i<p0_tf.length; i++)
			p0_tf[i] = new TextField(null);
		//Panel 1
		p1_l = new Label("Favorite food", Label.CENTER);
		p1_l.setForeground(Color.WHITE);
		p1_cb[0] = new Checkbox("짜장");
		p1_cb[1] = new Checkbox("짬뽕");
		p1_cb[2] = new Checkbox("라면");
		p1_cb[3] = new Checkbox("기타");
		for(i=0; i<p1_cb.length; i++)
			p1_cb[i].setForeground(Color.WHITE);
		p1_tf = new TextField();
		//Panel 2
		p2_l = new Label("Favorite music", Label.CENTER);
		p2_cbg = new CheckboxGroup();
		p2_cb[0] = new Checkbox("힙합", p2_cbg ,true);
		p2_cb[1] = new Checkbox("메탈", p2_cbg ,false);
		p2_cb[2] = new Checkbox("댄스", p2_cbg ,false);
		p2_cb[3] = new Checkbox("기타", p2_cbg ,false); 
		p2_tf = new TextField();
		//Panel 3
		p3_l = new Label("Favorite movie", Label.CENTER);
		p3_l.setForeground(Color.WHITE);
		p3_list = new List();
		p3_list.add("Iron man");
		p3_list.add("Hulk");
		p3_list.add("Saw");
		p3_list.add("Superman");
		p3_list.add("Darknigth");
		//Panel 4
		p4_l = new Label("Favorite professor", Label.CENTER);
		p4_choi = new Choice();
		p4_choi.add("김도완");
		p4_choi.add("박두영");
		p4_choi.add("김익상");
		p4_choi.add("정종환");
		p4_choi.add("류황");
		p4_choi.add("주기호");
		//Panel 5
		font = new Font("", Font.BOLD, 15);
		p5_l = new Label("Output field", Label.CENTER);
		p5_l.setFont(font);
		p5_l.setForeground(Color.WHITE);
		p5_ta = new TextArea();
		p5_ta.setEditable(false); //출력전용
		p5_b[0] = new Button("Output");
		p5_b[1] = new Button("Clear");
		p5_b[2] = new Button("Exit");
		//Panel 6
		p6_b[0] = new Button("Array Save");
		p6_b[1] = new Button("Array Output");
		p6_b[2] = new Button("File Save");
		p6_b[3] = new Button("File Load");
		p6_b[4] = new Button("RAF save");
		p6_b[5] = new Button("RAF load");
		p6_b[6] = new Button("DB SAVE");
		p6_b[7] = new Button("DB LOAD");
		p6_b[8] = new Button("DB Search");
		p6_b[9] = new Button("DB Delete");
	}
	
	private void settingComponents() {
		/*
		 * Setting for Panel
		 */
		//p[0]
		p[0].setBounds(0, 65, 170-10, 120);
		p[0].setLayout(new GridLayout(5,2,0,5));
		//p[1]
		p[1].setBounds(0, 195, 170, 100);
		p[1].setLayout(null);
		p[1].setBackground(Color.LIGHT_GRAY);
		//p[2]
		p[2].setBounds(0, 295, 170, 100);
		p[2].setLayout(null);
		//p[3]
		p[3].setBounds(0, 395, 170, 100);
		p[3].setLayout(null);
		p[3].setBackground(Color.LIGHT_GRAY);
		//p[4]
		p[4].setBounds(0, 495, 170, 80);
		p[4].setLayout(null);
		//p[5]
		p[5].setBounds(170, 65, 330, 510);
		p[5].setLayout(null);
		p[5].setBackground(Color.LIGHT_GRAY);
		//p[6]
		p[6].setBounds(0, 575, 500, 75);
		p[6].setLayout(new GridLayout(2,5,0,0));
		/*
		 * Size Setting for Components
		 */
		//Panel 0
		//Panel 1
		p1_l.		setBounds(0, 10, 170, 25);
		p1_cb[0].	setBounds(20, 40, 40, 15);
		p1_cb[1].	setBounds(70, 40, 40, 15);
		p1_cb[2].	setBounds(120, 40, 40, 15);
		p1_cb[3].	setBounds(20, 70, 40, 15);
		p1_tf.		setBounds(70, 66, 70, 20);
		//Panel 2
		p2_l.		setBounds(0, 10, 170, 25);
		p2_cb[0].	setBounds(20, 40, 40, 15);
		p2_cb[1].	setBounds(70, 40, 40, 15);
		p2_cb[2].	setBounds(120, 40, 40, 15);
		p2_cb[3].	setBounds(20, 70, 40, 15);
		p2_tf.		setBounds(70, 66, 70, 20);
		//Panel 3
		p3_l.setBounds(0, 10, 170, 25);
		p3_list.setBounds(35, 35, 100, 50);
		//Panel 4
		p4_l.setBounds(0, 10, 170, 25);
		p4_choi.setBounds(35, 40, 100, 30);
		//Panel 5
		p5_l.setBounds(0, 15, 330, 25);
		p5_ta.setBounds(20, 50, 285, 400);
		p5_b[0].setBounds(30, 470, 55, 25);
		p5_b[1].setBounds(100, 470, 55, 25);
		p5_b[2].setBounds(245, 470, 55, 25);
		//Panel 6
		
		
	}
	void addComponents() {
		/*
		 * Add components to panels
		 */
		//Panel 0
		for(i=0; i<p0_l.length; i++) {
			p[0].add(p0_l[i]);
			p[0].add(p0_tf[i]);
		}
		//Panel 1
		p[1].add(p1_l);
		for(i=0; i<p1_cb.length; i++)
			p[1].add(p1_cb[i]);
		p[1].add(p1_tf);
		//Panel 2
		p[2].add(p2_l);
		for(i=0; i<p2_cb.length; i++)
			p[2].add(p2_cb[i]);
		p[2].add(p2_tf);
		//Panel 3
		p[3].add(p3_l);
		p[3].add(p3_list);
		//Panel 4
		p[4].add(p4_l);
		p[4].add(p4_choi);
		//Panel 5
		p[5].add(p5_l);
		p[5].add(p5_ta);
		for(i=0; i<p5_b.length; i++)
			p[5].add(p5_b[i]);
		//Panel 6
		for(i=0; i<p6_b.length; i++)
			p[6].add(p6_b[i]);
		/*
		 * Add Panel to Frame
		 */
		//PopupMenu
		this.add(pm);
		//panel
		for(i=0; i<p.length; i++)
			this.add(p[i]);
		/*
		 * Add Action Listener
		 */
		//Menu
		for(i=0; i<menu0_item.length; i++)
			menu0_item[i].addActionListener(this);
		for(i=0; i<menu1_item.length; i++)
			menu1_item[i].addActionListener(this);
		menu3_item[0].addActionListener(this);
		//p[0]
		//p[1]
		//p[2]
		//p[3]
		//p[4]
		//p[5]
		for(i=0; i<p5_b.length; i++)
			p5_b[i].addActionListener(this);
		//p[6]
		for(i=0; i<p6_b.length; i++)
			p6_b[i].addActionListener(this);
		/*
		 * Add Mouse Listener
		 */
		//panel
		for(i=0; i<p.length; i++) {
			p[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					if(me.getModifiers() == me.BUTTON3_MASK)
						pm.show(f, me.getXOnScreen(), me.getYOnScreen());
				}
			});
		}
	}
	void popupMsg(String title, String msg) {
		JOptionPane.showMessageDialog(f, msg ,title, JOptionPane.WARNING_MESSAGE);
		//JOptionPane.showConfirmDialog(f, "ok?", "title", JOptionPane.OK_CANCEL_OPTION);
	}
}
/*
 * for Exit
 */
class EventHandler implements WindowListener {
	public void windowClosing(WindowEvent e) {
		e.getWindow().setVisible(false);
		e.getWindow().dispose();
		System.exit(0);
	}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}