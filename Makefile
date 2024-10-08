define SERVICE_CREATE
[Unit]
Description=Modem Zxyel C3000Z Scheduler
After=syslog.target

[Service]
User=root
ExecStart=/var/modem-zyxel-c3000z-scheduler/modem-zyxel-c3000z-scheduler.jar
SuccessExitStatus=143
TimeoutStopSec=10
Restart=on-failure
RestartSec=5

[Install]
WantedBy=multi-user.target
endef
export SERVICE_CREATE

publish:
	mvn clean install
	mkdir -p /var/modem-zyxel-c3000z-scheduler
	cp target/modem-zyxel-c3000z-scheduler-0.0.1-SNAPSHOT.jar /var/modem-zyxel-c3000z-scheduler/modem-zyxel-c3000z-scheduler.jar
	rm -f /etc/systemd/system/modem-zyxel-c3000z-scheduler.service/
	touch /etc/systemd/system/modem-zyxel-c3000z-scheduler.service
	echo "$$SERVICE_CREATE" > /etc/systemd/system/modem-zyxel-c3000z-scheduler.service
	systemctl daemon-reload
	systemctl enable modem-zyxel-c3000z-scheduler
	systemctl restart modem-zyxel-c3000z-scheduler
	systemctl status modem-zyxel-c3000z-scheduler