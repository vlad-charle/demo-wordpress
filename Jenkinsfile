pipeline {

  agent any

  stages {

    stage('Build image') {
      steps{
        script {
          dockerImage = docker.build("vladsanyuk/ssdevopscc:custom-wordpress")
        }
      }
    }

    stage('Push Image') {
      steps{
        script {
          docker.withRegistry('https://registry.hub.docker.com', 'docker-hub') {
            dockerImage.push()
          }
        }
      }
    }

  }
}