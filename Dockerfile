FROM openjdk:8
COPY target/photogallery.jar photogallery.jar
CMD ["java","-jar","photogallery.jar"]