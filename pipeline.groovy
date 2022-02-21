pipeline{

    agent any

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
                sh 'docker build -f Dockerfile -t dock-spring .'
            }
        }

        stage('Publish docker image'){
            steps{
                sh 'docker run -p 9393:9191 dock-spring'
            }
        }

    }

    post{
        always{
            archiveArtifacts artifacts: 'build/libs/TestingDocker-0.0.1-SNAPSHOT.jar'
        }
    }

}