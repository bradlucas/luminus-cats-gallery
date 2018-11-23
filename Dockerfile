FROM java:8-alpine

COPY target/uberjar/luminus-cats-gallery.jar /luminus-cats-gallery/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/luminus-cats-gallery/app.jar"]
