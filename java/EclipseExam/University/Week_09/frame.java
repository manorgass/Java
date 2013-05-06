import java.awt.*; //for GUI
import javax.swing.JOptionPane; //for Message Box
import java.awt.event.*; //for Button action
import java.io.*; //for File Input & Output

public class frame extends Frame implements ActionListener {
	static int i;
	//Panel
	static Panel p[] = new Panel[7];
	//Menu
	//new MenuShortcut 으로 단축키 생성 가능
	static MenuBar mb = new MenuBar();
	static Menu menu[] = new Menu[4];
	static MenuItem menu0_i[] = new MenuItem[4];
	static MenuItem menu1_i[] = new MenuItem[4];
	static MenuItem menu2_i[] = new MenuItem[4];
	static MenuItem menu3_i[] = new MenuItem[4];
	//PopupMenu
	static PopupMenu pm = new PopupMenu();
	static MenuItem pmItem[] = new MenuItem[3];
	//for p[0]
	static Label p0_l[] = new Label[5];
	static TextField p0_tf[] = new TextField[5];
	//for p[1]
	static Label p1_l;
	static Checkbox p1_cb[] = new Checkbox[4];
	static TextField p1_tf;
	//for p[2]
	static Label p2_l;
	static Checkbox p2_cb[] = new Checkbox[4];
	static CheckboxGroup p2_cbg;
	static TextField p2_tf;
	//for p[3]
	static Label p3_l;
	static List p3_list;
	//for p[4]
	static Label p4_l;
	static Choice p4_choi;
	//for p[5]
	static Font font;
	static Label p5_l;
	static TextArea p5_ta;
	static Button p5_b[] = new Button[2];
	//for p[6]
	static Button p6_b[] = new Button[10];
	//생성자
	public frame() {
		super("KJW 성적관리 프로그램");
		this.setLayout(null);
		this.setSize(505, 655);
		this.addWindowListener(new EventHandler()); //for Exit
		this.setResizable(false); //for fixing size
		/*
		 * Create Components
		 */
		//Panel
		for(i=0; i<p.length; i++) p[i] = new Panel();
		//Menu
		menu[0] = new Menu("File");
		menu0_i[0] = new MenuItem("Open");
		menu0_i[1] = new MenuItem("Save");
		menu0_i[2] = new MenuItem("Edit");
		menu0_i[3] = new MenuItem("Exit");
		for(i=0; i<menu0_i.length; i++)
			menu[0].add(menu0_i[i]);
		menu[1] = new Menu("Calculator");
		menu[2] = new Menu("DataBase");
		menu[3] = new Menu("Help");
		menu3_i[0] = new MenuItem("About this program...");
		menu[3].add(menu3_i[0]);
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
		p3_list.add("Hurk");
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
		p5_b[1] = new Button("Exit");
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
		p5_b[0].setBounds(105, 470, 55, 20);
		p5_b[1].setBounds(170, 470, 40, 20);
		//Panel 6
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
		for(i=0; i<menu0_i.length; i++)
			menu0_i[i].addActionListener(this);
		menu3_i[0].addActionListener(this);
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
		this.setMenuBar(mb);
		this.setVisible(true);
	}
	/*
	 * Main Function
	 */
	public static void main(String[] args) {
		final frame f = new frame();
		f.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				if(me.getModifiers() == me.BUTTON3_MASK)
					pm.show(f, me.getX(), me.getY());
			}
		});
	}
	/*
	 * for ActionEvents
	 */
	public void actionPerformed(ActionEvent e) {
		String event = e.getActionCommand();
		switch(event) {
		case "About this program...":
			JOptionPane.showMessageDialog(getParent(), 
					"Producer : 배재대학교 정보통신공학과 09학번 김중원\n" +
					"Purpose : Java programming 13년 2학년 1학기 10주차 과제\n" +
					"Email : manorgass@gmail.com\n" +
					"Facebook : facebook.com/manorgass" ,
					"Information", JOptionPane.PLAIN_MESSAGE);
			break;
		case "Output":
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
				JOptionPane.showMessageDialog(getParent(), atEmpty+"을 입력해주세요." ,"Error!!", JOptionPane.PLAIN_MESSAGE);
				break;
			}
			//p1
			int p1_count = 0;
			for(i=0; i<p1_cb.length; i++) {
				if(!p1_cb[i].getState())
					p1_count++;
			}
			if(p1_count == p1_cb.length) {
				JOptionPane.showMessageDialog(getParent(), "음식을 선택해주세요." ,"Error!!", JOptionPane.PLAIN_MESSAGE);
				break;
			}
			if(p1_cb[3].getState() && p1_tf.getText().equals("")) {
				JOptionPane.showMessageDialog(getParent(), "기타를 입력해주세요." ,"Error!!", JOptionPane.PLAIN_MESSAGE);
				break;
			}
			//p2
			if(p2_cbg.getSelectedCheckbox().getLabel().equals("기타") && p2_tf.getText().equals("")) {
				JOptionPane.showMessageDialog(getParent(), "기타를 입력해주세요." ,"Error!!", JOptionPane.PLAIN_MESSAGE);
				break;
			}
			//variable
			//p[0]
			String name	= p0_tf[0].getText();
			String num	= p0_tf[1].getText();
			int kor, mat, eng;
			try {
			kor		= Integer.parseInt(p0_tf[2].getText());
			mat		= Integer.parseInt(p0_tf[3].getText());
			eng		= Integer.parseInt(p0_tf[4].getText());
			} catch(NumberFormatException error) {
				JOptionPane.showMessageDialog(getParent(), "성적에 숫자를 입력해주세요!" ,"Error!!", JOptionPane.PLAIN_MESSAGE);
				break;
			}
			int sum		= kor + mat + eng;
			double ave	= sum/3;
			//p[1]
			p1_count = 0;
			String p1_cbv[] = new String[4];
			for(i=0; i<p1_cb.length; i++) {
				if(p1_cb[i].getState() && i<p1_cbv.length-1) {
					p1_cbv[p1_count]= p1_cb[i].getLabel();
					p1_count++;
				}
				else if(p1_cb[i].getState() && i == p1_cbv.length-1) {
					p1_cbv[p1_count] = p1_tf.getText();
					p1_count++;
				}
			}
			//p[2]
			String p2_cbv;
			if(p2_cbg.getSelectedCheckbox().getLabel() != "기타") 
				p2_cbv = p2_cbg.getSelectedCheckbox().getLabel();
			else
				p2_cbv = p2_tf.getText();
			//p[3]
			String p3_listv = p3_list.getSelectedItem();
			//p[4]
			String p4_choiv = p4_choi.getSelectedItem();
			//Output to p[5]_ta (Text Area)
			p5_ta.setText("******* Output of Data *******\n\n");
			//p0
			p5_ta.append("이름 : " + name + "\n\n" + 
						 "학번 : " + num  + "\n\n" + 
						 "국어 : " + kor  + "점\n\n" +
						 "영어 : " + eng  + "점\n\n" +
						 "수학 : " + mat  + "점\n\n" +
						 "합계 : " + sum  + "점\n\n" +
						 "평균 : " + ave  + "점\n\n" +
						 "좋아하는 음식 : ");
			//p1
			for(i=0; i<p1_count; i++) {
				if(i != p1_count-1)
					p5_ta.append(p1_cbv[i]+", ");
				else
					p5_ta.append(p1_cbv[i]+"\n\n");
			}
			//p2
			p5_ta.append("좋아하는 음악 : " + p2_cbv + "\n\n");
			//p3
			p5_ta.append("좋아하는 영화 : " + p3_listv + "\n\n");
			//p4
			p5_ta.append("좋아하는 교수님 : " + p4_choiv);
			break;
		case "Exit":
			System.exit(0);
			break;
		case "Array Save":
			break;
		case "Array Output":
			break;
		case "File save":
			break;
		case "File Load":
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
		}		
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