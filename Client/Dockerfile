FROM java
WORKDIR /hangmanclient
COPY . /hangmanclient
RUN javac HangmanApplication.java && \javac Hangman.java
EXPOSE 6789
CMD ["java","HangmanApplication"]
