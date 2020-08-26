package com.alink.documentmanagement.calendar;

import com.alink.documentmanagement.models.Calendar;

import java.util.List;

public class CalendarInterface {
    interface ICalendarView {
        void onUpdateList(List<Calendar> listCalendar);

        void onGetFileUrl(String url);

        void onGetFileFailed(String msg);
    }

    interface ICalendarFunction {
        void getListCalendar();

        void getFileUrl(String fileType, String fileId);
    }
}
