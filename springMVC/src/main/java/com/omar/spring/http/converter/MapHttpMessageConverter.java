package com.omar.spring.http.converter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author omar
 * Company:   csii
 * @version: 1.0
 * @since: JDK 1.6.0_26
 * Create at:   2018/9/20
 * Description: 测试版(仅供测试用，并未完全实现)消息转换器，将请求字符串转换为Map
 */
public class MapHttpMessageConverter extends AbstractHttpMessageConverter<Map> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");


    private final List<Charset> availableCharsets;

    private boolean writeAcceptCharset = true;

    public MapHttpMessageConverter(){
        this(DEFAULT_CHARSET);
    }

    public MapHttpMessageConverter(Charset defaultCharset){
        super(defaultCharset, MediaType.TEXT_PLAIN, MediaType.ALL);
        this.availableCharsets = new ArrayList<Charset>(Charset.availableCharsets().values());
    }

    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        this.writeAcceptCharset = writeAcceptCharset;
    }


    @Override
    protected boolean supports(Class<?> clazz) {
        return Map.class==clazz;
    }



    @Override
    protected Map readInternal(Class<? extends Map> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Charset charset = getContentTypeCharset(inputMessage.getHeaders().getContentType());
        String paramStr=StreamUtils.copyToString(inputMessage.getBody(), charset);
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit=paramStr.split("[&]");
        for(String strSplit:arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            if (arrSplitEqual.length > 1) {
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    @Override
    protected void writeInternal(Map map, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }

    @Override
    protected Long getContentLength(Map map, MediaType contentType) throws IOException {




        return super.getContentLength(map, contentType);
    }

    protected List<Charset> getAcceptedCharsets() {
        return this.availableCharsets;
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharset() != null) {
            return contentType.getCharset();
        }
        else {
            return getDefaultCharset();
        }
    }
}
