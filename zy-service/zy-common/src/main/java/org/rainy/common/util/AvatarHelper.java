package org.rainy.common.util;

import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Random;

public class AvatarHelper {

    // 可以直接在<img/>标签或者浏览器地址栏预览的base64编码头
    public static final String BASE64_PREFIX = "data:image/png;base64,";
    public static final String IMAGE_ROOT_URL = "D:/";
    public static final String IMAGE_SUFFIX = "png";

    
    public static String createAvatar(String username) throws IOException {
        String avatarBase64 = createBase64Avatar(username);
        return base64ToImage(avatarBase64, username);
    }
    
    /**
     * 生成头像的base64编码
     *
     * @param username
     * @return
     * @throws IOException
     */
    public static String createBase64Avatar(String username) throws IOException {
        return new String(Base64.getEncoder().encode(create(username)));
    }

    /**
     * base64转图片
     *
     * @param base64
     */
    public static String base64ToImage(String base64, String imageName) {
        
        File imageFile = new File(String.format("%s/%s.%s", IMAGE_ROOT_URL, imageName, IMAGE_SUFFIX));
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(imageFile))) {
            byte[] decode = Base64.getDecoder().decode(base64.getBytes());
            out.write(decode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageFile.getPath();
    }

    /**
     * 根据username生成一个头像，颜色随机。如果是使用hashCode()值的话，值可能为负数。需要要注意。
     *
     * @param username
     * @return
     * @throws IOException
     */
    public static byte[] create(String username) throws IOException {
        int width = 20;
        int grid = 5;
        int padding = width / 2;
        int size = width * grid + width;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        Graphics2D _2d = img.createGraphics();
        _2d.setColor(new Color(240, 240, 240));
        _2d.fillRect(0, 0, size, size);
        _2d.setColor(randomColor(80, 200));
        char[] idchars = createIdent(username);
        int i = idchars.length;
        for (int x = 0; x < Math.ceil(grid / 2.0); x++) {
            for (int y = 0; y < grid; y++) {
                if (idchars[--i] < 53) {
                    _2d.fillRect((padding + x * width), (padding + y * width), width, width);
                    if (x < Math.floor(grid / 2)) {
                        _2d.fillRect((padding + ((grid - 1) - x) * width), (padding + y * width), width, width);
                    }
                }
            }
        }
        _2d.dispose();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(img, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static Color randomColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(Math.abs(bc - fc));
        int g = fc + random.nextInt(Math.abs(bc - fc));
        int b = fc + random.nextInt(Math.abs(bc - fc));
        return new Color(r, g, b);
    }

    private static char[] createIdent(String username) {
        BigInteger bi_content = new BigInteger(username.getBytes());
        BigInteger bi = new BigInteger(username + "identicon" + username, 36);
        bi = bi.xor(bi_content);
        return bi.toString(10).toCharArray();
    }

    public static void main(String[] args) throws Exception {
        String avatar = createBase64Avatar("zhangyu");
        System.out.println(AvatarHelper.BASE64_PREFIX + avatar);
        String imageUrl = base64ToImage(avatar, "zhangyu");
        System.out.println(imageUrl);
    }

}
