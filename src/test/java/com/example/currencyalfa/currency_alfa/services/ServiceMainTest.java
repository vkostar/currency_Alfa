package com.example.currencyalfa.currency_alfa.services;

import com.example.currencyalfa.currency_alfa.client.GifClient;
import com.example.currencyalfa.currency_alfa.client.RatesClient;
import com.example.currencyalfa.currency_alfa.exceptions.GiphyApiException;
import com.example.currencyalfa.currency_alfa.models.GiphyData;
import com.example.currencyalfa.currency_alfa.models.GiphyImage;
import com.example.currencyalfa.currency_alfa.models.GiphyImages;
import com.example.currencyalfa.currency_alfa.models.GiphyResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceMainTest {

    @Mock
    private GifClient gifClient;

    @Mock
    private RatesClient ratesClient; // Mocked but not used in getUrl tests

    @Mock
    private ModelMapper modelMapper; // Mocked but not used in getUrl tests

    @InjectMocks
    private ServiceMain serviceMain;

    private final String MOCK_API_KEY = "test-api-key";
    private final String TEST_TAG = "test_tag";
    private final String TEST_URL = "http://example.com/gif.gif";

    @BeforeEach
    void setUp() {
        // Inject the mock API key into the private field
        ReflectionTestUtils.setField(serviceMain, "giphyApiKey", MOCK_API_KEY);
    }

    private GiphyResponse createFullGiphyResponse(String url) {
        GiphyResponse response = new GiphyResponse();
        GiphyData data = new GiphyData();
        GiphyImages images = new GiphyImages();
        GiphyImage image = new GiphyImage();
        image.setUrl(url);
        images.setOriginal(image);
        data.setImages(images);
        response.setData(data);
        return response;
    }

    @Test
    void whenValidResponse_thenReturnsUrl() {
        GiphyResponse mockedResponse = createFullGiphyResponse(TEST_URL);
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(ResponseEntity.ok(mockedResponse));

        String actualUrl = serviceMain.getUrl(TEST_TAG);
        assertEquals(TEST_URL, actualUrl);
    }

    @Test
    void whenResponseEntityIsNull_thenThrowsGiphyApiException() {
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(null);

        GiphyApiException exception = assertThrows(GiphyApiException.class, () -> {
            serviceMain.getUrl(TEST_TAG);
        });
        assertEquals("Giphy API response or body is null", exception.getMessage());
    }
    
    @Test
    void whenResponseBodyIsNull_thenThrowsGiphyApiException() {
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(ResponseEntity.ok(null));

        GiphyApiException exception = assertThrows(GiphyApiException.class, () -> {
            serviceMain.getUrl(TEST_TAG);
        });
        assertEquals("Giphy API response or body is null", exception.getMessage());
    }

    @Test
    void whenDataIsNull_thenThrowsGiphyApiException() {
        GiphyResponse mockedResponse = new GiphyResponse();
        mockedResponse.setData(null);
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(ResponseEntity.ok(mockedResponse));

        GiphyApiException exception = assertThrows(GiphyApiException.class, () -> {
            serviceMain.getUrl(TEST_TAG);
        });
        assertEquals("Invalid Giphy API response structure - URL not found", exception.getMessage());
    }

    @Test
    void whenImagesIsNull_thenThrowsGiphyApiException() {
        GiphyResponse mockedResponse = new GiphyResponse();
        GiphyData data = new GiphyData();
        data.setImages(null);
        mockedResponse.setData(data);
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(ResponseEntity.ok(mockedResponse));

        GiphyApiException exception = assertThrows(GiphyApiException.class, () -> {
            serviceMain.getUrl(TEST_TAG);
        });
        assertEquals("Invalid Giphy API response structure - URL not found", exception.getMessage());
    }

    @Test
    void whenOriginalIsNull_thenThrowsGiphyApiException() {
        GiphyResponse mockedResponse = new GiphyResponse();
        GiphyData data = new GiphyData();
        GiphyImages images = new GiphyImages();
        images.setOriginal(null);
        data.setImages(images);
        mockedResponse.setData(data);
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(ResponseEntity.ok(mockedResponse));

        GiphyApiException exception = assertThrows(GiphyApiException.class, () -> {
            serviceMain.getUrl(TEST_TAG);
        });
        assertEquals("Invalid Giphy API response structure - URL not found", exception.getMessage());
    }

    @Test
    void whenUrlIsNull_thenThrowsGiphyApiException() {
        GiphyResponse mockedResponse = createFullGiphyResponse(null);
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(ResponseEntity.ok(mockedResponse));

        GiphyApiException exception = assertThrows(GiphyApiException.class, () -> {
            serviceMain.getUrl(TEST_TAG);
        });
        assertEquals("Invalid Giphy API response structure - URL not found", exception.getMessage());
    }

    @Test
    void whenUrlIsEmpty_thenThrowsGiphyApiException() {
        GiphyResponse mockedResponse = createFullGiphyResponse("");
        when(gifClient.getImage(eq(MOCK_API_KEY), eq(TEST_TAG)))
                .thenReturn(ResponseEntity.ok(mockedResponse));

        GiphyApiException exception = assertThrows(GiphyApiException.class, () -> {
            serviceMain.getUrl(TEST_TAG);
        });
        assertEquals("Giphy API response structure is valid, but URL is empty", exception.getMessage());
    }
}
