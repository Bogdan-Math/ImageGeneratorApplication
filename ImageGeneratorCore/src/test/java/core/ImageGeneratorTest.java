package core;

import domain.InformationalImage;
import exception.MatrixSizeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import system.ResourceReader;

import java.io.InputStream;

import static domain.InformationalColor.*;
import static java.util.stream.Collectors.toMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ImageGeneratorTest {

    private ImageGenerator imageGenerator = new BasicImageGenerator();
    private ResourceReader resourceReader = new ResourceReader();
    private Settings settings             = mock(BasicSettings.class);
    private InformationalImage incomeImage;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {

        //set fields
        imageGenerator.setSettings(settings);

        //mock actions
        when(settings.getPatterns()).thenReturn(resourceReader.readAllIn("images/colors")
                .asByteArrays()
                .map(InformationalImage::new)
                .collect(toMap(
                        InformationalImage::averagedColor,
                        informationalImage -> informationalImage
                )));
        when(settings.getExpectedColumnsCount()).thenReturn(31); //31 -  because we want to move into incrementing loop for image width
        when(settings.getSubImage(anyInt(), anyInt(), anyInt(), anyInt())).thenCallRealMethod();
    }

    @Test
    public void exceptionMessage() {
        doReturn(5).when(settings).getExpectedColumnsCount();
        doReturn(3).when(settings).getImageWidth();
        thrown.expect(MatrixSizeException.class);
        thrown.expectMessage("Count of expected columns (is 5) could not be more than image width (is 3)");

        imageGenerator.generateImage();
    }

    @Test
    public void generateWhiteImage() {

        //Arrange
        this.incomeImage = new InformationalImage(streamOf("images/testable/1-white.jpg"));
        when(settings.getIncomeImage()).thenReturn(incomeImage);
        when(settings.getImageWidth()).thenReturn(incomeImage.getWidth());
        when(settings.getImageHeight()).thenReturn(incomeImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(generatedImage.averagedColor(), is(WHITE));
    }

    @Test
    public void generateGrayImage() {

        //Arrange
        this.incomeImage = new InformationalImage(streamOf("images/testable/2-gray.jpg"));
        when(settings.getIncomeImage()).thenReturn(incomeImage);
        when(settings.getImageWidth()).thenReturn(incomeImage.getWidth());
        when(settings.getImageHeight()).thenReturn(incomeImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(generatedImage.averagedColor(), is(GRAY));
    }

    @Test
    public void generateBlackImage() {

        //Arrange
        this.incomeImage = new InformationalImage(streamOf("images/testable/3-black.jpg"));
        when(settings.getIncomeImage()).thenReturn(incomeImage);
        when(settings.getImageWidth()).thenReturn(incomeImage.getWidth());
        when(settings.getImageHeight()).thenReturn(incomeImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25
        assertThat(generatedImage.averagedColor(), is(BLACK));
    }

    @Test
    public void generate4x4Image() {

        //Arrange
        this.incomeImage = new InformationalImage(streamOf("images/testable/4x4.jpg"));
        when(settings.getIncomeImage()).thenReturn(incomeImage);
        when(settings.getImageWidth()).thenReturn(incomeImage.getWidth());
        when(settings.getImageHeight()).thenReturn(incomeImage.getHeight());

        //Act
        InformationalImage generatedImage = imageGenerator.generateImage();

        //Assert
        assertNotNull(generatedImage);
        verify(settings, times(1024)).getSubImage(anyInt(), anyInt(), anyInt(), anyInt());//1024 = 32 * 32
        assertThat(generatedImage.getWidth(),  is(800));// 800 = 32 * 25
        assertThat(generatedImage.getHeight(), is(800));// 800 = 32 * 25

        assertThat(generatedImage.getSubImage(0, 0, 200, 200).averagedColor().almostEqualTo(BLACK), is(true));
        assertThat(generatedImage.getSubImage(200, 0, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(0, 200, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(200, 200, 200, 200).averagedColor().almostEqualTo(BLUE), is(true));

        assertThat(generatedImage.getSubImage(400, 0, 200, 200).averagedColor().almostEqualTo(GREEN), is(true));
        assertThat(generatedImage.getSubImage(600, 0, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(400, 200, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(600, 200, 200, 200).averagedColor().almostEqualTo(RED), is(true));

        assertThat(generatedImage.getSubImage(0, 400, 200, 200).averagedColor().almostEqualTo(RED), is(true));
        assertThat(generatedImage.getSubImage(200, 400, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(0, 600, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(200, 600, 200, 200).averagedColor().almostEqualTo(GREEN), is(true));

        assertThat(generatedImage.getSubImage(400, 400, 200, 200).averagedColor().almostEqualTo(BLUE), is(true));
        assertThat(generatedImage.getSubImage(600, 400, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(400, 600, 200, 200).averagedColor().almostEqualTo(WHITE), is(true));
        assertThat(generatedImage.getSubImage(600, 600, 200, 200).averagedColor().almostEqualTo(BLACK), is(true));

        assertThat(generatedImage.getSubImage(600, 600, 200, 200).averagedColor().almostEqualTo(WHITE), is(false));
    }

    private InputStream streamOf(String pathToImage) {
        return getClass().getClassLoader().getResourceAsStream(pathToImage);
    }
}