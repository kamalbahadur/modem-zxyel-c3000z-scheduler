package com.kg.modem_zyxel_c3000z.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kg.modem_zyxel_c3000z.dao.ScheduleDao;
import com.kg.modem_zyxel_c3000z.entity.Schedule;
import com.kg.modem_zyxel_c3000z.entity.ScheduleId;

@RestController
public class ModemSchedulerController {

    @Autowired ScheduleDao scheduleDao;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(value="/schedule", produces={"application/json","application/text"})
    public String schedule(@RequestParam(defaultValue = "show") String operation,
                           @RequestParam(required = false) String day,
                           @RequestParam(required = false) Integer hour,
                           @RequestParam(required = false) Integer minute,
                           @RequestParam(required = false) Boolean connect) {

        if ("view".equals(operation)) {
            List<Schedule> schedules = scheduleDao.findAll();
            try {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(schedules);
            } catch (JsonProcessingException e) {
                return e.getMessage();
            }
        } else if ("update".equals(operation)) {
            if (day == null || hour == null || minute == null || connect == null) {
                return "{\"message\":\"Values day, hour, minute and connect are required\"}";
            }

            ScheduleId scheduleId = new ScheduleId();
            scheduleId.setDay(day);
            scheduleId.setHour(hour);
            scheduleId.setMinute(minute);
            Schedule schedule = new Schedule();
            schedule.setId(scheduleId);
            schedule.setConnect(connect);
            try {
                return objectMapper.writeValueAsString(scheduleDao.save(schedule));
            } catch (JsonProcessingException e) {
                return e.getMessage();
            }
        } else if ("delete".equals(operation)) {
            if (day == null || hour == null || minute == null) {
                return "{\"message\":\"Values day, hour and minute are required\"}";
            }

            ScheduleId scheduleId = new ScheduleId();
            scheduleId.setDay(day);
            scheduleId.setHour(hour);
            scheduleId.setMinute(minute);
            try {
                scheduleDao.deleteById(scheduleId);
            } catch (Exception e) {
                return "{\"message\":\""+e.getMessage()+"\"}";
            }

            return "{\"message\":\"Deleted!\"}";
        } else {
            return "{\"message\":\"Operation not supported\"}";
        }
    }
}
