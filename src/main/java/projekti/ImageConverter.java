package projekti;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageConverter {
    public byte[] convertForWeb(byte[] file) throws IOException {
        BufferedImage image = toBufferedImage(file);
        
        int originalHeight = image.getHeight();
        int orginalWidth = image.getWidth();
        double aspectRatio = (double) orginalWidth / originalHeight;

        int height;
        int width;
        if (aspectRatio < 1.58) {
            height = 500;
            width = (int) (aspectRatio * height);
        } else {
            width = 790;
            height = (int) (width / aspectRatio);
        }

        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        return toByteArray(resizedImage);
    }

    private BufferedImage toBufferedImage(byte[] file) throws IOException {
        InputStream in = new ByteArrayInputStream(file);
        BufferedImage image = ImageIO.read(in);
        return image;
    }

    private byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", os);
        os.flush();
        byte[] imageInByte = os.toByteArray();
        os.close();
        return imageInByte;
    }
}
