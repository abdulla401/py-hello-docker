pipeline {
    agent any
    environment {
        AWS_ACCOUNT_ID="870920198456"
        AWS_DEFAULT_REGION="us-east-2"
        IMAGE_REPO_NAME="dmarc_repo"
        IMAGE_TAG="latest"
        REPOSITORY_URI = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}"
        AWS_REGION = 'us-east-2'
        GITCOMMIT = "${env.GIT_COMMIT}"
        POPESA = credentials('my.aws.credentials')


    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Echo') {
            steps {
                sh '''
                    echo 'hello world added jenkins file edit here good way to develop'
                    aws --version
                    echo $AWS_REGION
                    aws ec2 describe-instances
                   '''
            }


        }
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Logging into AWS ECR') {
            steps {
                script {
                    sh "aws ecr get-login-password --region ${AWS_DEFAULT_REGION} | docker login --username AWS --password-stdin ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com"
                }

            }
        }

        stage('Building image') {
            steps{
                script {
                    dockerImage = docker.build "${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }

        stage('Pushing to ECR') {
            steps{
                script {
                    sh "docker tag ${IMAGE_REPO_NAME}:${IMAGE_TAG} ${REPOSITORY_URI}:$IMAGE_TAG"
                    sh "docker push ${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_DEFAULT_REGION}.amazonaws.com/${IMAGE_REPO_NAME}:${IMAGE_TAG}"
                }
            }
        }

    }
}
