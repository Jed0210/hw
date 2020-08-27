package com.example.myApp;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import
java.util.Random;
public class IdentifyingCode {
    //隨機數數組，驗證碼上的數字和字母
    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    //這是一個單例模式
    private static IdentifyingCode IdentifyingCode;

    public static IdentifyingCode getInstance() {
        if (IdentifyingCode == null) {
            IdentifyingCode = new IdentifyingCode();
        }
        return IdentifyingCode;
    }

    //驗證碼個數
    private static final int CODE_LENGTH = 4;
    //字體大小
    private static final int FONT_SIZE = 50;
    //線條數
    private static final int LINE_NUMBER = 5;
    //padding，其中base的意思是初始值，而range是變化範圍。數值根據自己想要的大小來設置
    private static final int BASE_PADDING_LEFT = 10, RANGE_PADDING_LEFT = 100, BASE_PADDING_TOP = 75, RANGE_PADDING_TOP = 50;
    //驗證碼默認寬高
    private static final int DEFAULT_WIDTH = 450, DEFAULT_HEIGHT = 150;

    //畫布的長寬
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;

    //字體的隨機位置
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;
    //驗證碼個數，線條數，字體大小
    private int codeLength = CODE_LENGTH, line_number = LINE_NUMBER, font_size = FONT_SIZE;

    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();

    //驗證碼圖片(生成一個用位圖)
    public Bitmap createBitmap() {
        padding_left = 0;
        padding_top = 0;
        //創建指定格式，大小的位圖//Config.ARGB_8888是一種色彩的存儲方法
        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bp);

        code = createCode();
        //將畫布填充爲白色
        c.drawColor(Color.WHITE);
        //新建一個畫筆
        Paint paint = new Paint();
        //設置畫筆抗鋸齒
        paint.setAntiAlias(true);
        paint.setTextSize(font_size);
        //在畫布上畫上驗證碼
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            //這裏的padding_left,padding_top是文字的基線
            c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }
        //畫干擾線
        for (int i = 0; i < line_number; i++) {
            drawLine(c, paint);
        }
        //保存一下畫布
        c.save();
        c.restore();
        return bp;
    }

    //生成驗證碼
    private String createCode() {
        StringBuilder sb = new StringBuilder();
        //利用random生成隨機下標
        for (int i = 0; i < codeLength; i++) {
            sb.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return sb.toString();
    }

    //畫線
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    //隨機文字樣式，顏色，文字粗細與傾斜度
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());//true爲粗體，false爲非粗體
        double skew = random.nextInt(11) / 10;
        //隨機ture或者false來生成正數或者負數，來表示文字的傾斜度，負數右傾，正數左傾
        skew = random.nextBoolean() ? skew : -skew;
        //   paint.setUnderlineText(true);//下劃線
        // paint.setStrikeThruText(true);//刪除線
    }

    //生成隨機顏色，利用RGB
    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }

    //驗證碼位置隨機
    private void randomPadding() {

        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }

    public String getCode() {
        return code;
    }




}






