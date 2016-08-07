package GameState;

import Main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by MERDovashkinar on 8/5/2016.
 */
public class Updater extends GameState implements MouseListener{

    private String ftpURL_map = "ftp://%s:%s@%s/%s";
    private String server = "node0.net2ftp.ru";
    private String user = "vladschekochikhin%40gmail.com";
    private String pass = "9aa7fb95";
    private String newVersion = "version.txt";
    private String ftpURL;
    private int state;
    private ArrayList<String> output;
    URL url;

    URLConnection conn;
    InputStream inputStream;
    FileOutputStream outputStream;

    byte[] buffer = new byte[8192];
    int bytesRead1;
    boolean bytesRead = true;

    File file;
    FileReader fileReader;
    BufferedReader bufferedReader;

    private String version;

    private long progress;

    Font font;

    //buttons
    private Color[] color;
    private ArrayList<Rectangle> buttons;
    private String[] text;
    private int currentState;
    private int action;
    private boolean choose;

    private Point2D location;

    private boolean stop;



    public Updater (GameStateManager gsm){
        this.gsm =gsm;
        init();
    }

    private void goNext(){
        if (stop) return;
        state++;
    }

    @Override
    public void init() {
        state=0;
        ftpURL = String.format(ftpURL_map, user, pass, server, newVersion);
        output = new ArrayList<>();
        progress=0;
        font = new Font("Courier New", Font.PLAIN,18);

        //button
        buttons = new ArrayList<>();
        text = new String[]{
                "Update",
                "Pause",
                "Back",
                "Start"
        };
        for (int i=0;i<3;i++){
            buttons.add(new Rectangle(GamePanel.WIDTH/2-275+200*i,GamePanel.HEIGHT/2-350,150,50));
        }
        currentState=0;

        color = new Color[3];
        color[0] = Color.ORANGE;
        color[1] = Color.CYAN;
        color[2] = Color.BLUE;

        action=0;
        location = MouseInfo.getPointerInfo().getLocation();
        press=false;
    }

    @Override
    public void update() throws IOException {

        if (choose){
            if (currentState==0){
                if(state==-1){
                    state=1;
                }
            }else if(currentState==1){
                stop=!stop;
            }else if(currentState==2){
                gsm.setState(GameStateManager.MENUSTATE);
            }else if(currentState==3){
                File file = new File(version);
                File started = new File("start.bat");
                FileWriter writer = new FileWriter(started);
                writer.write("java -jar \"" +file.getAbsolutePath()+"\"");
                writer.close();
                Runtime.getRuntime().exec("cmd /c "+started.getAbsolutePath());
                Runtime.getRuntime().exit(0);
            }
        }

        switch (state){
            case 0:
                output.add("Hello, I gonna update. Press Enter to start");
                state=-1;
                break;
            case -1:
                break;
            case 1:
                try {
                    url = new URL(ftpURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                if (url!=null){
                    goNext();
                }
                break;
            case 2:
                output.add("Connecting to : "+ftpURL);
                goNext();
                break;
            case 3:
                conn = url.openConnection();
                inputStream = conn.getInputStream();
                outputStream = new FileOutputStream(newVersion);
                goNext();
                break;
            case 4:
                output.add("Connected");
                output.add("Getting version");
                goNext();
                break;
            case 5:
                while((bytesRead1 = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead1);
                }
                outputStream.close();
                inputStream.close();
                goNext();
                break;
            case 6:
                file = new File(newVersion);
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                version = bufferedReader.readLine();
                bufferedReader.close();
                fileReader.close();
                file.delete();
                output.add ("Newest version is " + version);
                goNext();
                break;
            case 7:
                String[] f = (new File(".")).list();
                for (int i=0;i<f.length;i++){
                    if (f[i].equals(version)){
                        state=11;
                        output.add("All files up-to-date");
                        break;
                    }
                }
                state++;
                break;
            case 8:
                output.add("Downloading new version");
                goNext();
                break;
            case 9:
                ftpURL = String.format(ftpURL_map, user, pass, server, version);
                url = new URL (ftpURL);
                conn = url.openConnection();
                inputStream = conn.getInputStream();
                outputStream = new FileOutputStream(version);
                output.add("connected");
                output.add("downloading: ");
                goNext();
                break;
            case 10:
                while ((bytesRead1 = inputStream.read(buffer)) != -1) {
                    progress++;
                    outputStream.write(buffer, 0, bytesRead1);
                }
                goNext();
                output.set(output.size()-1,"downloaded: " + progress*5067 +" bytes");
                break;
            case 11:
                outputStream.close();
                inputStream.close();
                goNext();
                buttons.add( new Rectangle(GamePanel.WIDTH/2+300,GamePanel.HEIGHT/2,150,50));
                break;
            case 12:
                output.add("Update complete");
                goNext();
                break;
            default:
                break;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        //set font
        g.setFont(font);

        //draw bg
        g.setColor(Color.black);
        g.fillRect(0,0, GamePanel.WIDTH,GamePanel.HEIGHT);

        //draw button
        for (int i=0;i<buttons.size();i++){
            if (i==currentState){
                g.setColor(Color.ORANGE);
                g.fill(buttons.get(i));
            }else{
                g.setColor(Color.GREEN);
                g.fill(buttons.get(i));
            }
            g.setFont(font);
            g.setColor(Color.black);
            g.drawString(text[i],(int) buttons.get(i).getX()+25,(int) buttons.get(i).getY()+25);
        }

        //draw
        g.setColor(Color.WHITE);
        g.fillRect(GamePanel.WIDTH/2-300,GamePanel.HEIGHT/2-300,600,600);

        // draw text
        g.setColor(Color.black);
        for (int i=0;i<output.size();i++){
            g.drawString(output.get(i),GamePanel.WIDTH/2-300,GamePanel.HEIGHT/2-300+30*(i+1));
        }
    }


    @Override
    public void keyPressed(int k) {
        if (k== KeyEvent.VK_ENTER){
            choose=true;
        }
        if (k==KeyEvent.VK_D){
            currentState++;
            if (currentState>=buttons.size()){
                currentState=0;
            }
        }
        if (k==KeyEvent.VK_A){
            currentState--;
            if (currentState<0){
                currentState=buttons.size()-1;
            }
        }

    }

    @Override
    public void keyReleased(int k) {
        if (k==KeyEvent.VK_ENTER){
            choose=false;
        }
    }

    private boolean press;
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        press=true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        press=false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
