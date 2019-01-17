package com.microsoft.graph.httpcore;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.ProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.message.BasicHttpResponse;
import org.junit.Test;

public class RedirectHandlerTest {
	
	String testmeurl = "https://graph.microsoft.com/v1.0/me/";
	String testurl = "https://graph.microsoft.com/v1.0/";

	@Test
	public void testIsRedirectedFailure() {
		RedirectHandler redirectHandler = RedirectHandler.INSTANCE;
		HttpGet httpget = new HttpGet(testmeurl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_MOVED_TEMPORARILY, "Moved Temporarily");
		HttpClientContext localContext = HttpClientContext.create();
		try {
			boolean isRedirected = redirectHandler.isRedirected(httpget, response, localContext);
			assertTrue(!isRedirected);
		} catch (ProtocolException e) {
			e.printStackTrace();
			fail("Redirect handler testIsRedirectedFailure failure");
		}
	}
	
	@Test
	public void testIsRedirectedFailure1() {
		RedirectHandler redirectHandler = RedirectHandler.INSTANCE;
		HttpGet httpget = new HttpGet(testmeurl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_BAD_REQUEST, "Bad Request");
		HttpClientContext localContext = HttpClientContext.create();
		try {
			boolean isRedirected = redirectHandler.isRedirected(httpget, response, localContext);
			assertTrue(!isRedirected);
		} catch (ProtocolException e) {
			e.printStackTrace();
			fail("Redirect handler testIsRedirectedFailure1 failure");
		}
	}
	
	@Test
	public void testIsRedirectedSuccess() {
		RedirectHandler redirectHandler = RedirectHandler.INSTANCE;
		HttpGet httpget = new HttpGet(testmeurl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_MOVED_TEMPORARILY, "Moved Temporarily");
		response.setHeader("location", testmeurl);
		HttpClientContext localContext = HttpClientContext.create();
		try {
			boolean isRedirected = redirectHandler.isRedirected(httpget, response, localContext);
			assertTrue(isRedirected);
		} catch (ProtocolException e) {
			e.printStackTrace();
			fail("Redirect handler testIsRedirectedSuccess failure");
		}
	}
	
	@Test
	public void testGetRedirectForGetMethod() {
		RedirectHandler redirectHandler = RedirectHandler.INSTANCE;
		HttpGet httpget = new HttpGet(testurl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_MOVED_TEMPORARILY, "Moved Temporarily");
		response.setHeader("location", testmeurl);
		HttpClientContext localContext = HttpClientContext.create();
		try {
			HttpRequest request = redirectHandler.getRedirect(httpget, response, localContext);
			assertTrue(request != null);
			final String method = request.getRequestLine().getMethod();
			assertTrue(method.equalsIgnoreCase(HttpGet.METHOD_NAME));
		} catch (ProtocolException e) {
			e.printStackTrace();
			fail("Redirect handler testGetRedirectForGetMethod failure");
		}
	}
	
	@Test
	public void testGetRedirectForHeadMethod() {
		RedirectHandler redirectHandler = RedirectHandler.INSTANCE;
		HttpHead httphead = new HttpHead(testurl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_MOVED_TEMPORARILY, "Moved Temporarily");
		response.setHeader("location", testmeurl);
		HttpClientContext localContext = HttpClientContext.create();
		try {
			HttpRequest request = redirectHandler.getRedirect(httphead, response, localContext);
			assertTrue(request != null);
			final String method = request.getRequestLine().getMethod();
			assertTrue(method.equalsIgnoreCase(HttpHead.METHOD_NAME));
		} catch (ProtocolException e) {
			e.printStackTrace();
			fail("Redirect handler testGetRedirectForHeadMethod failure");
		}
	}
	
	@Test
	public void testGetRedirectForPostMethod() {
		RedirectHandler redirectHandler = RedirectHandler.INSTANCE;
		HttpPost httppost = new HttpPost(testurl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_MOVED_TEMPORARILY, "Moved Temporarily");
		response.setHeader("location", testmeurl);
		HttpClientContext localContext = HttpClientContext.create();
		try {
			HttpRequest request = redirectHandler.getRedirect(httppost, response, localContext);
			assertTrue(request != null);
			final String method = request.getRequestLine().getMethod();
			assertTrue(method.equalsIgnoreCase(HttpPost.METHOD_NAME));
		} catch (ProtocolException e) {
			e.printStackTrace();
			fail("Redirect handler testGetRedirectForPostMethod failure");
		}
	}
	
	@Test
	public void testGetRedirectForPostMethod1() {
		RedirectHandler redirectHandler = RedirectHandler.INSTANCE;
		HttpPost httppost = new HttpPost(testurl);
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1, HttpStatus.SC_SEE_OTHER, "See Other");
		response.setHeader("location", testmeurl);
		HttpClientContext localContext = HttpClientContext.create();
		try {
			HttpRequest request = redirectHandler.getRedirect(httppost, response, localContext);
			assertTrue(request != null);
			final String method = request.getRequestLine().getMethod();
			assertTrue(method.equalsIgnoreCase(HttpGet.METHOD_NAME));
		} catch (ProtocolException e) {
			e.printStackTrace();
			fail("Redirect handler testGetRedirectForPostMethod1 failure");
		}
	}

}
