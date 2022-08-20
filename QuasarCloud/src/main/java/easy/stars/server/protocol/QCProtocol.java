package easy.stars.server.protocol;

import easy.stars.exceptions.ServerNotAllowedException;
import easy.stars.exceptions.URLNotFound;
import easy.stars.server.Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.security.Permission;
import java.util.List;
import java.util.Map;

public class QCProtocol extends Process {

    HttpURLConnection connection;

    final URL url;

    byte[] out;

    ConnectionType connectionType;

    public QCProtocol(Runnable process, ConnectionType connectionType, boolean fxUse) {
        super(process, fxUse);
        this.connectionType = connectionType;
        try {
            this.url = new URL(Server.MAIN_URL + connectionType.getUrl());
        } catch (MalformedURLException e) {
            throw new URLNotFound(e, Server.MAIN_URL + connectionType.getUrl());
        }
        try {
            connection = (HttpURLConnection) this.url.openConnection();
        } catch (IOException e) {
            throw new ServerNotAllowedException(e);
        }
        startProcess();
    }

    public QCProtocol(Runnable process, URL url, boolean fxUse) {
        super(process, fxUse);
        this.url = url;
        this.connectionType = null;
        startProcess();
    }

    public QCProtocol(Runnable process, URL url) {
        this(process, url, false);
    }

    public QCProtocol(Runnable process, ConnectionType connectionType) {
        this(process, connectionType, false);
    }

    @Override
    protected void preProcess() {
        try {
            connection.connect();
        } catch (IOException e) {
            throw new ServerNotAllowedException(e);
        }
    }

    @Override
    protected void postProcess() {
        connection.disconnect();
    }

    public HttpURLConnection getConnection() {
        return connection;
    }

    public int getResponseCode() {
        try {
            return connection.getResponseCode();
        } catch (IOException e) {
            return -1;
        }
    }

    public void setOut(byte[] out) {
        this.out = out;
    }

    public void setAuthenticator(Authenticator auth) {
        connection.setAuthenticator(auth);
    }

    public String getHeaderFieldKey(int n) {
        return connection.getHeaderFieldKey(n);
    }

    public void setFixedLengthStreamingMode(int contentLength) {
        connection.setFixedLengthStreamingMode(contentLength);
    }

    public void setFixedLengthStreamingMode(long contentLength) {
        connection.setFixedLengthStreamingMode(contentLength);
    }

    public void setChunkedStreamingMode(int chunkLen) {
        connection.setChunkedStreamingMode(chunkLen);
    }

    public String getHeaderField(int n) {
        return connection.getHeaderField(n);
    }

    public void setInstanceFollowRedirects(boolean followRedirects) {
        connection.setInstanceFollowRedirects(followRedirects);
    }

    public boolean getInstanceFollowRedirects() {
        return connection.getInstanceFollowRedirects();
    }

    public void setRequestMethod(String method) throws ProtocolException {
        connection.setRequestMethod(method);
    }

    public String getRequestMethod() {
        return connection.getRequestMethod();
    }

    public String getResponseMessage() throws IOException {
        return connection.getResponseMessage();
    }

    public long getHeaderFieldDate(String name, long Default) {
        return connection.getHeaderFieldDate(name, Default);
    }

    public void disconnect() {
        connection.disconnect();
    }

    public boolean usingProxy() {
        return connection.usingProxy();
    }

    public Permission getPermission() throws IOException {
        return connection.getPermission();
    }

    public InputStream getErrorStream() {
        return connection.getErrorStream();
    }

    public void connect() throws IOException {
        connection.connect();
    }

    public void setConnectTimeout(int timeout) {
        connection.setConnectTimeout(timeout);
    }

    public int getConnectTimeout() {
        return connection.getConnectTimeout();
    }

    public void setReadTimeout(int timeout) {
        connection.setReadTimeout(timeout);
    }

    public int getReadTimeout() {
        return connection.getReadTimeout();
    }

    public URL getURL() {
        return connection.getURL();
    }

    public int getContentLength() {
        return connection.getContentLength();
    }

    public long getContentLengthLong() {
        return connection.getContentLengthLong();
    }

    public String getContentType() {
        return connection.getContentType();
    }

    public String getContentEncoding() {
        return connection.getContentEncoding();
    }

    public long getExpiration() {
        return connection.getExpiration();
    }

    public long getDate() {
        return connection.getDate();
    }

    public long getLastModified() {
        return connection.getLastModified();
    }

    public String getHeaderField(String name) {
        return connection.getHeaderField(name);
    }

    public Map<String, List<String>> getHeaderFields() {
        return connection.getHeaderFields();
    }

    public int getHeaderFieldInt(String name, int Default) {
        return connection.getHeaderFieldInt(name, Default);
    }

    public long getHeaderFieldLong(String name, long Default) {
        return connection.getHeaderFieldLong(name, Default);
    }

    public Object getContent() throws IOException {
        return connection.getContent();
    }

    public Object getContent(Class<?>[] classes) throws IOException {
        return connection.getContent(classes);
    }

    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return connection.getOutputStream();
    }

    public void setDoInput(boolean doInput) {
        connection.setDoInput(doInput);
    }

    public boolean getDoInput() {
        return connection.getDoInput();
    }

    public void setDoOutput(boolean doOutput) {
        connection.setDoOutput(doOutput);
    }

    public boolean getDoOutput() {
        return connection.getDoOutput();
    }

    public void setAllowUserInteraction(boolean allowUserInteraction) {
        connection.setAllowUserInteraction(allowUserInteraction);
    }

    public boolean getAllowUserInteraction() {
        return connection.getAllowUserInteraction();
    }

    public void setUseCaches(boolean useCaches) {
        connection.setUseCaches(useCaches);
    }

    public boolean getUseCaches() {
        return connection.getUseCaches();
    }

    public void setIfModifiedSince(long ifModifiedSince) {
        connection.setIfModifiedSince(ifModifiedSince);
    }

    public long getIfModifiedSince() {
        return connection.getIfModifiedSince();
    }

    public boolean getDefaultUseCaches() {
        return connection.getDefaultUseCaches();
    }

    public void setDefaultUseCaches(boolean defaultUseCaches) {
        connection.setDefaultUseCaches(defaultUseCaches);
    }

    public void setRequestProperty(String key, String value) {
        connection.setRequestProperty(key, value);
    }

    public void addRequestProperty(String key, String value) {
        connection.addRequestProperty(key, value);
    }

    public String getRequestProperty(String key) {
        return connection.getRequestProperty(key);
    }

    public Map<String, List<String>> getRequestProperties() {
        return connection.getRequestProperties();
    }

    public enum ConnectionType {
        META("metadata"),
        REGISTER("tg/register"),
        SEND("tg/send"),
        UPDATE("tg/update"),
        PING("status"),
        ;

        final String url;

        ConnectionType(String type) {
            this.url = type;
        }

        public String getUrl() {
            return url;
        }
    }
}
