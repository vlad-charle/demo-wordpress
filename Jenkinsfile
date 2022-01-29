pipeline {

  agent any

  stages {

    stage('Checkout source') {
      steps{
        script {
          checkout scm
        }
      }
    }

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