package cat.calidos.doodles;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimeTest {

@Test
public void formatDurationTest() {
	assertEquals("1 minute and 2 seconds", Time.formatDuration(62));
	assertEquals("now", Time.formatDuration(0));
	assertEquals("1 second", Time.formatDuration(1));
	assertEquals("2 minutes", Time.formatDuration(120));
	assertEquals("1 hour", Time.formatDuration(3600));
	assertEquals("1 hour, 1 minute and 2 seconds", Time.formatDuration(3662));
}

}

/**
 * Copyright 2024 Daniel Giribet <dani - calidos.cat>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
