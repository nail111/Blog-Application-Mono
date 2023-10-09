# Используем базовый образ OpenJDK для Java
FROM openjdk:17
# label
LABEL mentainer="nailtagiyev1999@gmail.com"
# Установка рабочей директории внутри контейнера
WORKDIR /app
# Копирование JAR-файла приложения в контейнер
COPY target/blog-application-1.0-SNAPSHOT.jar .
# Указываем порт, на котором будет работать приложение
EXPOSE 8080
# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "blog-application-1.0-SNAPSHOT.jar"]