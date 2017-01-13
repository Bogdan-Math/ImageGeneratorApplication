import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageGenerator {

    public void copyImage(File fromFile, File toFile, String outputFormat) throws IOException {
            ImageIO.write(fileToBufferedImage(fromFile), outputFormat, toFile);
    }

    public BufferedImage fileToBufferedImage(File fromFile) throws IOException {
        return ImageIO.read(fromFile);
    }

    public byte[] bufferedImageToByteArray(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        baos.flush();
        byte[] imageInBytes = baos.toByteArray();
        baos.close();
        return imageInBytes;
    }

    public BufferedImage byteArrayToBufferedImage(byte[] imageInBytes) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(imageInBytes));
    }

    public void copyHalfImage(File fromFile, File toFile, String outputFormat) throws IOException {
        BufferedImage bufferedImage = fileToBufferedImage(fromFile);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        ImageIO.write(bufferedImage.getSubimage(0, 0, width / 2, height / 2), outputFormat, toFile);
    }
}
