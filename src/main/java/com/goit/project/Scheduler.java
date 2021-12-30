package com.goit.project;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.goit.project.controller.Bot;
import com.goit.project.controller.SendMessageService;
import lombok.SneakyThrows;


public class Scheduler implements Runnable{

    Bot bot;
    public Scheduler(Bot bot){
        this.bot = bot;
    }


    @SneakyThrows
    @Override
    public void run() {

        //System.out.println не комментировать, через них в консоли видно,
        // что тред запустился и работает даже без оповещений пользователям

        System.out.println("Scheduler started");
        Calendar calendar = new GregorianCalendar();

        // при первом запуске проверяет, в рабочее ли время запущено, и если нет, спит до 9 часов утра
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour <9 || hour > 18) {
            if(hour>=18) calendar.add(Calendar.DAY_OF_MONTH,1);
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date1 = calendar.getTime();
            long delay = date1.getTime() - System.currentTimeMillis()+1000;
            Thread.sleep(delay);
        }

        UserService userStorage = UserService.getInstance();

            do {
                System.out.println("Scheduler running");
                calendar = new GregorianCalendar();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                // получает список пользователей для текущего часа:
                List<Integer> people = userStorage.getUsersIdWithPresentScheduler(hour);
                SendMessageService a = new SendMessageService();
                for (Integer p : people) {
                    // рассылает сообщения пользователям, у которых стоит оповещение в этот час
                    // пользуясь встроенным методом бота execute
                    System.out.println("Scheduler for userId = " + p);
                    bot.execute(a.getInfoForScheduler(p));
                }
                // после окончания рассылки опять проверяет текущее время, чтобы усыпить до следующего рабочего часа
                if(hour>=18) calendar.add(Calendar.DAY_OF_MONTH,1);
                calendar.set(Calendar.HOUR_OF_DAY,(hour < 8?9:(hour < 18?hour+1:9)));
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date curDate = calendar.getTime();
                long sleepTime = curDate.getTime() - System.currentTimeMillis();
                System.out.println("Scheduler sleeps for "+sleepTime+" milliseconds");
                Thread.sleep(sleepTime);
            }while (true);

    }

    }
