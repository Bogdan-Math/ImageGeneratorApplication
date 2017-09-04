package layers.service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

public interface ImageGenerator {

    //TODO: add descriptive comments to this method
    List<List<BufferedImage>> asMatrix(int expectedColumns);
    List<List<Color>> averagedColorsMatrix();
    List<List<BufferedImage>> resultMatrix();
    BufferedImage generateImage();

    //TODO: move this methods to config class/file/etc
    ImageGenerator setImage(BufferedImage image);
    ImageGenerator setPatterns(Map<Color, BufferedImage> patterns);
    ImageGenerator setExpectedColumnsNumber(Integer expectedColumnsNumber);
    BufferedImage getImage();
    Map<Color, BufferedImage> getPatterns();
    Integer getExpectedColumnsNumber();
    //TODO: delete this method, after add Spring to tests
    ImageGenerator setColorInfo(ColorInfoService colorInfoService);
}
