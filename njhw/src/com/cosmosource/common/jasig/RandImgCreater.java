package com.cosmosource.common.jasig;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
/**
* @author 
* 
* image check RandImgCreater
*/
public class RandImgCreater {
    
    private static final String 
        CODE_LIST = "ABCDEFGHJMNPQRSTUVWXY23456789";
    private HttpServletResponse response = null;
    private static final int HEIGHT = 20;
    private static final int FONT_NUM = 4;
    private int width = 0;
    private int iNum = 0;
    private String codeList = "";
    private boolean drawBgFlag = false;
    
    private int rBg = 0;
    private int gBg = 0;
    private int bBg = 0;
    
    public RandImgCreater(HttpServletResponse response) {
        this.response = response;
        this.width = 13 * FONT_NUM + 12;
        this.iNum = FONT_NUM;
        this.codeList = CODE_LIST;
    }
    
    public RandImgCreater(HttpServletResponse response,int iNum) {
        this.response = response;
        this.width = 13 * iNum + 12;
        this.iNum = iNum;
        this.codeList = CODE_LIST;
    }
    
    public RandImgCreater(HttpServletResponse response,int iNum,String codeList) {
        this.response = response;
        this.width = 13 * iNum + 12;
        this.iNum = iNum;
        this.codeList = codeList;        
    }
    /**
     * ��jsp�е��ñ�����,���ܳ���getOutputStream() has already been called for this response�쳣,
     * ��ʹ��: response.flushBuffer();out.clear();out = pageContext.pushBody(); ���д���
     * @return ��У��ͼƬ,��������Ӧ���ַ�
     */ 
    public String createRandImage(){
        BufferedImage image = new BufferedImage(width, HEIGHT,
                BufferedImage.TYPE_INT_RGB);        
        Graphics g = image.getGraphics();   
        Color c = g.getColor();
        Random random = new Random();        
        if (drawBgFlag ){    //һ������ڶ��ε��ø÷���ʱ,����������
            g.setColor(new Color(rBg,gBg,bBg));
            g.fillRect(0, 0, width, HEIGHT);
        }else{
            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, width, HEIGHT);            
            for (int i = 0; i < 155; i++) { //������
                g.setColor(getRandColor(140, 200));
                int x = random.nextInt(width);
                int y = random.nextInt(HEIGHT);
                int xl = random.nextInt(10);
                int yl = random.nextInt(10);
                g.drawLine(x, y, x + xl, y + yl);
            }
        }        
        g.setFont(new Font("Times New Roman", Font.PLAIN, 18));        
        String sRand="";
        for (int i=0;i<iNum;i++){ //���ַ�
            int rand=random.nextInt(codeList.length());
            String strRand=codeList.substring(rand,rand+1);
            sRand+=strRand;
            g.setColor(new Color(26+random.nextInt(117),20+random.nextInt(117),20+random.nextInt(117)));
            g.drawString(strRand,13*i+6,16);
        }   
        g.setColor(c);
        g.dispose();
        OutputStream os = null;
        try{
            os = response.getOutputStream();
            ImageIO.write(image, "JPEG", os);
        }catch(IOException e){
            e.printStackTrace();
        }     
        finally{
            try {
                if(os!=null){
                    os.flush();
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }            
            os=null;
        }
        return sRand;
    }
    
    public void setBgColor(int r,int g,int b){ //���ñ�����ɫ 
        drawBgFlag = true;
        this.rBg = r;
        this.gBg = g;
        this.bBg = b;
    }

    private Color getRandColor(int fc, int bc) {//�õ�һ��������ɫֵ
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}