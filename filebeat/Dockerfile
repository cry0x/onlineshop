FROM docker.elastic.co/beats/filebeat:7.16.3
COPY --chown=root:filebeat filebeat.yml /usr/share/filebeat/filebeat.yml
RUN chmod a+rwx /usr/share/filebeat/filebeat.yml