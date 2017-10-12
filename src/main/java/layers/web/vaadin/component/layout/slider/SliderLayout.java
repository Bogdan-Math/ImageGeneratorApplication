package layers.web.vaadin.component.layout.slider;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import domain.Settings;
import layers.web.vaadin.component.layout.slider.listener.ExpectedColumnsNumberField;
import layers.web.vaadin.component.layout.slider.listener.ExpectedColumnsNumberLabel;
import layers.web.vaadin.component.layout.slider.listener.ExpectedColumnsNumberSlider;
import layers.web.vaadin.component.layout.slider.publisher.ColumnsNumberPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Scope("session")
public class SliderLayout extends HorizontalLayout {

    @Autowired
    private Settings settings;

    @Autowired
    private ExpectedColumnsNumberLabel label;

    @Autowired
    private ExpectedColumnsNumberField field;

    @Autowired
    private ExpectedColumnsNumberSlider slider;

    @Autowired
    private ColumnsNumberPublisher columnsNumberPublisher;

    @PostConstruct
    public void postConstruct() {
        setSizeFull();

        addComponents(label, field, slider);

        setComponentAlignment(label,  Alignment.MIDDLE_CENTER);
        setComponentAlignment(field,  Alignment.MIDDLE_CENTER);
        setComponentAlignment(slider, Alignment.MIDDLE_CENTER);

        columnsNumberPublisher.publishNewValue(settings.getExpectedColumnsNumber());
    }
}