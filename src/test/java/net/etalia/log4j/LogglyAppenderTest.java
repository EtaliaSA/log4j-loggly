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

import org.apache.log4j.Logger;
import org.junit.Test;

public class LogglyAppenderTest {

	@Test
	public void simpleLog() throws Exception {
		Logger log =  Logger.getLogger(LogglyAppenderTest.class);
		log.debug("Trying to send this to loggly...");
	}

	@Test
	public void error() throws Exception {
		Logger log =  Logger.getLogger(LogglyAppenderTest.class);
		try {
			throw new IllegalStateException("Just for test");
		} catch (Exception e) {
			log.error("Catched!", e);
		}
	}

}
