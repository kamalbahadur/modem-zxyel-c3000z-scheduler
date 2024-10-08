package com.kg.modem_zyxel_c3000z.scheduled;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kg.modem_zyxel_c3000z.dao.ScheduleDao;
import com.kg.modem_zyxel_c3000z.entity.Schedule;
import com.kg.modem_zyxel_c3000z.entity.ScheduleId;
import com.kg.modem_zyxel_c3000z.util.WifiHandler;

@Component
public class Monitor {

    @Autowired ScheduleDao scheduleDao;
    @Autowired WifiHandler wifiHandler;

    @Scheduled(cron = "0 * * * * *")
    public void run() {
        LocalDateTime now = LocalDateTime.now();
        String day = now.getDayOfWeek().toString();
        Integer hour = now.getHour();
        Integer minute = now.getMinute();

        Optional<Schedule> possibleSchedule = scheduleDao.findById(new ScheduleId(day, hour, minute));

        if (possibleSchedule.isPresent()) {
            Schedule schedule = possibleSchedule.get();
            if (schedule.getConnect()) {
                wifiHandler.connect();
            } else {
                wifiHandler.disconnect();
            }
        }
    }
}
