# modem-zxyel-c3000z-scheduler

This application retrieves WiFi connect and disconnect URLs via mDNS published by the `modem-zyxel-c3000z` app. An embedded database stores schedules; a background job runs every minute and calls connect or disconnect when a matching schedule entry exists.

Default HTTP port: **28080** (see `application.properties`).

If you are running a Linux-based system, `sudo make publish` installs this app as a service.

Check logs with:

```bash
sudo journalctl -u modem-zyxel-c3000z-scheduler
```

## API

All endpoints are `GET` requests on `/schedule`. Responses are JSON unless noted.

Base URL examples use `http://localhost:28080`.

### View schedules

Returns all schedule entries (pretty-printed JSON).

```
GET /schedule?operation=view
```

Example:

```bash
curl 'http://localhost:28080/schedule?operation=view'
```

Each entry has an `id` with `day`, `hour`, `minute`, and a `connect` flag (`true` = connect internet, `false` = disconnect).

### Add or update a schedule

Creates or replaces the entry for the given day, hour, and minute.

```
GET /schedule?operation=update&day=<DAY>&hour=<HOUR>&minute=<MINUTE>&connect=<true|false>
```

| Parameter | Required | Description |
|-----------|----------|-------------|
| `day` | yes | Day of week: `MONDAY`, `TUESDAY`, `WEDNESDAY`, `THURSDAY`, `FRIDAY`, `SATURDAY`, `SUNDAY` |
| `hour` | yes | Hour of day (0–23) |
| `minute` | yes | Minute (0–59) |
| `connect` | yes | `true` to connect, `false` to disconnect at that time |

Example (disconnect WiFi Monday at 22:30):

```bash
curl 'http://localhost:28080/schedule?operation=update&day=MONDAY&hour=22&minute=30&connect=false'
```

### Delete a schedule

Removes the entry for the given day, hour, and minute.

```
GET /schedule?operation=delete&day=<DAY>&hour=<HOUR>&minute=<MINUTE>
```

Example:

```bash
curl 'http://localhost:28080/schedule?operation=delete&day=MONDAY&hour=22&minute=30'
```

Success response:

```json
{"message":"Deleted!"}
```

### Pause scheduled connect/disconnect

Stops the scheduler from calling connect/disconnect URLs. Schedules remain in the database; they are not executed until resumed. Pause state is in-memory and resets when the application restarts.

```
GET /schedule?operation=pause
```

Example:

```bash
curl 'http://localhost:28080/schedule?operation=pause'
```

Response:

```json
{"paused":true,"message":"Scheduled connect/disconnect paused"}
```

### Resume scheduled connect/disconnect

Re-enables execution of schedule entries.

```
GET /schedule?operation=resume
```

Example:

```bash
curl 'http://localhost:28080/schedule?operation=resume'
```

Response:

```json
{"paused":false,"message":"Scheduled connect/disconnect resumed"}
```

### Scheduler pause status

Returns whether the scheduler is currently paused.

```
GET /schedule?operation=status
```

Example:

```bash
curl 'http://localhost:28080/schedule?operation=status'
```

Response:

```json
{"paused":false}
```

## Other URLs

| URL | Description |
|-----|-------------|
| `/h2-console` | H2 database console (enabled in config) |

This service also advertises API links via mDNS (`_http._tcp.local.`, service name `modem-zyxel-c3000z-scheduler`).

## Error responses

Missing parameters on `update` or `delete`:

```json
{"message":"Values day, hour, minute and connect are required"}
```

```json
{"message":"Values day, hour and minute are required"}
```

Unknown `operation`:

```json
{"message":"Operation not supported"}
```
