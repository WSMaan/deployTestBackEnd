pipeline {
    agent any
    environment {
        AWS_ACCOUNT_ID = "583187964056"
        AWS_REGION = "us-east-2"
        ECR_REPOSITORY_NAME = "examninja"
        BACKEND_DIR = "deployTestBackEnd"
        ECR_REGISTRY = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com"
        NODE_ENV = "production"
    }
    stages {
        stage('Setup AWS Credentials') {
            steps {
                withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws_key']]) {
                    echo 'AWS Credentials configured'
                }
            }
        }
        stage('Clone Backend Repository') {
            steps {
                dir('backend') {
                    git branch: 'master', url: 'https://github.com/WSMaan/deployTestBackEnd.git', credentialsId: 'GIT_HUB'
                }
            }
        }
        stage('Build Backend') {
            steps {
                dir('backend') {
                    sh 'mvn clean install'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                dir('backend') {
                    sh "docker build -t ${ECR_REGISTRY}/${ECR_REPOSITORY_NAME}:backend ."
                }
            }
        }
        stage('Push Docker Image to ECR') {
            steps {
                script {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws_key']]) {
                        sh '''
                        aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REGISTRY}
                        docker push ${ECR_REGISTRY}/${ECR_REPOSITORY_NAME}:backend
                        '''
                    }
                }
            }
        }
        stage('Deploy to EKS') {
            steps {
                script {
                    withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', credentialsId: 'aws_key']]) {
                        sh '''
                        export AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
                        export AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
                        export AWS_REGION=us-east-2
                        aws eks --region $AWS_REGION update-kubeconfig --name examninja
                        '''
                        dir('deployTestBackEnd') {
                            git branch: 'master', url: 'https://github.com/WSMaan/deployTestBackEnd.git', credentialsId: 'GIT_HUB'
                            sh '''
                            kubectl apply -f k8s/backend-deployment.yaml
                            '''
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            cleanWs()
        }
        failure {
            script {
                echo "Pipeline failed in stage: ${env.STAGE_NAME}"
            }
        }
        success {
            echo 'Pipeline succeeded!'
        }
    }
}
