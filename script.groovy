def buildImage() {
    // Build Docker image and tag it with appropriate naming
    dockerImage = docker.build("vladsanyuk/ssdevopscc:custom-wordpress")
}

def pushImage() {
    // Push it to specified registry with credentials saved in Jenkins
    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
        dockerImage.push()
    }
}

return this