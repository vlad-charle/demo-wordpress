def gv

pipeline {
  environment {
          // Use AWS credentials saved in Jenkins as secret text
          AWS_ACCESS_KEY_ID     = credentials('AWS_ACCESS_KEY_ID')
          AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')
      }

  agent any

  stages {
    stage('Load Groovy script') {
      // Load external Groovy script into the pipeline
      steps{
        script {
          gv = load "script.groovy"
        }
      }
    }
    stage('SonarQube') {
      // Chose SonarQube scanner to be used 
      environment {
        scannerHome = tool 'SonarQubeScanner'
      }
      steps {
        // Check code
        withSonarQubeEnv('sonarqube') {
          sh "${scannerHome}/bin/sonar-scanner"
        }

        // Wait for QualityGate results, SonarQube will send webhook to Jenkins, if it's successful and will continue pipeline execution
        timeout(time: 10, unit: 'MINUTES') {
          waitForQualityGate abortPipeline: true
        }
      }
    }
    stage('Build image') {
      // Build Docker image
      steps{
        script {
          gv.buildImage()
        }
      }
    }
    stage('Push Image') {
      // Push image to Docker Registry
      steps{
        script {
          gv.pushImage()
        }
      }
    }
    stage('Deploy App') {
      // Deploy app to AWS EKS, authentication is done with kubeconfig, that is stored as secret file credential in Jenkins
      steps {
          withCredentials([file(credentialsId: 'mykubeconfig', variable: 'KUBECONFIG')]) {
            sh 'kubectl apply -f /var/jenkins_home/secret.yaml'
            sh 'kubectl apply -f k8s'
        }
      }
    }
  }
  post {
    // Post message to Slack, if build is successful, message will be highlighted in green
    success {
        slackSend color: 'good', message: "Build succesful: ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
    }
    // Post message to Slack, if build is failed, message will be highlighted in red
    failure {
        slackSend color: 'danger', message: "Build failed: ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
    }
  }
}