def call() {
    pipeline {
        agent {
            label 'demo'
        }
        stages {
            stage('first stage') {
                steps {
                    echo 'My first step'
                }
            }
            stage('build') {
                steps {
                    sh 'gradle build'
                }
            }
            stage('test') {
                steps {
                    sh 'gradle test'
                }
            }
        }
        post {
            always {
                echo 'I run demo pipeline'
            }
            success {
                echo 'Yee'
            }
            failure {
                echo 'Boo'
            }
        }
    }
}