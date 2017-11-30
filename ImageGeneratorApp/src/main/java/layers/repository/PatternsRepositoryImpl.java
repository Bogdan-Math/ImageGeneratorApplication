package layers.repository;

import domain.InformationalColor;
import domain.InformationalImage;
import domain.PatternType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import system.ObjectTypeConverter;
import system.ResourceReader;

import java.util.Map;

import static domain.PatternType.*;
import static java.util.stream.Collectors.toMap;

@Repository
@Scope("singleton")
public class PatternsRepositoryImpl implements PatternsRepository {

    @Autowired
    private ResourceReader resourceReader;

    @Autowired
    private ObjectTypeConverter converter;

    @Override
    public Map<InformationalColor, InformationalImage> getCommons() {
        return initialize(COMMONS);
    }

    @Override
    public Map<InformationalColor, InformationalImage> getFlags() {
        return initialize(FLAGS);
    }

    @Override
    public Map<InformationalColor, InformationalImage> getPlains() {
        return initialize(PLAINS);
    }

    private Map<InformationalColor, InformationalImage> initialize(PatternType patternType) {
        return resourceReader.readFiles(patternType.getLocation())
                             .stream()
                             .collect(toMap(file -> converter.informationalImage(file).averagedColor(),// put Color              as KEY   in map
                                            file -> converter.informationalImage(file),                // put InformationalImage as VALUE in map
                                            (img_color_1, img_color_2) -> {
                                                System.out.println("Two same averaged colors: ");
                                                System.out.println(img_color_1);
                                                System.out.println(img_color_2);

                                                return img_color_1;
                                            }
                                     )
                             );
    }
}