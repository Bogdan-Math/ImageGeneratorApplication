package layers.web.vaadin.layout.images;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class ImagesLayout extends GridLayout {

    @Getter
    @Autowired
    @Qualifier(value = "originalImageView")
    private Image originalImageView;

    @Getter
    @Autowired
    @Qualifier(value = "generatedImageView")
    private Image generatedImageView;

    @PostConstruct
    public void postConstruct() {

        setColumns(2);
        setRows(1);
        setSizeFull();
        setSpacing(true);

        originalImageView.setSizeFull();
        generatedImageView.setSizeFull();

        originalImageView.setStyleName("bordered");
        generatedImageView.setStyleName("bordered");

        addComponents(originalImageView, generatedImageView);
    }
}
