package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ResourceTest {

    private Resource resource;

    @Before
    public void setUp() throws Exception {
        this.resource  = new Resource(createFile("images/puppy.jpg"));
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFullName() {
        assertEquals("puppy.jpg", resource.getNameWithExtension());
    }

    @Test
    public void getOnlyName() {
        assertEquals("original_image", resource.getOnlyName());
    }

    @Test
    public void getOnlyExtension() throws Exception {
        assertEquals("jpg", resource.getOnlyExtension());
    }

    private File createFile(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource("").getPath() + resourceName);
    }

}