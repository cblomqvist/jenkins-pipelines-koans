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
                    runGradle('command': 'build', 'defaultGradleArgs': 'arg1')
                }
            }
            stage('Test') {
                steps {
                    runGradle('command': 'test', 'defaultGradleArgs': 'arg1')
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