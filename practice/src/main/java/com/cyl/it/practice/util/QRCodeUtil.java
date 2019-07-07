package com.cyl.it.practice.util;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengyuanliang
 * @desc 用于自定义生成二维码
 * @since 2019-07-06
 */
public class QRCodeUtil {

    private static final String FORMAT_NAME_JPG = "jpg";
    private static final String FORMAT_NAME_PNG = "png";


    /**
     * 生成二维码矩阵信息
     * @param content 二维码图片内容
     * @param width 二维码图片宽度
     * @param height 二维码图片高度
     * 不需要 needCompress 这个参数  所以直接传  true
     */
    private  static BufferedImage createImage(String content ,int width, int height,  int  margin ,String logoPath, boolean needCompress) throws Exception {
        Map<EncodeHintType, Object>hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // 指定编码方式,防止中文乱码
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M); // 指定纠错等级
        hintMap.put(EncodeHintType.MARGIN, margin); // 指定二维码四周白色区域大小
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hintMap);

        BufferedImage bufferedImage = toBufferedImage(bitMatrix, width, height, logoPath, needCompress);

        return bufferedImage;

    }


     /**
     *    将二维码图片输出
     *    生成二维码图片
     *    二维码矩阵信息
     *     图片格式
     *      输出流
     *     图片路径
     **/
    private static BufferedImage toBufferedImage(BitMatrix bitMatrix ,int width, int height, String logoPath , boolean needCompress) throws Exception{
        int bitWidth = bitMatrix.getWidth();
        int bitHeight = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(bitWidth, bitHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < bitWidth; x++) {
            for (int y = 0; y < bitHeight; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }


        if (!StringUtils.isEmpty(logoPath)) {
            File file = new File(logoPath);
            if (!file.exists()) {
                System.err.println("" + logoPath + "   该文件不存在！");
                return  image;
            }
            Image src = ImageIO.read(new File(logoPath));
            int logoWidth = src.getWidth(null);
            int logoHeight = src.getHeight(null);
            if (needCompress) { // 压缩LOGO
                if (logoWidth > width) {
                    logoWidth = width / 4 ;
                }
                if (logoHeight > height) {
                    logoHeight = height / 4;
                }
               /* Image logoImg = src.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
                //这个可以不需要 只是在本地看看效果
                BufferedImage tag = new BufferedImage(logoWidth, logoHeight, BufferedImage.TYPE_INT_RGB);
                Graphics g = tag.getGraphics();
                g.drawImage(logoImg, 0, 0, null); // 绘制缩小后的图
                g.dispose();
                src = logoImg;*/
            }
            // 插入LOGO
            Graphics2D graph = image.createGraphics();
            // 计算logo图片放置位置
            int x = (width - logoWidth) / 2;
            int y = (height - logoHeight) / 2;
            // 在二维码图片上绘制logo图片
            graph.drawImage(src, x, y, logoWidth, logoHeight, null);
            // 绘制logo边框,可选
           // graph.drawRoundRect(x, y, logoWidth, logoHeight, 10, 10);
            Shape shape = new RoundRectangle2D.Float(x, y ,logoWidth, logoHeight,6, 6);
            // 画笔粗细
            graph.setStroke(new BasicStroke(3f));
            graph.setColor(Color.WHITE); // 边框颜色
            graph.draw(shape);
            // graph.drawRect(x, y, logoWidth, logoHeight); // 矩形边框
            graph.dispose();
        }
        return image;
    }



    public static void encode( String path , String formatName  ,String content ,int width, int height,  int  margin ,String logoPath, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, width, height, margin, logoPath, needCompress);

        File file = new File(path);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if( !file.exists() || !file.isDirectory()){
            file.mkdirs();
        }
        ImageIO.write(image, FORMAT_NAME_PNG, file);
    }


    public static String decode(String filePath) throws Exception {
        if ("".equalsIgnoreCase(filePath) && filePath.length() == 0) {
            return "二维码图片不存在!";
        }
        File file = new File(filePath);
        // BufferedImage image = ImageIO.read(new FileInputStream(filePath));
        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;


        Map<DecodeHintType,  Object> hintMaps = new HashMap<>();
        hintMaps.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        hintMaps.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);
        result = new MultiFormatReader().decode(bitmap, hintMaps);
        //Result decode = new QRCodeReader().decode(bitmap, hintMaps);

        String resultStr = result.getText();
        return resultStr;
    }




    /**
     * 为图片添加文字
     * @param pressText 文字
     * @param newImage 带文字的图片
     * @param targetImage 需要添加文字的图片
     * @param fontStyle 字体风格
     * @param color 字体颜色
     * @param fontSize 字体大小
     * @param width 图片宽度
     * @param height 图片高度
     */
    public static void pressText(String pressText, String newImage, String targetImage, int fontStyle, Color color, int fontSize, int width, int height) {
        // 计算文字开始的位置
        // x开始的位置：（图片宽度-字体大小*字的个数）/2
        int startX = (width - (fontSize * pressText.length())) / 2;
        // y开始的位置：图片高度-（图片高度-图片宽度）/2
        int startY = height /*- (height - width) / 2 */+ fontSize;
        try {
            File file = new File(targetImage);
            BufferedImage src = ImageIO.read(file);
            int imageW = src.getWidth(null);
            int imageH = src.getHeight(null);
            BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, imageW, imageH, null);
            g.setColor(color);
            g.setFont(new Font(null, fontStyle, fontSize));
            g.drawString(pressText, startX, startY);
            g.dispose();
            FileOutputStream out = new FileOutputStream(newImage);
            ImageIO.write(image, "png", out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        String path = "D:\\www\\a\\c.png";

        try {
            //encode(path, FORMAT_NAME_PNG, "www.baidu.com", 300, 300, 0, null, true);
            //encode(path, FORMAT_NAME_PNG, "我不当大哥好多年", 300, 300, 0, null, true);
            encode(path, FORMAT_NAME_PNG, "我不当大哥好多年", 600, 600, 0, "D:\\myVue\\project\\study\\src\\assets\\images\\1.jpg", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}