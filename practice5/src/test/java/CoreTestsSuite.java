import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(TextProcessorTest.class)
@IncludeTags("core")
public class CoreTestsSuite {
    // порожній клас для вибіркового заруску тестів
}