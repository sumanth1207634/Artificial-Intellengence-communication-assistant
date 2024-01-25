import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.sql.*;

import javax.swing.border.EmptyBorder;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.*;


public class Home

{

    public class Start{

    public Start(){
     
   JFrame root  =  new JFrame();

    JLabel l1 = new JLabel(" LPU - AICA");
    l1.setBounds(118, 80, 200, 30);
    l1.setFont(l1.getFont().deriveFont(32.0f));
    root.add(l1);

    JButton b1 = new JButton(" Start Chatting");
    root.setLayout(null);
    b1.setBounds(125, 450, 150, 50);
    root.add(b1);


    ImageIcon icon = new ImageIcon("E:/B2 sem/Java/Sms/src/images/Blogo.png");
    JLabel l = new JLabel(icon);
    l.setBounds(100, 180, icon.getIconWidth(), icon.getIconHeight());
    root.add(l);

    Color E4A649 = new Color(228, 166, 73);
    root.getContentPane().setBackground(E4A649);


    b1.addActionListener(e -> {
      root.dispose();
      try {
        new Chat();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    }); 

    root.setSize(450, 700);
    root.setResizable(false);
    root.setVisible(true);
    root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
      
  }

  
    public class Chat{

    public Chat() throws IOException{    
      
      JFrame f = new JFrame();
      
      Color E4A649 = new Color(228, 166, 73);
      f.getContentPane().setBackground(E4A649); 
     

      java.net.URL imgURL1 = Home.class.getResource("/images/arrow.png");
      ImageIcon img1 = new ImageIcon(imgURL1);
      JLabel l2 = new JLabel(img1);
      l2.setBounds(2, 10, 50, 50);
      f.add(l2);


      l2.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
          f.dispose();
          System.exit(0);
        }
      });


      java.net.URL imgURL = Home.class.getResource("/images/logo.png");
      ImageIcon img = new ImageIcon(imgURL);
      JLabel l1 = new JLabel(img);
      l1.setBounds(5, 10, 150, 50);
      f.add(l1);


      JLabel l3 = new JLabel("CHAT LPU");
      l3.setBounds(110, 12, 150, 30);
      l3.setFont(l3.getFont().deriveFont(20.0f));
      l3.setForeground(Color.WHITE);
      f.add(l3);

      JLabel l4 = new JLabel("Active Now");
      l4.setBounds(110, 21, 150, 50);
      l4.setForeground(Color.WHITE);
      f.add(l4);

  
      java.net.URL imgURL3 = Home.class.getResource("/images/video.png");
      ImageIcon img3 = new ImageIcon(imgURL3);
      JLabel l6 = new JLabel(img3);
      l6.setBounds(340, 12, 50, 47);
      f.add(l6);

      l6.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {


            Webcam webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(640, 480));


            WebcamPanel panel = new WebcamPanel(webcam);
            panel.setFPSDisplayed(true);
            panel.setDisplayDebugInfo(true);
            panel.setImageSizeDisplayed(true);
            panel.setMirrored(true);

            JFrame window = new JFrame("Test webcam panel");
            window.add(panel);
            window.setResizable(true);


            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
            window.setSize(600, 600);
            window.pack();
            window.setVisible(true);

            window.addWindowListener(new java.awt.event.WindowAdapter() {
              @Override
              public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                  webcam.close();
              }
          });



        }
      });

      java.net.URL imgURL2 = Home.class.getResource("/images/menu.png");
      ImageIcon img2 = new ImageIcon(imgURL2);
      JLabel l5 = new JLabel(img2);
      l5.setBounds(395, 10, 30, 49);
      f.add(l5);

      JPanel p1 = new JPanel();  
      p1.setBounds(0, 75, 440, 543);
      p1.setBackground(Color.WHITE);
      f.add(p1);

      l5.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
          JPopupMenu popup = new JPopupMenu();
          JMenuItem item1 = new JMenuItem("Clear Chat");
          JMenuItem item2 = new JMenuItem("Exit");
          JMenuItem item3 = new JMenuItem("Insert");
          JMenuItem item4 = new JMenuItem("Delete");
          popup.add(item3);
          popup.add(item4);
          popup.add(item1);
          popup.add(item2);
        
          popup.show(l5, -52, 67);

          item1.addActionListener(e -> {
            p1.removeAll();
            p1.revalidate();
            p1.repaint();
          });

          item2.addActionListener(e -> {
            f.dispose();
            System.exit(0);
          });

          item3.addActionListener(e -> {
            f.dispose();
            new Insert();
          });
        
          item4.addActionListener(e -> {
            f.dispose();
            new Delete();
          });
       
       
        }

     
      }); 

      p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
      JTextField t1 = new JTextField();
      t1.setBounds(2, 619, 323, 43);
      t1.setFont(t1.getFont().deriveFont(20.0f));
      f.add(t1);

      JButton b1 = new JButton("Send");
      f.setLayout(null);
      b1.setBounds(327, 619, 106, 43);
      f.add(b1);


       SessionName session;
       SessionsSettings sessionsSettings;
       // Set up Dialogflow session
       String projectId = "auto-reply-vgqd";
       String sessionId = java.util.UUID.randomUUID().toString();
       session = SessionName.of(projectId, sessionId);

       // Set up credentials from JSON file
       String credentialsFilePath = "E:/B2 sem/Java/Sms/src/auto-reply-vgqd-ff08e2959876.json";
       GoogleCredentials credentials = null;
       try (InputStream inputStream = new FileInputStream(credentialsFilePath)) {
           credentials = GoogleCredentials.fromStream(inputStream);
       } catch (IOException ex) {
           ex.printStackTrace();
           System.exit(1);
       }

       SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
       settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(credentials));
        sessionsSettings = settingsBuilder.build();
      

      b1.addActionListener(e -> {
          
          String text  = t1.getText();
          System.out.println(text);
          t1.setText("");
         
          JPanel p2 = new JPanel();
            if (text != null && !text.equals("")) {

                            p2.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
                            JLabel l8 = new JLabel(text);
                            l8.setFont(new Font("Tahoma", Font.PLAIN, 16));
                            l8.setBackground(new Color(228 , 166, 73));
                            l8.setOpaque(true);
                            l8.setBorder(new EmptyBorder(15, 15, 15, 15));
                            l8.setBounds(0, 10, 100, 100);
                            
                            p2.add(l8);
                            p2.setBackground(Color.WHITE);
                            p1.add(p2);
                            p1.revalidate();
                            p1.repaint();
  
          }
       

             if(text.equals("clear")){
    
            p1.removeAll();
            p1.revalidate();
            p1.repaint();
          }


            else if (text.equals("exit")) {
            f.dispose();
            System.exit(0);
          }

           
             else  if (text.equals("insert")){
            f.dispose();
            new Insert();
          }


            else  if (text.equals("delete")) {
            f.dispose();
            new Delete();
          }


            else  if(text.equals("help")){

            new JOptionPane();
            JOptionPane.showMessageDialog(null, "1. clear : To clear the chat\n2. exit : To exit the chat\n3. insert : To insert a new record\n4. delete : To delete a record", "Help", JOptionPane.INFORMATION_MESSAGE);

          }
       
   
          else  if(text.length()==8){

            int id1 = Integer.parseInt(text);
           try{
            Connection  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","1234");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from student where UID = "+id1);

            while(rs.next()){
              JPanel p4 = new JPanel();
              p4.setLayout(new FlowLayout(FlowLayout.LEFT));
              JLabel l10 = new JLabel("The given user id is,"+rs.getString(1));
              l10.setFont(new Font("Tahoma", Font.PLAIN, 16));
              l10.setBackground(new Color(228 , 166, 73));
              l10.setOpaque(true);
              l10.setBorder(new EmptyBorder(15, 15, 15, 15));
              l10.setBounds(0, 10, 100, 100);
              p4.add(l10);
              p4.setBackground(Color.WHITE);
              p1.add(p4);
              p1.revalidate();
              p1.repaint();

              JPanel p5 = new JPanel();
              p5.setLayout(new FlowLayout(FlowLayout.LEFT));
              JLabel l11 = new JLabel("hello "+rs.getString(2)+" 😄");
              l11.setFont(new Font("Tahoma", Font.PLAIN, 16));
              l11.setBackground(new Color(228 , 166, 73));
              l11.setOpaque(true);
              l11.setBorder(new EmptyBorder(15, 15, 15, 15));
              l11.setBounds(0, 10, 100, 100);
              p5.add(l11);
              p5.setBackground(Color.WHITE);
              p1.add(p5);
              p1.revalidate();
              p1.repaint();

              JPanel p6 = new JPanel();
              p6.setLayout(new FlowLayout(FlowLayout.LEFT));
              JLabel l12 = new JLabel("Your attendence is"+rs.getString(3));
              l12.setFont(new Font("Tahoma", Font.PLAIN, 16));

              l12.setBackground(new Color(228 , 166, 73));

              l12.setOpaque(true);
              l12.setBorder(new EmptyBorder(15, 15, 15, 15));
              l12.setBounds(0, 10, 100, 100);
              p6.add(l12);
              p6.setBackground(Color.WHITE);
              p1.add(p6);
              p1.revalidate();
              p1.repaint();

              JPanel p7 = new JPanel();
              p7.setLayout(new FlowLayout(FlowLayout.LEFT));
              JLabel l13 = new JLabel("Your CGPA is"+rs.getString(4));
              l13.setFont(new Font("Tahoma", Font.PLAIN, 16));
              l13.setBackground(new Color(228 , 166, 73));
              l13.setOpaque(true);
              l13.setBorder(new EmptyBorder(15, 15, 15, 15));
              l13.setBounds(0, 10, 100, 100);
              p7.add(l13);
              p7.setBackground(Color.WHITE);
              p1.add(p7);
              p1.revalidate();
              p1.repaint(); 
            }

            
           }catch(Exception e1){
              System.out.println(e1);
           
            }

          }

       
          else if (text != null ) {
            System.out.println(text);

            try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
              TextInput.Builder textInput = TextInput.newBuilder().setText(text).setLanguageCode("en-US");
              QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
              DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
              String reply = response.getQueryResult().getFulfillmentText();  
              
              JPanel p3 = new JPanel();
              p3.setLayout(new FlowLayout(FlowLayout.LEFT));
              JLabel l9 = new JLabel(reply);
              l9.setFont(new Font("Tahoma", Font.PLAIN, 16));
              l9.setBackground(new Color(228 , 166, 73));
              l9.setOpaque(true);
              l9.setBorder(new EmptyBorder(15, 15, 15, 15));
              l9.setBounds(0, 10, 100, 100);
              p3.add(l9);
              p3.setBackground(Color.WHITE);
              p1.add(p3);
              p1.revalidate();
              p1.repaint();

              

          } catch (Exception ex) {
              ex.printStackTrace();
          }

          }

    });
          
      f.setSize(450, 700);
      f.setResizable(false);
      f.setUndecorated(false);
      f.setVisible(true);

    }

  
  }



    public class Insert{
        public Insert()
     {
    JFrame f = new JFrame("Insert");
    f.getContentPane().setBackground(new Color(228, 166, 73));


    f.setLayout(null);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel l1 = new JLabel("INSERT");
    l1.setBounds(150,50, 200, 30);
    l1.setFont(l1.getFont().deriveFont(35.0f));
    f.add(l1);



    JLabel l0 = new JLabel("User ID");
    l0.setBounds(80, 140, 120, 30);
    l0.setFont(l0.getFont().deriveFont(20.0f));
    f.add(l0);
    JTextField t0 = new JTextField();
    t0.setBounds(250, 140, 100, 30);
    t0.setFont(t0.getFont().deriveFont(20.0f));
    f.add(t0);


 
    JLabel l2 = new JLabel("Name");
    l2.setBounds(80, 210, 100, 30);
    l2.setFont(l2.getFont().deriveFont(20.0f));
    f.add(l2);


    JTextField t1 = new JTextField();
    t1.setBounds(250, 210, 100, 30);
    t1.setFont(t0.getFont().deriveFont(20.0f));
    f.add(t1);

    
    JLabel l3 = new JLabel("Attendance");
    l3.setBounds(80, 270, 150, 30);
    l3.setFont(l3.getFont().deriveFont(20.0f));
    f.add(l3);
    JTextField t2 = new JTextField();
    t2.setBounds(250, 270, 100, 30);
    t2.setFont(t0.getFont().deriveFont(20.0f));
    f.add(t2);
    JLabel l4 = new JLabel("C.G.P.A");
    l4.setBounds(80, 330, 100, 30);
    l4.setFont(l4.getFont().deriveFont(20.0f));
    f.add(l4);
    JTextField t3 = new JTextField();
    t3.setBounds(250, 330, 100, 30);
    t3.setFont(t0.getFont().deriveFont(20.0f));
    f.add(t3);

    JLabel l5 = new JLabel("Image");
    l5.setBounds(80, 390, 100, 30);
    l5.setFont(l5.getFont().deriveFont(20.0f));
    f.add(l5);

    JButton b1 = new JButton("File");
    b1.setBounds(250, 390, 100, 30);
    b1.setFont(t0.getFont().deriveFont(20.0f));
    f.add(b1);

    JFileChooser fc = new JFileChooser();
    b1.addActionListener(e -> {
      int i = fc.showOpenDialog(f);
      if (i == JFileChooser.APPROVE_OPTION) {
        File f1 = fc.getSelectedFile();
        String filepath = f1.getPath();
        System.out.println(filepath);
      }
    });

    JButton b2 = new JButton("Insert");
    b2.setBounds(60, 480, 110, 40);
    f.add(b2);

    b2.addActionListener(e -> {
     
      
      if(t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("") || t0.getText().equals("")){
        JOptionPane.showMessageDialog(f, "Please fill all the fields");
      }
      else{
        try 
        {
          Connection  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","1234");
          Statement stmt = conn.createStatement();
          String sql = "CREATE TABLE IF NOT EXISTS student (UID VARCHAR(255), Name VARCHAR(255), Attendance VARCHAR(255), Marks VARCHAR(255), Image VARCHAR(255))";
          stmt.executeUpdate(sql);
          String sql1 = "INSERT INTO student (UID, Name, Attendance, Marks, Image) VALUES ('"+t0.getText()+"', '"+t1.getText()+"', '"+t2.getText()+"', '"+t3.getText()+"', '"+fc.getSelectedFile().getPath()+"')";
          stmt.executeUpdate(sql1);
          conn.close();
          JOptionPane.showMessageDialog(f, "Inserted Successfully");
          
        } 
        catch (Exception e1) 
        {
          System.out.println(e1);
        }

        f.dispose();
        try {
          new Chat();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }


    });
    JButton b3 = new JButton("Home");
    b3.setBounds(250, 480, 110, 40);
    f.add(b3);

    b3.addActionListener(e -> {
      f.dispose();
      try {
        new Chat();
      } catch (IOException e1) {
        e1.printStackTrace();
      }

      
    });




    f.setSize(450, 700);
    f.setResizable(false);
    f.setUndecorated(false);
    f.setVisible(true);
  }
}



    public class Delete{
    public Delete()
  {

    JFrame f = new JFrame("Delete");
    f.getContentPane().setBackground(new Color(228, 166, 73));
    f.setLayout(null);
   
    JLabel l1 = new JLabel("Delete");
    l1.setBounds(150, 50, 200, 30);
    l1.setFont(l1.getFont().deriveFont(35.0f));
    f.add(l1);




    JLabel l0 = new JLabel("User ID");
    l0.setBounds(80, 140, 120, 30);
    l0.setFont(l0.getFont().deriveFont(20.0f));
    f.add(l0);

    JTextField t0 = new JTextField();
    t0.setBounds(250, 140, 100, 30);
    t0.setFont(t0.getFont().deriveFont(20.0f));
    f.add(t0);


    JLabel l2 = new JLabel("Are you sure?");
    l2.setBounds(80, 200, 200, 30);
    l2.setFont(l2.getFont().deriveFont(20.0f));
    f.add(l2);


    JCheckBox c1 = new JCheckBox();
    c1.setBounds(250, 200, 30, 30);
    c1.setFont(c1.getFont().deriveFont(32.0f));
    f.add(c1);





    JButton b1 = new JButton("Delete");
    b1.setBounds(250, 350, 100, 30);
    f.add(b1);



    b1.addActionListener(e ->
    {
    if(t0.getText().equals(""))
    {
      JOptionPane.showMessageDialog(f, "Please Enter User ID");
    }
    else if(c1.isSelected() == false)
    {
      JOptionPane.showMessageDialog(f, "Please check the box");
    }
    else
    {
      
      try{
        Connection  conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java","root","1234");
        Statement stmt = conn.createStatement();
        String sql = "DELETE FROM student WHERE UID = '"+t0.getText()+"'";
        stmt.executeUpdate(sql);
        conn.close();
        JOptionPane.showMessageDialog(f, "Deleted Successfully");
        f.dispose();
        new Chat();
        
      }
      catch(Exception a)
      {
        System.out.println(a);
      }

    }

  });


  JButton b2 = new JButton("Chat");
  b2.setBounds(60, 350, 100, 30);
  f.add(b2);


  b2.addActionListener(e -> {
    f.dispose();
    try {
      new Chat();
    } catch (IOException e1) {
      e1.printStackTrace();
    }
  });


  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  f.setVisible(true);
  f.setBounds(100, 100, 450, 700);
  f.setResizable(false);

  }
}



public static void main(String[] args)
  {
     new Home().new Start();

  }


}