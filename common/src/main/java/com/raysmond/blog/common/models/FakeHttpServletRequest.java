package com.raysmond.blog.common.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import javax.persistence.*;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.StringReader;
import java.security.Principal;
import java.util.*;

@Data
@Getter @Setter
public class FakeHttpServletRequest {

    private static final String HTTP = "http";

    private static final String HTTPS = "https";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private static final String HOST_HEADER = "Host";

    private static final String CHARSET_PREFIX = "charset=";

    /**
     * Date formats as specified in the HTTP RFC
     * @see <a href="https://tools.ietf.org/html/rfc7231#section-7.1.1.1">Section 7.1.1.1 of RFC 7231</a>
     */
    private static final String[] DATE_FORMATS = new String[] {
            "EEE, dd MMM yyyy HH:mm:ss zzz",
            "EEE, dd-MMM-yy HH:mm:ss zzz",
            "EEE MMM dd HH:mm:ss yyyy"
    };


    // ---------------------------------------------------------------------
    // Public constants
    // ---------------------------------------------------------------------

    /**
     * The default protocol: 'HTTP/1.1'.
     * @since 4.3.7
     */
    public static final String DEFAULT_PROTOCOL = "HTTP/1.1";

    /**
     * The default scheme: 'http'.
     * @since 4.3.7
     */
    public static final String DEFAULT_SCHEME = HTTP;

    /**
     * The default server address: '127.0.0.1'.
     */
    public static final String DEFAULT_SERVER_ADDR = "127.0.0.1";

    /**
     * The default server name: 'localhost'.
     */
    public static final String DEFAULT_SERVER_NAME = "localhost";

    /**
     * The default server port: '80'.
     */
    public static final int DEFAULT_SERVER_PORT = 80;

    /**
     * The default remote address: '127.0.0.1'.
     */
    public static final String DEFAULT_REMOTE_ADDR = "127.0.0.1";

    /**
     * The default remote host: 'localhost'.
     */
    public static final String DEFAULT_REMOTE_HOST = "localhost";

    // ---------------------------------------------------------------------
    // ServletRequest properties
    // ---------------------------------------------------------------------

    private final Map<String, Object> attributes = new LinkedHashMap<String, Object>();

    private String characterEncoding;

    private byte[] content;

    private String contentType;

    private final Map<String, String[]> parameters = new LinkedHashMap<String, String[]>();

    private String protocol = DEFAULT_PROTOCOL;

    private String scheme = DEFAULT_SCHEME;

    private String serverName = DEFAULT_SERVER_NAME;

    private int serverPort = DEFAULT_SERVER_PORT;

    private String remoteAddr = DEFAULT_REMOTE_ADDR;

    private String remoteHost = DEFAULT_REMOTE_HOST;

    private boolean secure = false;

    private int remotePort = DEFAULT_SERVER_PORT;

    private String localName = DEFAULT_SERVER_NAME;

    private String localAddr = DEFAULT_SERVER_ADDR;

    private int localPort = DEFAULT_SERVER_PORT;

    private boolean asyncStarted = false;

    private boolean asyncSupported = false;

    private String realIP;


    // ---------------------------------------------------------------------
    // HttpServletRequest properties
    // ---------------------------------------------------------------------

    private String authType;

    private String method;

    private String pathInfo;

    private String contextPath = "";

    private String queryString;

    private String remoteUser;

    private final Set<String> userRoles = new HashSet<String>();

    private String requestedSessionId;

    private String requestURI;

    private String servletPath = "";

    private boolean requestedSessionIdValid = true;

    private boolean requestedSessionIdFromCookie = true;

    private boolean requestedSessionIdFromURL = false;

    private final Map<String, String> headers = new HashMap<>();

    private String userAgent;

    // ---------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------

    public FakeHttpServletRequest() {
        this.method = "method";
        this.requestURI = "requestURI";
        this.remoteAddr = "remoteAddr";
        this.realIP = "realIP";
        this.userAgent = "userAgent";
        headers.put("Content-Type", "application/json");
        headers.put("Host", "localhost");
        headers.put("User-Agent", "curl/7.43.0");
    }

    public String getHeader(String name) {
        String header = headers.get(name);
        return header;
    }

    public String getRemoteAddr() {
        return this.remoteAddr;
    }

}
