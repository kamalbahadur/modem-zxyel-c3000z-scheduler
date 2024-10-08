# modem-zxyel-c3000z-scheduler

This application retrieved wifi connect and disconnect URLs via mDNS published by `modem-zxyel-c3000z` app. 
An embedded database is used to store the schedules. Endpoints are provided to view/update the schedules.

If you are running linux based system, `sudo make publish` will install this app as a service.

You may check the logs by running `sudo journalctl -u modem-zyxel-c3000z-scheduler`