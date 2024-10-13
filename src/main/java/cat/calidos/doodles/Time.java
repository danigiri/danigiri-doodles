package cat.calidos.doodles;


public class Time {


/*
 * 
 * function which formats a duration, given as a number of seconds, in a human-friendly way. - For
 * seconds = 62, your function should return "1 minute and 2 seconds" - For seconds = 3662, your
 * function should return "1 hour, 1 minute and 2 seconds"
 * 
 * divide by the number of seconds a year and get remainder with that, divide by number of seconds
 * in a day, etc.
 * 
 * 
 */

public static String formatDuration(int seconds) { // 62
	if (seconds == 0) {
		return "now";
	}
	var secondsInMin = 60;
	var secondsInHour = 60 * secondsInMin;
	var secondsInDay = 24 * secondsInHour;
	var secondsInYear = 365 * secondsInDay;

	var b = new StringBuffer();
	var years = seconds / secondsInYear;
	var days = (seconds - (years * secondsInYear)) / secondsInDay;
	var hours = (seconds - (years * secondsInYear + days * secondsInDay)) / secondsInHour;
	var mins = (seconds - (years * secondsInYear + days * secondsInDay + hours * secondsInHour)) / secondsInMin; // 1
	var secs = seconds - (years * secondsInYear + days * secondsInDay + hours * secondsInHour + mins * secondsInMin); // 2

    int remaining = (years>0 ? 1 : 0)+(days>0 ? 1 : 0)+(hours>0 ? 1 : 0)+(mins>0 ? 1 : 0)+(secs>0 ? 1 : 0);
	remaining = timeAmountToString(years, remaining, "year", b);
	remaining = timeAmountToString(days, remaining, "day", b);
	remaining = timeAmountToString(hours, remaining, "hour", b);
	remaining = timeAmountToString(mins, remaining, "minute", b);
	timeAmountToString(secs, 0, "second", b);

	return b.toString();
}


private static int timeAmountToString(int amount, int remaining, String unit, StringBuffer b) {
	if (amount > 0) {
		remaining--;
		b.append(amount);
		b.append(" " + unit);
		if (amount > 1) {
			b.append('s');
		}
		if (remaining == 1) {
			b.append(" and ");
		} else if (remaining > 1) {
			b.append(", ");
		}
	}
	return remaining;
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
