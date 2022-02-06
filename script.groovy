def buildImage() {
    dockerImage = docker.build("vladsanyuk/ssdevopscc:custom-wordpress")
}

def pushImage() {
    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
        dockerImage.push()
    }
}

return this