package layer.web.vaadin.layout.header;

import layer.web.vaadin.ImageGeneratorPageObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@ContextConfiguration(locations = {
        "classpath*:layer/web/vaadin/UIIT-context.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class HeaderLayoutUIIT {

    @Autowired
    private ImageGeneratorPageObject imageGeneratorPage;

    @Before
    public void setUp() throws Exception {
        open(imageGeneratorPage.url());
    }

    @Test
    public void codacyLink() {
        imageGeneratorPage.codacyLink().click();
        assertThat(title(), containsString("ImageGenerator - Codacy - Dashboard"));
    }

    @Test
    public void githubLink() {
        imageGeneratorPage.githubLink().click();
        assertThat(title(), containsString("GitHub - Bogdan-Math/ImageGenerator"));
    }
}