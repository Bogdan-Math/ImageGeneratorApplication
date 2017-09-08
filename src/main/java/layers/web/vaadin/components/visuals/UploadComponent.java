package layers.web.vaadin.components.visuals;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Upload;
import layers.web.vaadin.components.listeners.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;

@SpringComponent
@Scope("session")
public class UploadComponent extends Upload {

    @Autowired
    private UploadReceiver receiver;

    @Autowired
    private UploadStartedListener startedListener;

    @Autowired
    private UploadProgressListener progressListener;

    @Autowired
    private UploadSucceededListener succeededListener;

    @Autowired
    private UploadFinishedListener finishedListener;

    @PostConstruct
    public void postConstruct() {
        setImmediateMode(true);
        setButtonCaption("select and generate image");
        setReceiver(receiver);
        addStartedListener(startedListener);
        addProgressListener(progressListener);
        addSucceededListener(succeededListener);
        addFinishedListener(finishedListener);
    }
}