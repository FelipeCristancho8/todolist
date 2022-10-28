pipeline {
    agent any
    triggers {
        pollSCM('* * * * *')
    }

    stages {
        stage('Clean') {
            steps{
                echo "------------> Clean <------------"
                bat 'gradlew clean'
            }
        }

        stage('Build') {
            steps{
                echo "------------> Build <------------"
                bat 'gradlew build -x test'
            }
        }

        stage('Test') {
        echo "------------> Test <------------"
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    bat 'gradlew test'
                }
            }
        }

        stage('Jacoco') {
            steps{
                echo "------------> Jacoco <------------"
                bat 'gradlew jacocoTestReport'
            }
        }

        stage('Sonar') {
            steps{
                echo "------------> Sonar <------------"
                withSonarQubeEnv('SonarProyecto'){
                    bat 'gradlew sonarqube'
                }
           }
        }

    }
}