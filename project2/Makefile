DOCKER_IMAGE_NAME := java-assignment

all: run

build:
	@docker build -t $(DOCKER_IMAGE_NAME) .

clean:
	rm -f *.class

run:
	@docker run -it --rm -v "$(shell pwd):/usr/src/app" $(DOCKER_IMAGE_NAME)

	
