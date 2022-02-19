# demo-wordpress-app

## Project description

This repo contains Wordpress application deployment for my demo project at SoftServe DevOps Crash Course 2021. 

## Project pre-requisites

Jenkins with installed Docker, kubectl, Helm, AWS CLI, AWS Authenticator, Terraform. I used latest Jenkins Docker image and installed all required packages: https://hub.docker.com/r/jenkins/jenkins

SonarQube. I use standard container installation with docker-compose: https://docs.sonarqube.org/latest/setup/install-server/

Rancher. I use standard test-purpose container installation: https://rancher.com/docs/rancher/v2.5/en/installation/other-installation-methods/single-node-docker/

Slack: https://slack.com

## Project structure

k8s folder contains manifests files, that is used by Jenkins to deploy the app.

Dockerfile custom Wordpress docker image build on latest version.

script.groovy is a Groovy script, that is further used in the pipeline.

sonar-project.properties is config file for SonarQube project for this application.

Jenkinsfile contains pipeline with a following stages:
1. Checkout GitHub
2. Load side Groovy script
3. Check the code with SonarQube
4. Build Docker image
5. Push image to the registry
6. Deploy application to the cluster
7. Send notification to respective Slack channel

The pipeline is triggered automatically by code change, via webhook from GitHub to Jenkins.

code change test 2
