pipeline{

    agent any

    environment{
        DOCKERHUB_CREDENTIALS=credentials('dockerhub')
    }

    triggers {
        pollSCM('* * * * *')
    }

    stages{

        stage('GitHub'){
            steps{
                git url:'https://github.com/ankushchauhan9/DockerTest.git'
            }
        }
        stage('Build Gradle'){
            steps{
                bat 'gradlew clean build'
            }
        }

        stage('Docker build'){
            steps{
                bat 'docker build -t 111docker222/jenkinspipeline:latest .'
            }
        }

        stage('Login to Docker Hub') {
            steps{
                bat 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
            }
        }

        stage('Docker push'){
            steps{
                bat 'docker push 111docker222/jenkinspipeline:latest'
            }
        }

    }

    post{
        always{
            archiveArtifacts artifacts: 'build/libs/TestingDocker-0.0.1-SNAPSHOT.jar'
        }
    }

}