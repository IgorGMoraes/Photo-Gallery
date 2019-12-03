# Photo-Gallery
#### A platform for photographers to share their work

Travelers Group is a worldwide organization where professionals and non-professionals photographers and artists (all of whom are called publishers) share their work to the internet, to a website. For each photo and album page, there will be ads of Travelers Group partners being displayed. Those ads are monetized based on the number of visualizations, and a percentage of this revenue will be shared with publishers proportional to the visualization of their materials.

## Usage

-  Clone the project
```
git clone https://github.com/IgorGMoraes/Photo-Gallery.git
```
<br>

- Install <a href="https://docs.docker.com/install/" target="_blank">Docker</a>

<br>

- Pull MySQL image to Docker
```
docker pull mysql
```

<br>

- Create network for the project
```
docker network create photogallery
```

<br>

- Inside Photo-Gallery directory create .jar file using maven
```
mvn package -DskipTests
```

<br>

- Build the docker image for the project
```
docker image build -t photogallery .
```

<br>

-  Run MySQL database as a container
```
docker container run --network photogallery --name mysqldb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=photogallery -d mysql:8
```

<br>

-  Check if MySQL container is ready
```
docker container logs -f mysqldb
```

<br>

-  Run the application as a container
```
docker container run --network photogallery --name photogallery-app -p 8080:8080 -d photogallery
```

<br>

-  To access the application go to:
<a href="https://localhost:8080" target="_blank">localhost:8080</a>
