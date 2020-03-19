package easymocklearning.easymock;

import com.easymocklearning.easymock.*;
import org.easymock.EasyMock;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;

public class ForecastProcessorUnitTest {
    private static int MAX_TEMP = 90;

    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @TestSubject
    private ForecastProcessor forecastProcessor = new ForecastProcessor();

    @Mock
    private WeatherService mockWeatherService;

    @Before
    public void setUp() {
        forecastProcessor.setWeatherService(mockWeatherService);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void givenLocationName_whenWeatherServicePopulatesTemperatures_thenMaxTempReturned() throws ServiceUnavailableException {
        mockWeatherService.populateTemperature(EasyMock.anyObject(Location.class));
         EasyMock.expectLastCall()
                .andAnswer(() -> {
                    Location passedLocation = (Location) EasyMock.getCurrentArguments()[0];
                    passedLocation.setMaximumTemparature(new BigDecimal(MAX_TEMP));
                    passedLocation.setMinimumTemperature(new BigDecimal(MAX_TEMP - 10));
                    return null;
                });
        EasyMock.replay(mockWeatherService);
        BigDecimal maxTemperature = forecastProcessor.getMaximumTemperature("New York");
        EasyMock.verify(mockWeatherService);
        Assert.assertEquals(maxTemperature, new BigDecimal(MAX_TEMP));
    }
}
