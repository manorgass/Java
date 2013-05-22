import java.awt.*; //for GUI
import javax.swing.*;
import org.omg.CORBA.Request;
import java.awt.event.*; //for Button action
import java.io.*;
import java.sql.*;
import java.util.Vector;

public class frame extends JFrame implements ActionListener {
	public static JFrame f;
	public static int i;
	//filediolog
	FileDialog fd;
	//Panel
	public static JPanel p[] = new JPanel[7];
	//Menu
	//new MenuShortcut 으로 단축키 생성 가능
	public static JMenuBar mb = new JMenuBar();
	public static JMenu menu[] = new JMenu[4];
	public static JMenuItem menu0_item[] = new JMenuItem[4];
	public static JMenuItem menu1_item[] = new JMenuItem[2];
	public static JMenuItem menu2_item[] = new JMenuItem[4];
	public static JMenuItem menu3_item[] = new JMenuItem[4];
	//PopupMenu
	public static JPopupMenu pm = new JPopupMenu();
	public static JMenu pmm;
	public static JMenuItem pmmItem[] = new JMenuItem[3];
	public static JMenuItem pmItem[] = new JMenuItem[3];
	//for p[0]
	public static JLabel p0_l[] = new JLabel[5];
	public static JTextField p0_tf[] = new JTextField[5];
	//for p[1]
	public static JLabel p1_l;
	public static JCheckBox p1_cb[] = new JCheckBox[4];
	public static JTextField p1_tf;
	//for p[2]
	public static JLabel p2_l;
	public static JRadioButton p2_rb[] = new JRadioButton[4];
	public static ButtonGroup p2_bg;
	public static JTextField p2_tf;
	//for p[3]
	public static JLabel p3_l;
	public static JList p3_list;
	public static JScrollPane p3_sp;
	public static String p3_list_data[] = new String[5];
	//for p[4]
	public static JLabel p4_l;
	public static Choice p4_choi;
	//for p[5]
	public static Font font;
	public static JLabel p5_l;
	public static JScrollPane p5_sp;
	public static JTextArea p5_ta;
	public static JButton p5_b[] = new JButton[3];
	//for p[6]
	public static JButton p6_b[] = new JButton[10];
	//for Array logic
	public static Students student[] = new Students[100];
	public static int stuIndex=0; //for students count
	//for DB connection
	static String id="root";
	static String password="root";
	static Connection con;
	static Statement stmt;
	static ResultSet rs = null;
	//생성자
	public frame() {
		super("KJW 성적관리 프로그램");
		this.getContentPane().setLayout(null);
		this.setSize(505 + 30, 655);
		this.setLocation(40,40);
		this.setResizable(false); //for fixing size
		
		makeComponents();
		settingComponents();
		addComponents();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setJMenuBar(mb);
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
			popupMsg(1, 
					"information", 
					"Producer : 배재대학교 정보통신공학과 09학번 김중원\n" +
					"Purpose : Java programming 13년 2학년 1학기 10주차 과제\n" +
					"Email : manorgass@gmail.com\n" +
					"Facebook : facebook.com/manorgass"
					);
			break;
		//p5 buttons
		case "Output":
			if(stuIndex == 0) { //check array value
				popupMsg(3, "Error!!", "Please enter the information to text field at left panel.");
				break;
			}
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
					getFavoriteMovie(), getFavoritProfessor());
			p5_ta.setText("");
			print_to_textarea(student[stuIndex]);
			stuIndex++;
			popupMsg(1, "ALERT", "SAVE COMPLETE.");
			break;
			
		case "Array Output":
			if(stuIndex == 0) { //check array value
				popupMsg(2, "ALERT", "Please input the data by the 'Array Save' button.");
				break;
			}
				
			p5_ta.setText("");
			for(i=0; i<stuIndex; i++)
				print_to_textarea(student[i]);
			popupMsg(1, "ALERT", "OUTPUT COMPLETE.");
			break;
		case "Save":	
		case "File Save":
			 
	        FileDialog fd = new FileDialog(this, "file save", FileDialog.SAVE); 
	        fd.setVisible(true); 
	        String file_location = fd.getDirectory(); 
	        file_location += fd.getFile();
			
			try {
				FileWriter fw = new FileWriter(file_location);
				BufferedWriter bw = new BufferedWriter(fw);
					
				bw.write(p5_ta.getText());
				bw.close();
			} catch (IOException IOe) {
				IOe.printStackTrace();
			}
			
			popupMsg(1, "ALERT", "SAVE COMPLETE");
			break;
		case "Open":
		case "File Load":
	        fd = new FileDialog(this, "file open", FileDialog.LOAD); 
	        fd.setVisible(true); 
	        file_location = fd.getDirectory(); 
	        file_location += fd.getFile();
	        
			String buffer = "";
			try
			{
				FileReader fr = new FileReader(file_location);
				BufferedReader br = new BufferedReader(fr);
				
				String line = null;
				while ((line = br.readLine()) != null) {
					buffer += line + "\r\n";
				}
				br.close();
			} 
			catch (IOException IOe) { IOe.printStackTrace(); }
			p5_ta.setText(buffer);
			popupMsg(1, "ALERT", "LOAD COMPLETE");
			break;
		case "RAF save":
			break;
		case "RAF load":
			break;
		case "DB SAVE":
			if(stuIndex == 0) { popupMsg(2, "Alert", "No data to be stored.");	break; }
			connectDB();
			String sql, name, num, food, music, movie, professor;
			String num_DB[] = new String[100];
			num_DB[0] = "";
			int count=0;
			try {
				/*rs = stmt.executeQuery("SELECT num FROM information");
				while(rs.next()) { //DB로부터 num의 값들을 dump
					num_DB[count] = rs.getString(1);
					count++;
				}*/
				if(stuIndex > 0) {
					for(i=0; i<stuIndex; i++) {
						student[i].calculateAve();
						//DB에 information 저장
						/*
						 * 같은 학번을 가진 자료가 DB에 존제할 경우 덮어씌울 건지를 물어본다.
						 */
						
						if(!num_DB[0].equals("")) {
							
						}
						sql = "INSERT INTO information VALUE " + "(" +
								"null, " +
								"\"" + student[i].name + "\"," +
								"\"" + student[i].num + "\"," +
								student[i].kor + "," +
								student[i].mat + "," +
								student[i].eng + "," +
								student[i].sum + "," +
								student[i].ave + "," +
								"\"" + student[i].favorite_food + "\"," +
								"\"" + student[i].favorite_music + "\"," +
								"\"" + student[i].favorite_movie + "\"," +
								"\"" + student[i].favorite_professor +"\")";
						stmt.executeUpdate(sql);
					}
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			disconnectDB();
			popupMsg(1, "알림", "Save complete.");
			break;
			
		case "DB LOAD":
			p5_ta.setText("");
			DB_LOAD("SELECT * FROM information");
			break;
		case "DB Search":
			connectDB();
			try {
				rs = stmt.executeQuery( "SELECT num FROM information");
				if(!rs.next()) {
					popupMsg(2, "ALERT", "DB is empty.");
					rs.close();
					break;
				} else { rs.close(); disconnectDB(); }
				String[] button = {"Name Search", "Num Search", "CANCLE"};
				int x = JOptionPane.showOptionDialog(f, "PLEASE SELECT SEARCH METHOD", "SEARCH SELECT", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.YES_NO_CANCEL_OPTION, null, button, button[0]);
				switch(x) {
				case 0: // Name search
					name = JOptionPane.showInputDialog(f, "PLEASE ENTER THE NAME THAT YOU WANT SEARCH.");
					sql = "SELECT * FROM information WHERE name = \"" + name + "\";";
					p5_ta.setText("");
					DB_LOAD(sql);
					break;
				case 1: // Number search
					num = JOptionPane.showInputDialog(f, "PLEASE ENTER THE NUMBER THAT YOU WANT SEARCH.");
					sql = "SELECT * FROM information WHERE num = \"" + num + "\";";
					p5_ta.setText("");
					DB_LOAD(sql);
					break;
				case 2: // Cancle
					break;
				}
			} catch (Exception eSerach){
				eSerach.printStackTrace();
			}
			break;
		case "DB Delete":
			connectDB();
			count = 0;
			try{
				rs = stmt.executeQuery( "SELECT num FROM information");
				if(!rs.next()) {
					popupMsg(2, "ALERT", "DB is empty.");
					rs.close();
					break;
				}
				String[] button = {"선택 삭제", "전체 삭제", "취소"};
				int x = JOptionPane.showOptionDialog(f, "What do you want?", "Delete Option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.YES_NO_CANCEL_OPTION, null, button, button[0]);
				switch(x) {
				case 0: // select drop
					String delIndex = JOptionPane.showInputDialog(f, "삭제하고자 하는 학번을 입력해주세요");
					sql = "DELETE FROM information WHERE num = \"" + delIndex + "\";";
					do{
						//입력한 데이터와 일치하는 값이 있을 경우 count를 증가시켜 존제 여부를 알림
						if(delIndex.equals(rs.getString(1))) count++;	
					} while(rs.next());
						if(count == 0) {
							popupMsg(2, "ALRERT", "NO DATA BE MATCHED INPUT NUMBER.");
							break;
						} else //일치하는 값이 있을 경우 해당 데이터 삭제
							stmt.executeUpdate(sql);
						popupMsg(1, "SUCCESS", "Delete complete.");
					break;
				
				case 1: // all drop
					sql = "TRUNCATE TABLE information;";
					stmt.executeUpdate(sql);
					popupMsg(1, "SUCCESS", "DATA DELETE COMPLETE.");
					break;
				
					
				default: // cancle
					break;
				}
			} catch (Exception delE) {
				delE.printStackTrace();
			}
			disconnectDB();
			break;
		
		case "Edit":
			p5_ta.setEditable(true);
			break;
		case "Sum of score":
			if(stuIndex == 0) {
				popupMsg(1, "Alert", "No data to be calculate.");
				break;
			} else {
				for(i=0; i<stuIndex; i++)
					student[i].calculateSum();
				break;
			}
		case "Ave of score":
			if(stuIndex == 0) {
				popupMsg(1, "Alert", "No data to be calculate.");
				break;
			} else {
				for(i=0; i<stuIndex; i++)
					student[i].calculateAve();
				break;
			}
			
		case "BLACK":
			setColor(Color.BLACK);
			break;
		case "PINK":
			setColor(Color.PINK);
			break;
		case "DARK GRAY":
			setColor(Color.DARK_GRAY);
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
			popupMsg(3, "Error!!", atEmpty+"을 입력해주세요.");
			return true;
		}
		//p1
		int p1_count = 0;
		for(i=0; i<p1_cb.length; i++) {
			if(!p1_cb[i].isSelected())
				p1_count++;
		}
		if(p1_count == p1_cb.length) {
			popupMsg(3, "Error!!", "음식을 선택해주세요.");
			return true;
		}
		if(p1_cb[3].isSelected() && p1_tf.getText().equals("")) {
			popupMsg(3, "Error!!", "기타를 입력해주세요.");
			return true;
		}
		//p2
		if(p2_rb[3].isSelected() && p2_tf.getText().equals("")) {
			popupMsg(3, "Error!!", "기타를 입력해주세요.");
			return true;
		}
		//p3
		if(p3_list.getSelectedIndex() == -1) {
			popupMsg(3, "Error!!", "좋아하는 영화를 선택해주세요.");
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
			popupMsg(3, "Error!!", "성적에 숫자를 입력해주세요!");
			return -1;
		}
	}
	private int getMat() {
		try {
			return Integer.parseInt(p0_tf[3].getText());
		} catch(NumberFormatException error) {
			popupMsg(3, "Error!!", "성적에 숫자를 입력해주세요!");
			return -1;
		}
	}
	private int getEng() {
		try {
			return Integer.parseInt(p0_tf[4].getText());
		} catch(NumberFormatException error) {
			popupMsg(3, "Error!!", "성적에 숫자를 입력해주세요!");
			return -1;
		}
	}
	private String getFavoriteFood() {
		String sumString = "";
		int count = 0;
		for(i=0; i<p1_cb.length; i++) {
			if(p1_cb[i].isSelected() && i<p1_cb.length-1) {
				if(count>1)
					sumString += ", ";
				sumString += p1_cb[i].getLabel();
				count++;
			}
			else if(p1_cb[i].isSelected() && i == p1_cb.length-1) {
				if(count>1)
					sumString += ", ";
				sumString += p1_tf.getText();
				count++;
			}
		}
		return sumString;
	}
	private String getFavoriteMusic()	{
		for(i=0; i<p2_rb.length-1; i++) {
			if(p2_rb[i].isSelected())
				return p2_rb[i].getText();
		}
		return p2_tf.getText();
	}
	private String getFavoriteMovie()	{	return p3_list_data[p3_list.getSelectedIndex()];	}
	private String getFavoritProfessor(){	return p4_choi.getSelectedItem();	}
	private void print_to_textarea(Students pst) {
		p5_ta.append("******* student *******\n\n");
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
		for(i=0; i<p.length; i++) p[i] = new JPanel();
		//Menu
		menu[0] = new JMenu("File");
		menu0_item[0] = new JMenuItem("Open");
		menu0_item[1] = new JMenuItem("Save");
		menu0_item[2] = new JMenuItem("Edit");
		menu0_item[3] = new JMenuItem("Exit");
		for(i=0; i<menu0_item.length; i++)
			menu[0].add(menu0_item[i]);
		menu[1] = new JMenu("Calculator");
		menu1_item[0] = new JMenuItem("Sum of score");
		menu1_item[1] = new JMenuItem("Ave of score");
		for(i=0; i<menu1_item.length; i++)
			menu[1].add(menu1_item[i]);
		menu[2] = new JMenu("DataBase");
		menu2_item[0] = new JMenuItem("DB LOAD");
		menu2_item[1] = new JMenuItem("DB SAVE");
		menu2_item[2] = new JMenuItem("DB Search");
		menu2_item[3] = new JMenuItem("DB Delete");
		for(i=0; i<menu2_item.length; i++)
			menu[2].add(menu2_item[i]);
		menu[3] = new JMenu("Help");
		menu3_item[0] = new JMenuItem("About this program...");
		menu[3].add(menu3_item[0]);
		for(i=0; i<menu.length; i++)
			mb.add(menu[i]);
		//Popup menu
		pmItem[0] = new JMenuItem("Edit");
		pmItem[1] = new JMenuItem("Copy");
		pmItem[2] = new JMenuItem("Paste");
		for(i=0; i<pmItem.length; i++)
			pm.add(pmItem[i]);
		JMenu pmm = new JMenu("Color");
		pmmItem[0] = new JMenuItem("BLACK");
		pmmItem[1] = new JMenuItem("PINK");
		pmmItem[2] = new JMenuItem("DARK GRAY");
		for(i=0; i<pmmItem.length; i++)
			pmm.add(pmmItem[i]);
		pm.add(pmm);
		//Panel 0
		p0_l[0] = new JLabel("이름: ", JLabel.CENTER);
		p0_l[1] = new JLabel("학번: ", JLabel.CENTER);
		p0_l[2] = new JLabel("국어: ", JLabel.CENTER);
		p0_l[3] = new JLabel("수학: ", JLabel.CENTER);
		p0_l[4] = new JLabel("영어: ", JLabel.CENTER);
		for(i=0; i<p0_tf.length; i++)
			p0_tf[i] = new JTextField(null);
		//Panel 1
		p1_l = new JLabel("Favorite food", JLabel.CENTER);
		p1_l.setForeground(Color.WHITE);
		p1_cb[0] = new JCheckBox("짜장");
		p1_cb[1] = new JCheckBox("짬뽕");
		p1_cb[2] = new JCheckBox("라면");
		p1_cb[3] = new JCheckBox("기타");
		for(i=0; i<p1_cb.length; i++)
			p1_cb[i].setForeground(Color.WHITE);
		p1_tf = new JTextField();
		//Panel 2
		p2_l = new JLabel("Favorite music", JLabel.CENTER);
		p2_bg = new ButtonGroup();
		p2_rb[0] = new JRadioButton("힙합" ,true);
		p2_rb[1] = new JRadioButton("메탈" ,false);
		p2_rb[2] = new JRadioButton("댄스" ,false);
		p2_rb[3] = new JRadioButton("기타" ,false);
		for(i=0; i<p2_rb.length; i++)
			p2_bg.add(p2_rb[i]);
		p2_tf = new JTextField();
		//Panel 3
		p3_l = new JLabel("Favorite movie", JLabel.CENTER);
		p3_l.setForeground(Color.WHITE);
		p3_list = new JList();
		p3_list_data[0] = new String("Iron man");
		p3_list_data[1] = new String("Hulk");
		p3_list_data[2] = new String("Saw");
		p3_list_data[3] = new String("Superman");
		p3_list_data[4] = new String("Darknigth");
		p3_list = new JList(p3_list_data);
		p3_sp = new JScrollPane(p3_list);  // for scroll bar
		//Panel 4
		p4_l = new JLabel("Favorite professor", JLabel.CENTER);
		p4_choi = new Choice();
		p4_choi.add("김도완");
		p4_choi.add("박두영");
		p4_choi.add("김익상");
		p4_choi.add("정종환");
		p4_choi.add("류황");
		p4_choi.add("주기호");
		//Panel 5
		font = new Font("", Font.BOLD, 15);
		p5_l = new JLabel("Output field", JLabel.CENTER);
		p5_l.setFont(font);
		p5_l.setForeground(Color.WHITE);
		p5_ta = new JTextArea();
		p5_ta.setEditable(false); //only display
		p5_ta.setLineWrap(true);  //auto line wrap
		p5_sp = new JScrollPane(p5_ta);
		p5_b[0] = new JButton("Output");
		p5_b[1] = new JButton("Clear");
		p5_b[2] = new JButton("Exit");
		//Panel 6
		p6_b[0] = new JButton("Array Save");
		p6_b[1] = new JButton("Array Output");
		p6_b[2] = new JButton("File Save");
		p6_b[3] = new JButton("File Load");
		p6_b[4] = new JButton("RAF save");
		p6_b[5] = new JButton("RAF load");
		p6_b[6] = new JButton("DB SAVE");
		p6_b[7] = new JButton("DB LOAD");
		p6_b[8] = new JButton("DB Search");
		p6_b[9] = new JButton("DB Delete");
	}
	private void settingComponents() {
		/*
		 * Setting for Panel
		 */
		//p[0]
		p[0].setBounds(0, 15, 170-10, 120);
		p[0].setLayout(new GridLayout(5,2,0,5));
		//p[1]
		p[1].setBounds(0, 145, 170, 100);
		p[1].setLayout(null);
		p[1].setBackground(Color.LIGHT_GRAY);
		//p[2]
		p[2].setBounds(0, 245, 170, 100);
		p[2].setLayout(null);
		//p[3]
		p[3].setBounds(0, 345, 170, 100);
		p[3].setLayout(null);
		p[3].setBackground(Color.LIGHT_GRAY);
		//p[4]
		p[4].setBounds(0, 445, 170, 80);
		p[4].setLayout(null);
		//p[5]
		p[5].setBounds(170, 15, 330 + 30, 510);
		p[5].setLayout(null);
		p[5].setBackground(Color.LIGHT_GRAY);
		//p[6]
		p[6].setBounds(0, 525, 500 + 30, 75);
		p[6].setLayout(new GridLayout(2,5,0,0));
		/*
		 * Size Setting for Components
		 */
		//Panel 0
		//Panel 1
		p1_l.		setBounds(0, 10, 170, 25);
		p1_cb[0].	setBounds(10, 40, 53, 15);
		p1_cb[1].	setBounds(60, 40, 53, 15);
		p1_cb[2].	setBounds(110, 40, 53, 15);
		p1_cb[3].	setBounds(10, 70, 53, 15);
		for(i=0; i<p1_cb.length; i++)
			p1_cb[i].setBackground(Color.LIGHT_GRAY);
		p1_tf.		setBounds(70, 66, 70, 20);
		//Panel 2
		p2_l.		setBounds(0, 10, 170, 25);
		p2_rb[0].	setBounds(10, 40, 53, 15);
		p2_rb[1].	setBounds(60, 40, 53, 15);
		p2_rb[2].	setBounds(110, 40, 53, 15);
		p2_rb[3].	setBounds(10, 70, 53, 15);
		p2_tf.		setBounds(70, 66, 70, 20);
		//Panel 3
		p3_l.setBounds(0, 10, 170, 25);
		p3_sp.setBounds(35, 35, 100, 50);
		//Panel 4
		p4_l.setBounds(0, 10, 170, 25);
		p4_choi.setBounds(35, 40, 100, 30);
		//Panel 5
		p5_l.setBounds(0, 15, 360, 25);
		p5_sp.setBounds(20, 50, 315, 400);
		p5_b[0].setBounds(20, 470, 75, 25);
		p5_b[1].setBounds(110, 470, 75, 25);
		p5_b[2].setBounds(280, 470, 55, 25);
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
		for(i=0; i<p2_rb.length; i++)
			p[2].add(p2_rb[i]);
		p[2].add(p2_tf);
		//Panel 3
		p[3].add(p3_l);
		p[3].add(p3_sp);
		//Panel 4
		p[4].add(p4_l);
		p[4].add(p4_choi);
		//Panel 5
		p[5].add(p5_l);
		p[5].add(p5_sp);
		for(i=0; i<p5_b.length; i++)
			p[5].add(p5_b[i]);
		//Panel 6
		for(i=0; i<p6_b.length; i++)
			p[6].add(p6_b[i]);
		/*
		 * Add Panel to Frame
		 */
		//PopupMenu
		this.getContentPane().add(pm);
		//panel
		for(i=0; i<p.length; i++)
			this.getContentPane().add(p[i]);
		/*
		 * Add Action Listener
		 */
		//PopupMenu
		for(i=0; i<pmItem.length; i++)
			pmItem[i].addActionListener(this);
		for(i=0; i<pmmItem.length; i++)
			pmmItem[i].addActionListener(this);
		//Menu
		for(i=0; i<menu0_item.length; i++) {
			menu0_item[i].addActionListener(this);
			menu2_item[i].addActionListener(this);
		}
		
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
						pm.show(f, me.getXOnScreen()-f.getX(), me.getYOnScreen()-f.getY());
				}
			});
		}
	}
	void popupMsg(int type, String title, String msg) {
		switch(type) {
			case 1:
				JOptionPane.showMessageDialog(f, msg ,title, JOptionPane.YES_NO_CANCEL_OPTION);
				break;
			case 2:
				JOptionPane.showMessageDialog(f, msg ,title, JOptionPane.WARNING_MESSAGE);
				break;
			case 3:
				JOptionPane.showMessageDialog(f, msg ,title, JOptionPane.ERROR_MESSAGE);
				break;
		}
	}
	
	void setColor(Color color) {
		p[1].setBackground(color);
		p[3].setBackground(color);
		p[5].setBackground(color);
		p1_l.setBackground(color);
		for(i=0; i<p1_cb.length; i++)
			p1_cb[i].setBackground(color);
		p3_l.setBackground(color);
		p5_l.setBackground(color);
	}
	
	void connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/students";
			String option="?useUnicode=true&characterEncoding=EUCKR";
			url = url + option;
		    con = DriverManager.getConnection(url, id, password);
		    stmt = con.createStatement();
		} catch(Exception e) { e.printStackTrace();	}
	}
	
	void disconnectDB() {
		try 					{ stmt.close(); con.close();	}
		catch (SQLException e) 	{ e.printStackTrace();			}
	}
	void DB_LOAD(String sql) {
		//Students tmp = new Students();
		boolean isnotEmpty;
		String name, num, food, music, movie, professor;
		int kor, mat, eng;
		connectDB();
		try {
			rs = stmt.executeQuery(sql);
			isnotEmpty = rs.next();
			if(isnotEmpty) {
				do {
					name =  rs.getString("name");
					num = rs.getString("num");
					kor = Integer.parseInt(rs.getString("kor"));
					mat = Integer.parseInt(rs.getString("mat"));
					eng = Integer.parseInt(rs.getString("eng"));
					food = rs.getString("food");
					music = rs.getString("music");
					movie = rs.getString("movie");
					professor = rs.getString("professor");
					student[stuIndex]= new Students(name, num, kor, mat, eng, food, music, movie, professor);
					//tmp.setValue(name, num, kor, mat, eng, food, music, movie, professor);
					print_to_textarea(student[stuIndex]);
					//print_to_textarea(tmp);
					stuIndex++;
				} while(rs.next()); 
				popupMsg(1, "ALERT", "LOAD COMPLETE.");
			} else {
				popupMsg(2, "ALERT", "DO NOT FIND DATA.");
			}
			rs.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		disconnectDB();
	}
}