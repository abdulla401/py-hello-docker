pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Hello') {
            steps {
                echo 'Hello World'
            }
        }
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [
                        [name: '*/master']
                ], extensions   : [], userRemoteConfigs: [
                        [url: 'https://github.com/abdulla401/dmarc']
                ]])
            }
        }
        stage('Deploy') {
            steps {
                script {
                    docker.withRegistry(
                            'https://870920198456.dkr.ecr.eu-central-1.amazonaws.com',
                            'ecr:eu-central-1:aws.token') {
                        def myImage = docker.build('asyed-ecr-test-repo')
                        myImage.push('1')
                    }

                }
            }
        }

    }
}