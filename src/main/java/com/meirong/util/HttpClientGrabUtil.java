package com.meirong.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpClientGrabUtil {
	private static HttpClient hc = new SystemDefaultHttpClient();

	public static void main(String[] args) {

		// System.out.println(testFetchAuchan());

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpGet loginHttpGet = new HttpGet("https://auchan.chinab2bi.com/security/login.hlt");
		// https://auchan.chinab2bi.com/security/login.hlt

		// HttpGet testGet = new
		// HttpGet("https://auchan.chinab2bi.com/auchan/buyGrnQry/detail.hlt?grnid=224428161");
		// testGet.setHeader("Set-Cookie",
		// "JSESSIONID=rcpgJLgS1QLt3H8TyPLws8hYVNjn9165mJLpb2mlLhFhHBpkSLp6!-628297828; path=/; domain=auchan.chinab2bi.com; HttpOnly");
		//
		// try {
		// HttpResponse response = closeableHttpClient.execute(testGet);
		// System.out.println(EntityUtils.toString(response.getEntity()));
		// } catch (ClientProtocolException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		try {
			HttpResponse loginResponse = closeableHttpClient.execute(loginHttpGet);

			Header[] headersBackFromLoginPage = loginResponse.getAllHeaders();
			System.out.println("Headers in login page ---------------------------------------------------");
			for (Header header : headersBackFromLoginPage) {

				System.out.println(header.getName() + "  ==  " + header.getValue());
			}
			loginResponse.getEntity().consumeContent();
			// loginHttpGet.releaseConnection();

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("j_username", "AC1356"));
			params.add(new BasicNameValuePair("j_password", "shyf1356"));

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");

			entity.setContentType("application/x-www-form-urlencoded"); 
			HttpPost loginPost = new HttpPost("https://auchan.chinab2bi.com/j_spring_security_check.hlt");
			loginPost.setEntity(entity);
			for (Header header : headersBackFromLoginPage) {
				if (header.getName().equalsIgnoreCase("Transfer-Encoding")) {
					continue;
				}
				loginPost.addHeader(header);
			}

			HttpResponse clickLoginButtonResponse = closeableHttpClient.execute(loginPost);
			System.out.println("==============================================================================");
			System.out.println(EntityUtils.toString(clickLoginButtonResponse.getEntity()));
			System.out.println("==============================================================================");

			Header[] headersBackFromClickSubmitButton = clickLoginButtonResponse.getAllHeaders();
			for (Header header : headersBackFromClickSubmitButton) {

				System.out.println(header.getName() + "  ==  " + header.getValue());
			}

			HttpGet again = new HttpGet("https://auchan.chinab2bi.com/security/login.hlt");
			closeableHttpClient.execute(again);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String fetchHTMLwithURL(String url) {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		request.addHeader("Content-Type", " text/html;charset=UTF-8");
		StringBuffer result = new StringBuffer();
		try {
			HttpResponse response = client.execute(request);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "gb2312"));

			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			return new String(result.toString().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static String testFetchAuchan() {
		String indexPageURL = "https://auchan.chinab2bi.com/security/login.hlt";

		HttpGet getHomePage = new HttpGet(indexPageURL);
		try {
			HttpResponse reponse = hc.execute(getHomePage);
			HttpEntity entity = reponse.getEntity();

			System.err.println(EntityUtils.toString(entity));
			System.out.println(hc);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String loginActionUrl = "https://auchan.chinab2bi.com/j_spring_security_check.hlt";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("j_username", "AC1356"));
		params.add(new BasicNameValuePair("j_password", "shyf1356"));

		String body = null;
		HttpPost httppost = new HttpPost(loginActionUrl);
		try {
			httppost.setEntity(new UrlEncodedFormEntity(params));
			// httppost.addHeader("JSESSIONID",
			// "JSESSIONID=fwb4JL2H7wnkNrhkgR7rTLrQxy66b65HR4JNPLWyPyXnBtD8Gs5g!-628297828");
			HttpResponse httpresponse = hc.execute(httppost);
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			if (entity != null) {
				entity.consumeContent();
			}

			System.out.println(httpresponse.getFirstHeader("location").getValue());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			httppost.releaseConnection();
		}

		return "";
	}

	/**
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, List<NameValuePair> params) {
		String body = null;
		try {
			HttpPost httppost = new HttpPost(url);
			httppost.setEntity(new UrlEncodedFormEntity(params));
			HttpResponse httpresponse = hc.execute(httppost);
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity);
			if (entity != null) {
				entity.consumeContent();
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static String fetchHTMLwithURL(String url, String fileToStore) throws IOException {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		request.addHeader("User-Agent", "Test");
		request.addHeader("Content-Type", " text/html;charset=UTF-8");
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

		StringBuffer result = new StringBuffer();
		String line = "";
		FileWriter fileWriter = new FileWriter(new File(fileToStore));

		while ((line = rd.readLine()) != null) {
			// result.append(line + "\n");

			System.out.println(line);
			fileWriter.write(line + "\n");
		}

		fileWriter.close();

		return result.toString();
	}

	public static CloseableHttpClient createSSLClientDefault() {

		try {

			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

				public boolean isTrusted(X509Certificate[] chain,

				String authType) throws CertificateException {

					return true;

				}

			}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

			return HttpClients.custom().setSSLSocketFactory(sslsf).build();

		} catch (KeyManagementException e) {

			e.printStackTrace();

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();

		} catch (KeyStoreException e) {

			e.printStackTrace();

		}

		return HttpClients.createDefault();

	}

}
