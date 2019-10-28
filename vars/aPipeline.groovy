def call() {
    pipeline {
        agent any

        parameters {
            string(name: 'testParameter', defaultValue: 'test')
            booleanParam(name: 'testBoolParameter', defaultValue: true)
        }

        environment {
            BRANCH_NAME = "master"
            TEST_CRED_ENV_VAR = credentials("MY_CREDS")
        }

        stages {
            stage('Clone') {
                steps {
                    checkout scm
                }
            }
            stage('Build') {
                steps {
                    sh 'gradle build'
                }
            }
            stage('Test') {
                steps {
                    sh 'gradle test'
                }
            }
        }

        post {
            always {
                echo 'I run test pipeline'
            }
            success {
                echo 'SUCCESS'
            }
            failure {
                echo 'FAIL'
            }
        }
    }
}