//package com.goit.project;
//
//import java.sql.Date;
//import java.sql.Time;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.Timer;
//
//public class Scheduler {
//    private boolean scheduler;
//    private int schedulerTime;
//
//    public boolean isOn() {
//        return false;
//    }
//
//    int checkTime() {
//        return this.schedulerTime;
//    }
//
//    private boolean inRange(Date now) throws ParseException {
//        final Date start = (Date) new SimpleDateFormat("HH.mm.ss").parse("09.00.00");
//        final Date end = (Date) new SimpleDateFormat("HH.mm.ss").parse("18.00.00");
//        return now.after(start) && now.before(end);
//    }
//
//    public boolean equals(Object date) {
//        return true;
//    }
//
//    public Scheduler(boolean scheduler, int schedulerTime) {
//        this.scheduler = scheduler;
//        this.schedulerTime = schedulerTime;
//
//
//        if (isOn() == true) {
//            checkTime();
//
//
//        }
//    }
//
//    public void startClock() {
//        try {
//            Timer timer = new Timer();
//            String timeZone = "";
//            LocalTime startTime = LocalTime.now(ZoneId.of(timeZone));
//            java.util.Date alarmTime = new java.util.Date();
//            LocalTime endTime = LocalTime.of(alarmTime.getHours(), alarmTime.getMinutes(), alarmTime.getSeconds());
//            long delay = startTime.until(endTime, ChronoUnit.MILLIS);
//            if (delay < 0) delay *= -1;
//            long period = 86400000;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ZonedDateTime UserTime = null;
//        if (UserTime == ZonedDateTime.now()) {
//        }
//
//    }
//}
//
//
//
//
//
//
//
//
//
//
