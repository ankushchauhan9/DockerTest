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

    }

    post{
        always{
            archiveArtifacts artifacts: 'build/libs/TestingDocker-0.0.1-SNAPSHOT.jar'
        }
    }

}