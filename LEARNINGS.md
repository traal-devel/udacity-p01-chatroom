


**Package the app**
```bash
mvn clean package -DskipTests
```

## Install on linux server

```bash
root@www:/etc/init.d# ln -s /usr/local/programs/udacity/chatroom-starter-0.0.2-SNAPSHOT.jar uda-chatroom
root@www:/etc/init.d# update-rc.d uda-chatroom defaults
root@www:/etc/init.d# service uda-chatroom
Usage: /etc/init.d/uda-chatroom {start|stop|force-stop|restart|force-reload|status|run}
root@www:/etc/init.d# service uda-chatroom start
root@www:/etc/init.d# netstat -tulpen | grep -i 8080
tcp        0      0 0.0.0.0:8080            0.0.0.0:*               LISTEN      1000       1051545    16182/java 
```

## Sources

- https://www.oracle.com/technical-resources/articles/java/jsr356.html
- http://rafaelhz.github.io/testing-websockets/
- https://github.com/cassiomolin/chat-websockets
- https://o7planning.org/de/10719/erstellen-sie-eine-einfache-chat-anwendung-mit-spring-boot-und-websocket
- https://www.youtube.com/watch?v=zySNX9_bbr8
- https://www.seleniumhq.org/selenium-ide/docs/en/introduction/code-export/


## Google App-Engine

:TODO:
==> Source [/home/traal-devel/.cache/google-cloud-tools-java/managed-cloud-sdk/LATEST/google-cloud-sdk/completion.bash.inc] in your profile to enable shell command completion for gcloud.
==> Source [/home/traal-devel/.cache/google-cloud-tools-java/managed-cloud-sdk/LATEST/google-cloud-sdk/path.bash.inc] in your profile to add the Google Cloud SDK command line tools to your $PATH.
