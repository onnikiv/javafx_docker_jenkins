pipeline {
    agent any
    tools{
        maven 'Maven 3.9.9'

    }

    environment {
        PATH = "C:\\Program Files\\Docker\\Docker\\resources\\bin;${env.PATH}"
        DOCKERHUB_CREDENTIALS_ID = 'Docker_Hub'
        DOCKER_IMAGE = 'onnikiv/temperature'
        DOCKER_TAG = 'latest'
    }

    stages {
        stage('Setup Maven') {
            steps {
                script {
                    def mvnHome = tool name: 'Maven 3.9.9', type: 'maven'
                    env.PATH = "${mvnHome}/bin:${env.PATH}"
                }
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/onnikiv/javafx_docker_jenkins'
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        sh 'mvn clean package -DskipTests'
                    } else {
                        bat 'mvn clean package -DskipTests'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo 'Tests'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    if (isUnix()) {
                        sh "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    } else {
                        bat "docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} ."
                    }
                }
            }
        }

        stage('Push Docker Image to Docker Hub') {
            steps {
                script {
                    echo "Attempting to use credentials: ${env.DOCKERHUB_CREDENTIALS_ID}"
                    withCredentials([usernamePassword(credentialsId: 'Docker_Hub', 
                                                    passwordVariable: 'DOCKER_PASSWORD', 
                                                    usernameVariable: 'DOCKER_USERNAME')]) {
                        echo "Credentials loaded successfully"
                        if (isUnix()) {
                            sh 'echo "Logging into Docker Hub..."'
                            sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'
                            sh "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                            sh 'docker logout'
                        } else {
                            bat 'echo "Logging into Docker Hub..."'
                            bat 'echo %DOCKER_PASSWORD% | docker login -u %DOCKER_USERNAME% --password-stdin'
                            bat "docker push ${DOCKER_IMAGE}:${DOCKER_TAG}"
                            bat 'docker logout'
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
    }
}