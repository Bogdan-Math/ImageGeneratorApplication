package core;

import domain.InformationalColor;
import domain.InformationalImage;
import exception.MatrixSizeException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static core.Settings.PATTERN_HEIGHT;
import static core.Settings.PATTERN_WIDTH;

public class BasicImageGenerator implements ImageGenerator {

    private Settings settings;

    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    //TODO: add descriptive comments to this method
    private List<List<InformationalImage>> asMatrix() {
        int expectedColumns = settings.getExpectedColumnsCount();
        int expectedRows    = 0;

        int width  = settings.getImageWidth();
        int height = settings.getImageHeight();

        if (expectedColumns > width) {
            throw new MatrixSizeException(String
                    .format("Count of expected columns (is %s) could not be more than image width (is %s).", expectedColumns, width));
        }

        int realColumnsNumber = expectedColumns;
        int realRowsNumber    = expectedRows;

        int squareWidth  = width / realColumnsNumber;
        int squareHeight = squareWidth * PATTERN_HEIGHT / PATTERN_WIDTH;

        squareHeight = (squareHeight != 0) ? squareHeight : 1;

        while (width - squareWidth * realColumnsNumber >= squareWidth) {
            realColumnsNumber++;
        }

        while (height - squareHeight * realRowsNumber >= squareHeight) {
            realRowsNumber++;
        }

        List<List<InformationalImage>> matrix = new ArrayList<>();
        for (int i = 0; i < realColumnsNumber; i++) {

            List<InformationalImage> matrixRow = new ArrayList<>();
            for (int j = 0; j < realRowsNumber; j++) {
                matrixRow.add(settings.getSubImage(i * squareWidth, j * squareHeight, squareWidth, squareHeight));
            }

            matrix.add(matrixRow);
        }

        return matrix;
    }

    private List<List<InformationalColor>> averagedColorsMatrix() {
        return asMatrix().stream()
                .map(row -> row.stream()
                        .map(InformationalImage::averagedColor)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    private List<List<InformationalImage>> resultMatrix() {
        List<List<InformationalColor>> matrix           = averagedColorsMatrix();
        Map<InformationalColor, InformationalImage> map = settings.getPatterns();
        List<List<InformationalImage>> result           = new ArrayList<>();

        for (List<InformationalColor> colors : matrix) {
            List<InformationalImage> resultRows = new ArrayList<>();

            for (InformationalColor color : colors) {
                int minColor              = Integer.MAX_VALUE;
                InformationalImage minImg = null;

                for (InformationalColor pColor : map.keySet()) {
                    int dColor = Math.abs(color.getRed()   - pColor.getRed())   +
                                 Math.abs(color.getGreen() - pColor.getGreen()) +
                                 Math.abs(color.getBlue()  - pColor.getBlue());
                    if (dColor < minColor) {
                        minColor = dColor;
                        minImg   = map.get(pColor);
                    }
                }
                resultRows.add(minImg);

            }
            result.add(resultRows);
        }
        return result;
    }

    @Override
    public InformationalImage generateImage() {
        List<List<InformationalImage>> resultMatrix = resultMatrix();

        int columns = resultMatrix.size();
        int rows = resultMatrix.get(0).size();

        InformationalImage imageWithMaxSize = findImageWithMaxSize(resultMatrix);
        int width  = imageWithMaxSize.getWidth() * columns;
        int height = imageWithMaxSize.getHeight() * rows;

        int squareWidth = width / columns;
        int squareHeight = height / rows;

        InformationalImage averagedImg = new InformationalImage(width, height, InformationalImage.TYPE_INT_RGB);

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                averagedImg.getGraphics()
                           .drawImage(resultMatrix.get(i).get(j).resizeTo(imageWithMaxSize.getWidth(), imageWithMaxSize.getHeight()),
                                   i * squareWidth,
                                   j * squareHeight,
                                   null);
            }
        }

        return averagedImg;
    }

    private InformationalImage findImageWithMaxSize(List<List<InformationalImage>> imgMatrix) {
        return imgMatrix.stream()
                        .flatMap(List::stream)
                        .max(Comparator.comparing(img -> img.getWidth() * img.getHeight()))
                        .get();
    }
}