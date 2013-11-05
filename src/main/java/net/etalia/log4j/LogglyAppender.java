/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.etalia.log4j;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * This appender send entries to <a href="http://www.loggly.com/">Loggly</a>
 * 
 * @author <a href="mailto:daniele.madama@etalia.net">Daniele Madama</a>
 *
 */
public class LogglyAppender extends AppenderSkeleton {

	private static String LOGGLY_URL="https://logs-01.loggly.com/inputs/";

	/**
	 * The Loggly token
	 */
	private String token;

	/**
	 * A tags for Loggly
	 */
	private String tags;

	public LogglyAppender() {
		PatternLayout l = new PatternLayout("%d{yyMMdd.HHmmss,SSS} %t %C{1}.%M %p: %m%n");
		setLayout(l);
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent event) {
		if (this.token == null || this.token.trim().isEmpty()) {
			String t = System.getProperty("loggly.token");
			if (t != null) {
				setToken(t);
			} else {
				return;
			}
		}
		String _url = LOGGLY_URL + this.token;
		try {
			URL url = new URL(_url);
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setDoOutput(true);
			urlConn.setRequestMethod("POST");
			urlConn.setRequestProperty("content-type", "text/plain");
			urlConn.setRequestProperty("X-LOGGLY-TAG", this.tags);
			urlConn.getOutputStream().write(this.layout.format(event).getBytes());
			if (event.getThrowableStrRep() != null) {
				for (String s : event.getThrowableStrRep()) {
					urlConn.getOutputStream().write(s.getBytes());
				}
			}
			urlConn.getOutputStream().flush();
			urlConn.getOutputStream().close();
			if (urlConn.getResponseCode() != 200) {
				System.err.println("Something was wrong ("+urlConn.getResponseCode()+") with message: " + event.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
