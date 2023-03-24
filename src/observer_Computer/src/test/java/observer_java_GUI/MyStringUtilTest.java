package observer_java_GUI;

import org.junit.Test;
import static org.junit.Assert.*;
import observer_java_GUI.src.charts_panels.panel_recorder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyStringUtilTest {

    @Test
    public void testSplitStringFromOccurrenceOfLastDirtySymbol() throws NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = panel_recorder.class;
        Method method = clazz.getDeclaredMethod("split_string_from_occurrence_of_last_dirty_symbol", String.class);
        method.setAccessible(true);

        // Test case 1: string contains forward slash
        String str1 = "path/to/file.png";
        String[] expected1 = { "path/to", "file.png" };
        assertArrayEquals(expected1, (String[]) method.invoke(null, str1));

        // Test case 2: string contains backslash
        String str2 = "C:\\path\\to\\file.png";
        String[] expected2 = { "C:\\path\\to", "file.png" };
        assertArrayEquals(expected2, (String[]) method.invoke(null, str2));

        // Test case 3: string contains neither forward slash nor backslash
        String str3 = "filename.png";
        String[] expected3 = { "", "filename.png" };
        assertArrayEquals(expected3, (String[]) method.invoke(null, str3));

        // Test case 4: string with long path and contains both the forward and back
        // slashes
        String str4 = "C:\\Google Drive/Electronics\\MicroControllers\\tp pratice\\2022-09-28 [Raspberry Pi 4 Self Driving Vechicle]\\src/observer_Computer/imgsqweqwe/Frame from 2023-03-24; March, Friday at time 10-23-06.258.png";
        String[] expected4 = {
                "C:\\Google Drive/Electronics\\MicroControllers\\tp pratice\\2022-09-28 [Raspberry Pi 4 Self Driving Vechicle]\\src/observer_Computer/imgsqweqwe",
                "Frame from 2023-03-24; March, Friday at time 10-23-06.258.png" };
        assertArrayEquals(expected4, (String[]) method.invoke(null, str4));
    }
}
