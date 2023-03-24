package observer_java_GUI;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import observer_java_GUI.src.charts_panels.panel_recorder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class FormatStringWithTimeTest {
    private static String input;
    private static String expectedOutput;

    @Test
    void testFormatStringWithTime() throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = panel_recorder.class;
        Method method = clazz.getDeclaredMethod("format_string_with_time", String.class);
        method.setAccessible(true);

        // * testFormatStringWithTime
        input = "The current time is ${HH:mm:ss} on ${dd/MM/yyyy}";
        expectedOutput = "The current time is "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                + " on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        assertEquals(expectedOutput, method.invoke(null, input));

        // * testFormatStringWithTimeEmptyString
        input = "";
        expectedOutput = "";
        assertEquals(expectedOutput, method.invoke(null, input));

        // * testFormatStringWithTimeNoTimeSpecifier
        input = "This is a test string";
        expectedOutput = "This is a test string";
        assertEquals(expectedOutput, method.invoke(null, input));

        // * testFormatStringWithTimeSingleTimeSpecifier
        input = "${yyyy-MM-dd}";
        expectedOutput = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        assertEquals(expectedOutput, method.invoke(null, input));

        // * testFormatStringWithTimeMultipleTimeSpecifiers
        input = "The current time is ${HH:mm:ss} on ${dd/MM/yyyy}, and the year is ${yyyy}.";
        expectedOutput = "The current time is "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                + " on " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ", and the year is "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy")) + ".";
        assertEquals(expectedOutput, method.invoke(null, input));

        // * testFormatStringWithTimeInvalidTimeSpecifier
        input = "The time is ${invalidSpecifier}!";
        assertThrows(IllegalArgumentException.class, () -> method.invoke(input));
    }
}
