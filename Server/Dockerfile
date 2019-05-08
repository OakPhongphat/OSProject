FROM java
WORKDIR /Server
COPY . /Server
RUN javac TCPConcurrentServer.java && \javac EchoThread.java
EXPOSE 6789
CMD ["java","TCPConcurrentServer"]
