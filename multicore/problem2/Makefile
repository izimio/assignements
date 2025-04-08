DOCKER_IMAGE_NAME := java-assignment

all: run

build:
	@docker build -t $(DOCKER_IMAGE_NAME) .

clean:
	sudo rm problem1/*.class
	sudo rm problem2/*.class

run:
	@docker run -it --rm -v "$(shell pwd):/usr/src/app" $(DOCKER_IMAGE_NAME)
