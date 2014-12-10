/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package otp.api;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class Clock
{

    private int interval;
    private Calendar calendar;

    public Clock()
    {
        interval = 30;
        initCalendar();
    }

    public Clock(int interval)
    {
        this.interval = interval;
        initCalendar();
    }

    public void initCalendar()
    {
        //同一時間時區為UTC
        calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
    }

    public int getIntervalSecond()
    {
        return interval;
    }

    public void setIntervalSecond(int second)
    {
        interval = second;
    }

    public long getCurrentInterval()
    {

        long currentTimeSeconds = calendar.getTimeInMillis() / 1000;
        return currentTimeSeconds / interval;
    }

    public long generateInterval(long timeInMillis)
    {
        long currentTimeSeconds = timeInMillis / 1000;
        return currentTimeSeconds / interval;
    }
}
